package domain;

import javax.persistence.Entity;

@Entity
public class DescontoPTotal extends Desconto {

	private double limite;
	private double percentagem;

	public DescontoPTotal() {
	}
	
	public DescontoPTotal(int tipoDesconto, String descricao, double limite, double percentagem) {
		super (tipoDesconto, descricao);
		this.limite = limite;
		this.percentagem = percentagem;
	}

	@Override
	public double getValorDesconto(Venda venda) {
		double totalVenda = venda.getTotal();
		return totalVenda > limite ? totalVenda * percentagem : 0;
	}
	
}
