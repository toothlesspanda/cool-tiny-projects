package agentsTP5;

import java.util.Date;
import java.util.Vector;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;

public class Initiator extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int nResponders;
	
	
	protected void setup(){
		
	Object [] args = getArguments();
	
	if(args != null && args.length > 0){
		
		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		for(int i = 0; i<args.length;i++){
			request.addReceiver(new AID((String) args[i], AID.ISLOCALNAME));
		}
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_SUBSCRIBE);
		request.setPerformative(ACLMessage.REQUEST);
		request.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
		request.setContent("dummy-action");
		
		addBehaviour(new SubscriptionInitiator(this, request){

			
			protected void handleRefuse(ACLMessage refuse){
				System.out.println("Agent" + refuse.getSender().getName()+"refused to perfom a subscribe ");
				request.setPerformative(ACLMessage.INFORM);
				nResponders--;
			}
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			protected void handleInform(ACLMessage inform){
				System.out.println("Agent "+inform.getSender().getName()+" successfully performed the subscribe");
			}
				
			
			protected void handleFailure(ACLMessage failure){
				if (failure.getSender().equals(myAgent.getAMS())) {
					// FAILURE notification from the JADE runtime: the receiver
					// does not exist
					System.out.println("Responder does not exist");
				}
				else {
					System.out.println("Agent "+failure.getSender().getName()+" failed to perform the requested action");
				}
				
			}
			
			protected void handleAllResponses(Vector notifications) {
				if (notifications.size() < nResponders) {
					// Some responder didn't reply within the specified timeout
					System.out.println("Timeout expired: missing "+(nResponders - notifications.size())+" responses");
				}
			}
			
		});
		
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_SUBSCRIBE);
		request.setPerformative(ACLMessage.CANCEL);
		request.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
		request.setContent("dummy-action");
		
		addBehaviour(new OneShotBehaviour(){

			@Override
			public void action() {
				
				
			}
			
		});
		
	}
		
		
		
	}

}
