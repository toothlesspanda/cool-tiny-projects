package spacegame;

import spacegame.AbstractGameElement;

public class Star extends AbstractGameElement {

	private char posicaoS;
	private int num_steps;
	private Coord2D ini;
	private int flag;
	private int contadorx=0;
	private int contadory=0;

	/**
	 * Inicializa o tipo Star
	 * @param initialPos
	 * @param posS
	 * @param steps
	 */
	public Star(Coord2D initialPos, char posS, int steps) {
		super(initialPos, "star.png");
		Star_pos(posS);
		num_steps=steps;
		ini= new Coord2D(initialPos.x,initialPos.y);
		flag=1;
	}

	/**
	 * actualiza a posição, se V ou H
	 * @param pos
	 */
	public void Star_pos(char pos){
		posicaoS=pos;
	}

	/**
	 * 
	 * @return posicaoS, posicao se V ou H da estrela
	 */
	public char getStarPosition(){
		return posicaoS;
	}
	
	/**
	 * Movimenta a estrela num dado sentido, 
	 * voltando à posição inicial da mesma
	 * num ciclo até ser apanhada pela nave
	 * 
	 */
	@Override
	public void step() {
		// Get current position
		Coord2D c = getPosition();
		char pos= getStarPosition();


		if(pos=='H'){ //se for horizontal

			if(contadorx<=num_steps){ //anda no eixo do x até chegar ao numero de passos a dar pela estrela
				c = new Coord2D(c.x+1,c.y);
				contadorx++;			
			}

			if(contadorx==num_steps){ 
				c = new Coord2D(c.x+1,c.y);
				flag=0;
			}

			if(flag==0 && c.x >ini.x){ //volta para trás o mesmo número de passos, no eixo do x
				c = new Coord2D(c.x-1,c.y);
			}

			if(c.x==ini.x){
				contadorx=0;
				flag=1;
			}
		}else

			if(pos=='V'){ //se for vertical

				if(contadory<=num_steps){ //anda no eixo do y até chegar ao numero de passos a dar pela estrela = new Coord2D(c.x,c.y+1);
					contadory++;			
				}

				if(contadory==num_steps){
					c = new Coord2D(c.x,c.y+1);
					flag=0;
				}

				if(flag==0 && c.y >ini.y){ //volta para trás o mesmo número de passos, no eixo do y
					c = new Coord2D(c.x,c.y-1);
				}

				if(c.y==ini.y){
					contadory=0;
					flag=1;
				}

			}
		setPosition(c);

	}
}

