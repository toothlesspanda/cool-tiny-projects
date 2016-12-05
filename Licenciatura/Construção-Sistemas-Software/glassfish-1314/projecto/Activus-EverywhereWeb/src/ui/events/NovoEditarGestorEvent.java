package ui.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import ui.helpers.GestorHelper;

@WebServlet("/Internal/novoEditarGestor")
public class NovoEditarGestorEvent extends Event {
	
	
	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		Activus app = (Activus) servletContext.getAttribute("app");
		//BancosHandler bancosHandler = app.getBancosHandler();
		GestorHelper nbh = new GestorHelper();
		//NovoContaPrazoHelper ncph = new NovoContaPrazoHelper();
		//nbh.setBancos(app.getBancosHandler().getBancos());
		nbh.setGestores(app.getGestoresHandler().getGestores());
		request.setAttribute("helper", nbh);
		
		request.getRequestDispatcher("../editarGestor.jsp").forward(request, response);
		

	}
	
	

}