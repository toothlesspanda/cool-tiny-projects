
public class exer2 {
	
	
	public static void main(String[] args) {
		int soma=0;
		for(int pos=0; pos<args.length; pos++){
			soma=soma + Integer.parseInt(args[pos]);
			
		}
		System.out.println("Soma =" + soma);
	}
}
