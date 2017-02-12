package projecto;


import java.util.ArrayList;
import java.util.List;

import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.domain.DFService;
import jade.domain.FIPAException;

import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;

public class Init extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setup()
	{
		Object[] args = getArguments();
		String port = (String) args[0];

		jade.wrapper.AgentContainer container = null;
		ProfileImpl profile1 = new ProfileImpl();

		try{
			if(port.equals("1221")){

				profile1.setParameter(jade.core.Profile.CONTAINER_NAME, "Agencia1");
				container = jade.core.Runtime.instance().createAgentContainer(profile1);
				
				String valor = "0:150000:20";
				List<String> locais = new ArrayList<String>();
				locais.add("Telheiras");
				locais.add("Parque das Nacoes");
				locais.add("Lisboa");
				locais.add("Odivelas");
				String tipo = "1:0";
				String ano = "-1";
				int transportes = -1;
				int estacionamento = -1;
				int zcomercial = 0;
				int equipada = 0;
				String eficiencia = "C";
				int imi = -1;
				
				Object[] clientArgs = new Object[10];
				clientArgs[0] = valor;
				clientArgs[1] = locais;
				clientArgs[2] = tipo;
				clientArgs[3] = ano;
				clientArgs[4] = transportes;
				clientArgs[5] = estacionamento;
				clientArgs[6] = zcomercial;
				clientArgs[7] = equipada;
				clientArgs[8] = eficiencia;
				clientArgs[9] = imi;
				
				((PlatformController) container).createNewAgent("Seller 1","projecto.AgentSeller", null).start();
				((PlatformController) container).createNewAgent("Seller 2","projecto.AgentSeller", null).start();
				((PlatformController) container).createNewAgent("Market","projecto.AgentMarket", null).start();
				((PlatformController) container).createNewAgent("Client","projecto.AgentClient", clientArgs ).start();
			}else if(port.equals("1223")){
				
				profile1.setParameter(jade.core.Profile.CONTAINER_NAME, "Agencia2");
				container = jade.core.Runtime.instance().createAgentContainer(profile1);
				((PlatformController) container).createNewAgent("Seller2","projecto.AgentSeller2", null).start();
				
			}else if (port.equals("1224")){
				
				profile1.setParameter(jade.core.Profile.CONTAINER_NAME, "Mercado");
				container = jade.core.Runtime.instance().createAgentContainer(profile1);
				((PlatformController) container).createNewAgent("Market","projecto.AgentMarket", null).start();
				
			}else if (port.equals("1225")){
				
				profile1.setParameter(jade.core.Profile.CONTAINER_NAME, "Cliente");
				container = jade.core.Runtime.instance().createAgentContainer(profile1);
				((PlatformController) container).createNewAgent("Client","projecto.AgentClient", null).start();
				
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
