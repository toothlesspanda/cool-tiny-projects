package ui.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import ui.helpers.PlanosPagamentoHelper;
import ui.helpers.PrestacaoHelper;

@WebServlet("/Internal/verPrestacoes")
public class VerPrestacoesEvent extends Event {

	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		
		Activus app = (Activus) servletContext.getAttribute("app");
		PlanosPagamentoHelper pph = new PlanosPagamentoHelper();
		PrestacaoHelper ph = new PrestacaoHelper();
		request.setAttribute("helper", pph);
		request.setAttribute("helper2", ph);
		try{
		
			ph.setPrestacao(app.getContasHandler().getPrestacoesDoPlanoByID(Integer.parseInt(request.getParameter("id"))));	
			//ph.setPrestacao(app.getContasHandler().getPrestacoesDoPlanoByID(1));	
			//nbh.addMessage("Banco criado com sucesso");
		}catch(ApplicationException e){
			//nbh.addMessage("Erro ao criar banco"+ e.getMessage());
		}
		request.getRequestDispatcher("../verPrestacoes.jsp").forward(request, response);

	}
}