package ui;

import javax.servlet.annotation.WebServlet;

import ui.events.CriarContaEmprestimoEvent;
import ui.events.CriarContaPrazoEvent;
import ui.events.EscolherGestorEvent;
import ui.events.EscolherGestorEvent2;
import ui.events.NovaContaEmprestimoEvent;
import ui.events.NovaContaPrazoEvent;

@WebServlet("/ContasHandler/*")
public class UIContasHandler extends UIUseCaseHandler {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void fillActions(){
		//actionHandlers.put("/verBancos",VerBancosEvent.class);
		
		actionHandlers.put("/novaContaPrazo", NovaContaPrazoEvent.class);
		actionHandlers.put("/novaContaEmprestimo", NovaContaEmprestimoEvent.class);
		actionHandlers.put("/criarContaPrazo", CriarContaPrazoEvent.class);
		actionHandlers.put("/criarContaEmprestimo", CriarContaEmprestimoEvent.class);
		actionHandlers.put("/escolherGestor", EscolherGestorEvent.class);
		actionHandlers.put("/escolherGestor2", EscolherGestorEvent2.class);
	}
}
