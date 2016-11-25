package domain;

import java.util.Calendar;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Prestacao
 *
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name="Prestacao.findALL", query="SELECT p FROM Prestacao p"),
	@NamedQuery(name="Prestacao.findByID", query="SELECT p FROM Prestacao p WHERE p.idPrestacao = :idPrestacao")
})
public class Prestacao{

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false)
	private int idPrestacao;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Calendar dataLimite;
	
	@Column(nullable=false)
	private double valorPrestacao;
	
	@Column(nullable=false)
	private boolean pagamento;
	
	@Temporal(TemporalType.DATE)
	private Calendar dataPagamento;
	
	@ManyToOne
    private PlanoPagamento plano;
	
	public Prestacao() {
		
	}
	public Prestacao(double valorPrestacao,Calendar dataLimite,PlanoPagamento plano){
		this.valorPrestacao = valorPrestacao;
		this.dataLimite = dataLimite;
		this.pagamento = false;
		this.dataPagamento = null;
		this.plano = plano;
	}
   
	public void pagar(){
		this.dataPagamento = Calendar.getInstance();
		this.pagamento = true;
	}
	
	public boolean getPagamento(){
		return this.pagamento;
	}
	
	public Calendar getDataPagamento2(){
		return this.dataPagamento;
	}
	
	public String getDataPagamento(){
		if(this.dataPagamento==null)
			return "";
		return dataPagamento.get(Calendar.YEAR) + "-" + (dataPagamento.get(Calendar.MONTH)+1) + "-" + dataPagamento.get(Calendar.DATE);
	}
	public double getValorPrestacao(){
		return this.valorPrestacao;
	}
	public Calendar getDataLimite2(){
		return this.dataLimite;
	}
	public String getDataLimite(){
		return dataLimite.get(Calendar.YEAR) + "-" + (dataLimite.get(Calendar.MONTH)+1) + "-" + dataLimite.get(Calendar.DATE);
	}
	
	public int getID(){
		return this.idPrestacao;
	}
	
	/**@Override
	public int compareTo(Prestacao o) {
	
		if(this.dataLimite.getTimeInMillis() > o.getDataLimite2().getTimeInMillis())
			return 1;
		else if(this.dataLimite.getTimeInMillis() < o.getDataLimite2().getTimeInMillis())
			return -1;
		return 0;
	}**/
	public PlanoPagamento getPlano(){
		return this.plano;
	}
}
