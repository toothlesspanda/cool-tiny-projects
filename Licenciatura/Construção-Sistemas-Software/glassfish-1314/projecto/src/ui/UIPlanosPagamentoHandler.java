package ui;

import javax.servlet.annotation.WebServlet;

import ui.events.VerPlanosPagamentoEvent;
import ui.events.VerPrestacoesEvent;
import ui.events.PagarPrestacaoEvent;

@WebServlet("/PlanosPagamentoHandler/*")
public class UIPlanosPagamentoHandler extends UIUseCaseHandler {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void fillActions(){
		
		actionHandlers.put("/verPlanosPagamento", VerPlanosPagamentoEvent.class);
		actionHandlers.put("/verPrestacoes", VerPrestacoesEvent.class);
		actionHandlers.put("/pagarPrestacao", PagarPrestacaoEvent.class);
	}
}