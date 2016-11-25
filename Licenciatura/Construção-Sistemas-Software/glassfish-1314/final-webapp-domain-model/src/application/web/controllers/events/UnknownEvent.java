package application.web.controllers.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Internal/unknownEvent")
public class UnknownEvent extends Event {

	private static final long serialVersionUID = 6737626345980172066L;

	@Override
	protected void process() throws ServletException, IOException {
		request.setAttribute("action", request.getPathInfo());
		request.getRequestDispatcher("/unknownEvent.jsp").forward(request, response);
	}
}
