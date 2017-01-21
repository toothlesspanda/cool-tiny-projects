package domain;

public class Funcionario {
	private String nome;
	private int nib;
	private int number;
	private int salario;
	
	public Funcionario(int id, String nome, int nib, int numero,int salario){
		this.nome = nome;
		this.nib = nib;
		this.number=numero;
		this.salario = salario;
	}
	
	public String getName(){
		return nome;
	}
	
	public int getNIB(){
		return nib;
	}
	
	public int getNumber(){
		return this.number;
	}
	
	public int getSal(){
		return this.salario;
	}
	
}
