package domain;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import domain.interfaces.IVenda;

/**
 * A representa��o de um registo da tabela de vendas como um
 * objeto em mem�ria.
 *	
 * @author fmartins
 *
 */
@Entity 
public class Venda implements IVenda {

	@Id	@GeneratedValue(strategy = SEQUENCE) private int idVenda;
	@Temporal(TemporalType.DATE) private Date data;
	private boolean emAberto;
	private Cliente cliente;
	@OneToMany(cascade = ALL)
	@JoinColumn(name = "produto", referencedColumnName = "idVenda") 
	private List<ProdutoVenda> produtosVenda;
		
	
	public Venda () {
	}
	
	public Venda(Date data, Cliente cliente) {
		this.data = data;
		this.cliente = cliente;
		this.emAberto = true;
		this.produtosVenda = new LinkedList<ProdutoVenda>();
	}

	// Getters e setters para os atributos
	public Date getData() {
		return data;
	}

	public double getTotal() {
		double total = 0;
		for (ProdutoVenda pv : produtosVenda)
			total += pv.getSubTotal();
		return total;
	}

	public double getTotalElegivel() {
		double total = 0;
		for (ProdutoVenda pv : produtosVenda)
			total += pv.getSubtotalElegivel();
		return total;
	}
	
	public boolean getEmAberto() {
		return emAberto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	
	public boolean isEmAberto() {
		return emAberto;
	}

	// @pre isEmAberto()
	public ProdutoVenda acrescentarProdutoAVenda(Produto produto, double qty) 
			throws ApplicationException {
		ProdutoVenda pv = new ProdutoVenda(produto, qty);
		produtosVenda.add(pv);
		return pv;
	}
	
	public double getValorDoDesconto () throws ApplicationException {
		Desconto desconto = cliente.getDesconto();
		return desconto.getValorDesconto(this);
	}
}
