package domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;


@Entity
@NamedQueries({ 
	@NamedQuery(name="Conta.findByID", query="SELECT c FROM Conta c WHERE c.idConta = :idConta"),
	@NamedQuery(name="Conta.findALL", query="SELECT c FROM Conta c")
})
public abstract class Conta {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idConta;
	
	@Temporal(TemporalType.DATE)
	private Calendar dataDeAbertura;
	
	private double taxa;
	
	private double valor;
	
	@ManyToOne
	private Gestor gestor;
	
	public Conta(){
		
	}

	public Conta(double valor,double taxa,Gestor g){
		this.valor = valor;
		this.taxa = taxa;
		this.dataDeAbertura = Calendar.getInstance();
		this.gestor = g;
	}
	
	public Calendar getDataAbertura(){
		return this.dataDeAbertura;
	}

	public double getTaxa() {
		return taxa;
	}

	public void setTaxa(double taxa) {
		this.taxa = taxa;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	public int getID(){
		return this.idConta;
	}
	public void setGestor(Gestor gestor){
		this.gestor = gestor;
	}
	public Gestor getGestor(){
		return this.gestor;
	}
	public String getGestorNome(){
		return this.gestor.getNome();
	}
	
	public abstract double calculaFluxo();
	
	public abstract String descricao();
	
}
