package domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class PrazoTransferObject extends ContasTransferObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private double valorAplicado;
	
	private boolean normal;
	
	private boolean renovacaoAutomatica;
	
	private Calendar prazoLimite;
	
	private List<EmprestimoTransferObject> emprestimos;

	public double getValorAplicado() {
		return valorAplicado;
	}

	public void setValorAplicado(double valorAplicado) {
		this.valorAplicado = valorAplicado;
	}

	public boolean isNormal() {
		return normal;
	}

	public void setNormal(boolean normal) {
		this.normal = normal;
	}

	public boolean isRenovacaoAutomatica() {
		return renovacaoAutomatica;
	}

	public void setRenovacaoAutomatica(boolean renovacaoAutomatica) {
		this.renovacaoAutomatica = renovacaoAutomatica;
	}

	public Calendar getPrazoLimite() {
		return prazoLimite;
	}

	public void setPrazoLimite(Calendar prazoLimite) {
		this.prazoLimite = prazoLimite;
	}

	public List<EmprestimoTransferObject> getEmprestimos() {
		return emprestimos;
	}

	public void setEmprestimos(List<EmprestimoTransferObject> emprestimos) {
		this.emprestimos = emprestimos;
	}

	

	
	

}
