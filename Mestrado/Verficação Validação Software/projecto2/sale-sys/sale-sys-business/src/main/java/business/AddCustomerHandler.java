package business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import facade.exceptions.ApplicationException;
import facade.handlers.IAddCustomerHandler;
import facade.interfaces.ICustomer;

/**
 * Handles the add customer use case. This represents a very 
 * simplified use case with just one operation: addCustomer.
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 *
 */
@Stateless
public class AddCustomerHandler implements IAddCustomerHandler {
	
	/**
	 * The customer's catalog
	 */
	@EJB
	private CustomerCatalog customerCatalog;
	
	/**
	 * The discount's catalog 
	 */
	@EJB
	private DiscountCatalog discountCatalog;
	
	/**
	 * Adds a new customer with a valid Number. It checks that there is no other 
	 * customer in the database with the same VAT.
	 * 
	 * @param vat The VAT of the customer
	 * @param denomination The customer's name
	 * @param phoneNumber The customer's phone 
	 * @param discountType The type of discount applicable to the customer
	 * @throws ApplicationException When the VAT number is invalid (we check it according 
	 * to the Portuguese legislation).
	 */
	public ICustomer addCustomer (int vat, String denomination, 
			int phoneNumber, int discountType) 
			throws ApplicationException {
		try {
			Discount discount = discountCatalog.getDiscount(discountType);
			return customerCatalog.addCustomer(vat, denomination, phoneNumber, discount);
		} catch (Exception e) {
			throw new ApplicationException("Error creating customer " + vat, e);
		}
	}	
	
	public Iterable<Discount> getDiscounts() throws ApplicationException {
		return discountCatalog.getDiscounts();
	}

	@Override
	public ICustomer getCustomer(int id) throws ApplicationException {
		return customerCatalog.getCustomerById(id);
	}

	@Override
	public Iterable<ICustomer> getCustomers() {
		return customerCatalog.getCustomers();
	}

	@Override
	public void deleteCustomer(int id) throws ApplicationException {
		customerCatalog.deleteCustomer(id);
	}
	
}
