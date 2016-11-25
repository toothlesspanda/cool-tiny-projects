package application;

import services.persistence.PersistenceException;
import services.persistence.RecordNotFoundException;
import services.persistence.rowDataGateway.ClientesRowGateway;

/**
 * Handles client's transactions. 
 * Each public method implements a transaction script.
 * 
 * @author fmartins
 *
 */
public class ClientesService {
	
	/**
	 * Add a new customer. It checks that there is no other customer in the database
	 * with the same NPC
	 * @param npc The NPC of the customer
	 * @param designacao The customer's name
	 * @param telefone The customer's phone 
	 * @param tipoDesconto The type of discount applicable for the customer
	 * @throws ApplicationException When the NPC is already in the database, when the NPC number is
	 * invalid (we check according to the Portuguese legislation), or when there is some unexpected
	 * SQL error.
	 */
	public void adicionarCliente (int npc, String designacao, int telefone, int tipoDesconto) 
			throws ApplicationException {
		// verificar que o nœmero de pessoa colectiva Ž œnico 
		try {
			ClientesRowGateway.getClientePorNPC(npc);
		} catch (RecordNotFoundException e) { // se o cliente n‹o existe, tenta adicionar.
			// verifica se o cliente tem o contribu’nte v‡lido
			if (!NPCValido (npc))
				throw new ApplicationException ("Numero de pessoa colectiva invalido!");
				
			// guardar a informa�‹o na base de dados
			try {
				ClientesRowGateway novoCliente = new ClientesRowGateway ();
				novoCliente.setNpc(npc);
				novoCliente.setDesignacao(designacao);
				novoCliente.setTelefone(telefone);
				novoCliente.setIdDesconto(tipoDesconto);
				novoCliente.insert();
			} catch (PersistenceException e1) {
				throw new ApplicationException ("Erro ao persistir o cliente!", e1);
			}
			return;
		} catch (PersistenceException e) {
			throw new ApplicationException ("Erro interno!", e);
		}
			
		throw new ApplicationException ("Cliente com NPC:" + npc + " ja' existe!");
	}

	
	/**
	 * Checks if a NPC number is valid.
	 * 
	 * @param npc The number to check.
	 * @return Whether the NPC is valid. 
	 */
	private boolean NPCValido(int npc) {
		// se n‹o tem 9 d’gitos, erro!
		if (npc < 100000000 || npc > 999999999)
			return false;
		
		// se o primeiro nœmero n‹o Ž 1, 2, 5, 6, 8, 9, erro!
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
