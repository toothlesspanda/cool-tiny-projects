package ui;

import javax.servlet.annotation.WebServlet;

import ui.events.VerMapaFluxosEvent;

@WebServlet("/MapaFluxosHandler/*")
public class UIMapaFluxosHandler extends UIUseCaseHandler {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void fillActions(){
		//actionHandlers.put("/verBancos",VerBancosEvent.class);
		
		actionHandlers.put("/verMapaFluxos", VerMapaFluxosEvent.class);
	}
}