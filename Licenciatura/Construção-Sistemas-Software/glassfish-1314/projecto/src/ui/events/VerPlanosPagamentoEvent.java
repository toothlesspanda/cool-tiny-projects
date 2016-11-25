package ui.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import ui.helpers.PlanosPagamentoHelper;

@WebServlet("/Internal/verPlanosPagamento")
public class VerPlanosPagamentoEvent extends Event {

	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		
		PlanosPagamentoHelper pph = new PlanosPagamentoHelper();
		Activus app = (Activus) servletContext.getAttribute("app");
		pph.setPlanos(app.getContasHandler().getPlanos());
		request.setAttribute("helper", pph);
		request.getRequestDispatcher("../verPlanosPagamento.jsp").forward(request, response);

	}
}