package pco;


public class Exercicio9 {
	static int count(char[][] v, char  c){
		int counting=0;
		
		for(int i=0; i< v.length; i++){
			for(int j=0; j< v[i].length; j++){
				if(c==v[i][j])
					counting++;			
			}		
		}
		
		return counting;
		
	}
	public static void main(String[] args) {
		char[][] v = {
				{ 'a', 'b', 'c', 'd'},
				{'e', 'a', 'g'},
				{'a','a'},
				{'j'}
		};
		int n= count(v,'a');
		System.out.println(n);
	}

}
