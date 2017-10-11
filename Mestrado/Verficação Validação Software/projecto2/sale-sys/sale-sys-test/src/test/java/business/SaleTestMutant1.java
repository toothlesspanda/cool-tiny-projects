package business;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import facade.exceptions.ApplicationException;

/**
 * Testing Mutant 1
 * 
 * @author vvs003
 *
 */
public class SaleTestMutant1 {

	SaleTest saleTest;

	/**
	 * Sets new Sale
	 */
	@Before
	public void setSale() {
		saleTest = new SaleTest(null, null);
		saleTest.status = SaleStatus.OPEN;
	}

	/**
	 * Test Sale when status equals CLOSED</br>
	 * Path - [0,1]
	 * 
	 * @throws ApplicationException
	 */
	@Test(expected = ApplicationException.class)
	public void addProductToSale1() throws ApplicationException {
		Product product = mock(Product.class);
		saleTest.status = SaleStatus.CLOSED;
		when(product.getQty()).thenReturn((double) 1);
		saleTest.addProductToSaleMutant1(product, 1);
	}

	/**
	 * Test Sale when Product qty is less than requested</br>
	 * Path - [0,2,4]
	 * 
	 * @throws ApplicationException
	 */
	@Test(expected = ApplicationException.class)
	public void addProductToSale2() throws ApplicationException {
		Product product = mock(Product.class);
		saleTest.status = SaleStatus.CLOSED;
		when(product.getQty()).thenReturn((double) 1);
		saleTest.addProductToSaleMutant1(product, 2);
	}

	/**
	 * Weak Mutation Test
	 * 
	 * sale status is CLOSED when it should be OPEN and shouldn't add the
	 * product Expected size 0, but is more than 1
	 * 
	 * @throws ApplicationException
	 */

	@Test
	public void addProductToSale4() throws ApplicationException {
		Product product = mock(Product.class);
		saleTest.status = SaleStatus.CLOSED;
		when(product.getQty()).thenReturn((double) 2);
		saleTest.addProductToSaleMutant1(product, 2);

		int expectedSize = 0;
		int actualSize = saleTest.saleProducts.size();

		assertEquals(expectedSize, actualSize);
	}

}
