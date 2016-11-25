package application.web.controllers;

import javax.servlet.annotation.WebServlet;

/**
 * Handles the criar cliente use case.
 * 
 *   O                          ------------
 *  -|-                         | : System |  
 *  / \                         ------------
 * : Employee                        |  
 *   |                               |
 *   |    novoCliente()              |
 *   | ----------------------------> |
 *   |                               |
 *   | criarCliente (nome, npc, telf |
 *   |         tipoDesconto)         |
 *   | ----------------------------> |
 *   |                               |
 *   |             ok                |
 *   | <--------------------------   |
 *   |                               |
 * 
 * 
 * @author fmartins
 */
@WebServlet("/CriarClienteHandler/*")
public class CriarClienteHandler extends UseCaseHandler {

	private static final long serialVersionUID = -6880793766141064852L;

	/* (non-Javadoc)
	 * @see ui.UIUserCaseHandler#fillActions()
	 */
	@Override
	protected void fillActions() {
		actionHandlers.put("/novoCliente", "/Internal/novoCliente");
		actionHandlers.put("/criarCliente", "/Internal/criarCliente");
	}
}
