package application.web.datapresentation;

import domain.interfaces.IVenda;

/**
 * Helper class to assist in the response of novo cliente.
 * This class is the response information expert.
 * 
 * @author fmartins
 *
 */
public class EfectuarVendaHelper extends Helper {

	private String npc;
	private IVenda venda;
		
	public String getNpc() {
		return npc;
	}
	
	public void setNpc(String npc) {
		this.npc = npc;
	}
		
	public void clearFields() {
		npc = "";
	}

	public void setVenda(IVenda novaVenda) {
		venda = novaVenda;		
	}
	
	public String getDesignacao() {
		return venda == null ? "" : venda.getCliente().getDesignacao();
	}
}
