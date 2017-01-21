package domain;

public class Secretariado extends Funcionario {
	
	private String departamento;
	
	public Secretariado(int id,String nome, int nib, int numero,
					int salario, String departamento) {
		super(id,nome,nib,numero,salario);
		this.departamento = departamento;
		
	}

	
	public String getDepart(){
		return departamento;
	}

}
