package pco;

import java.util.Scanner;

public class EuclidesV2 {

	public static int mdc(int a, int b ){
		
		return b==0?a:mdc(b,a%b);
		
	}
	public static void main(String[] args) {
		/*Scanner na = new Scanner(System.in);*/
		int a=0,b=10;
		
		int i,j;
		/*System.out.print("Introduza o A e o B: ");
		a = na.nextInt();
		b = na.nextInt();*/
		
		for(i=0;i<=10;i++){
			for(j=0;j<=10;j++yka66){
				System.out.println("a="+ i +" b="+ j +" mdc: "+mdc(j,i%j));
				System.out.println(i);
			}
			System.out.println(i);
		}
		
	
		/*System.out.print("Por Euclides : ");
		System.out.println(mdc(a,b));*/
	}

}
