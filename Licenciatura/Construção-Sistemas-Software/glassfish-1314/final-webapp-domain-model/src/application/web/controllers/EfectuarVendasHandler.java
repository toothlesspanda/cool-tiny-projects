package application.web.controllers;

import javax.servlet.annotation.WebServlet;

/**
 * Handles the efectuar venda use case.
 *	
 * @author fmartins
 */
@WebServlet("/EfectuarVendasHandler/*")
public class EfectuarVendasHandler extends UseCaseHandler {

	private static final long serialVersionUID = -6880793766141064852L;

	/* (non-Javadoc)
	 * @see ui.UIUserCaseHandler#fillActions()
	 */
	@Override
	protected void fillActions() {
		actionHandlers.put("/novaVenda", "/Internal/novaVenda");
		actionHandlers.put("/criarVenda", "/Internal/criarVenda");
	}
}
