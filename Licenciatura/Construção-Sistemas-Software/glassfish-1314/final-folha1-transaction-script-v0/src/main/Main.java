package main;

import java.sql.SQLException;

import application.ApplicationException;
import application.ClientesService;
import application.VendasService;

import services.persistence.DataSource;
import services.persistence.PersistenceException;

/**
 * The big bang class.
 * 
 * Move the sql dump file into the virtual machine (use SSH) and perform the following:
 * 
 *  myqsl --user=root -p csdb < csbddump.sql  (use password 'root')
 *  
 *  this will create the table structure necessary to run this class
 *  
 * To check if the dump went ok, enter sql and try these instructions:
 * 
 *  show databases;
 *  use csdb; 
 *  show tables;
 *  select * from clientes;
 *	
 * @author fmartins, jneto
 */
public class Main {

	/**
	 * Fire in the hole!!
	 * 
	 * @param args Command line parameters
	 */
	public static void main(String[] args) {
		// Estabelece ligacao com a base de dados
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
			// adicionar um cliente (deve usar um NPC valido).
			cs.adicionarCliente(204223776, "Cliente 1", 217500255, 2);

			// criar uma nova venda
			int novaVenda = vs.novaVenda(168027852);

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
				System.out.println("Causa: ");
			e.printStackTrace();
		}
		
		// Fecha a ligacao com a base de dados
		DataSource.INSTANCE.close();
	}
}
