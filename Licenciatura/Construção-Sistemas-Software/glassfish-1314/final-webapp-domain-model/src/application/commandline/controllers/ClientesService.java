package application.commandline.controllers;

import domain.ApplicationException;
import domain.controllers.ClientesHandler;

/**
 * Handles client's transactions. 
 * Each public method implements a transaction script.
 * 
 * @author fmartins
 *
 */
public class ClientesService {
	
	private ClientesHandler clientesHandler;

	public ClientesService(ClientesHandler clientesHandler) {
		this.clientesHandler = clientesHandler;
	}
	
	public void adicionarCliente (int npc, String designacao, int telefone, int tipoDesconto) 
			throws ApplicationException {
		clientesHandler.adicionarCliente(npc, designacao, telefone, tipoDesconto);
	}	
}
