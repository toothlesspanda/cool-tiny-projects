package domain;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import domain.interfaces.IDesconto;

@Entity
@NamedQueries({
	@NamedQuery(name="Desconto.findByTipo", query="SELECT d FROM Desconto d WHERE d.tipoDesconto = :tipoDesconto"),
	@NamedQuery(name="Desconto.findAll", query="SELECT d FROM Desconto d")
})
@Inheritance(strategy = SINGLE_TABLE)
public abstract class Desconto implements IDesconto {
	@Id	private int tipoDesconto;
	private String descricao;
	
	public Desconto(int tipoDesconto, String descricao) {
		this.tipoDesconto = tipoDesconto;
		this.descricao = descricao;
	}

	public Desconto() {
	}
	
	/* (non-Javadoc)
	 * @see domain.IDesconto#getDescricao()
	 */
	@Override
	public String getDescricao() {
		return descricao;
	}

	/* (non-Javadoc)
	 * @see domain.IDesconto#getTipoDesconto()
	 */
	@Override
	public int getTipoDesconto() {
		return tipoDesconto;
	}

	/* (non-Javadoc)
	 * @see domain.IDesconto#getValorDesconto(domain.Venda)
	 */
	public abstract double getValorDesconto(Venda venda);
}
 