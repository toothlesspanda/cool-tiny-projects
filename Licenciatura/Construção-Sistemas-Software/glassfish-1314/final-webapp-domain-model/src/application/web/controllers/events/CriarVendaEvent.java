package application.web.controllers.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import application.web.datapresentation.EfectuarVendaHelper;
import domain.ApplicationException;
import domain.VeA;
import domain.controllers.VendasHandler;

/**
 * Handles the criar cliente event.
 * If the request is valid, it dispatches it to the domain model (the application's business logic)
 * Notice as well the use of an helper class to assist in the jsp response. 
 * 
 * @author fmartins
 *
 */
@WebServlet("/Internal/criarVenda")
public class CriarVendaEvent extends Event {

	private static final long serialVersionUID = 439512602423110424L;
	
	@Override
	protected void process() throws ServletException, IOException {
		VeA app = (VeA) getServletContext().getAttribute("app");
		VendasHandler vendasHandler = app.getVendasHandler();

		EfectuarVendaHelper nvh = criarHelper();
		request.setAttribute("helper", nvh);
		
		if (validaInput(nvh)) {
			try {
				nvh.setVenda(vendasHandler.novaVenda(intValue(nvh.getNpc()))); 
				//nvh.clearFields();
				nvh.addMessage("Venda criado com sucesso.");
			} catch (ApplicationException e) {
				nvh.addMessage("Erro ao criar venda: " + e.getMessage());
			}
		} else
			nvh.addMessage("Erro ao validar venda");
		
		request.getRequestDispatcher("../efectuarVenda.jsp").forward(request, response);
	}

	
	private boolean validaInput(EfectuarVendaHelper nvh) {		
		// validar npc
		boolean result = isFilled(nvh, nvh.getNpc(), "� obrigat�rio preencher o N�mero de pessoa colectiva")
				 			&& isInt(nvh, nvh.getNpc(), "N�mero de pessoal colectiva cont�m caracteres n�o num�ricos");
		
		return result;
	}	
	
	private EfectuarVendaHelper criarHelper() {
		EfectuarVendaHelper nvh = new EfectuarVendaHelper();

		// obter e guardar informa��o
		nvh.setNpc(request.getParameter("npc"));
		
		return nvh;
	}	

}
