package services.persistence.tableDataGateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.persistence.DataSource;

/**
 * Table Data Gateway para a tabela de clientes
 *
 * Chamadas de atenção:
 * 1. Esta classe está implementada como um singleton. É uma das abordagens
 * possíveis à sua implementação. Outra alternativa é, por exemplo, implementar
 * todos os métodos de forma estática. Nenhuma das abordagens é genuinamente orientada
 * a objetos e traz complicações no futuro. Na versão 2.0 deste pacote irei implementar
 * as classes resolvendo este problema.
 * 
 * 2. As strings de SQL também não deveriam fazer parte desta classe. Ver a discussão
 * em services.persistence.rowGateway.ClientesRowGateway sobre o assunto e a versão
 * 2.0 para uma eventual solução do problema.
 * 
 * 3. Nesta abordagem ao acesso aos dados os métodos devolvem conjuntos resultado
 * com a informação obtida das seleções à base de dados. Estou a utilizar a 
 * classe ResultSet da API JDBC. Este abordagem tem alguns problemas, em particular,
 * o de não se fechar a ResultSet nem o Statement. Isto provoca a utilização 
 * de recursos na base de dados de forma não ótima. Só quando os objetos 
 * correspondentes aos ResultSets e Statements forem reclamados (garbage collected)
 * é que estes recursos serão libertados.
 * 
 * 4. Os métodos desta classe não deveriam lançar a exceção SQLException. A
 * camada de negócio não deveria ter conhecimento sobre a forma como a persistência
 * é implementada. Imagine-se que se resolve substituir o acesso a base de dados
 * por um acesso a ficheiros; então, a exceção lançada passaria a ser IOException,
 * em vez da atual, o que traria impacto na camada de negócio. Utilizar uma exceção
 * própria da camada e "embrulhar" as exceções de mais baixo nível como 
 * descritivas da causa (real) do problema é uma melhor solução. Veja, como exemplo,
 * as classes do pacote services.persistence.rowGateway.
 * 
 * 5. Todos estas decisões quando ao desenho das classes deste pocote 
 * foram tomadas de forma a se perceber a essência do Table Data Gateway. Claro, 
 * que introduzem outros problemas, alguns dos quais discutidos acima, que 
 * serão resolvidos na versão seguinte.
 * 
 * @author fmartins
 *
 */
public enum ClientesTableGateway {
	INSTANCE;
	
	private static String obterClienteNPCSQL =
			   "select idCliente, npc, designacao, telefone, idDesconto " +
					"from clientes " +
					"where npc = ?";
	/**
	 * Gets a client by its NPC 
	 * 
	 * @param npc The NPC number of the client to search for
	 * @return The result set of the query
	 * @throws SQLException 
	 */
	public ResultSet getClientePorNPC (int npc) throws SQLException {
		// obter comando
		PreparedStatement comando = DataSource.INSTANCE.prepare(obterClienteNPCSQL);
		// set statement arguments
		comando.setInt(1, npc);
		// executa SQL
		return comando.executeQuery();
	}

	
	private static String obterClienteIdSQL = 
			"select idCliente, npc, designacao, telefone, idDesconto " +
				"from clientes " +
				"where idCliente = ?";
	
	
	/**
	 * Gets a client by its Id number
	 * 
	 * @param idCliente The id of the client to search for
	 * @return The result set of the query
	 * @throws SQLException
	 */
	public ResultSet getClientePorId(int idCliente) throws SQLException {
		// obter comando
		PreparedStatement comando = DataSource.INSTANCE.prepare(obterClienteIdSQL);			
		// set statement arguments
		comando.setInt(1, idCliente);
		// executa SQL
		return comando.executeQuery();
	}
	
	
	private static String inserirClienteSQL = 
			"insert into clientes (npc, designacao, telefone, idDesconto) " +
				"values (?, ?, ?, ?)";
	
	/**
	 * Add a new client to provided that its NPC is not in the database
	 * 
	 * @param npc The NPC of the customer
	 * @param designacao The customer's name
	 * @param telefone The customer's phone number
	 * @param desconto The discount type for the customer
	 * @throws SQLException
	 */
	public void adicionarCliente (int npc, String designacao, int telefone, int desconto) throws SQLException {
		// obter comando
		PreparedStatement comando = DataSource.INSTANCE.prepare(inserirClienteSQL);			
		// set statement arguments
		comando.setInt(1, npc);
		comando.setString(2, designacao);
		comando.setInt(3, telefone);
		comando.setInt(4, desconto);
		// executa SQL
		comando.executeUpdate();
	}
}
