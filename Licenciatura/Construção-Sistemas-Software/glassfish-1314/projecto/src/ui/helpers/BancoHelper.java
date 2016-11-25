package ui.helpers;

import java.util.LinkedList;
import java.util.List;

import domain.ApplicationException;
import domain.Banco;


public class BancoHelper extends Helper{
	
	private String designacao;
	private String sigla;
	private int id;
	private List<Banco> bancos;

	public BancoHelper() {
		 bancos = new LinkedList<Banco>();
		 
	}

	public String getDesignacao(){
		return this.designacao;
	}
	
	public void setDesignacao(String designacao){
		this.designacao=designacao;
	}
	public String getSigla(){
		return this.sigla;
	}
	public int getID(){
		return this.id;
	}
	public void setID(int id){
		this.id = id;
	}
	
	public void setSigla(String sigla){
		this.sigla=sigla;
	}
	
	
	public void clearFields () {
		  sigla = "";
		  designacao = "";
		 }
	
	public void setBancos(List<Banco> bancos) throws ApplicationException{
		this.bancos=bancos;
	}
	
	public Iterable<Banco> getBancos(){
		return this.bancos;
	}
	
	public void removeBanco(String designacao){
		bancos.remove(designacao);
	}
}
