package agentsTP5;

import java.util.Vector;

import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SubscriptionResponder;

public class Responder extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setup(){

		System.out.println("Agent "+getLocalName()+" waiting for requests...");
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_SUBSCRIBE),
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST) );


		addBehaviour(new SubscriptionResponder(this, template){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected ACLMessage handleSubscription(ACLMessage sub){

				ACLMessage msg = sub.createReply();
				if(createSubscription(sub) != null){
					msg.setPerformative(ACLMessage.AGREE);					
				}else{
					msg.setPerformative(ACLMessage.REFUSE);
				}
					
				return msg;

			}

			protected ACLMessage handleCancel(ACLMessage inform){
				
				Vector subs = getSubscriptions();
				for(int i=0; i< subs.size(); i++)	
					((SubscriptionResponder.Subscription) subs.elementAt(i)).notify(inform);
				
			
				return null;
			}
			//
		/*protected void notify(ACLMessage inform){		          
				// go through every subscription
				//fazer isto on cancel
				Vector subs = getSubscriptions();
				for(int i=0; i< subs.size(); i++)	
					((SubscriptionResponder.Subscription) subs.elementAt(i)).notify(inform);
			}*/

		});

	}

}
