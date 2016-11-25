	package application;

import domain.ApplicationException;
import domain.Cliente;
import domain.Produto;
import domain.Venda;

/**
 * Handles sales' transactions. 
 * Each public method implements a transaction script.
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
	 * @throws ApplicationException
	 */
	public int novaVenda (int npc) throws ApplicationException {
		int idCliente = Cliente.INSTANCE.getClienteId(npc);
		return Venda.INSTANCE.novaVenda(idCliente);
	}

	
	/**
	 * Add a product to an open sale.
	 * 
	 * @param idVenda The sale's id.
	 * @param codProd The product's code.
	 * @param qty The amount to sell.
	 * @throws ApplicationException 
	 * @pre: qty > 0
	 */
	public void acrescentarProdutoAVenda (int idVenda, int codProd, double qty) throws ApplicationException {
		int idProduto = Produto.INSTANCE.getProdutoId(codProd);
		Venda.INSTANCE.acrescentarProdutoAVenda (idVenda, idProduto, qty);
	}
	
	
	/**
	 * Computes the discount amount for a sale (based on the discount type of the customer).
	 * 
	 * @param idVenda The sale's id
	 * @return The discount amount
	 * @throws ApplicationException 
	 */
	public double getValorDoDesconto (int idVenda) throws ApplicationException {
		return Venda.INSTANCE.getValorDoDesconto(idVenda);
	}
	
}
