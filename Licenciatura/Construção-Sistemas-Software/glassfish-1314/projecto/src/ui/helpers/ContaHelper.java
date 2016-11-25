package ui.helpers;

import java.util.LinkedList;
import java.util.List;

import domain.Conta;

public class ContaHelper extends Helper{

	private double deposito;
	private double taxaJuro;
	private int id;
	private int ano;
	private int mes;
	private int dia;
	private List<Conta> contas;
	
	public ContaHelper(){
		this.contas = new LinkedList<Conta>();
	}
	public double getDeposito(){
		return this.deposito;
	}
	public double getTaxaJuro(){
		return this.taxaJuro;
	}
	public int getID(){
		return this.id;
	}
	public List<Conta> getContas(){
		return this.contas;
	}
	
	public void clearFields () {
		  this.deposito = 0.0;
		  this.taxaJuro = 0.0;
	}
	public int getAno(){
		return this.ano;
	}
	public int getMes(){
		return this.mes;
	}
	public int getDia(){
		return this.dia;
	}
	public void setAno(int ano){
		this.ano = ano;
	}
	public void setMes(int mes){
		this.mes = mes;
	}
	public void setDia(int dia){
		this.dia = dia;
	}
	public void setTaxaJuro(double taxaJuro){
		this.taxaJuro = taxaJuro;
	}
	public void setDeposito(double deposito){
		this.deposito = deposito;
	}
	
}
