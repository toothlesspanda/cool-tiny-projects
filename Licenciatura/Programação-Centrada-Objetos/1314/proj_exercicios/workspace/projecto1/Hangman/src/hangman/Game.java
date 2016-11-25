package hangman;

import java.util.Scanner;

public class Game {
	static String w; //palavra para adivinhar
	static int tentativas; // numero de tentativas
	//private int nletrasW; //numero de letras
	//private char letra; //letra inserida
	static char[] lF; //letras ditas e erradas
	static char[] lW; //letras ditas e certas
	static int guessletras; //numero de letras inseridas
	int ocorrencias; // numero de vezes na palavra secreta
	
	Game(String word){
		w=word;
		tentativas=0;
	}
	
	public static int totalLetters(){	
		return w.length();		
	}
	public static int missedLetters(){
		return tentativas;
	}
	public static int guessedLetters(){
		return guessletras;
	}
	
	public boolean gameOver(){
		if(missedLetters()==6 || guessedLetters()==totalLetters()){
			return true;
		}
		
		return false;
		
	}
	public boolean playerWon(){
		if(guessedLetters()==totalLetters())
			return true;
		return false;
		
	}
	public boolean playerLost(){
		if(tentativas==6){
			return true;
		}	
		return false;	
	}
	
	public int guess(char l){
		int j=0;
		if(gameOver())
			j=-2;
		if(usedLetter(l))
			j=-1;
		for(int i=0; i <w.length();i++){
			if(!usedLetter(l) && w.charAt(i)!=l){	
				tentativas++;
				j= 0;
			}
			if(!usedLetter(l) && w.charAt(i)==l){/*?comparar a letra a todas as letras da string ?*/
				lW[i]=l;
				
			}
			ocorrencias++;
			j= ocorrencias;
		}
		return j;
	}
	public static boolean usedLetter(char l){
		for(int i=0; i!= '\0'; i++){
			if(lW[i]==l || lF[i]==l){
				return true;
			}
		}
		return false;
		
	}
	public static boolean guessedLetter(char l){
		for(int i=0; i <w.length();i++){
			if(usedLetter(l) && w.charAt(i)==l){
				lW[i]=l;	
				return true;
			}
		}
		return false;
	}
	public boolean missedLetter(char l){
		for(int i=0; i < '\0' ;i++){
			if(usedLetter(l) && lF[i]==l){
				return true;
			}
		}
		return false;		
	}
	
	public String toString(){
		char[] letrasC=null;
		String letrasCC=null;
		char[] wC = w.toCharArray();
				
		if(guessletras == 0){
			for(int i=0; i < w.length();i++ ){
				letrasCC ="_ ";
			}
			
		}
		char[] letras = letrasCC.toCharArray();
		if(guessletras > 0){	
			for(int i=0; wC[i] !='\0';i++){
				for(int j=0; lW[i] != '\0';j++){				
					if(wC[i]==lW[j]){
						letras[i]= lW[j];
					
						}
					}			
				}
		}
		

		letrasCC= new String(letras);
		return letrasCC;
			
	}

}