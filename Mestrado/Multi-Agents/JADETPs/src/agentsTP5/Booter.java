package agentsTP5;

import jade.core.Agent;
import jade.core.AgentContainer;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;

public class Booter extends Agent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AgentContainer agentes = null;

	protected void setup(){
		ProfileImpl profile = new ProfileImpl();
		profile.setParameter(jade.core.Profile.CONTAINER_NAME, "AGENTES");
		agentes = (AgentContainer) jade.core.Runtime.instance().createAgentContainer(profile);
		
		//AgentController theDealer = agentes.createNewAgent("Dealer", 	);	
	}

}
