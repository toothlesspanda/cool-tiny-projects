package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * A representa��o de um registo da tabela de produtosVenda como um
 * objeto em mem�ria.
 *	
 * Chamadas de aten��o:
 * 1. Ver coment�rios na classe ClientesRowGateway
 * 
 * @author fmartins
 *
 */
@Entity
public class ProdutoVenda {
	
	@Id @GeneratedValue(strategy = SEQUENCE) private int idProdutoVenda;
	private Produto produto;
	private double qty;
	
	public ProdutoVenda() {
	}
	
	public ProdutoVenda (Produto produto, double qty) {
		this.produto = produto;
		this.qty = qty;
	}

	// Getters e setters para os atributos
	public Produto getProduto() {
		return produto;
	}

	public double getQty() {
		return qty;
	}

	public double getSubTotal() {
		return qty * produto.getValorUnitario();
	}

	public double getSubtotalElegivel() {
		return produto.isElegivelDesconto() ? getSubTotal() : 0;
	}
}
