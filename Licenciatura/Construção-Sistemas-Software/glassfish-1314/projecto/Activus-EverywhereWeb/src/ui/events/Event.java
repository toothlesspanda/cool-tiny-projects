package ui.events;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			help.addMessage(mensagem +", Erro: "+ e);
			return false;
		}
	}
	protected boolean isValidEmailAddress(Helper help, String email, String mensagem) {
		 
		Pattern pattern = Pattern.compile("^[!#-'\\*\\+\\-/0-9=\\?A-Z\\^_`a-z{-~]+(\\.[!#-'\\*\\+\\-/0-9=\\?A-Z\\^_`a-z{-~]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		
		Matcher matcher = pattern.matcher(email);
	
				if(matcher.matches()){
			   		return true;
			   		
				}
			  else{ 
				  help.addMessage(mensagem);
				  return false;  
			  }
	}
	protected boolean isValidName(Helper help, String name, String mensagem) {
		 
		String regx = "^[\\p{L} .'-]+$";
	    Pattern p = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
	    Matcher m = p.matcher(name);
	    if(m.find()){
	    	return true;
	    }
	    else{
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
	
	protected boolean isAno(Helper help, String num, String mensagem){
		
		if(isInt(help,num,"O ano não é um número inteiro")){
			if(Integer.parseInt(num)>= Calendar.getInstance().get(Calendar.YEAR)){
				return true;
			}
		}
		help.addMessage(mensagem);
		return false;

	}
	
	protected boolean isMes(Helper help, String num, String mensagem){
		if(isInt(help,num,"O mês não é um número inteiro")){
			int i = Integer.parseInt(num);
			if(i > 0 && i < 13){
				return true;
			}
		}
		help.addMessage(mensagem);
		return false;

	}
	
	protected boolean isDia(Helper help, String ano,String mes,String dia, String mensagem){
		if(isInt(help,dia,"O dia não é um número inteiro")){
			int a = Integer.parseInt(ano);
			int m = Integer.parseInt(mes);
			int d = Integer.parseInt(dia);
			
			Calendar mycal = new GregorianCalendar(a,m, d);

			// Get the number of days in that month
			int diasMax = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
			if(d < 0 || diasMax < d){
				System.out.println("ola7");
				help.addMessage(mensagem);
				return false;
			}
			System.out.println("ola8");
			return true;
		}
		System.out.println("ola9");
		help.addMessage(mensagem);
		return false;

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
