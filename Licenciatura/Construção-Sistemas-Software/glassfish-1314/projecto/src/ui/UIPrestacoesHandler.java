package ui;

import javax.servlet.annotation.WebServlet;

import ui.events.VerPrestacoesEvent;

@WebServlet("/PrestacoesHandler/*")
public class UIPrestacoesHandler extends UIUseCaseHandler {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void fillActions(){
		
		actionHandlers.put("/verPrestacoes", VerPrestacoesEvent.class);
		
	}
}
