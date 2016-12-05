package ui.events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import domain.BancosHandler;
import domain.Emprestimo;
import domain.GestoresHandler;
import domain.Prazo;
import ui.helpers.BancoHelper;
import ui.helpers.ContaHelper;
import ui.helpers.GestorHelper;

@WebServlet("/Internal/criarContaEmprestimo")
public class CriarContaEmprestimoEvent extends Event {
	
	
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
		
			request.setAttribute("helper2", ncph);	
			
			ncph.setDeposito(Double.parseDouble(deposito));
			ncph.setTaxaJuro(Double.parseDouble(taxaJuro));
			
			ncph.setAno(Integer.parseInt(request.getParameter("ano")));
			
			ncph.setMes(Integer.parseInt(request.getParameter("mes")));
			
			ncph.setDia(Integer.parseInt(request.getParameter("dia")));
			
			gh.setID(Integer.parseInt(request.getParameter("id")));
		
		
		
		
		
		String[] ls = request.getParameterValues("contasID");
		
		List<Prazo> pl = new ArrayList<Prazo>();
		if(ls!=null){
		for(int i= 0;i < ls.length;i++){
			pl.add(app.getContasHandler().getContaPrazo(Integer.parseInt(ls[i])));
		}
		}
		else
			pl = null;
		
		
		
		
		try{
			Calendar c = Calendar.getInstance();
			c.set(ncph.getAno(),ncph.getMes(),ncph.getDia());
			
			int bancoID = gestorHandler.getBancoIDByGestorID(gh.getID());
			//Emprestimo emp = new Emprestimo(ncph.getDeposito(),ncph.getTaxaJuro(),c,null);
			bancosHandler.addContaEmprestimoBanco(bancoID,ncph.getDeposito(),ncph.getTaxaJuro(),gh.getID(),c,pl);
			gh.clearFields();
			gh.addMessage("Conta criada com sucesso");
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
		request.getRequestDispatcher("../novaContaEmprestimo.jsp").forward(request, response);

	}
	
	/**private void validaInput(NovoBancoHelper nbh){
		nbh.setDesignacao(request.getParameter("designacao"));
	}**/

}