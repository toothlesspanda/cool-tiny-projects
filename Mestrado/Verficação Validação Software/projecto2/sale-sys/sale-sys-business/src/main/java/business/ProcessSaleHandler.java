package business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import facade.exceptions.ApplicationException;
import facade.handlers.IProcessSaleHandler;


/**
 * Handles use case process sale (version with two operations: 
 * newSale followed by an arbitray number of addProductToSale) 
 * 
 * @author fmartins
 *
 */
@Stateless
public class ProcessSaleHandler implements IProcessSaleHandler {
	
	/**
	 * The sale's catalog
	 */
	@EJB
	private SaleCatalog saleCatalog;

	/**
	 * The customer's catalog
	 */
	@EJB
	private CustomerCatalog customerCatalog;
	
	/**
	 * The product's catalog
	 */
	@EJB
	private ProductCatalog productCatalog;
	
	/* (non-Javadoc)
	 * @see facade.handlers.IProcessSaleHandler#newSale(int)
	 */
	@Override
	public int newSale (int vatNumber) throws ApplicationException {
		Customer customer = customerCatalog.getCustomer(vatNumber);
		Sale sale = saleCatalog.newSale(customer);
		return sale.getId();
	}

	/* (non-Javadoc)
	 * @see facade.handlers.IProcessSaleHandler#addProductToSale(int, double)
	 */
	@Override
	public void addProductToSale (int saleId, int prodCod, double qty) throws ApplicationException {
		Product product = productCatalog.getProduct(prodCod);			
		saleCatalog.addProductToSale(saleId, product, qty);
	}

	/* (non-Javadoc)
	 * @see facade.handlers.IProcessSaleHandler#getSaleDiscount()
	 */
	@Override
	public double getSaleDiscount (int saleId) throws ApplicationException  {
		return saleCatalog.discount(saleId);
	}
}
