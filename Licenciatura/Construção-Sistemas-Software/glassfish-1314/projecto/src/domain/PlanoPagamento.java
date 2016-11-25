package domain;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.*;

/**
 * 
 * @author css008
 * 
 *	Uma tabela Plano Pagamento para todos os emprestimos
 *  Cada linha corresponde a uma prestação com id do Emprestimo
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name="PlanoPagamento.findALL", query="SELECT p FROM PlanoPagamento p"),
	@NamedQuery(name="PlanoPagamento.findByID", query="SELECT p FROM PlanoPagamento p WHERE p.idPlanoPagamento = :idPlanoPagamento")
})
public class PlanoPagamento {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false)
	private int idPlanoPagamento;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Calendar dataInicio;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Calendar dataFim;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "plano")
    //@OrderBy("dataLimite ASC")
	private List<Prestacao> prestacoes;
	
	private int numeroPagamentos;
	
	private boolean prestacoesPagas;
	
	public PlanoPagamento(){}
	
	public PlanoPagamento(double capital,double taxa,Calendar dataInicio,Calendar dataFim){
		
		this.dataInicio=dataInicio;
		this.dataFim=dataFim;
		this.prestacoes = geraPrestacoes(capital,taxa,dataInicio,dataFim);
		this.numeroPagamentos = 0;
		this.prestacoesPagas = false;
	}
	public int quantasPrestacoes(){
		return this.prestacoes.size();
	}
	
	private List<Prestacao> geraPrestacoes(double capital,double taxa,Calendar dataInicio,Calendar dataFim){
		
		int diffY = this.dataFim.get(Calendar.YEAR) - this.dataInicio.get(Calendar.YEAR);
		int diffM = diffY * 12 + this.dataFim.get(Calendar.MONTH) - this.dataInicio.get(Calendar.MONTH);
		
		//Set<Prestacao> setp = new TreeSet<Prestacao>();
		List<Prestacao> setp = new ArrayList<Prestacao>();
		
		for(int i = 1;i < diffM; i++){
			Calendar c = Calendar.getInstance();
			c.set(this.dataInicio.get(Calendar.YEAR), this.dataInicio.get(Calendar.MONTH)+i, this.dataInicio.get(Calendar.DATE));
			Prestacao p = new Prestacao((capital/(diffM-1))*taxa,c,this);
			setp.add(p);
		}
		
		return setp;
	}
	private int diferencaMes(Calendar cal){
		Calendar agora = Calendar.getInstance();
		int diffY = cal.get(Calendar.YEAR) - agora.get(Calendar.YEAR);
		return diffY * 12 + cal.get(Calendar.MONTH) - agora.get(Calendar.MONTH);
	}
	public double fluxo(){
		double auxiliar = 0.0;
		
		for(Prestacao p: this.prestacoes){
			if(p.getPagamento()){
				if(diferencaMes(p.getDataPagamento2())==0){
					auxiliar = auxiliar - p.getValorPrestacao();
				} 
				
			}
		}
		
		return auxiliar;
	}
	public int getID(){
		return this.idPlanoPagamento;
	}
	
	public void actualizaPagas(){
		System.out.println("ola");
		this.numeroPagamentos++;
		if(this.numeroPagamentos == this.prestacoes.size())
			this.prestacoesPagas = true;
	}
	
	public boolean prestacoesPagas(){
		return this.prestacoesPagas;
	}
	public List<Prestacao> getPrestacoes(){
		return this.prestacoes;
	}
	public String getDataInicio(){
		return dataInicio.get(Calendar.YEAR) + "-" + (dataInicio.get(Calendar.MONTH)+1) + "-" + dataInicio.get(Calendar.DATE);
	}
	public String getDataFim(){
		return dataFim.get(Calendar.YEAR) + "-" + (dataFim.get(Calendar.MONTH)+1) + "-" + dataFim.get(Calendar.DATE);
	}
	public int getNumeroPagamentos(){
		return this.numeroPagamentos;
	}
	public boolean getPrestacoesPagas(){
		return this.prestacoesPagas;
	}
	
}
