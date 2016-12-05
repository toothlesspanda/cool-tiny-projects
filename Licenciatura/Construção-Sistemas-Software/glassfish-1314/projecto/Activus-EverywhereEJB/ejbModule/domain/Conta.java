package domain;

import java.util.Calendar;

import javax.persistence.*;


@Entity
@NamedQueries({ 
	@NamedQuery(name="Conta.findByID", query="SELECT c FROM Conta c WHERE c.idConta = :idConta"),
	@NamedQuery(name="Conta.findPrazoByID", query="SELECT c FROM Prazo c WHERE c.idConta = :idConta"),
	@NamedQuery(name="Conta.findByTipo", query="SELECT c FROM Conta c WHERE TYPE(c) IN :classe"),
	@NamedQuery(name="Conta.findALL", query="SELECT c FROM Conta c"),
	@NamedQuery(name="Conta.findByBanco", query="SELECT c FROM Conta c WHERE c.banco.idBanco = :idBanco"),
	@NamedQuery(name="Conta.findByBancoWithType", query="SELECT c FROM Conta c WHERE c.banco.idBanco = :idBanco AND TYPE(c) IN :classe"),
	
})
	abstract class Conta {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idConta;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar dataDeAbertura;
	
	@Column(nullable = false)
	private double taxa;
	
	@Column(nullable = false)
	private double valor;
	
	@ManyToOne
	private Banco banco;
	
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
	
	public void setBanco(Banco banco){
		this.banco = banco;
	}
	public Banco getBanco(){
		return this.banco;
	}
	
	public String getGestorNome(){
		return this.gestor.getNome();
	}
	
	public abstract double getCalculaFluxo();
	
	public abstract String descricao();
	
}
