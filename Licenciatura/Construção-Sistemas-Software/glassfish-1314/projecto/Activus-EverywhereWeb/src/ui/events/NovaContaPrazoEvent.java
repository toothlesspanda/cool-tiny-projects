package ui.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import ui.helpers.BancoHelper;

@WebServlet("/Internal/novaContaPrazo")
public class NovaContaPrazoEvent extends Event {
	
	
	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		Activus app = (Activus) servletContext.getAttribute("app");
		//BancosHandler bancosHandler = app.getBancosHandler();
		BancoHelper nbh = new BancoHelper();
		//NovoContaPrazoHelper ncph = new NovoContaPrazoHelper();
		nbh.setBancos(app.getBancosHandler().getBancos());
		if(nbh.getBancos().isEmpty())
			nbh.addMessage("Não existem bancos, adiciona primeiro um banco antes de tentar criar uma conta");
		
		
		request.setAttribute("helper", nbh);
		
		request.getRequestDispatcher("../novaContaPrazo.jsp").forward(request, response);
		

	}
	
	

}
