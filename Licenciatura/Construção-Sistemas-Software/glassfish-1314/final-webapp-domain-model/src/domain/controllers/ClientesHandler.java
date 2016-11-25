package domain.controllers;

import domain.ApplicationException;
import domain.CatalogoClientes;
import domain.CatalogoDescontos;
import domain.Desconto;

/**
 * Handles client's transactions. 
 * Each public method implements a transaction script.
 * 
 * @author fmartins
 *
 */
public class ClientesHandler {
	
	private CatalogoClientes catalogoClientes;
	private CatalogoDescontos catalogoDescontos;
	
	public ClientesHandler(CatalogoClientes catalogoClientes, CatalogoDescontos catalogoDescontos) {
		this.catalogoClientes = catalogoClientes;
		this.catalogoDescontos = catalogoDescontos;
	}

	public void adicionarCliente (int npc, String designacao, int telefone, int tipoDesconto) 
			throws ApplicationException {
		Desconto desconto = catalogoDescontos.getDesconto(tipoDesconto);
		catalogoClientes.adicionarCliente(npc, designacao, telefone, desconto);
	}	
}
