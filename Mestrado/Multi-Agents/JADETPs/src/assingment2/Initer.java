package assingment3;

import java.util.Iterator;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class Initer extends Agent{

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

		addBehaviour(new TickerBehaviour(this, 1000) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void onTick() {

				try{
					
					DFAgentDescription dfd = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					sd.setType("conversadores");
					dfd.addServices(sd);

					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);


					DFAgentDescription[] result = DFService.search(myAgent,	dfd);
					//for ( int i = 0; i < result.length; ++i){ 
						//talkAgents.addElement(result[i].getName());
						//System.out.println(" initer agent " + result[i].getName());
						msg.addReceiver(result[0].getName());
					//}

					msg.setContent("1");
					msg.setConversationId("conversa 1");
					msg.setLanguage("English");
					myAgent.send(msg);
					doDelete();
				}catch(FIPAException fe){
					fe.printStackTrace();
				}
			}
		
		} );

	}

	protected void takeDown() {
		System.out.println(" Atention: " +getAID().getName() + " is leaving...");
	}


}

