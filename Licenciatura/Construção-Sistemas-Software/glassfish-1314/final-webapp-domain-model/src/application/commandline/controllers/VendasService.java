package application.commandline.controllers;

import domain.ApplicationException;
import domain.controllers.VendasHandler;
import domain.interfaces.IVenda;

/**
 * Handles sales' transactions. 
 * Each public method implements a transaction script.	
 *	
 * @author fmartins
 *
 */
public class VendasService {

	private VendasHandler vendasHandler;
	private IVenda venda;

	public VendasService(VendasHandler vendasHandler) {
		this.vendasHandler = vendasHandler;
	}
	
	public void novaVenda (int npc) throws ApplicationException {
		venda = vendasHandler.novaVenda(npc);
	}

	public void acrescentarProdutoAVenda (int codProd, double qty) throws ApplicationException {
		vendasHandler.acrescentarProdutoAVenda(venda, codProd, qty);
	}
	
	public double getValorDoDesconto () throws ApplicationException {
		return venda.getValorDoDesconto();
	}
}
