package domain;

import java.util.Calendar;
import java.util.List;

public class BancosHandler {

	private CatalogoBancos catalogoBancos;
	private CatalogoGestores catalogoGestores;
	private CatalogoContas catalogoContas;

	
	public BancosHandler(CatalogoBancos catalogoBancos,
			CatalogoGestores catalogoGestores, CatalogoContas catalogoContas) {
		this.catalogoBancos = catalogoBancos;
		this.catalogoGestores = catalogoGestores;
		this.catalogoContas = catalogoContas;
	}


	public void adicionarBanco (String sigla,String designacao) 
			throws ApplicationException {
		this.catalogoBancos.addBanco(sigla,designacao);
	}	
	public Banco getBanco(int id)
			throws ApplicationException {
		return this.catalogoBancos.getBancoByID(id);
	}
	
	public Gestor devolveGestorPorNome (String nome) 
			throws ApplicationException {
		return this.catalogoGestores.getGestorByNAME(nome);
	}
	public Gestor devolveGestorPorEmail (String email) 
			throws ApplicationException {
		return this.catalogoGestores.getGestorByEMAIL(email);
	}
	public Gestor devolveGestorPorID (int id) 
			throws ApplicationException {
		return this.catalogoGestores.getGestorByID(id);
	}
	public void updateGestorEmail (String email,int id) 
			throws ApplicationException {
		this.catalogoGestores.updateGestorEmail(email, id);
	}
	public List<Banco> getBancos() 
			throws ApplicationException{
		return this.catalogoBancos.getBancos();
	}
	public void deleteGestor (int id) 
			throws ApplicationException {
		this.catalogoGestores.deleteGestor(id);
	}
	public void addGestorBanco(int id,String nome,String email,int telefone)
			throws ApplicationException {
		this.catalogoBancos.addGestorBanco(id,nome,email,telefone );
	}
	public void addContaBanco(int id,Conta c)
			throws ApplicationException {
		this.catalogoBancos.addContaBanco(id, c);
	}
	public void addContaPrazoBanco(int id,double deposito,double taxaJuro,int idGestor,Calendar limite,boolean renovacaoAutomatica)
			throws ApplicationException {
		this.catalogoBancos.addContaPrazoBanco(id, deposito, taxaJuro, idGestor, limite, renovacaoAutomatica);
	}
	public void addContaEmprestimoBanco(int id,double deposito,double taxaJuro,int idGestor,Calendar limite,List<Prazo> lp)
			throws ApplicationException {
		this.catalogoBancos.addContaEmprestimoBanco(id, deposito, taxaJuro, idGestor, limite, lp);
	}
	 public void removeBanco(String designacao) 
	   throws ApplicationException{
	  this.catalogoBancos.removeBanco(designacao);
	 }
	 public void removeBancoById(int id) 
			   throws ApplicationException{
			  this.catalogoBancos.removeBancoByID(id);
			 }
}
