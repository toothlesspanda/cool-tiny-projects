package domain;

/**
 * Represents a cash payment
 * 
 * @author fmartins
 *
 */
public class Pagamento {

	/**
	 * The amount given by the customer
	 */
	private double pagamento;
	private int tipo;

	
	/**
	 * Creates a payment given the amount tendered by the customer
	 * 
	 * @param cashTendered The amount the customer tendered
	 */
	public Pagamento(double pagamento) {
		this.pagamento = pagamento;
		tipo=0;
	}

	
	/**
	 * @return The amount given by the customer
	 */
	public double getPagamento() {
		return pagamento;
	}

	public void cash(){
		tipo =1;
	}
	
	public void mb(){
		tipo=2;
	}
	
	public int getTipo(){
		return tipo;
	}

}
