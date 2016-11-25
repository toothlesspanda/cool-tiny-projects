package application;

import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import services.persistence.DataSource;
import services.persistence.PersistenceException;
import services.persistence.RecordNotFoundException;
import services.persistence.rowDataGateway.ClientesRowGateway;
import services.persistence.rowDataGateway.ConfiguracoesRowGateway;
import services.persistence.rowDataGateway.ProdutosRowGateway;
import services.persistence.rowDataGateway.ProdutosVendaRowGateway;
import services.persistence.rowDataGateway.VendasRowGateway;

/**
 * Handles sales' transactions. 
 * Each public method implements a transaction script.	
 *	
 * Chamadas de aten�‹o:
 * 1. Notem o controle de transa�›es no mŽtodo acrescentarProdutoAVenda. 
 * H‡ que garantir que foram actualizadas as duas tabelas: produtos (a exist�ncia do produto)
 * e a tabela de vendas (inserir mais um registo). Se aconteceu algum erro, 
 * a transac�‹o Ž desfeita. No caso de ter de se desfazer a transa�‹o (rollback) a
 * exce�‹o Ž reenviada para que n‹o se perca o motivo pelo qual o erro se deu.
 * Esta exce�‹o ser‡ apanhada no catch do n’vel acima deste (6 linhas abaixo) que
 * ir‡ embrulhar a exce�‹o noutra aplicacional que contŽm uma mensagem genŽrica
 * para o utilizador de auto-n’vel. Contudo, na causa da exce�‹o, est‡ registado
 * o erro de mais baixo n’vel.
 *	 
 * @author fmartins
 *
 */
public class VendasService {
	
