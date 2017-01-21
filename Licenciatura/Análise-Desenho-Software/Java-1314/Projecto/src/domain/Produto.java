package domain;

public class Produto {
	
	private double preco;
	private int peso;
	private String descricao;
	
	public Produto( double preco,int peso, String descricao ){
		this.peso=peso;
		this.descricao=descricao;
		this.preco=preco;
	}
		
	public int getPeso(){
		return this.peso;
	}
	
	public String getDesc(){
		return this.descricao;
	}
	
	public double getPreco(){
		return preco;
	}
}
