package domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Prazo extends Conta {
	
	
	private double valorAplicado;
	
	private boolean normal;
	
	private boolean renovacaoAutomatica;
	
	@Temporal(TemporalType.DATE)
	private Calendar prazoLimite;
	
	public Prazo(){ 
	
	}

	public Prazo(double deposito,double taxaJuro,Gestor g,Calendar limite,boolean renovacaoAutomatica){
		super(deposito,taxaJuro,g);
		this.normal = true;
		this.valorAplicado = 0;
		this.prazoLimite = limite;
		this.setRenovacaoAutomatica(renovacaoAutomatica);
	}

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
		int year = this.prazoLimite.getTime().getYear();
		int month = this.prazoLimite.getTime().getMonth();
		int day = this.prazoLimite.getTime().getDay();
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
		
		long diff = this.getDataAbertura().getTimeInMillis() - ca.getTimeInMillis();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
		return (int) diffDays;
		
	}
	

	@Override
	public double calculaFluxo() {
		return (this.getValor() * this.getTaxa() * (diferencaDias() / 365));
	}
	@Override
	public String descricao() {
		// TODO Auto-generated method stub
		return "Deposito a Prazo";
	}
}
