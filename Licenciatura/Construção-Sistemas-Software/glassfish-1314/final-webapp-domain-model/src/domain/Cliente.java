package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 * A representa��o de um registo da tabela de clientes como um
 * objeto em mem�ria.
 *	
 * @author fmartins
 *
 */
@Entity
@NamedQuery(name="Clientes.findByNPC", query="SELECT c FROM Cliente c WHERE c.npc = :npc")
public class Cliente {

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE) private int idCliente;
	@Column(nullable = false, unique = true) private int npc;
	@Column(nullable = false) private String designacao;
	private int telefone;
	@OneToOne @JoinColumn(nullable = false) private Desconto desconto;
		
	public Cliente() {
	}
	
	public Cliente(int npc, String designacao, int telefone, Desconto desconto) 
			throws ApplicationException {
		if (!NPCValido (npc))
			throw new ApplicationException ("N�mero de pessoa colectiva inv�lido!");
		this.npc = npc;
		this.designacao = designacao;
		this.telefone = telefone;
		this.desconto = desconto;
	}

	public int getNpc() {
		return npc;
	}

	public String getDesignacao() {
		return designacao;
	}

	public int getTelefone() {
		return telefone;
	}

	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}

	public Desconto getDesconto() {
		return desconto;
	}

	public void setDesconto(Desconto desconto) {
		this.desconto = desconto;
	}

	/**
	 * Checks if a NPC number is valid.
	 * 
	 * @param npc The number to check.
	 * @return Whether the NPC is valid. 
	 */
	private boolean NPCValido(int npc) {
		// se n�o tem 9 d�gitos, erro!
		if (npc < 100000000 || npc > 999999999)
			return false;
		
		// se o primeiro n�mero n�o � 1, 2, 5, 6, 8, 9, erro!
		int primeiroDigito = npc / 100000000;
		if (primeiroDigito != 1 && primeiroDigito != 2 && 
			primeiroDigito != 5 && primeiroDigito != 6 &&
			primeiroDigito != 8 && primeiroDigito != 9)
			return false;
		
		// validar a congru�ncia modulo 11.
		int soma = 0;
		int checkDigit = npc % 10;
		npc /= 10;
		
		for (int i = 2; i < 10 && npc != 0; i++) {
			soma += npc % 10 * i;
			npc /= 10;
		}
		
		int checkDigitCalc = 11 - soma % 11;
		if (checkDigitCalc == 10)
			checkDigitCalc = 0;
		return checkDigit == checkDigitCalc;
	}
}
