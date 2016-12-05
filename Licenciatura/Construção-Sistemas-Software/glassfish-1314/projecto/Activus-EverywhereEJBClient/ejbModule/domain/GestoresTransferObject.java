package domain;

import java.io.Serializable;
import java.util.List;

public class GestoresTransferObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int idGestor;
	
	private String email;
	
	private String nome;
	
	private int telefone;
	
	private List<ContasTransferObject> contas;
	
	private BancosTransferObject banco;

	public int getIdGestor() {
		return idGestor;
	}

	public void setIdGestor(int idGestor) {
		this.idGestor = idGestor;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getTelefone() {
		return telefone;
	}

	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}

	public List<ContasTransferObject> getContas() {
		return contas;
	}

	public void setContas(List<ContasTransferObject> contas) {
		this.contas = contas;
	}

	public BancosTransferObject getBanco() {
		return banco;
	}

	public void setBanco(BancosTransferObject banco) {
		this.banco = banco;
	}
	

}
