package facade.handlers;

import javax.ejb.Local;

import business.Discount;
import facade.exceptions.ApplicationException;
import facade.interfaces.ICustomer;

@Local
public interface IAddCustomerHandler {

	public ICustomer addCustomer (int vat, String denomination, 
			int phoneNumber, int discountType) 
			throws ApplicationException;
	
	public Iterable<Discount> getDiscounts() throws ApplicationException;

	public ICustomer getCustomer(int id) throws ApplicationException;

	public Iterable<ICustomer> getCustomers();

	public void deleteCustomer(int id) throws ApplicationException;
}
