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
	
	nbh.clearFields();
	nbh.clearMessages();
	nbh.setBancos(app.getBancosHandler().getBancos());
	
	try{
		
		
		String telefone = request.getParameter("telefone");
		String email = request.getParameter("email");
		String nome = request.getParameter("nome");
		String id = request.getParameter("id");
		if(id == null){
			nbh.addMessage("Não existem bancos, adicione um banco antes de adicionar um gestor");
			request.getRequestDispatcher("../novoGestor.jsp").forward(request, response);
		}
		
		if(!isValidName(nbh,nome,"O nome "+nome+" é invalido") | !isInt(nbh,telefone,"O telefone não é um número") 
			| !isValidEmailAddress(nbh,email,"O email "+email+" é inválido")){
			
			nbh.addMessage("Erro ao criar o gestor");
		}
		else{
			
			nbh.setID(Integer.parseInt(id));
			
			ngh.setEmail(email);
			
			ngh.setNome(nome);
			
			ngh.setTelefone(telefone);
			
			bancosHandler.addGestorBanco(nbh.getID(),ngh.getEmail(), ngh.getNome(), intValue(ngh.getTelefone()));
			
			nbh.addMessage("Gestor criado com sucesso");
		}
				
	}catch(ApplicationException e){
		nbh.addMessage("Erro ao criar gestor ao criar banco"+ e.getMessage());
	}
	
	request.getRequestDispatcher("../novoGestor.jsp").forward(request, response);

}



}
