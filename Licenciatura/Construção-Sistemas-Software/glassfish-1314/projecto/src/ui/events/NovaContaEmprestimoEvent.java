package ui.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import ui.helpers.BancoHelper;

@WebServlet("/Internal/novaContaEmprestimo")
public class NovaContaEmprestimoEvent extends Event {
	
	
	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		Activus app = (Activus) servletContext.getAttribute("app");
		//BancosHandler bancosHandler = app.getBancosHandler();
		BancoHelper nbh = new BancoHelper();
		//NovoContaPrazoHelper ncph = new NovoContaPrazoHelper();
		nbh.setBancos(app.getBancosHandler().getBancos());
		request.setAttribute("helper", nbh);
		
		request.getRequestDispatcher("../novaContaEmprestimo.jsp").forward(request, response);
		

	}
	
	

}
