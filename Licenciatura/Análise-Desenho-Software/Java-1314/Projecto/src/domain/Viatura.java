package domain;

public class Viatura {

	private String matricula;
	private int km;
	private int ano;
	private boolean livre;

	private String tipo;
	private int kmActual;

	public Viatura (String matricula, int kmActual,int km,int ano,String tipo, boolean livre ){
		this.matricula = matricula;
		this.km = km;
		this.ano = ano;
		this.livre = livre;

		this.tipo =tipo;
		this.kmActual =kmActual;
	}
	
	public String getMatr(){
		return this.matricula;
	}
	
	public int getKm(){
		return this.km;
	}
	public int getAno(){
		return this.ano;
	}
	
	public boolean getLivre(){
		return this.livre;
	}
	
	public String getTipo(){
		return this.tipo;
	}
	
	public void ocupado(){
		livre = false;
	}
	
	public void livre(){
		livre=true;
	}
	
	public int getkmActual(){
		return this.kmActual;
	}
	
	public void actualiza(int km){
		kmActual += km;
	}
	
}