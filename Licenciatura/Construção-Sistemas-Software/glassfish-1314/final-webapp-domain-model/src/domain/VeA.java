package domain;

import javax.persistence.EntityManagerFactory;

import domain.controllers.ClientesHandler;
import domain.controllers.DescontosHandler;
import domain.controllers.VendasHandler;

public class VeA {

	private CatalogoClientes catalogoClientes;
	private CatalogoDescontos catalogoDescontos;
	private ClientesHandler clientesHandler;
	private VendasHandler vendasHandler;
	private CatalogoVendas catalogoVendas;
	private CatalogoProdutos catalogoProdutos;
	private DescontosHandler descontosHandler;
	
	public VeA(EntityManagerFactory emf) {
		this.catalogoClientes = new CatalogoClientes(emf);
		this.catalogoDescontos = new CatalogoDescontos(emf);
		this.catalogoVendas = new CatalogoVendas(emf);
		this.catalogoProdutos = new CatalogoProdutos(emf);
		this.clientesHandler = new ClientesHandler(catalogoClientes, catalogoDescontos);
		this.vendasHandler = new VendasHandler(catalogoVendas, catalogoClientes, catalogoProdutos);
		this.descontosHandler = new DescontosHandler(catalogoDescontos);
	}
	
	public ClientesHandler getClientesHandler () {
		return clientesHandler;
	}

	public VendasHandler getVendasHandler() {
		return vendasHandler;
	}
	
	public DescontosHandler getDescontosHandler () {
		return descontosHandler;
	}
}
