package pco;

public class Exercicio3 {
	static int[] append(int[] a,int[] b){
		int[] v = new int [a.length + b.length]; /*criar um novo vector*/
		
		for(int i=0; i<a.length; i++){
			v[i]=a[i];
		}
		for(int i=0; i <b.length;i++){
			v[a.length + i]= b[i];
		}
		
		
		return v;
	}
	
	public static void main(String[] args) {
		int[] c={3,6,8};
		int[] d={9,10,14};
		int[] v=append(c,d);
		System.out.println(v);
		for(int i=0; i < v.length;i++){
			System.out.printf("%d ", v[i]);
			/*System.out.println(i + " " + v[i]);*/
		}
		System.out.printf("\n");
		

	}

}
