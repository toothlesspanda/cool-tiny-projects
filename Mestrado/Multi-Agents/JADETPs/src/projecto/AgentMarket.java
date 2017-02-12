package projecto;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class AgentMarket extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DFAgentDescription dfd = new DFAgentDescription();
	List<House> listHouses = new ArrayList<House>();
	boolean ticker = false;

	protected void setup(){

		//AMSAgentDescription ams = new AMSAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName("Market");
		sd.setType(getAID().getLocalName());;
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}


		ParallelBehaviour parallel = new ParallelBehaviour(this, ParallelBehaviour.WHEN_ALL);


		parallel.addSubBehaviour(new SacaImoveis(this,10000));
		parallel.addSubBehaviour(new Vendida());

		addBehaviour(parallel);
	}

	private class Vendida extends CyclicBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {

			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			ACLMessage msg = myAgent.receive(mt);
			if(msg != null){	
				try {
					House houseVendida = (House)msg.getContentObject();

					BufferedReader br;
					br = new BufferedReader(new FileReader("./src/projecto/houses.txt"));
					String oldtext = "";
					String oldline ="";
					String newline ="";
					String line = br.readLine();
					while (line != null) {						
						oldtext += line + "\r\n";
						String[] newargs = line.split(";");
						
						if(houseVendida.getId() == Integer.parseInt(newargs[0])){
							StringBuilder builder = new StringBuilder();
							for(String s : newargs) {
								oldline = builder.append(s+";").toString();
							}
							oldline = oldline.replaceAll("\\;+$", "");
							builder= new StringBuilder();
							newargs[13]="1";
							for(String s : newargs) {	
								newline = builder.append(s+";").toString();
							}
							newline = newline.replaceAll("\\;+$", "");

						}
						
						line = br.readLine();
					}
					br.close();					
					String newtext = oldtext.replaceAll(oldline, newline);
					FileWriter writer = new FileWriter("./src/projecto/houses.txt");
		            writer.write(newtext);
		            writer.close();
				} catch (IOException | UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}


	/**
	 * Lê as casas do ficheiro houses.txt
	 * distribui pelos sellers, envia mensagem a cada um com uma lista
	 * 
	 * @author Inês
	 *
	 */
	private class SacaImoveis extends TickerBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public SacaImoveis(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}

		@SuppressWarnings("resource")
		protected void onTick() {
			//lÃª ficheiro e distribui pelos sellers
			ticker = true;
			int indexBeforeRead = listHouses.size();
			try {

				BufferedReader br = new BufferedReader(new FileReader("./src/projecto/houses.txt"));
				String line = br.readLine();
				while (line != null) {

					String[] newargs = line.split(";");
					if(newargs.length > 0 ){
						//System.out.println("LINHA: "+ line);
						if(Integer.parseInt(newargs[0]) >= listHouses.size()){

							House newhouse = new House(Integer.parseInt(newargs[0]),Integer.parseInt(newargs[1]),newargs[2],newargs[3],
									newargs[4],Integer.parseInt(newargs[5]),Integer.parseInt(newargs[6]),
									Integer.parseInt(newargs[7]),Integer.parseInt(newargs[8]),
									newargs[9],Integer.parseInt(newargs[10]));

							if(!listHouses.contains(newhouse)){
								listHouses.add(newhouse);
							}
						}
					}
					line = br.readLine();
				}
				System.out.println("Market: casas carregadas");
				//if(indexBeforeRead != listHouses.size()){

				ACLMessage msg = new ACLMessage(ACLMessage.CFP);
				//DFAgentDescription[] result = DFService.search(myAgent,	dfd);


				msg.addReceiver(new AID("Seller 1", AID.ISLOCALNAME));			
				List<House> seller1List = new ArrayList<House>();

				for(int h = 1 + indexBeforeRead ; h < listHouses.size();h++){ //distribui as casas
					if(h%2 == 0){
						seller1List.add(listHouses.get(h));
					}
				}

				msg.setContentObject((Serializable) seller1List);
				send(msg);

				msg.clearAllReceiver();

				msg.addReceiver(new AID("Seller 2", AID.ISLOCALNAME));			
				List<House> seller2List = new ArrayList<House>();

				for(int h = 1 + indexBeforeRead; h < listHouses.size();h++){ //distribui as casas
					if(h%2 != 0){
						seller2List.add(listHouses.get(h));
					}
				}
				System.out.println("tamanho : "+ seller2List.size());
				msg.setContentObject((Serializable) seller2List);
				send(msg);
				//}
				System.out.println("Market: casas distribuidas");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


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
