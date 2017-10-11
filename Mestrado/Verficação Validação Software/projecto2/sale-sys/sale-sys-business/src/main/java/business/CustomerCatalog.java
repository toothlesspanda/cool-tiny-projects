package business;

import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import facade.exceptions.ApplicationException;
import facade.interfaces.ICustomer;

/**
 * A catalog of customers
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 *
 */
@Stateless
public class CustomerCatalog {

	/**
	 * Entity manager for accessing the persistence service
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Finds a customer given its VAT number.
	 * 
	 * @param vat
	 *            The VAT number of the customer to fetch from the repository
	 * @return The Customer object corresponding to the customer with the vat
	 *         number.
	 * @throws ApplicationException
	 *             When the customer with the vat number is not found.
	 */
	public Customer getCustomer(int vat) throws ApplicationException {
		TypedQuery<Customer> query = em.createNamedQuery(Customer.FIND_BY_VAT_NUMBER, Customer.class);
		query.setParameter(Customer.NUMBER_VAT_NUMBER, vat);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException("Customer not found.");
		}
	}

	/**
	 * Adds a new customer
	 * 
	 * @param vat
	 *            The customer's VAT number
	 * @param designation
	 *            The customer's designation
	 * @param phoneNumber
	 *            The customer's phone number
	 * @param discountType
	 *            The customer's discount type
	 * @return
	 * @throws ApplicationException
	 *             When the customer is already in the repository or the vat
	 *             number is invalid.
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public ICustomer addCustomer(int vat, String designation, int phoneNumber, Discount discountType)
			throws ApplicationException {
		if (Customer.isValidVAT(vat)) {
			Customer customer = new Customer(vat, designation, phoneNumber, discountType);
			em.persist(customer);
			return customer;
		} else {
			throw new ApplicationException("Invalid Vat!");
		}
	}

	public ICustomer getCustomerById(int id) throws ApplicationException {
		Customer c = em.find(Customer.class, id);
		if (c == null)
			throw new ApplicationException("Customer with id " + id + " not found");
		else
			return c;
	}

	public Iterable<ICustomer> getCustomers() {
		TypedQuery<Customer> query = em.createNamedQuery(Customer.FIND_ALL_CUSTOMERS, Customer.class);
		return new LinkedList<>(query.getResultList());
	}

	public void deleteCustomer(int id) throws ApplicationException {
		Customer customer = em.find(Customer.class, id);
		if (customer == null)
			throw new ApplicationException("Customer with id " + id + " not found");
		em.remove(customer);
	}
}
