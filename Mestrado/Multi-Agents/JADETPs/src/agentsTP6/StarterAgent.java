package agentsTP6;

import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.JADEAgentManagement.CreateAgent;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREInitiator;

public class StarterAgent extends Agent {


	protected void setup()
	{
		AMSAgentDescription ams = new AMSAgentDescription();
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("start");
		sd.setName("Agent Starter");
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

				Scanner s = new Scanner(System.in);
				String contaTotal = ""; 
				String service = "";


				System.out.print("Conta: ");

				contaTotal = s.nextLine();
				String[] conta = contaTotal.split(" ");


				if(conta[2].equals("+")){
					service = "sum";
				}else if(conta[2].equals("-")){
					service = "sub";
				}else if(conta[2].equals("/")){
					service = "div";
				}if(conta[2].equals("*")){
					service = "multi";
				}





				AID receiver = getServices(service);
				System.out.println(receiver.getLocalName());
				ACLMessage msg = new ACLMessage(ACLMessage.CFP);
				msg.setContent(contaTotal);
				msg.addReceiver(receiver);
				//msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
				send(msg);

				msg = myAgent.blockingReceive();	
				if (msg != null) {
					System.out.println("Resultado: "+ msg.getContent());
					s.reset();
				}else{
					block();
				}
			}
		});
	}

	private AID getServices(String service){

		DFAgentDescription serviceTemplate = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(service);
		serviceTemplate.addServices(sd);
		SearchConstraints search = new SearchConstraints();
		search.setMaxDepth((long) 2);
		search.setMaxResults((long)1);

		try {
			return DFService.search(this, serviceTemplate,search)[0].getName();
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
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
