package domain.controllers;

import domain.ApplicationException;
import domain.CatalogoDescontos;
import domain.Desconto;


public class DescontosHandler {
	private CatalogoDescontos catalogoDescontos;
	
	public DescontosHandler(CatalogoDescontos catalogoDescontos) {
		this.catalogoDescontos = catalogoDescontos;
	}
	
	public Iterable<Desconto> getDescontos() throws ApplicationException {
		return catalogoDescontos.getDescontos();
	}
	
}
