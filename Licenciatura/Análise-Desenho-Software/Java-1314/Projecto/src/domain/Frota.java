package domain;

import java.util.HashMap;

public class Frota {
	
	private HashMap<Integer,Viatura> hashF;
	private HashMap<Integer,Motorista> hashMot;
	
	public Frota(){
		hashF = new HashMap<Integer,Viatura>();
		hashMot = new HashMap<Integer,Motorista>();
	}
	
	public HashMap<Integer,Viatura> getHashF(){
		return hashF;
	}
	
	public void addViatura(Viatura viatura){
		hashF.put(hashF.size()+1, viatura);
	}
	
	public void delViatura(int id){
		hashF.remove(id);
	}

	
	//
	public HashMap<Integer,Motorista> getHashMot(){
		return hashMot;
	}
	
	public void addMotorista(Motorista m){
		hashMot.put(hashMot.size()+1, m);
	}
	
	public void delMotorista(int id){
		hashMot.remove(id);
	}
	

}
