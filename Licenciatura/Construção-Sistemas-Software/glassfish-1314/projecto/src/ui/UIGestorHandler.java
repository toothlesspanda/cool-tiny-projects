package ui;
import javax.servlet.annotation.WebServlet;

import ui.events.CriarGestorEvent;
import ui.events.EditarGestorEvent;
import ui.events.NovoEditarGestorEvent;
import ui.events.NovoGestorEvent;



@WebServlet("/GestorHandler/*")
public class UIGestorHandler extends UIUseCaseHandler {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void fillActions(){
		actionHandlers.put("/novoGestor",NovoGestorEvent.class);
		actionHandlers.put("/criarGestor", CriarGestorEvent.class);
		actionHandlers.put("/novoEditarGestor", NovoEditarGestorEvent.class);
		actionHandlers.put("/editarGestor", EditarGestorEvent.class);
	}
}
