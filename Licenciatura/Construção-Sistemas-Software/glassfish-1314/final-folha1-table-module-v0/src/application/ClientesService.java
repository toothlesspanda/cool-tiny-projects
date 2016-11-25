package application;

import domain.ApplicationException;
import domain.Cliente;


public class ClientesService {
	
	public void adicionarCliente (int npc, String designacao, int telefone, int tipoDesconto) 
			throws ApplicationException {
		Cliente.INSTANCE.adicionarCliente(npc, designacao, telefone, tipoDesconto);
	}
}
