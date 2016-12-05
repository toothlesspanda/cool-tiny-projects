package ui.helpers;

import java.util.LinkedList;
import java.util.List;

import domain.Conta;
import domain.Emprestimo;
import domain.Prazo;

public class ContaHelper extends Helper{

	private double deposito;
	private double taxaJuro;
	private int id;
	private int ano;
	private int mes;
	private int dia;
	private double total;
	private double calculaFluxo;
	private String prazoLimite;
	private String data;
	private String descricao;
	private List<Conta> contas;
	private List<Prazo> contasPrazo;
	private List<Emprestimo> contasEmprestimo;
	
	public ContaHelper(){
		this.contas = new LinkedList<Conta>();
		this.contasPrazo = new LinkedList<Prazo>();
		this.contasEmprestimo = new LinkedList<Emprestimo>();
	}
	public double getDeposito(){
		return this.deposito;
	}
	public double getTaxaJuro(){
		return this.taxaJuro;
	}
	
	public double getCalculaFluxo(){
		return this.calculaFluxo;
	}
	public void setCalculaFluxo(double calculaFluxo){
		this.calculaFluxo = calculaFluxo;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
	public String getData(){
		return this.data;
	}
	public void setData(String data){
		this.data = data;
	}
	public double getTotal(){
		return this.total;
	}
	public void setTotal(double total){
		this.total = total;
	}
	
	public int getID(){
		return this.id;
	}
	public List<Prazo> getContasPrazo(){
		return this.contasPrazo;
	}
	public void setContasPrazo(List<Prazo> contas){
		System.out.println("SIZE: "+contas.size());
		this.contasPrazo= contas;
	}
	public List<Emprestimo> getContasEmprestimo(){
		return this.contasEmprestimo;
	}
	public void setContasEmprestimo(List<Emprestimo> contas){
		this.contasEmprestimo= contas;
	}
	
	public List<Conta> getContas(){
		return this.contas;
	}
	public void setContas(List<Conta> contas){
		this.contas= contas;
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
	public String getLimite(){
		return this.prazoLimite;
	}
	public void setLimite(String prazoLimite){
		this.prazoLimite=prazoLimite;
	}
}
