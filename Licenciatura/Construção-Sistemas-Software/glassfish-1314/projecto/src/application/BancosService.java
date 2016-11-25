package application;

import java.util.Calendar;
import java.util.List;

import domain.ApplicationException;
import domain.Banco;
import domain.BancosHandler;
import domain.Conta;
import domain.Emprestimo;
import domain.Gestor;
import domain.Prazo;

/**
 * Handles sales' transactions. 
 * Each public method implements a transaction script.	
 *	
 * @author fmartins
 *
 */
public class BancosService {

	private BancosHandler bancosHandler;

	public BancosService(BancosHandler bancosHandler) {
		this.bancosHandler = bancosHandler;
	}
	
	public void adicionarBanco(String sigla,String designacao) 
			throws ApplicationException {
		this.bancosHandler.adicionarBanco(sigla,designacao);
	}	
	
	public Gestor devolveGestorPorNome (String nome) 
			throws ApplicationException {
		return this.bancosHandler.devolveGestorPorNome(nome);
	}
	public Gestor devolveGestorPorEmail (String email) 
			throws ApplicationException {
		return this.bancosHandler.devolveGestorPorEmail(email);
	}
	public Gestor devolveGestorPorID (int id) 
			throws ApplicationException {
		return this.bancosHandler.devolveGestorPorID(id);
	}
	public void updateGestorEmail (String email,int id) 
			throws ApplicationException {
		this.bancosHandler.updateGestorEmail(email,id);
	}
	public void deleteGestor (int id) 
			throws ApplicationException {
		this.bancosHandler.deleteGestor(id);
	}
	public void addGestorBanco(int id,String nome,String email,int telefone)
			throws ApplicationException {
		this.bancosHandler.addGestorBanco(id,nome,email,telefone);
		
	}
	public Banco getBanco(int id)
			throws ApplicationException {
		
		return this.bancosHandler.getBanco(id);
		
	}
	public void addContaBanco(int id,Conta c)
			throws ApplicationException {
		this.bancosHandler.addContaBanco(id, c);
	}
	public void addContaPrazoBanco(int id,double deposito,double taxaJuro,int idGestor,Calendar limite,boolean renovacaoAutomatica)
			throws ApplicationException {
		this.bancosHandler.addContaPrazoBanco(id, deposito, taxaJuro, idGestor, limite, renovacaoAutomatica);
	}
	public void addContaEmprestimoBanco(int id,double deposito,double taxaJuro,int idGestor,Calendar limite,List<Prazo> lp)
			throws ApplicationException {
		this.bancosHandler.addContaEmprestimoBanco(id, deposito, taxaJuro, idGestor, limite, lp);
	}
	public void removeBanco(String designacao) 
			   throws ApplicationException{
		this.bancosHandler.removeBanco(designacao);
	}
	public void removeBancoById(int id) 
			   throws ApplicationException{
		this.bancosHandler.removeBancoById(id);
	}
}
