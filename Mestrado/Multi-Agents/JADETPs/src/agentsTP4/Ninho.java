package agentsTP4;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Ninho extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void setup(){
		
		addBehaviour(new CyclicBehaviour(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				ACLMessage msg = myAgent.receive();	
				if(msg != null){
					
					if(msg.getContent().equals("Regresso")){
						ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
						double foo = (double)Math.random() * 100;

						if (foo <= 80.0){
							msg1.setContent("NO");
							System.out.println("### Ninho: Não podes Formiga!");
						}else {
							msg1.setContent("OK");
							System.out.println("### Ninho: Bem-vinda Formiga");
						}
						
						msg1.addReceiver(new AID("Formiga", AID.ISLOCALNAME));

						myAgent.send(msg1);
										
					}
					if(msg.getContent().equals("Sair")){
						ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);

						msg1.addReceiver(new AID("Formiga", AID.ISLOCALNAME));
						msg1.setContent("OK");

						myAgent.send(msg1);
						System.out.println("### Ninho: Até já Formiga");
					}
				}else{
					block();
				}
				
			}
			
		});
	}

}
