package facade.handlers;

import facade.exceptions.ApplicationException;

public interface IProcessSaleHandler {

	/**
	 * Creates a new sale
	 * 
	 * @param vatNumber The customer's VAT number for the sale
	 * @throws ApplicationException In case the customer is not in the repository
	 */
	int newSale(int vatNumber) throws ApplicationException;

	/**
	 * Adds a product to the current sale
	 * 
	 * @param prodCod The product code to be added to the sale 
	 * @param qty The quantity of the product sold
	 * @throws ApplicationException When the sale is closed, the product code
	 * is not part of the product's catalog, or when there is not enough stock
	 * to proceed with the sale
	 */
	void addProductToSale(int saleId, int prodCod, double qty) throws ApplicationException;

	/**
	 * @return The sale's discount, according to the customer discount type
	 * @throws ApplicationException When some persistence error occurs
	 */
	double getSaleDiscount(int saleId) throws ApplicationException;

}