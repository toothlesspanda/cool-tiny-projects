package ui.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import ui.helpers.BancoHelper;

@WebServlet("/Internal/novoBanco")
public class NovoBancoEvent extends Event  {
	
	@Override
	public void process() throws ServletException, IOException{
		BancoHelper nbh = new BancoHelper();
		request.setAttribute("helper", nbh);
		request.getRequestDispatcher("../novoBanco.jsp").forward(request, response);

	}
}
