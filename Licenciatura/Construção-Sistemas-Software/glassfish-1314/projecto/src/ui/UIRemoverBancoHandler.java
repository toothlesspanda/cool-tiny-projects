package ui;

import javax.servlet.annotation.WebServlet;

import ui.events.RemoverBancoEvent;

@WebServlet("/RemoverBancoHandler/*")
public class UIRemoverBancoHandler extends UIUseCaseHandler {

	private static final long serialVersionUID = 1L;

	@Override
	protected void fillActions() {
		actionHandlers.put("/removerBanco", RemoverBancoEvent.class);
	}

}
