package ui.helpers;

import java.util.LinkedList;
import java.util.List;



import domain.ApplicationException;
import domain.PlanoPagamento;


public class PlanosPagamentoHelper extends Helper{
	
	private int id;
	private List<PlanoPagamento> planos;
	private String dataInicio;
	private String dataFim;
	private int numeroPagamentos;
	private boolean prestacoesPagas;

	public PlanosPagamentoHelper() {
		 this.planos = new LinkedList<PlanoPagamento>();
	}
	
	public void clearFields () {
		  
		 }
	
	public void setPlanos(List<PlanoPagamento> planos) throws ApplicationException{
		this.planos=planos;
	}
	public List<PlanoPagamento> getPlanos(){
		return this.planos;
	}
	
	public int getID(){
		return this.id;
	}
	public void setID(int id){
		this.id = id;
	}


	public int getNumeroPagamentos() {
		return numeroPagamentos;
	}


	public void setNumeroPagamentos(int numeroPagamentos) {
		this.numeroPagamentos = numeroPagamentos;
	}


	public boolean isPrestacoesPagas() {
		return prestacoesPagas;
	}


	public void setPrestacoesPagas(boolean prestacoesPagas) {
		this.prestacoesPagas = prestacoesPagas;
	}


	public String getDataFim() {
		return this.dataFim;
	}


	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}


	public String getDataInicio() {
		return this.dataInicio;
	}


	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
}
