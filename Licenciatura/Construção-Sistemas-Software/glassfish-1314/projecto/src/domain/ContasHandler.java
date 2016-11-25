package domain;

import java.util.List;
import java.util.Set;



public class ContasHandler {
	

	private CatalogoContas catalogoContas; 
	
	public ContasHandler(CatalogoContas catalogoContas){
		this.catalogoContas = catalogoContas;
	}
	public List<Conta> getContas()
			throws ApplicationException {
		return this.catalogoContas.getContas();
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
	
}
