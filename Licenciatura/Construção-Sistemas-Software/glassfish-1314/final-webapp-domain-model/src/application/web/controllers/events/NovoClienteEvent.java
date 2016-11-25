package application.web.controllers.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import application.web.datapresentation.NovoClienteHelper;
import domain.VeA;


/**
 * Handles the novo cliente event
 * 
 * @author fmartins
 *
 */
@WebServlet("/Internal/novoCliente")
public class NovoClienteEvent extends Event {
	
	private static final long serialVersionUID = 1153275246947791355L;

	@Override
	protected void process() throws ServletException, IOException {
		VeA app = (VeA) getServletContext().getAttribute("app");

		NovoClienteHelper nch = new NovoClienteHelper();
		nch.setDescontosHandler(app.getDescontosHandler());
		request.setAttribute("helper", nch);
		request.getRequestDispatcher("../novoCliente.jsp").forward(request, response);
	}

}
