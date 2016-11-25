package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Grandeza {

	@Id private int idGrandeza;
	private String descricao;
	private String unidades;

	public Grandeza(int idGrandeza, String descricao, String unidades) {
		this.idGrandeza = idGrandeza;
		this.descricao = descricao;
		this.unidades = unidades;
	}

	public Grandeza() {
	}

	public int getIdGrandeza() {
		return idGrandeza;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getUnidades() {
		return unidades;
	}
	
}
