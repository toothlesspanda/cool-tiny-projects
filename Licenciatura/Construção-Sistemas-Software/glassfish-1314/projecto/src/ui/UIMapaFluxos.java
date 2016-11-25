package ui;

import javax.servlet.annotation.WebServlet;

import ui.events.VerMapaFluxosEvent;

@WebServlet("/MapaFluxos/*")
public class UIMapaFluxos extends UIUseCaseHandler {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void fillActions(){
		//actionHandlers.put("/verBancos",VerBancosEvent.class);
		
		actionHandlers.put("/verMapaFluxosEvent", VerMapaFluxosEvent.class);
	}
}