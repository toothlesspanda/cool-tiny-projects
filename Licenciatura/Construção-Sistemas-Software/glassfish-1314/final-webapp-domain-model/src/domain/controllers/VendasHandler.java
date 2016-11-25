package domain.controllers;

import domain.ApplicationException;
import domain.CatalogoClientes;
import domain.CatalogoProdutos;
import domain.CatalogoVendas;
import domain.Cliente;
import domain.Produto;
import domain.Venda;
import domain.interfaces.IVenda;

/**
 * Handles client's transactions. 
 * Each public method implements a transaction script.
 * 
 * @author fmartins
 *
 */
public class VendasHandler {
	
	private CatalogoVendas catalogoVendas;
	private CatalogoClientes catalogoClientes;
	private CatalogoProdutos catalogoProdutos;
	
	public VendasHandler(CatalogoVendas catalogoVendas, CatalogoClientes catalogoClientes, 
							CatalogoProdutos catalogoProdutos) {
		this.catalogoVendas = catalogoVendas;
		this.catalogoClientes = catalogoClientes;
		this.catalogoProdutos = catalogoProdutos;
	}

	public Venda novaVenda (int npc) throws ApplicationException {
		Cliente cliente = catalogoClientes.getClienteNPC(npc);
		return catalogoVendas.novaVenda(cliente);
	}

	public void acrescentarProdutoAVenda (IVenda iVenda, int codProd, double qty) 
					throws ApplicationException {
		if (!(iVenda instanceof Venda))
			throw new ApplicationException("Este tipo de venda não é suportada!"); 
		Venda venda = (Venda) iVenda;
		
		if (!venda.isEmAberto())
			throw new ApplicationException("N�o se podem acrescentar artigos a uma venda fechada.");

		Produto produto = catalogoProdutos.getProduto(codProd);
			
		// se tem quantidade suficiente...
		if (produto.getQty() >= qty) {
			// adicionar o produto � venda, e actualiza o invent�rio!
			catalogoVendas.acrescentarProdutoAVenda(venda, produto, qty);
		} else
			throw new ApplicationException("O produto " + codProd + " tem quantidade ("  + 
							produto.getQty() + ") insuficiente para venda");
	}

	public double getValorDoDesconto (IVenda venda) throws ApplicationException {
		return venda.getValorDoDesconto();
	}
}
