package domain;

public class Motorista extends Funcionario{

	private int licenca;
	private boolean ocupado;
	
	public Motorista(int id,String nome, int nib, int numero,int salario, int licenca,boolean ocupado) {
		super(id,nome, nib, numero,salario);
		this.licenca= licenca;
		this.ocupado = ocupado;
		
	}
	
	public int getLicense(){
		return this.licenca;
	}
	
	public void ocupado(){
		ocupado = true;		
	}
	
	public void livre(){
		ocupado=false;
	}

	public boolean getState(){
		return ocupado;
	}
}
