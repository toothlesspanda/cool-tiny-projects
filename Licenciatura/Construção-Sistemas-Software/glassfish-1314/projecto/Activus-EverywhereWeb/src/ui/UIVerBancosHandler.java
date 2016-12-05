package ui;
import javax.servlet.annotation.WebServlet;


import ui.events.RemoverBancoEvent;
import ui.events.VerBancosEvent;



@WebServlet("/VerBancosHandler/*")
public class UIVerBancosHandler extends UIUseCaseHandler {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void fillActions(){
		actionHandlers.put("/verBancos",VerBancosEvent.class);
		actionHandlers.put("/removerBanco",RemoverBancoEvent.class);
	}
}
