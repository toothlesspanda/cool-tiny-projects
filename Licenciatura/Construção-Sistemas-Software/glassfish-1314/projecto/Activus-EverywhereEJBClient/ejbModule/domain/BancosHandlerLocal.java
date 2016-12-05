package domain;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Local;

@Local
public interface BancosHandlerLocal {

	public void adicionarBanco (String sigla,String designacao) 
			throws ApplicationException;
	
	public BancosTransferObject getBanco(int id)
			throws ApplicationException;
	
	public GestoresTransferObject devolveGestorPorNome (String nome) 
			throws ApplicationException;
	
	public GestoresTransferObject devolveGestorPorEmail (String email) 
			throws ApplicationException;
	
	public GestoresTransferObject devolveGestorPorID (int id) 
			throws ApplicationException;
	
	public void updateGestorEmail (String email,int id) 
			throws ApplicationException;
	
	public List<BancosTransferObject> getBancos() 
			throws ApplicationException;
	
	public void deleteGestor (int id) 
			throws ApplicationException;
	
	public void addGestorBanco(int id,String nome,String email,int telefone)
			throws ApplicationException;
	
	public void addContaBanco(int id,ContasTransferObject c)
			throws ApplicationException;
	
	public void addContaPrazoBanco(int id,double deposito,double taxaJuro,int idGestor,Calendar limite,boolean renovacaoAutomatica)
			throws ApplicationException;
	
	public void addContaEmprestimoBanco(int id,double deposito,double taxaJuro,int idGestor,Calendar limite,List<PrazoTransferObject> lp)
			throws ApplicationException;
	
	 public void removeBanco(String designacao) 
			throws ApplicationException;
	 
	 public void removeBancoById(int id) 
			throws ApplicationException;
	
	
}
