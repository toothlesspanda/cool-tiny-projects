package ui.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import ui.helpers.BancoHelper;

@WebServlet("/Internal/verBancos")
public class VerBancosEvent extends Event {

	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		BancoHelper nbh = new BancoHelper();
		Activus app = (Activus) servletContext.getAttribute("app");
		nbh.setBancos(app.getBancosHandler().getBancos());
		request.setAttribute("helper", nbh);
		
		if(nbh.getBancos().isEmpty())
			nbh.addMessage("Não existem bancos");
		
		request.getRequestDispatcher("../verBancos.jsp").forward(request, response);

		
		
	}
}
