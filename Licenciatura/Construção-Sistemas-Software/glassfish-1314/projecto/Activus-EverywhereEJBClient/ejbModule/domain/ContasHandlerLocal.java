package domain;

import java.util.List;

import javax.ejb.Local;


@Local
public interface ContasHandlerLocal {


	public List<ContasTransferObject> getContas() throws ApplicationException;
	
	public List<PrazoTransferObject> getContasPrazo() throws ApplicationException;
	
	public PrazoTransferObject getContaPrazo(int id) throws ApplicationException;
	
	public List<PrazoTransferObject> getContasPrazoDoBanco(int id) throws ApplicationException;
	
	public List<EmprestimoTransferObject> getContasEmprestimo() throws ApplicationException;
	
	public List<EmprestimoTransferObject> getContasEmprestimoDoBanco(int id) throws ApplicationException;
	
	public List<PlanoPagamentoTransferObject> getPlanos() throws ApplicationException;
	
	public void pagaPrestacao(int id) throws ApplicationException;
	
	public List<PrestacaoTransferObject> getPrestacoesDoPlanoByID(int id) throws ApplicationException;
	
	public List<String> getMapaFluxos() throws ApplicationException;
	
	public List<PrestacaoTransferObject> getPrestacoesPagasEsteMes() throws ApplicationException;
	
	
}
