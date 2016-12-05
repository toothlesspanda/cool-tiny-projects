package domain;

import java.util.List;

import javax.ejb.Local;

@Local
public interface GestoresHandlerLocal {

	public List<GestoresTransferObject> getGestores()
			throws ApplicationException;

	public void associaContaGestor(int idGestor,int idConta)
			throws ApplicationException;
	
	public List<ContasTransferObject> getContasDeGestor(int id)
			throws ApplicationException;
	
	public void updateGestorEmail(int id,String email)
			throws ApplicationException;
	
	public void updateGestorTelefone(int id,int telefone)
			throws ApplicationException;
	
	public int getBancoIDByGestorID(int id) 
			throws ApplicationException;
	
}
