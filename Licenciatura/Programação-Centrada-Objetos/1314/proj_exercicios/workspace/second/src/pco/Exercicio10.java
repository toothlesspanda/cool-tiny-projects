package pco;

public class Exercicio10 {
	static int[][] identityMatrix(int n){
		int[][] I = new int[n][n]; 
		for(int i=0; i<n;i++){
				I[i][i]= 1;
		}
		return I;
	}
	public static void main(String[] args) {
		/*int[][] M={
				{1,0,0,0},
				{0,1,0,0},
				{0,0,1,0},
				{0,0,0,1},
		};*/
		int count=0;
		int[][] J = identityMatrix(4);
		for(int i=0; i<4;i++){
			for(int j=0; j<4;j++){
				System.out.print(J[i][j]+" ");
				count++;
			}
			if(count%4==0)
				System.out.printf("\n");
		}

	}

}
