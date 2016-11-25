package domain;

import javax.persistence.EntityManagerFactory;

public class Activus {

	
	private BancosHandler bancosHandler;
	private ContasHandler contasHandler;
	private GestoresHandler gestoresHandler;
	private CatalogoGestores catalogoGestores;
	private CatalogoBancos catalogoBancos;
	private CatalogoContas catalogoContas;
	
	public Activus(EntityManagerFactory emf) {
		// TODO Auto-generated constructor stub
		this.catalogoBancos = new CatalogoBancos(emf);
		this.catalogoGestores = new CatalogoGestores(emf);
		this.catalogoContas = new CatalogoContas(emf);
		this.bancosHandler = new BancosHandler(catalogoBancos, catalogoGestores,catalogoContas);
		this.contasHandler = new ContasHandler(catalogoContas);
		this.gestoresHandler = new GestoresHandler(catalogoGestores);
		
	}
	
	public BancosHandler getBancosHandler(){
		return this.bancosHandler;
	}
	
	public ContasHandler getContasHandler(){
		return this.contasHandler;
	}

	public GestoresHandler getGestoresHandler() {
		return gestoresHandler;
	}


}
