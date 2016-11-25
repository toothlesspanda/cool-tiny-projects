package ui.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import ui.helpers.BancoHelper;

@WebServlet("/Internal/verMapaFluxos")
public class VerMapaFluxosEvent extends Event {

	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		BancoHelper nbh = new BancoHelper();
		Activus app = (Activus) servletContext.getAttribute("app");
		
		//app.getContasHandler().
		
		nbh.setBancos(app.getBancosHandler().getBancos());
		request.setAttribute("helper", nbh);
		request.getRequestDispatcher("../verBancos.jsp").forward(request, response);

	}
}
