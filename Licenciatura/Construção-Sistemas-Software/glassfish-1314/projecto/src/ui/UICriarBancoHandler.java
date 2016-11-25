package ui;
import javax.servlet.annotation.WebServlet;

import ui.events.CriarBancoEvent;
import ui.events.NovoBancoEvent;



@WebServlet("/CriarBancoHandler/*")
public class UICriarBancoHandler extends UIUseCaseHandler {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void fillActions(){
		actionHandlers.put("/novoBanco",NovoBancoEvent.class);
		actionHandlers.put("/criarBanco", CriarBancoEvent.class);
	}
}