	/**
	 * Starts a new sale for a given customer identified by its NPC
	 * 
	 * @param npc The NPC of the customer the sale belongs to
	 * @return The id of the new sale. This id is usefull for adding produts 
	 * to the sale and for computing the sale's total, discount, etc.
	 * @throws ApplicationException When the customer does not exist or 
	 * when an unexpected SQL error occurs.
	 */
	public int novaVenda (int npc) throws ApplicationException {
		
		try {
			// obter o id de cliente em fun�‹o do nœmero de pessoa colectiva
			ClientesRowGateway cliente = ClientesRowGateway.getClientePorNPC (npc);
			// criar a venda
			VendasRowGateway novaVenda = new VendasRowGateway();
			novaVenda.setIdCliente(cliente.getIdCliente());
			novaVenda.setData(new Date());
			novaVenda.insert();
			return novaVenda.getIdVenda();
		} catch (RecordNotFoundException e) {
			throw new ApplicationException("N‹o existe cliente com NPC " + npc);
		} catch (PersistenceException e) {
			throw new ApplicationException("Erro interno ao adicionar a venda", e);
		}
	}

	
	/**
	 * Add a product to an open sale.
	 * 
	 * @param idVenda The sale's id.
	 * @param codProd The product's code.
	 * @param qty The amount to sell.
	 * @throws ApplicationException When the sale is closed, the product code does not exist,
	 * there's not enough amount of product to be sold, or when same unexpected SQL error occurs. 
	 * @pre: qty > 0
	 */
	public void acrescentarProdutoAVenda (int idVenda, int codProd, double qty) throws ApplicationException {
		try {
			// verificar se a venda existe e est‡ em aberto
			VendasRowGateway venda = null;
			try {
				venda = VendasRowGateway.getVendaPorId(idVenda);
				if (venda.getEmAberto().equals("N"))
					throw new ApplicationException("Nao se podem acrescentar artigos a uma venda fechada.");
			} catch (RecordNotFoundException e) {
				throw new ApplicationException("A venda com id: " + idVenda + " nao existe.");
			}
			
			// obter o id do produto a partir do c—digo
			ProdutosRowGateway produto = null;
			try {
				produto = ProdutosRowGateway.getProdutoPorCodProd(codProd);
			} catch (RecordNotFoundException e) {
				throw new ApplicationException("O produto " + codProd + " n‹o foi encontrado");
			}
			
			// se tem quantidade suficiente...
			if (produto.getQty() >= qty) {
				try {
					// JDBC Begin transaction!
					DataSource.INSTANCE.beginTransaction();
					// diminuir valor ao inventario
					produto.setQty(produto.getQty() - qty);
					produto.actualizarValorInventario();
					// adicionar o produto ˆ venda 
					ProdutosVendaRowGateway produtoVenda = new ProdutosVendaRowGateway();
					produtoVenda.setIdProduto(produto.getIdProduto());
					produtoVenda.setIdVenda(venda.getIdVenda());
					produtoVenda.setQty(qty);
					produtoVenda.insert();
					// JDBC commit
					DataSource.INSTANCE.commit();
				} catch (PersistenceException e) {
					DataSource.INSTANCE.rollback();
					throw e;
				}
			} else
				throw new ApplicationException("O produto " + codProd + " tem quantidade ("  + 
							produto.getQty() + ") insuficiente para venda");
		} catch (PersistenceException e) {
			throw new ApplicationException("Erro interno ao vender o produto " + codProd, e);
		}		
	}
	
	
	/**
	 * Computes the discount amount for a sale (based on the discount type of the customer).
	 * 
	 * @param idVenda The sale's id
	 * @return The discount amount
	 * @throws ApplicationException When the sale does not exist or when an unexpected SQL
	 * exception occurs.
	 */
	public double getValorDoDesconto (int idVenda) throws ApplicationException {
		try {
			VendasRowGateway venda = null;
			// verificar se a venda existe
			try {
				venda = VendasRowGateway.getVendaPorId(idVenda);
				// se a venda est‡ fechado o valor do desconto j‡ foi apurado
				if (venda.getEmAberto().equals("N")) 
					return venda.getDesconto();
			} catch(RecordNotFoundException e) {
				throw new ApplicationException("A venda com id: " + idVenda + " n‹o existe.");		
			}
			
			// obter o cliente a partir da venda. Existe sempre, por causa da integridade referencial.
			ClientesRowGateway cliente = ClientesRowGateway.getClientePorId(venda.getIdCliente());
					
			// obter os produtos vendidos
			Set<ProdutosVendaRowGateway> produtosVenda = ProdutosVendaRowGateway.getProdutosDaVenda(idVenda);
			double desconto = 0;
		
			// calcular desconto 
			switch (cliente.getIdDesconto()) {
			case 1:
				desconto = 0;
			case 2:
				desconto = calcularPercentagemTotal (produtosVenda);
				break;
			case 3:
				desconto = calcularPercentagemProdutoElegivel (produtosVenda);
				break;
			}
			
			return desconto;
		} catch (PersistenceException e) { 
			throw new ApplicationException("Erro interno calcular desconta para a vender " + idVenda, e);			
		}
	}
	
	
	/**
	 * Computes the type 1 discount
	 * @param rs The result set with the sold products 
	 * @return The discount value
	 * @throws PersistenceException 
	 * @throws SQLException When some unexpected error occurs.
	 */
	private double calcularPercentagemTotal(Set<ProdutosVendaRowGateway> produtosVenda) 
			throws PersistenceException {
		double totalVenda = 0;
		for (ProdutosVendaRowGateway produtoVenda : produtosVenda) {
			// obter a informa�‹o do produto (existe sempre por causa da integridade referencial)
			ProdutosRowGateway produto = new ProdutosRowGateway().getProdutoPorId(produtoVenda.getIdProduto());
			totalVenda += produtoVenda.getQty() * produto.getValorUnitario();
		}
		
		// obter valores da configuracao
		// existe sempre, a n‹o ser que a aplica�‹o n‹o esteja bem configurada!!
		ConfiguracoesRowGateway config = ConfiguracoesRowGateway.getConfiguracoes();
			
		return totalVenda > config.getLimiteTotal() ? totalVenda * config.getPercentagemTotal() : 0;
	}
	

	/**
	 * Computes the type 2 discount
	 * @param produtosVenda The set with the sold products 
	 * @return The discount value
	 * @throws PersistenceException When some unexpected error occurs.
	 */
	private double calcularPercentagemProdutoElegivel(Set<ProdutosVendaRowGateway> produtosVenda) 
			throws PersistenceException {
		double totalElegivel = 0;		
		for (ProdutosVendaRowGateway produtoVenda : produtosVenda) {
			// obter a informa�‹o do produto (existe sempre por causa da integridade referencial)
			ProdutosRowGateway produto = new ProdutosRowGateway().getProdutoPorId(produtoVenda.getIdProduto());
			if (produto.getElegivelDesconto().equals("S"))
				totalElegivel += produtoVenda.getQty() * produto.getValorUnitario();
		}
		
		// obter valores da configuracao
		// existe sempre, a n‹o ser que a aplica�‹o n‹o esteja bem configurada!!
		ConfiguracoesRowGateway config = ConfiguracoesRowGateway.getConfiguracoes();

		return totalElegivel * config.getPercentagemElegivel();
	}
}
