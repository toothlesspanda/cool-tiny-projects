package ui.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import ui.helpers.BancoHelper;
import ui.helpers.GestorHelper;
import domain.Activus;
import domain.ApplicationException;
import domain.BancosHandler;

@WebServlet("/Internal/criarGestor")
public class CriarGestorEvent extends Event {

	
@Override
public void process() throws ServletException, IOException,
		ApplicationException {
	Activus app = (Activus) servletContext.getAttribute("app");
	BancosHandler bancosHandler = app.getBancosHandler();
	BancoHelper nbh = new BancoHelper();
	GestorHelper ngh = new GestorHelper();
	request.setAttribute("helper", nbh);
	request.setAttribute("helper2", ngh);
	try{
		
		System.out.println("Aqui!!!");
		
		nbh.setID(Integer.parseInt(request.getParameter("id")));
		
		System.out.println("ID "+nbh.getID());
		
		ngh.setEmail(request.getParameter("email"));
		
		System.out.println("Email "+ngh.getEmail());
		
		ngh.setNome(request.getParameter("nome"));
		
		System.out.println("Nome "+ngh.getNome());
		
		ngh.setTelefone(request.getParameter("telefone"));
		
		System.out.println("TLF "+ngh.getTelefone());
		
		
		bancosHandler.addGestorBanco(nbh.getID(),ngh.getEmail(), ngh.getNome(), intValue(ngh.getTelefone()));	
		ngh.clearFields();
		ngh.addMessage("Gestor criado com sucesso");
	}catch(ApplicationException e){
		ngh.addMessage("Gestor ao criar banco"+ e.getMessage());
	}
	
	request.getRequestDispatcher("../novoGestor.jsp").forward(request, response);

}



protected int intValue(String num) {
	try {
		return Integer.parseInt(num);
	} catch (NumberFormatException e) {
		return 0;
	}
}

}
