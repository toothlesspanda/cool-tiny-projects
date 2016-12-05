package ui.events;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import domain.Activus;
import domain.ApplicationException;
import domain.Conta;
import domain.ContasHandler;
import domain.Prestacao;
import ui.helpers.ContaHelper;
import ui.helpers.PrestacaoHelper;

@WebServlet("/Internal/verMapaFluxos")
public class VerMapaFluxosEvent extends Event {

	@Override
	public void process() throws ServletException, IOException, ApplicationException{
		PrestacaoHelper ph = new PrestacaoHelper();
		ContaHelper ch = new ContaHelper();
		Activus app = (Activus) servletContext.getAttribute("app");
		
		//app.getContasHandler().
		ContasHandler handler = app.getContasHandler();
		
		ph.setPrestacao(handler.getPrestacoesPagasEsteMes());
		ch.setContasPrazo(handler.getContasPrazo());
		request.setAttribute("helper", ch);
		request.setAttribute("helper2", ph);
		
		if(ph.getPrestacoes().isEmpty() && ch.getContasPrazo().isEmpty())
			ch.addMessage("Não houve movimentos da conta este més ");
		
		
		Calendar cal = Calendar.getInstance();
		
		double total = 0.0;
		
		List<Prestacao> lp = ph.getPrestacoes();
		List<Conta> lc = ch.getContas();
		
		for(Conta c:lc){
			total=total+c.getValor();
		}
		
		for(Prestacao p: lp){
			total=total-p.getValorPrestacao();
		}
		
		ch.setTotal(total);
		
		ch.setData(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE));
		
		request.getRequestDispatcher("../verMapaFluxos.jsp").forward(request, response);

	}
}
