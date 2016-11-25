package domain;

import javax.persistence.Entity;

@Entity
public class DescontoElegivel extends Desconto {

	private double percentagem;

	public DescontoElegivel() {
	}
	
	public DescontoElegivel(int tipoDesconto, String descricao, double percentagem) {
		super (tipoDesconto, descricao);
		this.percentagem = percentagem;
	}

	@Override
	public double getValorDesconto(Venda venda) {
		double totalElegivel = venda.getTotalElegivel();
		return totalElegivel * percentagem;
	}
	
}
