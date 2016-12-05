package ui.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import domain.BancosHandler;
import ui.helpers.BancoHelper;
import ui.helpers.ContaHelper;
import ui.helpers.GestorHelper;

@WebServlet("/Internal/prazo")
public class PrazoEvent extends Event {
	
	
	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		Activus app = (Activus) servletContext.getAttribute("app");
		BancosHandler bancosHandler = app.getBancosHandler();
		BancoHelper nbh = new BancoHelper();
		
		
		
		nbh.setBancos(app.getBancosHandler().getBancos());
		request.setAttribute("helper", nbh);
		
		
		try{
			
			
			String id = request.getParameter("id");
			if(id == null || id.equals("")){
				nbh.clearMessages();
				nbh.addMessage("Não existem bancos, adiciona primeiro um banco antes de tentar criar uma conta");
				request.getRequestDispatcher("../novaContaPrazo.jsp").forward(request, response);
				return;
			}
			else{
				GestorHelper gh = new GestorHelper();
				ContaHelper ncph = new ContaHelper();
				request.setAttribute("helper2", gh);
				request.setAttribute("helper3", ncph);
				nbh.setID(Integer.parseInt(request.getParameter("id")));
				gh.setIDBanco(nbh.getID());
				
				//ncph.setContasEmprestimo(app.getContasHandler().getContasEmprestimoDoBanco(nbh.getID()));
				gh.setGestores(bancosHandler.getBanco(nbh.getID()).getListaGestor());
			}
			
			/**Calendar c = Calendar.getInstance();
			c.set(ncph.getAno(),ncph.getMes(),ncph.getDia());
			Emprestimo emp = new Emprestimo(ncph.getDeposito(),ncph.getTaxaJuro(),c,null);**/
			//bancosHandler.addContaEmprestimoBanco(nbh.getID(),emp);	
			
			
			
			nbh.clearFields();
			nbh.addMessage("Conta criada com sucesso");
		}catch(ApplicationException e){
			nbh.addMessage("Erro ao criar conta"+ e.getMessage());
		}
		
		//validaInput(nbh);
		//request.setAttribute("helper", nbh);
		/**try{
			bancosHandler.removeBanco(nbh.getDesignacao());
			nbh.removeBanco(nbh.getDesignacao());
			nbh.addMessage("Remoção de banco com sucesso");
		}catch(ApplicationException e){
			nbh.addMessage("Erro ao remover banco"+ e.getMessage());
		}**/
		
		System.out.println("aqui Prazo");
		request.getRequestDispatcher("../criaContaPrazo.jsp").forward(request, response);

	}
	
	/**private void validaInput(NovoBancoHelper nbh){
		nbh.setDesignacao(request.getParameter("designacao"));
	}**/

}
