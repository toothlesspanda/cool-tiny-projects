package application.web.controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * An input controller that will handles the request of a use case.
 * Thing of objects of this class as statless use case controllers.
 *  
 * Http requests will be dispatch to this class with an URL of
 * the form: server-address:port/AppROOT/useCaseHandler/action
 * (see the web.xml mapping an notice the * in the mapping). The
 * request is handled generically, by using the action in the URL
 * (obtained using request.getPathInfo()) and dispatching it its
 * action object (using the strategy pattern) by getting the object
 * from a map defined by each specific use case handler (using
 * the template method pattern. See the init method). 
 *
 * Uses the prototype pattern for holding the classes (prototypes)
 * for creating object that will handle each client request from a 
 * specific action. We need to create new objects, since objects
 * of this class may have multiple thread (each processing a
 * different request) and it will cause race conditions in the
 * attributes of the requests. Imagine that a request is being
 * processed by a CriarClientEvent, and a new CriarClienteEvent
 * arrives. Which request is going to be used in the CriarClienteEvent
 * object? To avoid this, we create new objects to handle each
 * request.
 *
 * Bare in mind that multiple threads of objects of this class
 * may be created to handle simultaneous requests, so you must
 * be aware of race conditions. Notice that the only instance
 * atribute is used for reading as doGets or doPosts happen, 
 * but it is only written in the init method, which according
 * to the servlet life cycle is only called once at servlet
 * instantiation. Vide http://download.oracle.com/javaee/6/api/
 * javax/servlet/GenericServlet.html#init(javax.servlet.ServletConfig)
 * 
 * Patterns used: Strategy, Template method, Prototype
 * 
 * @author fmartins
 *
 */
public abstract class UseCaseHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Maps http actions to the objects that are going to handle them
	 */
	protected HashMap<String, String> actionHandlers;
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		String actionHandlerAddress = getActionHandlerAddress(action);
		request.getRequestDispatcher(actionHandlerAddress).forward(request, response);
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
	private String getActionHandlerAddress(String action) {
		String result = actionHandlers.get(action);
		if (result == null)
			result = actionHandlers.get("unknownAction");
		return result;
	}

	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() {
		actionHandlers = new HashMap<String, String>();
		actionHandlers.put("unknownAction", "/Internal/unknownEvent");
		fillActions();
	}


	/**
	 * Allow subclasses to specify how to treat specific actions.
	 * Vide the template method init above.
	 */
	protected abstract void fillActions();
}
