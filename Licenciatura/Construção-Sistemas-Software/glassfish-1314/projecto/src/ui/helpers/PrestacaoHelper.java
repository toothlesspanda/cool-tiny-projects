package ui.helpers;

import java.util.LinkedList;
import java.util.List;

import domain.ApplicationException;
import domain.Prestacao;


public class PrestacaoHelper extends Helper{
	
	private int id;
	private List<Prestacao> prestacoes;
	private String dataLimite;
	private String dataPagamento;
	private double valorPrestacao;
	private boolean pagamento;

	public PrestacaoHelper(){
		 this.prestacoes = new LinkedList<Prestacao>();
	}
	
	public void clearFields () {
		  
	}
	
	public void setPrestacao(List<Prestacao> prestacoes) throws ApplicationException{
		this.prestacoes=prestacoes;
	}
	public List<Prestacao> getPrestacoes(){
		return this.prestacoes;
	}
	
	public int getID(){
		return this.id;
	}
	public void setID(int id){
		this.id = id;
	}


	public double getValorPrestacao() {
		return this.valorPrestacao;
	}


	public void setValorPrestacao(double valorPrestacao) {
		this.valorPrestacao = valorPrestacao;
	}


	public boolean isPagamento() {
		return pagamento;
	}


	public void setPagamento(boolean pagamento) {
		this.pagamento = pagamento;
	}


	public String getDataLimite() {
		return dataLimite;
	}


	public void setDataLimite(String dataLimite) {
		this.dataLimite = dataLimite;
	}


	public String getDataPagamento() {
		return this.dataPagamento;
	}


	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
}
