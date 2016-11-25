package domain;

import java.util.Calendar;
import java.util.List;

public class GestoresHandler {

	private CatalogoGestores catalogoGestores; 
	
	public GestoresHandler(CatalogoGestores catalogoGestores){
		this.catalogoGestores = catalogoGestores;
	}
	public List<Gestor> getGestores()
			throws ApplicationException {
		return this.catalogoGestores.getGestores();
	}

	public void associaContaGestor(int idGestor,int idConta)
			throws ApplicationException {
		this.catalogoGestores.associaContaGestor(idGestor,idConta);
	}
	
	public List<Conta> getContasDeGestor(int id)
			throws ApplicationException {
		return this.catalogoGestores.getContasDeGestor(id);
		
	}
	public void updateGestorEmail(int id,String email)
			throws ApplicationException {
		this.catalogoGestores.updateGestorEmail(email, id);
	}
	public void updateGestorTelefone(int id,int telefone)
			throws ApplicationException {
		this.catalogoGestores.updateGestorTelefone(telefone, id);
	}
	public int getBancoIDByGestorID(int id) 
			throws ApplicationException{
		return this.catalogoGestores.getBancoIDByGestorID(id);
	}
	
}
