package business;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import facade.exceptions.ApplicationException;

public class SaleTest extends Sale {

	public SaleStatus status;
	public List<SaleProduct> saleProducts;

	public SaleTest(Date date, Customer customer) {
		super(date, customer);
		this.status = SaleStatus.OPEN;
		this.saleProducts = new LinkedList<SaleProduct>();
	}

	@Override
	public boolean isOpen() {
		return status == SaleStatus.OPEN;
	}

	public void addProductToSale(Product product, double qty) throws ApplicationException {
		if (!isOpen())
			throw new ApplicationException("Cannot add products to a closed sale.");

		// if there is enough stock
		if (product.getQty() >= qty) {
			// adds product to sale
			product.setQty(product.getQty() - qty);
			saleProducts.add(new SaleProduct(product, qty));
		} else
			throw new ApplicationException("Product " + product.getProdCod() + " has stock (" + product.getQty()
					+ ") which is insuficient for the current sale");
	}

	/**
	 * Mutant 1
	 * 
	 * First condition was changed from !isOpen() to isOpen()
	 * 
	 * @param product
	 * @param qty
	 * @throws ApplicationException
	 */
	public void addProductToSaleMutant1(Product product, double qty) throws ApplicationException {

		if (isOpen())
			throw new ApplicationException("Cannot add products to a closed sale.");

		// if there is enough stock
		if (product.getQty() >= qty) {
			// adds product to sale
			product.setQty(product.getQty() - qty);
			saleProducts.add(new SaleProduct(product, qty));
		} else {
			throw new ApplicationException("Product " + product.getProdCod() + " has stock (" + product.getQty()
					+ ") which is insuficient for the current sale");
		}
	}

	/**
	 * Mutant 2
	 * 
	 * Second condition changed from >= to <
	 * 
	 * @param product
	 * @param qty
	 * @throws ApplicationException
	 */
	public void addProductToSaleMutant2(Product product, double qty) throws ApplicationException {

		if (!isOpen())
			throw new ApplicationException("Cannot add products to a closed sale.");

		// if there is enough stock
		if (product.getQty() < qty) {
			// adds product to sale
			product.setQty(product.getQty() - qty);
			saleProducts.add(new SaleProduct(product, qty));
		} else
			throw new ApplicationException("Product " + product.getProdCod() + " has stock (" + product.getQty()
					+ ") which is insuficient for the current sale");
	}

	/**
	 * Mutant 3
	 * 
	 * Changed from product.setQty(product.getQty() - qty) to
	 * product.setQty(product.getQty() + qty)
	 * 
	 * @param product
	 * @param qty
	 * @throws ApplicationException
	 */
	public void addProductToSaleMutant3(Product product, double qty) throws ApplicationException {
		if (!isOpen())
			throw new ApplicationException("Cannot add products to a closed sale.");

		// if there is enough stock
		if (product.getQty() >= qty) {
			// adds product to sale
			product.setQty(product.getQty() + qty);
			saleProducts.add(new SaleProduct(product, qty));

		} else
			throw new ApplicationException("Product " + product.getProdCod() + " has stock (" + product.getQty()
					+ ") which is insuficient for the current sale");
	}

	/**
	 * Mutant 4
	 * 
	 * Changed second condition FROM product.getQty() >= qty TO product.getQty()
	 * >= saleProducts.size())
	 * 
	 * @param product
	 * @param qty
	 * @throws ApplicationException
	 */
	public void addProductToSaleMutant4(Product product, double qty) throws ApplicationException {
		if (!isOpen())
			throw new ApplicationException("Cannot add products to a closed sale.");

		qty = qty - 1.0;
		// if there is enough stock
		if (product.getQty() >= qty) {
			// adds product to sale
			product.setQty(product.getQty() - qty);
			saleProducts.add(new SaleProduct(product, qty));
		} else
			throw new ApplicationException("Product " + product.getProdCod() + " has stock (" + product.getQty()
					+ ") which is insuficient for the current sale");
	}

	/**
	 * Mutant 5
	 * 
	 * Changed saleProducts.add(new SaleProduct(product, qty)); to
	 * saleProducts.add(new SaleProduct(product, product.getQty()));
	 * 
	 * @param product
	 * @param qty
	 * @throws ApplicationException
	 */
	public void addProductToSaleMutant5(Product product, double qty) throws ApplicationException {
		if (!isOpen())
			throw new ApplicationException("Cannot add products to a closed sale.");

		// if there is enough stock
		if (product.getQty() >= qty) {
			// adds product to sale
			product.setQty(product.getQty() - qty);
			saleProducts.add(new SaleProduct(product, product.getQty()));
		} else
			throw new ApplicationException("Product " + product.getProdCod() + " has stock (" + product.getQty()
					+ ") which is insuficient for the current sale");
	}

	/**
	 * Mutant 6
	 * 
	 * Changed >= to ==
	 * 
	 * @param product
	 * @param qty
	 * @throws ApplicationException
	 */
	public void addProductToSaleMutant6(Product product, double qty) throws ApplicationException {
		if (!isOpen())
			throw new ApplicationException("Cannot add products to a closed sale.");

		// if there is enough stock
		if (product.getQty() == qty) {
			// adds product to sale
			product.setQty(product.getQty() - qty);
			saleProducts.add(new SaleProduct(product, qty));
		} else
			throw new ApplicationException("Product " + product.getProdCod() + " has stock (" + product.getQty()
					+ ") which is insuficient for the current sale");
	}

	/**
	 * Mutant 7
	 * 
	 * Duplicated product.setQty(product.getQty() - qty);
	 * 
	 * @param product
	 * @param qty
	 * @throws ApplicationException
	 */
	public void addProductToSaleMutant7(Product product, double qty) throws ApplicationException {
		if (!isOpen())
			throw new ApplicationException("Cannot add products to a closed sale.");

		// if there is enough stock
		if (product.getQty() >= qty) {
			// adds product to sale
			product.setQty(product.getQty() - qty);
			saleProducts.add(new SaleProduct(null, qty));

		} else
			throw new ApplicationException("Product " + product.getProdCod() + " has stock (" + product.getQty()
					+ ") which is insuficient for the current sale");
	}

	/**
	 * Mutant 8
	 * 
	 * Changed FROM product.setQty(product.getQty() - qty); TO
	 * product.setQty(product.getQty() - 0);
	 * 
	 * @param product
	 * @param qty
	 * @throws ApplicationException
	 */
	public void addProductToSaleMutant8(Product product, double qty) throws ApplicationException {
		if (!isOpen())
			throw new ApplicationException("Cannot add products to a closed sale.");

		// if there is enough stock
		if (product.getQty() >= qty) {
			// adds product to sale
			product.setQty(product.getQty());
			saleProducts.add(new SaleProduct(product, qty));
		} else
			throw new ApplicationException("Product " + product.getProdCod() + " has stock (" + product.getQty()
					+ ") which is insuficient for the current sale");
	}

}
