package ui.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import ui.helpers.ContaHelper;

@WebServlet("/Internal/verContasEmprestimo")
public class VerContasEmprestimoEvent extends Event {

	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		ContaHelper ch = new ContaHelper();
		Activus app = (Activus) servletContext.getAttribute("app");
		ch.setContasEmprestimo(app.getContasHandler().getContasEmprestimo());
		
		if(ch.getContasEmprestimo().isEmpty()){
			ch.addMessage("Não existem emprestimos");
		}
		
		request.setAttribute("helper", ch);
		request.getRequestDispatcher("../verContasEmprestimo.jsp").forward(request, response);

	}
}
