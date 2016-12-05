package domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
	class Prazo extends Conta {
	
	private double valorAplicado;
	
	@Column(nullable = false)
	private boolean normal;
	
	@Column(nullable = false)
	private boolean renovacaoAutomatica;
	
	@Temporal(TemporalType.DATE)
	private Calendar prazoLimite;
	
	@ManyToMany(mappedBy="contasPrazo")
	private List<Emprestimo> emprestimos;
	
	public Prazo(){ 
	
	}

	public Prazo(double deposito,double taxaJuro,Gestor g,Calendar limite,boolean renovacaoAutomatica){
		super(deposito,taxaJuro,g);
		this.normal = true;
		this.valorAplicado = 0;
		this.prazoLimite = limite;
		this.setRenovacaoAutomatica(renovacaoAutomatica);
		this.emprestimos = new ArrayList<Emprestimo>();
	}

	/**private String getLimite(){
		return prazoLimite.get(Calendar.YEAR) + "-" + (prazoLimite.get(Calendar.MONTH)+1) + "-" + prazoLimite.get(Calendar.DATE);
	}
	
	private void setLimite(Calendar cal){
		this.prazoLimite = cal;
	}**/
	
	public void encerrar(boolean normal) {
		this.normal = false;
	}
	public boolean encerrada(){
		return !this.normal;
	}

	public double getValorAplicado() {
		return valorAplicado;
	}

	public void setValorAplicado(double valorAplicado) {
		this.valorAplicado = valorAplicado;
	}

	
	public boolean isRenovacaoAutomatica() {
		return renovacaoAutomatica;
	}

	public void setRenovacaoAutomatica(boolean renovacaoAutomatica) {
		this.renovacaoAutomatica = renovacaoAutomatica;
	}
	public void aplicaJuros(){
		this.valorAplicado = this.valorAplicado + (this.getValor() * this.getTaxa());
	}
	public void renovarPrazo(){
		aplicaJuros();
		int year = this.prazoLimite.get(Calendar.YEAR);
		int month = this.prazoLimite.get(Calendar.MONTH) + 1;
		int day = this.prazoLimite.get(Calendar.DATE);
		this.prazoLimite.set(year+2,month,day);
	}
	public void naoRenovar(){
		//this.valorAplicado = this.valorAplicado + (this.getValor() * this.getTaxa()) + this.getValor();
		this.valorAplicado = (this.getValor() * this.getTaxa() * (diferencaDias() / 365));
		this.setValor(0.0);
		this.setTaxa(0.0); 
	}
	public boolean alturaRenovar(){
		if(!this.encerrada()){
			Calendar c = Calendar.getInstance();
			int diffY = this.prazoLimite.get(Calendar.YEAR) - c.get(Calendar.YEAR);
			int diffM = diffY * 12 + this.prazoLimite.get(Calendar.MONTH) - c.get(Calendar.MONTH);
			if(diffM <= 3)
				return true;
			return false;	
		}
		return false;
	}
	public int diferencaDias(){
		Calendar ca = Calendar.getInstance();
		
		long diff = ca.getTimeInMillis() - this.getDataAbertura().getTimeInMillis();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
		return (int) diffDays;
		
	}
	

	@Override
	public double getCalculaFluxo() {
		
		return (this.getValor() * (this.getTaxa()/100) * (diferencaDias() / 365));
	}
	@Override
	public String descricao() {
		// TODO Auto-generated method stub
		return "Deposito a Prazo";
	}
	public void addEmprestimo(Emprestimo e){
		this.emprestimos.add(e);
	}
}
