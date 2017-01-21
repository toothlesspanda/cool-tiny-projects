package domain;

public class Morada {
		

	private String rua;
	private String andar;
	private int porta;
	private String cp;
	private String local;
	private int distEmp;
	
	public Morada(String rua,String andar,int porta, String cp,
					String local, int distEmp){
	
		this.rua=rua;
		this.andar=andar;
		this.porta=porta;
		this.cp=cp;
		this.local=local;
		this.distEmp=distEmp;
				
	}
	
	public String getRua(){
		return this.rua;
	}
	public String getAndar(){
		return this.andar;
	}
	public int getPorta(){
		return this.porta;
	}
	public String getCp(){
		return this.cp;
	}
	public String getLocal(){
		return this.local;
	}
	public int getDistEmp(){
		return this.distEmp;
	}
}
