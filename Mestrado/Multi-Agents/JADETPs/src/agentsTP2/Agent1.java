package agentsTP2;

import java.util.Iterator;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Agent1 extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setup() {

		// Printout a welcome message
		System.out.println("Hello World. I'm an agent!");
		System.out.println("My local-name is "+getAID().getLocalName());
		System.out.println("My GUID is "+getAID().getName());
		System.out.println("My addresses are:");
		Iterator it = getAID().getAllAddresses();
		while (it.hasNext()) {
			System.out.println("- "+it.next());
		}
		System.out.println("...and I'm starting...");

		addBehaviour(new MyBehaviour());

	}

	protected void takeDown() {
		System.out.println(" Atention: " +getAID().getName() + " is leaving...");
	}

	private class MyBehaviour extends CyclicBehaviour {
		//private MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP); //filtrar os tipos de mensagem que recebe, 
																					@SuppressWarnings("unused")
		//neste caso tipo CFP
		@Override
		public void action() {
			ACLMessage msg = myAgent.receive();		
			
			
			if (msg != null) {
				int type = msg.getPerformative();
				//  Message received. Process it
				if(type == ACLMessage.CFP){
					System.out.println("I DONT LIKE THIS");
				}else if (type == ACLMessage.INFORM){
						System.out.println("YOU BASTARD");
				}else if(type == ACLMessage.CANCEL){
					doDelete();
				}			
			} else {
				block();
			}
		
		}

	}

}
