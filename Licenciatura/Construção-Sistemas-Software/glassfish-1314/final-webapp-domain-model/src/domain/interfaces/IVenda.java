package domain.interfaces;

import domain.ApplicationException;
import domain.Cliente;


public interface IVenda {

	public double getTotal();

	public double getTotalElegivel();
	
	public Cliente getCliente();
	
	public double getValorDoDesconto () throws ApplicationException;
}