package domain;

import java.util.HashMap;

public class CatalogoOrdens {
	
	private HashMap<Integer,Ordem> ordens;
	
	public CatalogoOrdens(){
		ordens = new HashMap<Integer,Ordem>();
	}
	
	public HashMap<Integer,Ordem> getOrdens(){
		return this.ordens;
	}
	
	public void addOrdem(Ordem o){
		int id = ordens.size()+1;
		ordens.put(id,o);
	}
	
	public void delCliente(int id){
		ordens.remove(id);
	}
	
}
