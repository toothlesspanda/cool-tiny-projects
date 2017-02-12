package projecto;

import java.io.Serializable;

public class House implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	int valor; // valor
	String local; //Local
	String tipo; //"0-6"
	String ano; // "1999"
	int transportes; // 1 existe, 0 não
	int estacionamento; // 1 existe, 0 não
	int zcomercial;  // 1 existe, 0 não
	int equipada;  // 1 existe, 0 não
	String eficiencia; // "A", "B"
	int imi;  //valor do imi
	boolean vendida;
	
	public House(int id, int valor, String local, String tipo, String ano, int transportes, int estacionamento, int zcomercial,
			int equipada, String eficiencia, int imi) {
		super();
		this.id = id;
		this.valor = valor;
		this.local = local;
		this.tipo = tipo;
		this.ano = ano;
		this.transportes = transportes;
		this.estacionamento = estacionamento;
		this.zcomercial = zcomercial;
		this.equipada = equipada;
		this.eficiencia = eficiencia;
		this.imi = imi;
		this.vendida = false;
	}
	
	public int getId() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}


	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public int getTransportes() {
		return transportes;
	}

	public void setTransportes(int transportes) {
		this.transportes = transportes;
	}

	public int getEstacionamento() {
		return estacionamento;
	}

	public void setEstacionamento(int estacionamento) {
		this.estacionamento = estacionamento;
	}

	public int getZcomercial() {
		return zcomercial;
	}

	public void setZcomercial(int zcomercial) {
		this.zcomercial = zcomercial;
	}

	public int getEquipada() {
		return equipada;
	}

	public void setEquipada(int equipada) {
		this.equipada = equipada;
	}

	public String getEficiencia() {
		return eficiencia;
	}

	public void setEficiencia(String eficiencia) {
		this.eficiencia = eficiencia;
	}

	public int getImi() {
		return imi;
	}

	public void setImi(int imi) {
		this.imi = imi;
	}
	
	public void setVendida(){
		this.vendida = true;
	}
	
	public boolean getVendida(){
		return vendida;
	}
	

}
