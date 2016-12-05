package agentsTP6;

import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.AgentContainer;
import jade.core.ContainerID;
import jade.core.ProfileImpl;
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
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;

public class BootAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String port;


	protected void setup()
	{
		Object[] args = getArguments();
		String port = (String) args[0];

		jade.wrapper.AgentContainer basics1 = null;
		jade.wrapper.AgentContainer basics2 = null;
		jade.wrapper.AgentContainer basics3 = null;
		jade.wrapper.AgentContainer basics4 = null;
		ProfileImpl profile1 = new ProfileImpl();
		ProfileImpl profile2 = new ProfileImpl();

		try{
			if(port.equals("1222")){

				profile1.setParameter(jade.core.Profile.CONTAINER_NAME, "basics1");
				basics1 = jade.core.Runtime.instance().createAgentContainer(profile1);
				((PlatformController) basics1).createNewAgent("Sum","agentsTP6.SumAgent", null).start();

				profile2.setParameter(jade.core.Profile.CONTAINER_NAME, "basics2");
				basics2 =jade.core.Runtime.instance().createAgentContainer(profile2);
				((PlatformController) basics2).createNewAgent("Sub","agentsTP6.SubAgent", null).start();

			}else if(port.equals("1223")){
				profile1.setParameter(jade.core.Profile.CONTAINER_NAME, "basics3");
				basics3 = jade.core.Runtime.instance().createAgentContainer(profile1);
				((PlatformController) basics3).createNewAgent("Div","agentsTP6.DivideAgent", null).start();

				profile2.setParameter(jade.core.Profile.CONTAINER_NAME, "basics4");
				basics4 = jade.core.Runtime.instance().createAgentContainer(profile2);
				((PlatformController) basics4).createNewAgent("Multi","agentsTP6.MultiAgent", null).start();
			}
		}catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
