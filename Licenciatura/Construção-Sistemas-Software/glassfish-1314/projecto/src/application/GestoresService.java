package application;

import java.util.Calendar;
import java.util.List;

import domain.ApplicationException;
import domain.Conta;
import domain.Emprestimo;
import domain.Gestor;
import domain.GestoresHandler;
import domain.Prazo;

public class GestoresService {

	private GestoresHandler gestoresHandler;
	
	public GestoresService(GestoresHandler gestoresHandler){
		this.gestoresHandler = gestoresHandler;
	}
	public List<Gestor> getContas()
				throws ApplicationException {
		return this.gestoresHandler.getGestores();
	}
	
	public List<Conta> getContasDeGestor(int id)
			throws ApplicationException {
		return this.gestoresHandler.getContasDeGestor(id);
		
	}
	public void associaContaGestor(int idGestor,int idConta)
			throws ApplicationException {
		this.gestoresHandler.associaContaGestor(idGestor,idConta);
	}
}
