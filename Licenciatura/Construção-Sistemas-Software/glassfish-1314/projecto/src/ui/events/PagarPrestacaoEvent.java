package ui.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import ui.helpers.PlanosPagamentoHelper;
import ui.helpers.PrestacaoHelper;

@WebServlet("/Internal/pagarPrestacao")
public class PagarPrestacaoEvent extends Event {

	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		
		Activus app = (Activus) servletContext.getAttribute("app");
		PrestacaoHelper ph = new PrestacaoHelper();
		request.setAttribute("helper", ph);
		try{
		
			app.getContasHandler().pagaPrestacao(Integer.parseInt(request.getParameter("id")));	
			//ph.setPrestacao(app.getContasHandler().getPrestacoesDoPlanoByID(1));	
			//nbh.addMessage("Banco criado com sucesso");
		}catch(ApplicationException e){
			//nbh.addMessage("Erro ao criar banco"+ e.getMessage());
		}
		response.sendRedirect("../index.html");
	}
}