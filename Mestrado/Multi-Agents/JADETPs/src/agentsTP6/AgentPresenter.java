package agentsTP6;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import javax.swing.JOptionPane;

/**
 * @author ladmin
 *
 */
@SuppressWarnings("serial")
public class AgentPresenter extends Agent{


	protected void setup()
	{
		AMSAgentDescription ams = new AMSAgentDescription();
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("present");
		sd.setName("Agent Presenter");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		addBehaviour(new CyclicBehaviour(this)
		{
			public void action()
			{
				MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
				ACLMessage msg = myAgent.receive(mt);
				if (msg != null) {
					System.out.println("Mensagem: "+ msg.getContent());
					JOptionPane.showMessageDialog(null, msg.getContent() , getAID().getLocalName(), JOptionPane.PLAIN_MESSAGE);
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
