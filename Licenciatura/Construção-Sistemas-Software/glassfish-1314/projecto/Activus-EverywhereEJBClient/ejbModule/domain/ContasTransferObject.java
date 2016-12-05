package domain;

import java.io.Serializable;
import java.util.Calendar;

public class ContasTransferObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idConta;
	
	private Calendar dataDeAbertura;
	
	private double taxa;
	
	private double valor;
	
	private BancosTransferObject banco;
	
	private GestoresTransferObject gestor;

	public int getIdConta() {
		return idConta;
	}

	public void setIdConta(int idConta) {
		this.idConta = idConta;
	}

	public Calendar getDataDeAbertura() {
		return dataDeAbertura;
	}

	public void setDataDeAbertura(Calendar dataDeAbertura) {
		this.dataDeAbertura = dataDeAbertura;
	}

	public double getTaxa() {
		return taxa;
	}

	public void setTaxa(double taxa) {
		this.taxa = taxa;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public BancosTransferObject getBanco() {
		return banco;
	}

	public void setBanco(BancosTransferObject banco) {
		this.banco = banco;
	}

	public GestoresTransferObject getGestor() {
		return gestor;
	}

	public void setGestor(GestoresTransferObject gestor) {
		this.gestor = gestor;
	}

}
