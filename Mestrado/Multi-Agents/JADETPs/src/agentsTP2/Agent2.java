package agentsTP2;

import java.util.Iterator;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class Agent2 extends Agent{

	/**
	 * 
	 */
	/*private static final long serialVersionUID = 1L;

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

		@Override
		public void action() {
			
			ACLMessage msg= newACLMessage(ACLMessage.INFORM);
			msg.addReceiver(newAID("Peter",AID.ISLOCALNAME));
			msg.setContent("Today it’s raining");
			msg.setLanguage("English");

			send(msg);
			ACLMessage msg = myAgent.receive();
			if (msg != null) {
				//  Message received. Process it
				System.out.println("Message received");
				
			}
			else {
				block();
			}

		}

	}*/

}
