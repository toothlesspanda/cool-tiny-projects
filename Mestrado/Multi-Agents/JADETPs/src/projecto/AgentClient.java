package projecto;
import java.util.ArrayList;
import java.util.List;

import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.core.AID;
import jade.core.Agent;
/**
 * Perfil:
 * Considera bastante o valor da casa, no m√°ximo 180 000 euros
 * Pretende uma casa numa zona nova Telheiras ou Parque das Na√ß√µes, em √∫ltimo caso, Odivelas ou Loures
 * Visto que o cliente √© sozinho, pretende uma casa de tipologia 1 ou 0
 * 
 * 
 * @author merlin
 *
 */
public class AgentClient extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String valor; //range do valor
	List<String> locais = new ArrayList<String>(); //lista de locais
	String tipo; //"2;1;0" t0 a t2
	String ano; //range de anos, -1 indiferente
	int transportes; // 1 quer, 0 n√£o, 2 √©-lhe indiferente
	int estacionamento;  // 1 quer, 0 n√£o, 2 √©-lhe indiferente
	int zcomercial;  // 1 quer, 0 n√£o, 2 √©-lhe indiferente
	int equipada;  // 1 quer, 0 n√£o, 2 √©-lhe indiferente
	String eficiencia; //minimo
	int imi;  //valor maximo do imi a pagar, -1 √© indiferente
	boolean accept = false;
	House ultimaProposta;

	@SuppressWarnings("unchecked")
	protected void setup() {
		Object[] args = getArguments();
		valor = (String) args[0];
		locais = (List<String>) args[1];
		tipo = (String) args[2];
		ano = (String) args[3];
		transportes = (int) args[4];
		estacionamento = (int) args[5];
		zcomercial = (int) args[6];
		equipada = (int) args[7];
		eficiencia = (String) args[8];
		imi = (int) args[9];
		

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		//ServiceDescription sd = new ServiceDescription();
		//sd.set
		//sd.setName("Client");
		//dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		System.out.println("CLIENTE");
		addBehaviour(new ProfileClientBehaviour());


	}

	private class ProfileClientBehaviour extends CyclicBehaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void action()
		{

			//Envia mensagem ao seller com proposta
			//recebe proposta
			//se n√£o exstir nada desiste, procura outro seller
			//ve proposta, aceita ou rejeita;
			//porque rejeita?
			//se aceita, termina
			//ao fim de X propostas rejeitadas, contacta outro seller
			myAgent.doWait(5000);
			String content = valor+";"+locais+";"+tipo+";"+ano+";"+transportes+";"+estacionamento+";"+
					zcomercial +";"+equipada+";"+eficiencia+";"+imi;
			ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
			request.setContent(content);

			request.addReceiver(new AID("Seller 1", AID.ISLOCALNAME));
			request.addReceiver(new AID("Seller 2", AID.ISLOCALNAME));

			//System.out.println("Enviei mensagem para: " + request.getAllReceiver().next());
			send(request);
			myAgent.doWait(2000);
			while(!accept){
				MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
				ACLMessage msg = myAgent.receive(mt);
				if (msg != null && msg.getPerformative() == ACLMessage.PROPOSE ) {
					try {
						House ph = (House)msg.getContentObject();
						if(ph != null){
							System.out.println("Proposta recebida por :"+ msg.getSender().getLocalName());
							System.out.println("				ID :"+ ph.getId());
							System.out.println("				PreÁo :"+ ph.getValor());
							System.out.println("				Local :"+ ph.getLocal());
							System.out.println("				Tipologia:"+ ph.getTipo());
							System.out.println("Vou avaliar a proposta");
							myAgent.doWait(3000);
							if(ultimaProposta != (House) msg.getContentObject()){
								ultimaProposta = (House) msg.getContentObject();
								String resposta = avaliaProposta((House) msg.getContentObject());
	
								if(resposta.equals("0")){
									ACLMessage reply = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
									reply.addReceiver(msg.getSender());
									send(reply);
									System.out.println("Aceito a Proposta");
									accept = true;
									myAgent.doDelete();
								}else{
									ACLMessage reply = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
									reply.addReceiver(msg.getSender());
									reply.setContent(resposta);
									send(reply);
									System.out.println("Rejeito a Proposta");
								}
							}else{
								ACLMessage reply = new ACLMessage(ACLMessage.CANCEL);
								reply.addReceiver(msg.getSender());
								send(reply);
								System.out.println("N„o quero negociar mais");
								myAgent.doDelete();
							}
						}
					} catch (UnreadableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				else {
					block();
				}

			}

			if(accept){
				doDelete();
			}
		}
	}

	/**
	 * FunÁ„o que avalia a proposta do seller
	 * 
	 * @param house
	 * @return valores de acordo com, aceitaÁ„o e/ou recusa;
	 */
	public String avaliaProposta(House house){
		
		String resposta = "0";
		String[] valores = valor.split(":");
		String[] tipos = tipo.split(":");
		int tolerancia = (int) ((int)Integer.parseInt(valores[1])*0.2) + Integer.parseInt(valores[1]);
		//System.out.println("Tolerancia " + tolerancia);
		if(house.getValor() > tolerancia/*Integer.parseInt(valores[1])*/){ // aceita se baixar o valor
			resposta = "1";
			System.out.println("Cliente: O valor da casa vai para alÈm do que posso com ID"+house.getId());
		}
		
		if(house.getValor() > tolerancia && Integer.parseInt(house.getTipo()) < Integer.parseInt(tipos[0])  ){ //aceita se baixar o valor e aumentar a tipologia
			resposta = "13";
			System.out.println("Cliente: O valor da casa vai para alÈm do que posso e a tipologia È inferior com ID"+house.getId());
		}
		
		if(house.getValor() <= tolerancia && Integer.parseInt(house.getTipo()) <= Integer.parseInt(tipos[0]) && !locais.contains(house.getLocal())){ //aceita se a localidade for uma da lista
			resposta = "23";
			System.out.println("Cliente: Posso comprar, mas e a tipologia È inferior e n„o est· dentro da lista com ID"+house.getId());
		}
		
		if(house.getValor() <= tolerancia 
				&& Integer.parseInt(house.getTipo()) > Integer.parseInt(tipos[1]) 
				&& !locais.contains(house.getLocal())){ //aceita se a localidade for uma da lista
			resposta = "02";
			System.out.println("Cliente: Posso comprar, n„o est· dentro da lista com ID"+house.getId());
		}
		
		if(house.getValor() <= tolerancia 
				&& Integer.parseInt(house.getTipo()) <= Integer.parseInt(tipos[1]) 
				&& locais.contains(house.getLocal()) && locais.indexOf(house.getLocal()) >= 1 ){
			resposta = "023";
			System.out.println("Cliente: Posso comprar, mas a tipologia È inferior e a localidade n„o È das minhas favoritas"+house.getId());
		}
		
		if(house.getValor() <= tolerancia 
				&& Integer.parseInt(house.getTipo()) >= Integer.parseInt(tipos[1]) 
				&& locais.contains(house.getLocal()) && locais.indexOf(house.getLocal()) >= 1 ){
			resposta = "02+";
			System.out.println("Cliente: Posso comprar, mas a localidade n„o È das minhas favoritas"+house.getId());
		}
		
		return resposta;
	}
}
