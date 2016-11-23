/*****************************************************************
JADE - Java Agent DEvelopment Framework is a framework to develop 
multi-agent systems in compliance with the FIPA specifications.
Copyright (C) 2000 CSELT S.p.A. 

GNU Lesser General Public License

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation, 
version 2.1 of the License. 

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the
Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA  02111-1307, USA.
 *****************************************************************/

package agentsTP4;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;

import jade.lang.acl.ACLMessage;
import jade.core.behaviours.FSMBehaviour;

/**
   This example shows the usage of the <code>FSMBehavior</code> that allows
   composing behaviours according to a Finite State Machine.<br>
   @author Giovanni Caire - TILAB
 */
public class Formiga extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// State names
	private static final String STATE_NINHO = "N";
	private static final String STATE_FOME = "F";
	private static final String STATE_COMIDA = "C";
	private int time = 0;
	private boolean ninho = false;

	FSMBehaviour fsm;

	protected void setup() {
		fsm = new FSMBehaviour(this) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public int onEnd() {
				System.out.println("FSM behaviour completed.");
				myAgent.doDelete();
				return super.onEnd();
			}
		};


		// Register state A (first state)
		fsm.registerFirstState(new FomeSend(), STATE_NINHO);

		// Register state B
		fsm.registerState(new ProcuraComida(), STATE_FOME);

		// Register state C
		fsm.registerState(new ProcuraNinho(), STATE_COMIDA);


		// Register the transitions
		fsm.registerTransition(STATE_NINHO, STATE_NINHO, 0);
		fsm.registerTransition(STATE_NINHO, STATE_NINHO, 1);
		fsm.registerTransition(STATE_NINHO, STATE_NINHO, 2);
		fsm.registerTransition(STATE_NINHO, STATE_NINHO, 3);
		fsm.registerTransition(STATE_NINHO, STATE_NINHO, 4);
		fsm.registerTransition(STATE_NINHO, STATE_FOME, 5);

		fsm.registerTransition(STATE_FOME, STATE_FOME, 0);
		fsm.registerTransition(STATE_FOME, STATE_COMIDA, 1);
		
		fsm.registerTransition(STATE_COMIDA, STATE_COMIDA, 0);
		fsm.registerTransition(STATE_COMIDA, STATE_NINHO, 1);
		fsm.registerTransition(STATE_COMIDA, STATE_FOME, 2);

		ParallelBehaviour parallel = new ParallelBehaviour(this, ParallelBehaviour.WHEN_ALL);

		
		parallel.addSubBehaviour(new Fome(this,1000));
		parallel.addSubBehaviour(fsm);
		
		addBehaviour(parallel);
	}

	/**
	   Inner class NamePrinter.
	   This behaviour just prints its name
	 */
	private class Fome extends TickerBehaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Fome(Agent a, long period) {
			super(a, period);
		}

		protected void onTick() {
			time++;
			System.out.println("Ticker time " + time);
		}
	}

	private class FomeSend extends OneShotBehaviour{


		private static final long serialVersionUID = 1L;

		@Override
		public void action() {

		}

		public int onEnd(){
			int tmp = time;
			if(time == 5){
				System.out.println("### Formiga: Tenho fome!");

				ninho = false;
				time = 0;				
			}

			return tmp;

		}
	}

	/**
	   Inner class RandomGenerator.
	   This behaviour prints its name and exits with a random value
	   between 0 and a given integer value
	 */
	private class ProcuraComida extends OneShotBehaviour {


		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int state = 0;

		public void action() {
			if(!ninho){
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

				msg.addReceiver(new AID("Ninho", AID.ISLOCALNAME));
				msg.setContent("Sair");
				System.out.println("### Formiga: Quero sair do Ninho");

				myAgent.send(msg);
				ninho = true;
				
				ACLMessage msg_ninho = myAgent.blockingReceive();

				if(msg_ninho != null){
					if(msg_ninho.getContent().equals("OK") && msg_ninho.getSender().equals(new AID("Ninho",AID.ISLOCALNAME))){
						ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);

						msg1.addReceiver(new AID("Comida", AID.ISLOCALNAME));
						msg1.setContent("Comida");
						System.out.println("### Formiga: Vou Procurar Comida");

						myAgent.send(msg1);
						state = 1;
					}
				}
			}else{
				ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
				msg1.addReceiver(new AID("Comida", AID.ISLOCALNAME));
				msg1.setContent("Comida");
				System.out.println("### Formiga: Vou Procurar Comida");

				myAgent.send(msg1);
				state = 1;
			}
		}

		public int onEnd() {
			return state;
		}
	}

	private class ProcuraNinho extends OneShotBehaviour {


		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int state = 0;

		public void action() {
			ACLMessage msg_comida = myAgent.blockingReceive();

			if(msg_comida != null){
				if(msg_comida.getContent().equals("OK") 
						&& msg_comida.getSender().equals(new AID("Comida",AID.ISLOCALNAME))){
					ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);

					msg1.addReceiver(new AID("Ninho", AID.ISLOCALNAME));
					msg1.setContent("Regresso");
					System.out.println("### Formiga: Vou para o Ninho");

					myAgent.send(msg1);
					
					ACLMessage msg_ninho = myAgent.blockingReceive();
					if(msg_ninho != null){
						if(msg_ninho.getContent().equals("OK") 
								&& msg_ninho.getSender().equals(new AID("Ninho",AID.ISLOCALNAME))){
								state = 1;
								ninho = false;
						}
						
						if(msg_ninho.getContent().equals("NO") 
								&& msg_ninho.getSender().equals(new AID("Ninho",AID.ISLOCALNAME))){
							System.out.println("### Formiga: À espera para entrar -------------- :(");
							state = 2;
						}
					}
				}else if(msg_comida.getContent().equals("NO") 
						&& msg_comida.getSender().equals(new AID("Comida",AID.ISLOCALNAME))){
							state = 2;
				}
			}

		}

		public int onEnd() {
			return state;
		}
	}

}


