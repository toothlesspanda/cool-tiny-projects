package application.web.controllers.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import application.web.datapresentation.NovoClienteHelper;
import domain.ApplicationException;
import domain.VeA;
import domain.controllers.ClientesHandler;

/**
 * Handles the criar cliente event.
 * If the request is valid, it dispatches it to the domain model (the application's business logic)
 * Notice as well the use of an helper class to assist in the jsp response. 
 * 
 * @author fmartins
 *
 */
@WebServlet("/Internal/criarCliente")
public class CriarClientEvent extends Event {
	
	private static final long serialVersionUID = 8104190499584788489L;
	
	@Override
	protected void process() throws ServletException, IOException {
		VeA app = (VeA) getServletContext().getAttribute("app");
		ClientesHandler clientesHandler = app.getClientesHandler();

		NovoClienteHelper nch = criarHelper(app);
		request.setAttribute("helper", nch);
		
		if (validaInput(nch)) {
			try {
				clientesHandler.adicionarCliente(intValue(nch.getNpc()), 
						nch.getDesignacao(), intValue(nch.getTelefone()), intValue(nch.getTipoDesconto()));
				nch.clearFields();
				nch.addMessage("Cliente criado com sucesso.");
			} catch (ApplicationException e) {
				nch.addMessage("Erro ao criar cliente: " + e.getMessage());
			}
		} else
			nch.addMessage("Erro ao validar cliente");
		
		request.getRequestDispatcher("../novoCliente.jsp").forward(request, response);
	}

	
	private boolean validaInput(NovoClienteHelper nch) {
		// validar a designa��o
		boolean result = isFilled(nch, nch.getDesignacao(), "� obrigat�rio preencher a Designa��o.");
		
		// validar npc
		result &= isFilled(nch, nch.getNpc(), "� obrigat�rio preencher o N�mero de pessoa colectiva")
				 			&& isInt(nch, nch.getNpc(), "N�mero de pessoal colectiva cont�m caracteres n�o num�ricos");
		
		// validar telefone
		if (!nch.getTelefone().equals(""))
			result &= isInt(nch, nch.getTelefone(), "N�mero de telefone cont�m caracteres n�o num�ricos");

		// validar desconto
		result &= isFilled(nch, nch.getTipoDesconto(), "� obrigat�rio preencher o Tipo de desconto") 
					&& isInt(nch, nch.getTipoDesconto(), "Tipo de desconto cont�m caracteres n�o num�ricos");

		return result;
	}


	private NovoClienteHelper criarHelper(VeA app) {
		NovoClienteHelper nch = new NovoClienteHelper();
		nch.setDescontosHandler(app.getDescontosHandler());

		// obter e guardar informa��o
		nch.setDesignacao(request.getParameter("designacao"));
		nch.setNpc(request.getParameter("npc"));
		nch.setTelefone(request.getParameter("telefone"));
		nch.setTipoDesconto(request.getParameter("desconto"));
		
		return nch;
	}	
}
