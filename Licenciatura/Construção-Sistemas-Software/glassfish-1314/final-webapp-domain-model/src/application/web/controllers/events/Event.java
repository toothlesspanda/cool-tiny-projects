package application.web.controllers.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.web.datapresentation.Helper;

/**
 * An abstract http event request handler. 
 * Think of it as an event in the SSD diagram.
 * It has an init method, because objects are
 * create from the prototype (vide UIUseCaseHandler)
 * and its easier to use a no parameters construct.
 * 
 * It allows subclasses to define (the strategy of) how
 * to handle each event.
 * 
 * We need to store the http request context, since
 * events are not http servlets and do not have access to
 * the request data.
 *  
 * @author fmartins
 *
 */
public abstract class Event extends HttpServlet {

	private static final long serialVersionUID = -7066373204929867189L;
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.request = request;
		this.response = response;
		process();
	}

	/**
	 * Strategy method for processing each request
	 * @throws ServletException
	 * @throws IOException
	 */
	protected abstract void process() throws ServletException, IOException;
	
	// the following methods might need to be refactored in order to avoid
	// duplicated code
	protected boolean isInt(Helper help, String num, String mensagem) {
		try {
			Integer.parseInt(num);
			return true;
		} catch (NumberFormatException e) {
			help.addMessage(mensagem);
			return false;
		}
	}

	protected int intValue(String num) {
		try {
			return Integer.parseInt(num);
		} catch (NumberFormatException e) {
			return 0;
		}
	}


	protected boolean isFilled (Helper helper, String valor, String mensagem) {
		if (valor.equals("")) {
			helper.addMessage(mensagem);
			return false;
		}
		return true;
	}

}
