package application;

import java.util.List;

import domain.ApplicationException;
import domain.Conta;
import domain.ContasHandler;
import domain.Emprestimo;
import domain.PlanoPagamento;
import domain.Prazo;

public class ContasService {

	private ContasHandler contasHandler;
	
	public ContasService(ContasHandler contasHandler){
		this.contasHandler = contasHandler;
	}
	public List<Conta> getContas()
				throws ApplicationException {
		return this.contasHandler.getContas();
	}
	public List<PlanoPagamento> getPlanos()
			throws ApplicationException {
		return this.contasHandler.getPlanos();
	}
	public void pagaPrestacao(int id) 
			throws ApplicationException{
		this.contasHandler.pagaPrestacao(id);
	}
	public List<String> getMapaFluxos()
			throws ApplicationException {
		return this.contasHandler.getMapaFluxos();
	}
}
