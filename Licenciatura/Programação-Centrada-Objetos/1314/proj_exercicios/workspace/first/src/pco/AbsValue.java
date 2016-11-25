package pco;

import java.util.Scanner;

public class AbsValue {

	public static int abs(int n) {
		int mod;
		if (n > 0) {
			mod = n;
		} else {
			mod = -n;
		}

		return mod;
	}

	public static void main(String[] args) {
		Scanner n = new Scanner(System.in);
		int inteiro;
		System.out.print("Introduza o numero: ");
		inteiro = n.nextInt();

		System.out.print("O módulo é ");
		System.out.println(abs(inteiro));
	}

}
