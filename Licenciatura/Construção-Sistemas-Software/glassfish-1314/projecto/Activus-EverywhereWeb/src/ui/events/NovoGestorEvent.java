package ui.events;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import ui.helpers.BancoHelper;
import ui.helpers.GestorHelper;

@WebServlet("/Internal/novoGestor")
public class NovoGestorEvent extends Event {
	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		Activus app = (Activus) servletContext.getAttribute("app");
		//BancosHandler bancosHandler = app.getBancosHandler();
		BancoHelper nbh = new BancoHelper();
		GestorHelper gh = new GestorHelper(); 
		//NovoContaPrazoHelper ncph = new NovoContaPrazoHelper();
		nbh.setBancos(app.getBancosHandler().getBancos());
		request.setAttribute("helper", nbh);
		if(nbh.getBancos().isEmpty()){
			nbh.addMessage("Não existem bancos");
			nbh.addMessage("Adicione um banco para associar um novo gestor");
		}
		
		
		request.setAttribute("helper2", gh);
		request.getRequestDispatcher("../novoGestor.jsp").forward(request, response);
	}
}
