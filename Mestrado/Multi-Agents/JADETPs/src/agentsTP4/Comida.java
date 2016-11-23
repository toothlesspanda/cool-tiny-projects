package agentsTP4;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Comida extends Agent{

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
					ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
					double foo = (double)Math.random() * 100;

					if (foo <= 95.0){
						msg1.setContent("NO");
						System.out.println("### Comida: Vai procurar outra comida!");
					}else {
						msg1.setContent("OK");
						System.out.println("### Comida: Mnhame mnhame");
					}
					
					msg1.addReceiver(new AID("Formiga", AID.ISLOCALNAME));
					myAgent.send(msg1);
				}else{
					block();
				}
				
			}
			
		});
	}

}
