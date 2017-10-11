package projecto;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class AgentSeller extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<House> listHouses = new ArrayList<House>();
	House casaProposta = null;

	String valor;
	List<String> locais = new ArrayList<String>();
	//clientHouse[0];
	String tipo ;
	String ano;
	int transportes;
	int estacionamento; 
	int zcomercial;
	int equipada;
	String eficiencia; 
	int imi; 
	String reason;
	int maxValue;

	protected void setup()
	{

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getAID().getLocalName());
		sd.setType(getAID().getLocalName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		ParallelBehaviour parallel = new ParallelBehaviour(this, ParallelBehaviour.WHEN_ANY);

		parallel.addSubBehaviour(new GuardaImoveis());
		parallel.addSubBehaviour(new Negotiate());

		addBehaviour(parallel);

	}

	/**
	 * recebe mensagem do market, de 60 em 60s, guarda caso haja um novo
	 * @author InÍs
	 *
	 */
	public class GuardaImoveis extends CyclicBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unchecked")
		@Override
		public void action() {


			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
			ACLMessage msg = blockingReceive(mt);
			if(msg != null){
				try {
					List<House> tmp = (ArrayList<House>) msg.getContentObject();
					for(House h : tmp){
						System.out.println(getLocalName() + " Casa :" + h.id);
						listHouses.add(h);
					}

					System.out.println(getLocalName()+ ": lista de casas: "+ listHouses.size());
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else {
				block();
			}
		}
	}


	public class Negotiate extends CyclicBehaviour{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void action()
		{

			//fica sempre √† espera de mensagens de cliente
			//quando recebe uma mensagem, avalia as propostas mais proximas
			//envia a proposta mais pr√≥xima
			//fica √† espera de resposta de cliente
			//refuse: manda outra proposta
			//accept: termina a liga√ß√£o

			//MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			ACLMessage msg = myAgent.receive();

			if (msg != null) {
				if(msg.getPerformative() == ACLMessage.REQUEST){
					System.out.println(myAgent.getLocalName() + ": Casa do Cliente mensagem: "+ msg.getContent());
					String[] clientHouse = msg.getContent().split(";");

					valor = clientHouse[0];
					locais = new ArrayList<String>();
					//clientHouse[0];
					String[] tmpLocais = clientHouse[1].split(", ");
				
					for(int lh = 0 ; lh < tmpLocais.length ; lh++){
						if(tmpLocais[lh].contains("[")){
							tmpLocais[lh]= tmpLocais[lh].replace("[", "");
						}
						if(tmpLocais[lh].contains("]")){
							tmpLocais[lh] = tmpLocais[lh].replace("]", "");
						}
						locais.add(tmpLocais[lh]);
					}
					tipo = clientHouse[2];
					ano = clientHouse[3];
					transportes = Integer.parseInt(clientHouse[4]); 
					estacionamento = Integer.parseInt(clientHouse[5]); 
					zcomercial = Integer.parseInt(clientHouse[6]); 
					equipada = Integer.parseInt(clientHouse[7]);
					eficiencia = clientHouse[8]; 
					imi = Integer.parseInt(clientHouse[9]); 
					reason = "0";
					System.out.println(valor+" "+locais+" "+tipo+" "+
							ano+" "+ transportes+" "+ estacionamento+" "+ zcomercial+" "+ equipada+" "+ eficiencia+" "+ imi+" "+ reason+" "+true);

					casaProposta = getAnHouse(valor, locais, tipo,
							ano, transportes, estacionamento, zcomercial, equipada, eficiencia, imi, reason,true  );

					System.out.println(myAgent.getLocalName() + ": Vou procurar uma proposta ");
					myAgent.doWait(2000);
					ACLMessage reply = new ACLMessage(ACLMessage.PROPOSE);

					try {
						reply.setContentObject((Serializable) casaProposta);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					reply.addReceiver(msg.getSender());
					send(reply);
				}else if(msg != null && msg.getPerformative() == ACLMessage.REJECT_PROPOSAL){
					System.out.println(myAgent.getLocalName() + ": O cliente rejeitou a proposta. Raz„o: "+ msg.getContent());
					reason = msg.getContent();
					casaProposta= getAnHouse(valor, locais, tipo,
							ano, transportes, estacionamento, zcomercial, equipada, eficiencia, imi, reason, false );

					System.out.println(myAgent.getLocalName() + ": Vou procurar uma proposta ");
					myAgent.doWait(2000);
					ACLMessage reply = new ACLMessage(ACLMessage.PROPOSE);

					try {
						reply.setContentObject((Serializable) casaProposta);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					reply.addReceiver(msg.getSender());
					send(reply);
				}else if(msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL ){
					for(House h: listHouses){
						if(h.equals(casaProposta)){
							h.setVendida();
							ACLMessage newmsg = new ACLMessage(ACLMessage.INFORM);
							newmsg.addReceiver(new AID("Market",AID.ISLOCALNAME));
							try {
								newmsg.setContentObject((Serializable)h);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							send(newmsg);
						}
					}
					System.out.println(myAgent.getLocalName() + ": Obrigado pelo negÛcio ");
				}else if(msg.getPerformative() == ACLMessage.CANCEL){
					System.out.println(myAgent.getLocalName() + ": Obrigado pelo negÛcio ");
				}
				else{
					block();
				}
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


	/**
	 * 
	 * FunÁ„o de negÛcio
	 * 
	 * 
	 * @param valor
	 * @param locais
	 * @param tipo
	 * @param ano
	 * @param transportes
	 * @param estacionamento
	 * @param zcomercial
	 * @param equipada
	 * @param eficiencia
	 * @param imi
	 * @return
	 */
	public House getAnHouse(String valor, List<String> locals, String tipo,
			String ano,	int transportes, int estacionamento,
			int zcomercial,	int equipada,String eficiencia, int imi, String reason, boolean firstTime){

		//todo

		String[] valores = valor.split(":");
		String[] tipos = tipo.split(":");
	
		maxValue = (int) (Integer.parseInt(valores[1])*((double)Integer.parseInt(valores[2])/100) + Integer.parseInt(valores[1]));
		
		int valorTecto = Integer.parseInt(valores[1]);
		House newhouse = null;
		if(firstTime){
			for(House h : listHouses){
				if(h.getValor() <= valorTecto && (h.getLocal().equals(locais.get(0)) || h.getLocal().equals(locais.get(1)) ) ){
					newhouse = h;
					casaProposta = h;
					break;
				}else if( h.getValor() <= valorTecto){
					newhouse = h;
					casaProposta = h;
					break;
				}else{
					newhouse = h;
					casaProposta = h;
					break;
				}
			}			
		}
		if(!firstTime){

			if(reason.equals("1")){ 
				for(House h: listHouses){//valor alto
					if(!h.vendida && h.getValor() < casaProposta.getValor()){
						newhouse = h;
						casaProposta = h;
						break;
					}
				}
			}

			if(reason.equals("2")){ // local errado
				for(House h: listHouses){
					if(!h.vendida && locais.contains(h.getLocal())){
						newhouse = h;
						casaProposta = h;
						break;
					} 
				}

			}
			if(reason.equals("3")){ // tipologia errada
				for(House h: listHouses){
					if(!h.vendida && Integer.parseInt(h.getTipo()) <= Integer.parseInt(tipos[1])
							&& Integer.parseInt(h.getTipo()) >= Integer.parseInt(tipos[0])){
						newhouse = h;
						casaProposta = h;
						break;
					}
				}
			}
			if(reason.equals("13")){ // tipologia errada
				for(House h: listHouses){
					if(!h.vendida && h.getValor() < casaProposta.getValor()
							&& Integer.parseInt(h.getTipo()) >= Integer.parseInt(tipos[1])){
						newhouse = h;
						casaProposta = h;
						break;
					}
				}

			}
			if(reason.equals("23")){ // tipologia errada
				for(House h: listHouses){
					/*System.out.println("23  house / resto id "+ h.getId() +"  " + h.getValor() + "/" + casaProposta.getValor() + "/ " + maxValue + "  " 
							+ h.getTipo() + "/" + tipos[0] + "  "
							+ h.getLocal() + "/" + (locals.contains(h.getLocal())) + "  ");*/
					if(!h.vendida && h.getValor() > casaProposta.getValor() && h.getValor() < maxValue
							&& locals.contains(h.getLocal()) && Integer.parseInt(h.getTipo()) >= Integer.parseInt(tipos[0])){
						newhouse = h;
						casaProposta = h;
						break;
					}
				}
			}

			if(reason.equals("02")){ // tipologia errada

				for(House h: listHouses){
					if(!h.vendida && h.getValor() <= maxValue
							&& Integer.parseInt(h.getTipo()) >= Integer.parseInt(tipos[1])
							&& locals.contains(h.getLocal())){
						newhouse = h;
						casaProposta = h;
						break;
					}
				}
			}
			if(reason.equals("023")){ // tipologia errada

				for(House h: listHouses){
					if(!h.vendida && h.getValor() <= maxValue
							&& Integer.parseInt(h.getTipo()) >= Integer.parseInt(tipos[0]) 
							&& locals.contains(h.getLocal())){
						
						if(locals.indexOf(h.getLocal()) <= 1){
							newhouse = h;
							casaProposta = h;
							break;
						}
					}
				}
			}

			if(reason.equals("02+")){ // tipologia errada
			
				for(House h: listHouses){
					/*System.out.println("02+  house / resto id "+ h.getId() +"  " + h.getValor() + "/" + casaProposta.getValor() + "  " 
							+ h.getTipo() + "/" + tipos[0] + "  "
							+ h.getLocal() + "/" + (locals.indexOf(h.getLocal()) <= 1) + "  ");*/
					if(!h.vendida && h.getValor() <= casaProposta.getValor()
							&& Integer.parseInt(h.getTipo()) >= Integer.parseInt(tipos[0])
							&& locais.contains(h.getLocal()) ){
						if(locals.indexOf(h.getLocal()) <= 1){
							newhouse = h;
							casaProposta = h;
							break;
						}
					}
				}
			}
			
			if(newhouse == null){
					House tmp_h;
					int tolerancia = (int) (casaProposta.getValor() * 0.1 +casaProposta.getValor());
					tmp_h = casaProposta;
					tmp_h.setValor(tolerancia);
					newhouse = tmp_h;
				
			}

		}
		

		return newhouse;
	}
}


