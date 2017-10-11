package business;

public class CustomerTest extends Customer {

	/**
	 * Checks if a VAT number is valid.
	 * 
	 * @param vat
	 *            The number to be checked.
	 * @return Whether the VAT number is valid.
	 */
	public static boolean isValidVAT(int vat) {
		// If the number of digits is not 9, error!
		if (vat < 100000000 || vat > 999999999)
			return false;

		// If the first number is not 1, 2, 5, 6, 8, 9, error!
		int firstDigit = vat / 100000000;
		if (firstDigit != 1 && firstDigit != 2)
			// fmartins: comentado para facilitar os testes
			// && firstDigit != 5 && firstDigit != 6 &&
			// firstDigit != 8 && firstDigit != 9)
			return false;

		// Checks the congruence modules 11.
		int sum = 0;
		int checkDigit = vat % 10;
		vat /= 10;

		for (int i = 2; i < 10 && vat != 0; i++) {
			sum += vat % 10 * i;
			vat /= 10;
		}

		int checkDigitCalc = 11 - sum % 11;
		if (checkDigitCalc == 10)
			checkDigitCalc = 0;
		return checkDigit == checkDigitCalc;
	}
}
