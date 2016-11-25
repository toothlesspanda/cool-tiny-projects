package application.web;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import domain.VeA;

/**
 * Application Lifecycle Listener implementation class Leasing
 *
 */
@WebListener
public class Startup implements ServletContextListener {

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
    	EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("final-webapp-domain-model");
    	VeA app = new VeA(emf);
        event.getServletContext().setAttribute("emf", emf);
        event.getServletContext().setAttribute("app", app);
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event)  {
        EntityManagerFactory emf =
                (EntityManagerFactory) event.getServletContext().getAttribute("emf");
    	emf.close();
    }

	
}
