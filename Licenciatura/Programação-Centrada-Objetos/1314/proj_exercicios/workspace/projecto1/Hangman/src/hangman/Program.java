package hangman;

import java.util.Scanner;
import hangman.Game;

public class Program {


	public static void main(String[] args) {
		String w="ola";
		//int m = new missedLetters();
		Game word = new Game(w);
		char[] letra;
		//Object obj;
		Scanner s= new Scanner(System.in);
		String out;
		
		
		//System.out.println(Game.toString());
		System.out.print("Letras: ");
		letra=s.next().toCharArray();
		
		
		//letra= letrasU.toCharArray();
		while(Game.tentativas!=6 && Game.guessletras==Game.w.length()){
			System.out.print("Letras: ");
			letra=s.next().toCharArray();
		
			for(int i=0; letra[i]!='\0';i++){
				for(int j=0; j<w.length();j++){
					if(letra[i]==w.charAt(j)){
						System.out.println("Good, "+ (i+1) +" matches for letter '" +letra[i]+"'");
						//Game.lW[]=letra[i];
						Game.guessletras++;
						i++;
					}else if(letra[i]!=w.charAt(j)){
						System.out.println("Letter ’"+letra[i]+"’ not in secret word!");
						Game.tentativas++;
						i++;
					}else if(Game.usedLetter(letra[i])){
						System.out.println("Letter ’"+letra[i]+"’ used before");
						i++;
					}
				}
			}
			
			
			
		}
	}	

	

}
