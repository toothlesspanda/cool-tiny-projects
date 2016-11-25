package ui.events;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import domain.GestoresHandler;
import ui.helpers.GestorHelper;

@WebServlet("/Internal/editarGestor")
public class EditarGestorEvent extends Event {
	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		Activus app = (Activus) servletContext.getAttribute("app");
		GestoresHandler gestoresHandler = app.getGestoresHandler();
		GestorHelper nbh = new GestorHelper();
		//NovoContaPrazoHelper ncph = new NovoContaPrazoHelper();
		//nbh.setBancos(app.getBancosHandler().getBancos());
		nbh.setGestores(gestoresHandler.getGestores());
		request.setAttribute("helper", nbh);
		
		int id = Integer.parseInt(request.getParameter("id"));
		String tlm = request.getParameter("telefone");
		String email = request.getParameter("email");
		
		if(tlm != null){
			int telefone = Integer.parseInt(tlm);
			gestoresHandler.updateGestorTelefone(id, telefone);
		}
		if(email != null){
			gestoresHandler.updateGestorEmail(id, email);
		}
		request.getRequestDispatcher("../editarGestor.jsp").forward(request, response);
	}
}
