package domain;

import java.io.Serializable;
import java.util.Calendar;


public class PrestacaoTransferObject implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private int idPrestacao;
	
	private Calendar dataLimite;
	
	private double valorPrestacao;
	
	private boolean pagamento;
	
	private Calendar dataPagamento;
	
    private PlanoPagamentoTransferObject plano;

	public int getIdPrestacao() {
		return idPrestacao;
	}

	public void setIdPrestacao(int idPrestacao) {
		this.idPrestacao = idPrestacao;
	}

	public Calendar getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(Calendar dataLimite) {
		this.dataLimite = dataLimite;
	}

	public double getValorPrestacao() {
		return valorPrestacao;
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

	public Calendar getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Calendar dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public PlanoPagamentoTransferObject getPlano() {
		return plano;
	}

	public void setPlano(PlanoPagamentoTransferObject plano) {
		this.plano = plano;
	}
	
	
}
