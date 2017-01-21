package domain;

public class Servico {

	private double preco;
	public Servico(){
	 preco=0;	
	}
	
	public double price(int peso){
	
		
		if(peso == -2){
			preco = 4;
		}else if(peso == 50){
			preco = 25;
		}else if(peso == 100){
			preco = 50;
		}else if(peso == 500){
			preco = 100;
		}else if ( peso == 1000){
			preco = 120;
		}
		return preco;
	}
	
}
