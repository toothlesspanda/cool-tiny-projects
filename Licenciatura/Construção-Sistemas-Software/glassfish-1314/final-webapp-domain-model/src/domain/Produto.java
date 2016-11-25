package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.NamedQuery;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * A representa��o de um registo da tabela de produtos como um
 * objeto em mem�ria.
 *	
 * Chamadas de aten��o:
 * 1. Ver classe ClientesRowGateway
 * 
 * @author fmartins
 *
 */
@Entity
@NamedQuery(name="Produto.findByCodProd", query="SELECT p FROM Produto p WHERE p.codProd = :codProd")
public class Produto {

	@Id @GeneratedValue(strategy = SEQUENCE)
	private int idProduto;
	private int codProd;
	private String descricao;
	private double valorUnitario;
	private double qty;
	private boolean elegivelDesconto;
	private Grandeza grandeza;
	
	public Produto(int codProd, String descricao, double valorUnitario,
			double qty, boolean elegivelDesconto, Grandeza grandeza) {
		this.codProd = codProd;
		this.descricao = descricao;
		this.valorUnitario = valorUnitario;
		this.qty = qty;
		this.elegivelDesconto = elegivelDesconto;
		this.grandeza = grandeza;
	}

	public Produto() {
	}
	
	// Getters e setters para os atributos
	public int getCodProd() {
		return codProd;
	}

	public String getDescricao() {
		return descricao;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public double getQty() {
		return qty;
	}

	public boolean isElegivelDesconto() {
		return elegivelDesconto;
	}

	public void setElegivelDesconto(boolean elegivelDesconto) {
		this.elegivelDesconto = elegivelDesconto;
	}

	public Grandeza getIdGrandeza() {
		return grandeza;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}
		
}
