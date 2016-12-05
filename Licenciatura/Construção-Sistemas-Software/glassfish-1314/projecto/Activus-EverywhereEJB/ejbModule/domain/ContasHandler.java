package domain;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ContasHandler implements ContasHandlerRemote, ContasHandlerLocal{
	

	@EJB private CatalogoContas catalogoContas; 
	
	public ContasHandler(CatalogoContas catalogoContas){
		this.catalogoContas = catalogoContas;
	}
	public List<Conta> getContas()
			throws ApplicationException {
		return this.catalogoContas.getContas();
	}
	public List<Prazo> getContasPrazo()
			throws ApplicationException {
		return this.catalogoContas.getContasPrazo();
	}
	public Prazo getContaPrazo(int id) 
			throws ApplicationException {
		return this.catalogoContas.getContaPrazo(id);
	}
	public List<Prazo> getContasPrazoDoBanco(int id)
			throws ApplicationException {
		
		System.out.println("ContasHandler getContas");
		
		return this.catalogoContas.getContasPrazoDoBanco(id);
	}
	public List<Emprestimo> getContasEmprestimo()
			throws ApplicationException {
		return this.catalogoContas.getContasEmprestimo();
	}
	
	public List<Emprestimo> getContasEmprestimoDoBanco(int id)
			throws ApplicationException {
		return this.catalogoContas.getContasEmprestimoDoBanco(id);
	}
	
	public List<PlanoPagamento> getPlanos()
			throws ApplicationException {
		return this.catalogoContas.getPlanos();
	}
	public void pagaPrestacao(int id) 
			throws ApplicationException{
		this.catalogoContas.pagaPrestacao(id);
	}
	public List<Prestacao> getPrestacoesDoPlanoByID(int id) 
			throws ApplicationException {
		return this.catalogoContas.getPrestacoesDoPlanoByID(id);
	}
	public List<String> getMapaFluxos()
			throws ApplicationException {
		return this.catalogoContas.mapaFluxos();
	}
	public List<Prestacao> getPrestacoesPagasEsteMes() 
			throws ApplicationException {
		return this.catalogoContas.getPrestacoesPagasEsteMes();
	}
	
}
