package ui;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ui.events.Event;
import ui.events.UnknownEvent;
import domain.ApplicationException;

/**
 * Servlet implementation class UIUseCaseHandler
 */
@WebServlet("/UIUseCaseHandler")
public abstract class UIUseCaseHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * Maps http actions to the objects that are going to handle them
	 */
	protected HashMap<String, Class<?>> actionHandlers;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UIUseCaseHandler() {
        super(); 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		Event actionHandler = newActionHandler(action);
		actionHandler.init(getServletContext(), request, response);
		try {
			actionHandler.process();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * @param action The http request action.
	 * @return the action associated with the http request.
	 * In case no action is defined, the unknown action is
	 * returns.
	 */
	@SuppressWarnings("unchecked")
	private Event newActionHandler(String action) {
		Class<Event> result = (Class<Event>) actionHandlers.get(action);
		if (result == null)
			result = (Class<Event>) actionHandlers.get("unknownAction");
		try {
			return result.newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() {
		actionHandlers = new HashMap<String, Class<?>>();
		actionHandlers.put("unknownAction", UnknownEvent.class);
		fillActions();
	}


	/**
	 * Allow subclasses to specify how to treat specific actions.
	 * Vide the template method init above.
	 */
	protected abstract void fillActions();
}
