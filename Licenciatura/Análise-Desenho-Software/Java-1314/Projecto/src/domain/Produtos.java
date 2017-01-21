package domain;

import java.util.ArrayList;

public class Produtos {

	private ArrayList<Produto> produto;


	public Produtos() {
		produto = new ArrayList<Produto>();
	}

	public ArrayList<Produto> getPro() {
		return this.produto;
	}
}