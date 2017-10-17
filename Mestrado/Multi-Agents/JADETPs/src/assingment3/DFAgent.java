package assingment3;

import java.util.Vector;
import java.util.function.Consumer;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
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


	private int conversa = 1 ;
	private String content_msg;
	private String conversation_id;
	private DFAgentDescription dfd = new DFAgentDescription();
	private boolean inicia_conversa = false;
	private AID initer = null;

	protected void setup() {

		// Register the book-selling service in the yellow pages

		dfd.setName(getAID());

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

		ParallelBehaviour parallel = new ParallelBehaviour(this, ParallelBehaviour.WHEN_ALL);


		parallel.addSubBehaviour(new Send());
		parallel.addSubBehaviour(new Receive());

		addBehaviour(parallel);
	}

	protected class Receive extends CyclicBehaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;


		@Override
		public void action() {
			ACLMessage msg = myAgent.receive();	

			if(msg != null){
				content_msg = msg.getContent();
				conversation_id = msg.getConversationId();
				System.out.println( "Eu: " + getLocalName() + " Recebi de:" + msg.getSender().getLocalName() + "Assunto:" + conversation_id + " Content: " + content_msg);
				
			}else{
				block();
			}
		}

	}

	protected class Send extends CyclicBehaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;


		@Override
		public void action() {
			try {

				ACLMessage new_msg = new ACLMessage(ACLMessage.INFORM);
				DFAgentDescription[] result = DFService.search(myAgent,	new DFAgentDescription());
				for ( int i = 0; i < result.length; ++i){ //para cada agente

					
					if(!result[i].getName().equals(myAgent.getAID())){
						new_msg.addReceiver(result[i].getName());
					}


				}
				
				if(content_msg != null){
					if(content_msg.equals("10")){

						new_msg.setConversationId("Terminated");
						new_msg.setContent(conversation_id);
						conversa++;
						initer = myAgent.getAID();
						send(new_msg);
						inicia_conversa = true;
						new_msg.setConversationId("conversa " + String.valueOf(conversa));
						new_msg.setContent("1");
						System.out.println(myAgent.getName() + "Vou iniciar uma nova conversa: " + new_msg.getConversationId());
						send(new_msg);
					}else if(!conversation_id.equals("Terminated")){

						//System.out.println( "De: " + getLocalName() + " Para: Assunto:" + conversation_id + " Content: " + content_msg);
						new_msg.setConversationId(conversation_id);
						new_msg.setContent(String.valueOf(Integer.parseInt(content_msg) + 1));
						send(new_msg);
					}
				}

			}catch (FIPAException fe) {
				fe.printStackTrace();
			}
		}

	}

	protected void takeDown() {
		System.out.println(" Atention: " +getAID().getName() + " is leaving...");
	}
}
