package domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class PlanoPagamentoTransferObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int idPlanoPagamento;
	
	private Calendar dataInicio;
	
	private Calendar dataFim;
	
	private List<PrestacaoTransferObject> prestacoes;
	
	private int numeroPagamentos;
	
	private boolean prestacoesPagas;

	public int getIdPlanoPagamento() {
		return idPlanoPagamento;
	}

	public void setIdPlanoPagamento(int idPlanoPagamento) {
		this.idPlanoPagamento = idPlanoPagamento;
	}

	public Calendar getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Calendar getDataFim() {
		return dataFim;
	}

	public void setDataFim(Calendar dataFim) {
		this.dataFim = dataFim;
	}

	public List<PrestacaoTransferObject> getPrestacoes() {
		return prestacoes;
	}

	public void setPrestacoes(List<PrestacaoTransferObject> prestacoes) {
		this.prestacoes = prestacoes;
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

}
