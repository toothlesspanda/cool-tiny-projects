package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class Banco {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idBanco;
	@Column(nullable = false)
	private String sigla;
	@Column(unique = true,nullable = false)
	private String designacao;
	
	//@OneToMany
	//@JoinColumn (nullable = false, name = "fkConta")
	@OneToMany(cascade = {CascadeType.ALL})
	//@JoinColumn(name = "conta", referencedColumnName = "idBanco") 
	private List<Conta> listaContas;
	//@OneToMany
	//@JoinColumn (nullable = false, name = "fkGestor")
	@OneToMany(cascade = {CascadeType.ALL},mappedBy="banco" )
	//@JoinColumn(name = "gestor", referencedColumnName = "idBanco") 
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

	/**public void addConta(int numero,Conta conta){
		//this.contas.put(numero,conta);
	}**/

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
