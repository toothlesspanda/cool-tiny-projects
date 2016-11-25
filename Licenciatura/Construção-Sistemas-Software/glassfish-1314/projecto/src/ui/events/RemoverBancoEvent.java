package ui.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import domain.BancosHandler;
import ui.helpers.BancoHelper;

@WebServlet("/Internal/removerBanco")
public class RemoverBancoEvent extends Event {
	
	
	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		Activus app = (Activus) servletContext.getAttribute("app");
		BancosHandler bancosHandler = app.getBancosHandler();
		BancoHelper nbh = new BancoHelper();
		validaInput(nbh);
		request.setAttribute("helper", nbh);
		try{
			bancosHandler.removeBanco(nbh.getDesignacao());
			nbh.removeBanco(nbh.getDesignacao());
			nbh.addMessage("Remoção de banco com sucesso");
		}catch(ApplicationException e){
			nbh.addMessage("Erro ao remover banco"+ e.getMessage());
		}
		
		request.getRequestDispatcher("../verBancos.jsp").forward(request, response);

	}
	
	private void validaInput(BancoHelper nbh){
		nbh.setDesignacao(request.getParameter("designacao"));
	}

}
