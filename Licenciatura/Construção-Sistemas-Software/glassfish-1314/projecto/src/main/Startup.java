package main;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import domain.Activus;

@WebListener
public class Startup implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("activus-jpa");
		
		Activus app = new Activus(emf);
		event.getServletContext().setAttribute("emf", emf);
		event.getServletContext().setAttribute("app",app);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		EntityManagerFactory emf = (EntityManagerFactory) event.getServletContext().getAttribute("emf");
		emf.close();
		
	}

}
