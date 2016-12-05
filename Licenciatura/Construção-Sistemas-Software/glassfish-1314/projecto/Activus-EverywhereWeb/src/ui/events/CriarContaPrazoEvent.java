package ui.events;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import domain.BancosHandler;
import domain.GestoresHandler;
import ui.helpers.BancoHelper;
import ui.helpers.ContaHelper;
import ui.helpers.GestorHelper;

@WebServlet("/Internal/criarContaPrazo")
public class CriarContaPrazoEvent extends Event {
	
	
	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		Activus app = (Activus) servletContext.getAttribute("app");
		BancosHandler bancosHandler = app.getBancosHandler();
		GestoresHandler gestorHandler = app.getGestoresHandler();
		GestorHelper gh = new GestorHelper();
		ContaHelper ncph = new ContaHelper();
		BancoHelper nbh = new BancoHelper();
		
		//nbh.setBancos(app.getBancosHandler().getBancos());
		
		
		String deposito = request.getParameter("deposito");
		String taxaJuro = request.getParameter("taxaJuro");
		String ano = request.getParameter("ano");
		String mes = request.getParameter("mes");
		String dia = request.getParameter("dia");
		/**
		request.getRequestDispatcher("../novaContaPrazo.jsp").forward(request, response);
	}**/
		if(!isDouble(nbh,deposito,"O deposito não é do tipo double") | !isDouble(nbh,taxaJuro,"A taxaJuro não é do tipo double")
				| !isAno(nbh, ano, "O ano não é válido") | !isMes(nbh, mes, "O mes é invalido")){
			request.setAttribute("helper", nbh);
			nbh.setBancos(app.getBancosHandler().getBancos());
			
		}
		else if(!isDia(nbh,ano,mes,dia,"O dia não é válido")){
			System.out.println("aqui2");
			request.setAttribute("helper", nbh);
			nbh.setBancos(app.getBancosHandler().getBancos());
		}
		else{
		request.setAttribute("helper", gh);
		request.setAttribute("helper2", ncph);	
			
		ncph.setDeposito(Double.parseDouble(deposito));
		ncph.setTaxaJuro(Double.parseDouble(taxaJuro));
		
		ncph.setAno(Integer.parseInt(request.getParameter("ano")));
		
		ncph.setMes(Integer.parseInt(request.getParameter("mes")));
		
		ncph.setDia(Integer.parseInt(request.getParameter("dia")));
		
		gh.setID(Integer.parseInt(request.getParameter("id")));
		try{
			Calendar c = Calendar.getInstance();
			c.set(ncph.getAno(),ncph.getMes(),ncph.getDia());
			//Prazo p = new Prazo(ncph.getDeposito(),ncph.getTaxaJuro(),c,true);
			//bancosHandler.addContaPrazoBanco(nbh.getID(), p);	
			gh.clearFields();
			gh.addMessage("Conta criada com sucesso");
			
			int bancoID = gestorHandler.getBancoIDByGestorID(gh.getID());
			
			//Emprestimo emp = new Emprestimo(ncph.getDeposito(),ncph.getTaxaJuro(),c,null);
			bancosHandler.addContaPrazoBanco(bancoID,ncph.getDeposito(),ncph.getTaxaJuro(),gh.getID(),c,true);
			request.setAttribute("helper", nbh);
			nbh.setBancos(app.getBancosHandler().getBancos());
		}catch(ApplicationException e){
			gh.addMessage("Erro ao criar conta"+ e.getMessage());
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
		}
		request.getRequestDispatcher("../novaContaPrazo.jsp").forward(request, response);
	}
	
	/**private void validaInput(NovoBancoHelper nbh){
		nbh.setDesignacao(request.getParameter("designacao"));
	}**/

}