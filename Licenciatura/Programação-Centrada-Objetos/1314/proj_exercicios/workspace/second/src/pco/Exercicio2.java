package pco;

public class Exercicio1{
	static boolean contains (int[] v, int e){
		for(i=0;i<v.length && value ==false ;i++){
			if(v[i]==e){
				value= true;
			}
		}
		
		return value;	
	}
	public static void main(String[] args){
		int[] v = {1,2,7 };
		boolean a= contains(v,4);
		boolean b=contains(v,7);
		System.out.println(a);
		System.out.println(b);
		
	}
	