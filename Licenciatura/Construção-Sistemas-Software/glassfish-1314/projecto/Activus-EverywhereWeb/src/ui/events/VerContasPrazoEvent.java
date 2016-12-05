package ui.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import ui.helpers.ContaHelper;

@WebServlet("/Internal/verContasPrazo")
public class VerContasPrazoEvent extends Event {

	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		ContaHelper ch = new ContaHelper();
		Activus app = (Activus) servletContext.getAttribute("app");
		ch.setContasPrazo(app.getContasHandler().getContasPrazo());
		request.setAttribute("helper", ch);
		
		if(ch.getContasPrazo().isEmpty()){
			ch.addMessage("Não existem contas a prazo");
		}
		
		request.getRequestDispatcher("../verContasPrazo.jsp").forward(request, response);

	}
}
