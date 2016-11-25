package ui.events;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ui.helpers.Helper;
import domain.ApplicationException;

public abstract class Event {

	/**
	 * Http request informatino 
	 */
	protected ServletContext servletContext;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	
	/**
	 * Sets the http request information
	 * @param servletContext The servlet context
	 * @param request The http request data
	 * @param response The http response data
	 */
	public void init(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) {
		this.servletContext = servletContext;
		this.request = request;
		this.response = response;
	}
	
	
	/**
	 * Strategy method for processing each request
	 * @throws ServletException
	 * @throws IOException
	 * @throws ApplicationException 
	 */
	public abstract void process() throws ServletException, IOException, ApplicationException;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ApplicationException {
		this.request = request;
		this.response = response;
		process();
	}

	/**
	 * @throws ApplicationException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ApplicationException {
		doGet(request, response);
	}
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

	protected boolean isDouble(Helper help, String num, String mensagem) {
		try {
			Double.parseDouble(num);
			return true;
		} catch (NumberFormatException e) {
			help.addMessage(mensagem);
			return false;
		}
	}
	
	protected double doubleValue(String num) {
		try {
			return Double.parseDouble(num);
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
