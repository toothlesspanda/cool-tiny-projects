package ui.helpers;

import java.util.LinkedList;
import java.util.List;

import domain.ApplicationException;
import domain.Gestor;


public class GestorHelper extends Helper{
	
	private String nome;
	private String email;
	private String telefone;
	private String banco;
	private int id;
	private int idBanco;
	private List<Gestor> gestores;

	public GestorHelper() {
		 this.gestores = new LinkedList<Gestor>();
	}

	public String getNome(){
		return this.nome;
	}
	
	public String getTelefone(){
		return this.telefone;
	}
	public String  getEmail() {
		return this.email;
	}
	public void setNome(String nome){
		this.nome=nome;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public void setEmail(String email){
		this.email=email;
	}
	
	public void set(String banco){
		this.banco = banco;
	}
	public String getBanco(){
		return this.banco;
	}
	
	
	public void clearFields () {
		  nome = "";
		  email = "";
		  telefone = "";
		 }
	
	public void setGestores(List<Gestor> gestores) throws ApplicationException{
		this.gestores=gestores;
	}
	public List<Gestor> getGestores(){
		return this.gestores;
	}
	
	public int getID(){
		return this.id;
	}
	public void setID(int id){
		this.id = id;
	}
	public int getIDBanco(){
		return this.idBanco;
	}
	public void setIDBanco(int idBanco){
		this.idBanco = idBanco;
	}
}
