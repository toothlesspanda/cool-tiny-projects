package domain;

import java.util.ArrayList;


public class Empresa {
	
	
	private ArrayList<Ordem> allOrders;
	
	public Empresa(){
		allOrders = new ArrayList<Ordem> ();
	}
	
	public void addSale(Ordem o) {
		allOrders.add(o);
	}

}
