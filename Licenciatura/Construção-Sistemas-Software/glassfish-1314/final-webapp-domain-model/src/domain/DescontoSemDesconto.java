package domain;

import javax.persistence.Entity;

@Entity
public class DescontoSemDesconto extends Desconto {

	public DescontoSemDesconto() {
	}
	
	public DescontoSemDesconto (int tipoDesconto, String descricao) {
		super(tipoDesconto, descricao);
	}
	
	@Override
	public double getValorDesconto(Venda venda) {	
		return venda.getTotal();	
	}
	
}
