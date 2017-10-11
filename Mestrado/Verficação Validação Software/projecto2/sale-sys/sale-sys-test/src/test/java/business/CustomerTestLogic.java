package business;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class CustomerTestLogic {

	/**
	 * NIF Inválido</br>
	 * a || ~b
	 */
	@Test
	public void isValidVatTest1() {
		Customer customer = mock(Customer.class);
		when(customer.getVATNumber()).thenReturn(999);
		assertTrue(CustomerTest.isValidVAT(customer.getVATNumber()));
	}

	/**
	 * NIF Inválido</br>
	 * ~a || b
	 */
	@Test
	public void isValidVatTest2() {
		Customer customer = mock(Customer.class);
		when(customer.getVATNumber()).thenReturn(1230806345);
		assertTrue(CustomerTest.isValidVAT(customer.getVATNumber()));
	}

	/**
	 * NIF Válido</br>
	 * ~c && d, ~g, h
	 */
	@Test
	public void isValidVatTest3() {
		Customer customer = mock(Customer.class);
		when(customer.getVATNumber()).thenReturn(152404465);
		assertTrue(CustomerTest.isValidVAT(customer.getVATNumber()));
	}

	/**
	 * NIF Inválido</br>
	 * c && ~d, g, ~h
	 */
	@Test
	public void isValidVatTest4() {
		Customer customer = mock(Customer.class);
		when(customer.getVATNumber()).thenReturn(233693221);
		assertTrue(CustomerTest.isValidVAT(customer.getVATNumber()));
	}

}
