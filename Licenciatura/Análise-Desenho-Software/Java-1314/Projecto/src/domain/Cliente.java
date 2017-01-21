package domain;

import java.util.ArrayList;


public class Cliente {

	private ArrayList<Morada> m;
	private String nome;
	private int telefone;
	
	public Cliente (String nome, int telefone ){
		this.m = new ArrayList<Morada>();
		this.nome = nome;
		this.telefone = telefone;
	}

	public ArrayList<Morada> getMoradas(){
		return this.m;
	}

	public String getNome(){
		return this.nome;
	}
	public int getTelefone(){
		return this.telefone;
	}
	
	public void addMorada(Morada m1){
		m.add(m1);
	}
	
	public void delMorada(int id){
		m.remove(id);
	}
	
	public Morada getMorada(Morada m1){
		Morada m2= null;
		if(m.contains(m1)){
			m2=m1;
		}
		return m2;
	}
}
