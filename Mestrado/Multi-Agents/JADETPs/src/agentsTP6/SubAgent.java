package agentsTP6;

import javax.swing.JOptionPane;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SubAgent extends Agent {
	

	protected void setup()
	{
		AMSAgentDescription ams = new AMSAgentDescription();
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("sub");
		sd.setName("Agent Sub");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		addBehaviour(new CyclicBehaviour(this)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void action()
			{
				MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
				ACLMessage msg = myAgent.receive(mt);
				if (msg != null) {
					System.out.println("Sub mensagem: "+ msg.getContent());
					String[] conta =  msg.getContent().split(" ");
					int total = Integer.parseInt(conta[0]) - Integer.parseInt(conta[1]);
					ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
					reply.setContent(String.valueOf(total));
					reply.addReceiver(msg.getSender());
					send(reply);
				}
				else {
					block();
				}
			}
		});
	}

	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		// Printout a dismissal message
		System.out.println("Presenter-agent "+getAID().getName()+" terminating.");
	}



}
