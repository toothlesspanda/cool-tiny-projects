package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


/**
 * @author Raposo
 * O melhor autor do mundo
 * nº 1 ao nível mundial
 *
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name="Banco.findBySIGLA", query="SELECT b FROM Banco b WHERE b.sigla = :sigla"),
	@NamedQuery(name="Banco.findByDESIGNACAO", query="SELECT b FROM Banco b WHERE b.designacao = :designacao"),
	@NamedQuery(name="Banco.findByID", query="SELECT b FROM Banco b WHERE b.idBanco = :idBanco"),
	@NamedQuery(name="Banco.findAll", query="SELECT b FROM Banco b"),
	@NamedQuery(name="Banco.removeByID", query="DELETE FROM Banco b WHERE b.idBanco = :idBanco"),
	@NamedQuery(name="Banco.removeByDesignacao", query="DELETE FROM Banco b WHERE b.designacao = :designacao")
	
})
	class Banco {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idBanco;
	@Column(nullable = false)
	private String sigla;
	@Column(unique = true,nullable = false)
	private String designacao;
	
	@OneToMany(cascade = {CascadeType.ALL},mappedBy = "banco")
	private List<Conta> listaContas;
	
	@OneToMany(cascade = {CascadeType.ALL},mappedBy="banco" )
	private List<Gestor> listaGestor;
	
	public Banco(){};
	
	public Banco(String sigla,String designacao){
		this.listaContas = new ArrayList<Conta>();
		this.listaGestor = new ArrayList<Gestor>();
		this.sigla = sigla;
		this.designacao = designacao;
	}

	public String getSigla() {
		return sigla;
	}

	public String getDesignacao() {
		return designacao;
	}

	public List<Conta> getListaContas() {
		return this.listaContas;
	}

	public void addListaContas(Conta conta) {
		this.listaContas.add(conta);
	}

	public List<Gestor> getListaGestor() {
		return this.listaGestor;
	}

	public void addListaGestor(Gestor gestor) {
		this.listaGestor.add(gestor);
	}
	public int getID(){
		return this.idBanco;
	}
	
}
