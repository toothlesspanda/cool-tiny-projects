package domain;

public class Ordem {

	private Cliente c;
	private Motorista m;
	private Viatura v;
	private Produtos produtos;
	private Pagamento paga;
	private int state;
	private Morada dest;
	private Morada ori;
	
	public Ordem(Cliente c, Motorista m,Viatura v,Produtos produtos, Pagamento paga, Morada ori, Morada dest){
		this.c=c;
		this.m=m;
		this.v=v;
		this.produtos =produtos;
		this.state=0;
		this.paga=paga;
		this.ori = ori;
		this.dest = dest;
	}
	
	//START boolean
	
	public void started(){
		state =1;		
	}
	
	public void finish(){
		state=0;
	}
	
	public void filaEspera(){
		state =2;
	}
	
	public int getState(){
		return state;
	}
	
	public Cliente orderClient(){
		return c;
	}

	
	public Motorista orderMotorista(){
		return m;
	}
	
	public Viatura orderViatura(){
		return v;
	}
	
	public Produtos orderProdut(){
		return produtos;
	}
	
	public Pagamento orderPay(){
		return paga;
	}
	
	public Morada dest(){
		return dest;
	}
	public Morada ori(){
		return ori;
	}
	
	//terminado boolean
	
	
	
		
	
	
	
}
