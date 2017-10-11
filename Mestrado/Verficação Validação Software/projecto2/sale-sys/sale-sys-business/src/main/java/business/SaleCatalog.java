package business;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.exceptions.ApplicationException;

/**
 * A catalog for sales
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 *
 */
@Stateless
public class SaleCatalog {

	/**
	 * Entity manager factory for accessing the persistence service 
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Creates a new sale and adds it to the repository
	 * 
	 * @param customer The customer the sales belongs to
	 * @return The newly created sale
	 */
	public Sale newSale (Customer cliente) throws ApplicationException {
		Sale sale = new Sale(new Date(), cliente);
		em.persist(sale);
		return sale;
	}

	// @pre venda.isEmAberto()
	// @pre produto.qty() >= qty
	/**
	 * @param sale
	 * @param product
	 * @param qty
	 * @throws ApplicationException
	 */
	public void addProductToSale (int saleId, Product product, double qty) 
			throws ApplicationException {
		em.merge(product);
		Sale sale = findSale(saleId);
		sale.addProductToSale(product, qty);
	}

	public double discount(int saleId) throws ApplicationException {
		return findSale(saleId).discount();
	}

	private Sale findSale(int saleId) throws ApplicationException {
		Sale sale = em.find(Sale.class, saleId);
		if (sale == null)
			throw new ApplicationException ("Sale with id " + saleId + " does not exist!");
		return sale;
	}
}
