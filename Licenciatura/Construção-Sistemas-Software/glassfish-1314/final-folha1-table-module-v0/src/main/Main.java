package main;

import java.sql.SQLException;

import services.persistence.DataSource;
import services.persistence.PersistenceException;
import application.ClientesService;
import application.VendasService;
import domain.ApplicationException;

/**
 * The big bang class.
 *	
 * @author fmartins
 */
public class Main {

	/**
	 * Fire in the hole!!
	 * 
	 * @param args Command line parameters
	 */
	public static void main(String[] args) {
		// Estabelece liga�‹o com a base de dados
		try {
			DataSource.INSTANCE.connect("jdbc:mysql://192.168.56.10/csdb", "cs", "cs");
		} catch (PersistenceException e) {
			System.out.println("Error connecting database");
			System.out.println("Application Message: " + e.getMessage());
			System.out.println("SQLException: " + e.getCause().getMessage());
			System.out.println("SQLState: " + ((SQLException) e.getCause()).getSQLState());
			System.out.println("VendorError: " + ((SQLException) e.getCause()).getErrorCode());
			return;
		}
		
		// Cria os dois servi�os dispon’veis
		ClientesService cs = new ClientesService ();
		VendasService vs = new VendasService();
			
		
		// Efectua um pequeno teste
		try {
			// adicionar um cliente.
			//cs.adicionarCliente(501097350, "c4", 217500255, 2);

			// criar uma nova venda
			int novaVenda = vs.novaVenda(501097350);

			// adicionar produtos
			vs.acrescentarProdutoAVenda(novaVenda, 123, 10);
			vs.acrescentarProdutoAVenda(novaVenda, 124, 5);
			
			// obter o valor do desconto
			double desconto = vs.getValorDoDesconto(novaVenda);
			System.out.println(desconto);

		} catch (ApplicationException e) {
			System.out.println("Erro: " + e.getMessage());
			// para efeitos de depuracao. Tipicamente na aplicacao
			// podia ficar associado a um botao de "detalhes" da mensagem de erro.
			if (e.getCause() != null)
				e.getCause().printStackTrace();
		}
		
		// Fecha a liga�‹o com a base de dados
		DataSource.INSTANCE.close();
	}
}
