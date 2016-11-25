package application.web.datapresentation;

import java.util.LinkedList;

import domain.ApplicationException;
import domain.controllers.DescontosHandler;
import domain.interfaces.IDesconto;

/**
 * Helper class to assist in the response of novo cliente.
 * This class is the response information expert.
 * 
 * @author fmartins
 *
 */
public class NovoClienteHelper extends Helper {

	private String designacao;
	private String npc;
	private String telefone;
	private String tipoDesconto;
	private DescontosHandler descontosHandler;
		
	public void setDescontosHandler(DescontosHandler descontosHandler) {
		this.descontosHandler = descontosHandler;
	}
	
	public void setDesignacao(String designacao) {
		this.designacao = designacao;	
	}
	
	public String getDesignacao() {
		return designacao;
	}
	
	public String getNpc() {
		return npc;
	}
	
	public void setNpc(String npc) {
		this.npc = npc;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setTipoDesconto(String tipoDesconto) {
		this.tipoDesconto = tipoDesconto;
	}

	public String getTipoDesconto() {
		return tipoDesconto;
	}
	
	public void clearFields() {
		designacao = npc = telefone = "";
		tipoDesconto = "1";
	}
	
	public Iterable<? extends IDesconto> getDescontos () {
		try {
			return descontosHandler.getDescontos();
		} catch (ApplicationException e) {
			return new LinkedList<IDesconto> ();		
		}
	}	
}
