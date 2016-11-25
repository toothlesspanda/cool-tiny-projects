package application.web.controllers.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import application.web.datapresentation.EfectuarVendaHelper;


/**
 * Handles the novo cliente event
 * 
 * @author fmartins
 *
 */
@WebServlet("/Internal/novaVenda")
public class NovaVendaEvent extends Event {

	private static final long serialVersionUID = 7548812337957317432L;

	@Override
	protected void process() throws ServletException, IOException {
		EfectuarVendaHelper nvh = new EfectuarVendaHelper();
		request.setAttribute("helper", nvh);
		request.getRequestDispatcher("../efectuarVenda.jsp").forward(request, response);
	}

}
