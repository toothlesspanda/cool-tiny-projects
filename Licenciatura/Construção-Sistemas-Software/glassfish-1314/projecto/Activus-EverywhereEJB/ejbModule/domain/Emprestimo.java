package domain;

import java.util.Calendar;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Emprestimo
 *
 */
@Entity
	class Emprestimo extends Conta{
	
	@OneToOne(cascade = {CascadeType.ALL})
	private PlanoPagamento plano;
	
	@ManyToMany
	@JoinTable(name = "CONTASPRAZO_EMPRESTIMOS")
	private List<Prazo> contasPrazo;
	
	public Emprestimo() {
		
	}
	public Emprestimo(double deposito,double taxaJuro,Gestor g,Calendar limite,List<Prazo> contasPrazo){
		super(deposito,taxaJuro,g);
		this.plano = new PlanoPagamento(this.getValor(), this.getTaxa(),Calendar.getInstance(),limite);
		this.contasPrazo = contasPrazo;
		
	}
	public PlanoPagamento getPlano() {
		return plano;
	}
	@Override
	public double getCalculaFluxo() {
		// TODO Auto-generated method stub
		return this.plano.fluxo();
	}
	@Override
	public String descricao() {
		// TODO Auto-generated method stub
		return "Emprestimo";
	}
}
