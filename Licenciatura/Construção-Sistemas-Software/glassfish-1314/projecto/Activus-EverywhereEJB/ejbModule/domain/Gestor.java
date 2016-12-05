package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries({ 
	@NamedQuery(name="Gestor.findALL", query="SELECT g FROM Gestor g"),
	@NamedQuery(name="Gestor.findByEMAIL", query="SELECT g FROM Gestor g WHERE g.email = :email"),
	@NamedQuery(name="Gestor.findByNAME", query="SELECT g FROM Gestor g WHERE g.nome = :nome"),
	@NamedQuery(name="Gestor.findByBanco", query="SELECT g FROM Gestor g WHERE g.banco.idBanco = :idBanco"),
	@NamedQuery(name="Gestor.findByID", query="SELECT g FROM Gestor g WHERE g.idGestor = :idGestor"),
	@NamedQuery(name="Gestor.updateEMAIL", query="UPDATE Gestor g SET g.email = :email WHERE g.idGestor = :idGestor"),
	@NamedQuery(name="Gestor.updateTELEFONE", query="UPDATE Gestor g SET g.telefone = :telefone WHERE g.idGestor = :idGestor"),
	@NamedQuery(name="Gestor.delete", query="DELETE FROM Gestor g WHERE g.idGestor = :idGestor"),
	@NamedQuery(name="Gestor.findContas", query="SELECT c FROM Conta c,Gestor g WHERE g.idGestor = :idGestor") 
	
})
	class Gestor {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idGestor;
	
	@Column(name="email", nullable=false,unique = true)
	private String email;
	
	@Column(name="nome", nullable=false)
	private String nome;
	
	@Column(name="telefone", nullable=false)
	private int telefone;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "gestor")
	private List<Conta> contas;
	
	@ManyToOne
	private Banco banco;
	
	public Gestor() { }
	
	public Gestor(String email,String nome,int telefone,Banco b){
		this.email = email;
		this.nome = nome;
		this.telefone = telefone;
		this.contas = new ArrayList<Conta>();
		this.banco = b;
		
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public int getTelefone() {
		return telefone;
	}

	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public void addConta(Conta c){
		c.setGestor(this);
		this.contas.add(c);
	}
	public List<Conta> getContas(){
		return this.contas;
	}
	public int getID(){
		return this.idGestor;
	}
	public int getIDBanco(){
		return this.banco.getID();
	}
	
	public String getBanco(){
		return this.banco.getSigla();
	}

}
