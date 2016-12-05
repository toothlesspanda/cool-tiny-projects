package domain;

import java.io.Serializable;
import java.util.List;

public class EmprestimoTransferObject extends ContasTransferObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PlanoPagamentoTransferObject plano;
	
	private List<PrazoTransferObject> contasPrazo;

	public PlanoPagamentoTransferObject getPlano() {
		return plano;
	}

	public void setPlano(PlanoPagamentoTransferObject plano) {
		this.plano = plano;
	}

	public List<PrazoTransferObject> getContasPrazo() {
		return contasPrazo;
	}

	public void setContasPrazo(List<PrazoTransferObject> contasPrazo) {
		this.contasPrazo = contasPrazo;
	}

}
