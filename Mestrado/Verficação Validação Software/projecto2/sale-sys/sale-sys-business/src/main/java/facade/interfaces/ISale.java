package facade.interfaces;

import facade.exceptions.ApplicationException;


public interface ISale {

	public double total();

	public double eligibleDiscountTotal();
	
	public ICustomer getCustomer();
	
	public double discount () throws ApplicationException;
}