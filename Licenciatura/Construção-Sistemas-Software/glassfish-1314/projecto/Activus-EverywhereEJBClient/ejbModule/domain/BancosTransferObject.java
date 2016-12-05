package domain;

import java.io.Serializable;
import java.util.List;

public class BancosTransferObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int idBanco;
	
	private String sigla;
	
	private String designacao;
	
	private List<ContasTransferObject> listaContas;
	
	private List<GestoresTransferObject> listaGestor;
	
	public List<ContasTransferObject> getListaContas(){
		return this.listaContas;
	}
	public List<GestoresTransferObject> getListaGestor(){
		return this.listaGestor;
	}
	public void setListaContas(List<ContasTransferObject> listaContas){
		this.listaContas = listaContas;
	}
	
	public void setListaGestor(List<GestoresTransferObject> listaGestor){
		this.listaGestor = listaGestor;
	}
	
	public int getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(int idBanco) {
		this.idBanco = idBanco;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDesignacao() {
		return designacao;
	}

	public void setDesignacao(String designacao) {
		this.designacao = designacao;
	}
	

	
	

}
