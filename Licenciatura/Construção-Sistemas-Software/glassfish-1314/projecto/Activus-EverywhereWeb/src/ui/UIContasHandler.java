package ui;

import javax.servlet.annotation.WebServlet;

import ui.events.CriarContaEmprestimoEvent;
import ui.events.CriarContaPrazoEvent;
import ui.events.PrazoEvent;
import ui.events.EmprestimoEvent;
import ui.events.NovaContaEmprestimoEvent;
import ui.events.NovaContaPrazoEvent;
import ui.events.VerContasPrazoEvent;
import ui.events.VerContasEmprestimoEvent;

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
		actionHandlers.put("/prazo", PrazoEvent.class);
		actionHandlers.put("/emprestimo", EmprestimoEvent.class);
		actionHandlers.put("/verContasPrazo", VerContasPrazoEvent.class);
		actionHandlers.put("/verContasEmprestimo", VerContasEmprestimoEvent.class);
	}
}
