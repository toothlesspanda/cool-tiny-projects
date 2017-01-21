package domain;

import java.util.HashMap;


public class CatalogoCliente {
	
	private HashMap<Integer,Cliente> clientes;
	
	public CatalogoCliente(){
		clientes = new HashMap<Integer,Cliente>();
	}
	
	public HashMap<Integer,Cliente> getClientes(){
		return this.clientes;
	}
	
	public void addCliente(Cliente c){
		int id = clientes.size()+1;
		clientes.put(id,c);
	}
	
	public void delCliente(int id){
		clientes.remove(id);
	}
	
	public Cliente getCliente(Cliente c){
		Cliente c1 = null;
		if(clientes.containsValue(c)){
			c1 = c;
		}
		return c1;
	}

}
