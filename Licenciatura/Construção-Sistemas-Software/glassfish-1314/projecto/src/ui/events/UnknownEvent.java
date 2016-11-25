package ui.events;

import java.io.IOException;

import javax.servlet.ServletException;

public class UnknownEvent extends Event {

	@Override
	public void process() throws ServletException, IOException {
		request.setAttribute("action", request.getPathInfo());
		request.getRequestDispatcher("/unknownEvent.jsp").forward(request, response);
	}

}
