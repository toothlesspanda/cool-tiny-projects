package business;

import static org.junit.Assert.assertTrue;
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
public class SaleTestMutant2 {

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
	 * A test that doesn't reach the mutation
	 * 
	 * @throws ApplicationException
	 */
	@Test(expected = ApplicationException.class)
	public void addProductToSale1() throws ApplicationException {
		Product product = mock(Product.class);
		saleTest.status = SaleStatus.CLOSED;
		when(product.getQty()).thenReturn((double) 1);
		saleTest.addProductToSaleMutant2(product, 2);
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
		when(product.getQty()).thenReturn((double) 1);
		saleTest.addProductToSaleMutant2(product, 2);
	}

	/**
	 * Weak Mutation Test
	 * 
	 * @throws ApplicationException
	 */
	@Test
	public void addProductToSale4() throws ApplicationException {
		Product product = mock(Product.class);
		when(product.getQty()).thenReturn((double) 0);
		saleTest.addProductToSaleMutant2(product, 1);

		assertTrue("Expected a positive product quantity", product.getQty() - 1 > 0);
	}

}
