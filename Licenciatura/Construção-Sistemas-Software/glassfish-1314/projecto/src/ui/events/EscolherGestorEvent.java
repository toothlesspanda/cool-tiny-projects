package ui.events;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import domain.BancosHandler;
import domain.Emprestimo;
import ui.helpers.BancoHelper;
import ui.helpers.ContaHelper;
import ui.helpers.GestorHelper;

@WebServlet("/Internal/escolherGestor")
public class EscolherGestorEvent extends Event {
	
	
	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		Activus app = (Activus) servletContext.getAttribute("app");
		BancosHandler bancosHandler = app.getBancosHandler();
		BancoHelper nbh = new BancoHelper();
		GestorHelper gh = new GestorHelper();
		//ContaHelper ncph = new ContaHelper();
		nbh.setBancos(app.getBancosHandler().getBancos());
		request.setAttribute("helper", nbh);
		request.setAttribute("helper2", gh);
		//request.setAttribute("helper2", ncph);
		
		/**ncph.setDeposito(Double.parseDouble(request.getParameter("deposito")));
		
		System.out.println("Deposito "+ncph.getDeposito());
		
		ncph.setTaxaJuro(Double.parseDouble(request.getParameter("taxaJuro")));
		
		System.out.println("TaxaJuro "+ncph.getTaxaJuro());
		
		ncph.setAno(Integer.parseInt(request.getParameter("ano")));
		
		System.out.println("Ano "+ncph.getAno());
		
		ncph.setMes(Integer.parseInt(request.getParameter("mes")));
		
		System.out.println("Mes "+ncph.getMes());
		
		ncph.setDia(Integer.parseInt(request.getParameter("dia")));
		
		System.out.println("Dia "+ncph.getDia());
		
		System.out.println("Aqui!!!");**/
		nbh.setID(Integer.parseInt(request.getParameter("id")));
		gh.setIDBanco(nbh.getID());
		System.out.println("ID "+nbh.getID());
		
		try{
			/**Calendar c = Calendar.getInstance();
			c.set(ncph.getAno(),ncph.getMes(),ncph.getDia());
			Emprestimo emp = new Emprestimo(ncph.getDeposito(),ncph.getTaxaJuro(),c,null);**/
			//bancosHandler.addContaEmprestimoBanco(nbh.getID(),emp);	
			
			gh.setGestores(bancosHandler.getBanco(nbh.getID()).getListaGestor());
			
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
		
		//response.sendRedirect("../index.html");
		request.getRequestDispatcher("../escolherGestor.jsp").forward(request, response);

	}
	
	/**private void validaInput(NovoBancoHelper nbh){
		nbh.setDesignacao(request.getParameter("designacao"));
	}**/

}
