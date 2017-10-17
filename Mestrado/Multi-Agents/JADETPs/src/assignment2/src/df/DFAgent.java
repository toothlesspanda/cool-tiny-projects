package df;

import java.util.Vector;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class DFAgent extends Agent {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Vector<AID> talkAgents;
	private int conversa = 1 ;
	private AID tmp;
	private DFAgentDescription dfd = new DFAgentDescription();

	protected void setup() {

		// Register the book-selling service in the yellow pages
		
		dfd.setName(getAID());
		tmp = getAID();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("conversadores");
		sd.setName(getLocalName()+" - conversador");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		addBehaviour(new CyclicBehaviour() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;


			@Override
			public void action() {
				try {
					
					/*DFAgentDescription dfd = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					sd.setType("conversadores");
					dfd.addServices(sd);*/
					
					ACLMessage msg = myAgent.receive();		
					ACLMessage new_msg = new ACLMessage(ACLMessage.INFORM);
					if(msg != null){
						DFAgentDescription[] result = DFService.search(myAgent,	dfd);
						//talkAgents.clear();

						for ( int i = 0; i < result.length; ++i){ //para cada agente
													
							if(result[i].getName() != myAgent.getAID()){
								
								new_msg.addReceiver(result[i].getName());
							}
						}
										
							
						String content = msg.getContent();
						
						if(content.equals("10")){

							String tmp_id = msg.getConversationId();
							new_msg.setConversationId("Terminated");
							new_msg.setContent(tmp_id);
							conversa++;
							send(new_msg);
							new_msg.setConversationId("conversa " + String.valueOf(conversa));
							new_msg.setContent("0");
							System.out.println(myAgent.getName() + "Vou iniciar uma nova conversa: " + new_msg.getConversationId());
							send(new_msg);
						}else if(!msg.getConversationId().equals("Terminated")){
							System.out.println( getName() +" Assunto:" + msg.getConversationId() + " Content: " + content);
							new_msg.setConversationId(msg.getConversationId());
							new_msg.setContent(String.valueOf(Integer.parseInt(content) + 1));
							send(new_msg);
						}

					}else{
						block();
					}


				}catch (FIPAException fe) {
					fe.printStackTrace();
				}
			}

		});
	}

	protected void takeDown() {
		System.out.println(" Atention: " +getAID().getName() + " is leaving...");
	}
}
