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
public class SaleTestMutant8 {

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
		saleTest.addProductToSaleMutant8(product, 1);
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
		saleTest.addProductToSaleMutant8(product, 2);
	}

	/**
	 * Test Sale when Successful (must add product to saleProducts and
	 * quantities much match)</br>
	 * Path - [0,2,3]
	 * 
	 * @throws ApplicationException
	 */
	@Test
	public void addProductToSale3() throws ApplicationException {
		Product product = mock(Product.class);
		when(product.getQty()).thenReturn((double) 10);
		saleTest.addProductToSaleMutant8(product, 5);

		int expectedLength = 1;
		int actualLength = saleTest.saleProducts.size();
		assertEquals(expectedLength, actualLength);

		double expectedQty = (double) 5;
		double actualQty = saleTest.saleProducts.get(0).getQty();
		assertEquals(expectedQty, actualQty, 0);
	}

	/**
	 * 
	 * Weak Mutant Test
	 * 
	 * @throws ApplicationException
	 */
	@Test
	public void addProductToSale4() throws ApplicationException {
		Product product = mock(Product.class);
		when(product.getQty()).thenReturn((double) 10);
		saleTest.addProductToSaleMutant8(product, 5);

		double expectedQty = (double) 5;
		double actualQty = saleTest.saleProducts.get(0).getProduct().getQty();
		assertEquals(expectedQty, actualQty, 0);
	}

	/**
	 * 
	 * Reach without an error test
	 * 
	 * @throws ApplicationException
	 */
	@Test
	public void addProductToSale5() throws ApplicationException {
		Product product = mock(Product.class);
		when(product.getQty()).thenReturn((double) 0);
		saleTest.addProductToSaleMutant8(product, 0);

		double expectedQty = (double) 0;
		double actualQty = saleTest.saleProducts.get(0).getProduct().getQty();
		assertEquals(expectedQty, actualQty, 0);
	}

}
