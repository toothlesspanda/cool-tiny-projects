package services.persistence.rowDataGateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.persistence.DataSource;
import services.persistence.PersistenceException;
import services.persistence.RecordNotFoundException;

/**
 * A representação de um registo da tabela de clientes como um
 * objeto em memória.
 *	
 * Chamadas de atenção:
 * 1. As string de SQL não deveriam estar nesta classe porque assim
 * limita a portabilidade em relação às bases de dados
 * 
 * 2. Esta classe não lança exceções SQL, mas sim exceções próprias 
 * deste pacote. No entanto, as exceções SQL (de mais baixo nível) são
 * "embrulhadas" na exceção enviada e podem ser obtidas utilizando o método
 * getCause da classe exception.
 * 
 * 3. No método para inserir um novo cliente usa-se o método GetGeneratedKeys
 * para obter o idCliente que é um campo gerado automaticamente (auto-incrementado)
 * pela base de dados.
 * 
 * 4. Preparar o comando para obter as chaves geradas leva um argumento extra. Veja
 * DataSource.prepareGetGenKey(...).
 * 
 * 5. Os recursos de acesso à base de dados devem ser fechados o mais rapidamente 
 * possível. Incluí um bloco finally que trata de fechar os ResultSets e Statements
 * em cada método. Recorde-se que o bloco finally é sempre executado quer tenha sido
 * lançada uma exceção quer não tenha. Mesmo que o bloco try contenha um return, como
 * acontece no método getClientePorId, o bloco finally é executado antes da função 
 * terminar. Podem consultar mais informação sobre exceções em 
 * http://download.oracle.com/javase/tutorial/essential/exceptions/index.html
 * A partir da versão 7 do Java existe um novo construtor try-with-resources 
 * que facilita esta tarefa. Pode ver mais informação em 
 * http://download.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html.
 * 
 * 6. Quando se fecha, por exemplo, um result set, o método close pode mandar uma
 * exceção SQL. Não queremos que esta exceção seja lançada no bloco finally senão
 * outra qualquer exceção lançada anteriormente (no bloco try ou catch) é simplemente
 * ignorada e é lançada esta do finally. De qualquer forma não há nada que
 * possamos fazer caso o close lance uma exceção. Daí ignoramos esta exceção. 
 * Ver DataSource.close. Este problema do lançamento de exceções no bloco finally
 * é tratado, por exemplo, no livro Thinking in Java (http://www.mindview.net/Books/TIJ/)
 * sob o nome "Pitfall: the lost exception". Podem encontrar o texto no capítulo 9
 * em (http://www.smart2help.com/e-books/tij-3rd-edition/).
 * 
 * 7. A utilização de Singletons (como é o caso do DataSource) dificulta os testes.
 * Programei assim para ser mais simples de perceberem, mas na versão 2 resolvo 
 * este problema, bem como o problema das constantes de SQL. A solução involve
 * a utilização de fábricas de objectos e é um pouco mais complicada de seguir 
 * de início.
 * 
 * 8. A utilização de métodos estáticos para se obter informação da base
 * de dados é só uma forma de resolver o problema sobre "quem cria os objectos
 * desta classe". Outra forma é ter uma classe específica para obter a informação
 * da base de dados e criar estes objectos. Isto encontra-se exemplificado na
 * versão "sofisticada" deste exemplo.
 * 
 * @author fmartins
 *
 */
public class ClientesRowGateway {
	// atributos que representam os campos da tabela
	private int idCliente;
	private int npc;
	private String designacao;
	private int telefone;
	private int idDesconto;
	
	// Getters e setters para os atributos
	public int getNpc() {
		return npc;
	}

	public void setNpc(int npc) {
		this.npc = npc;
	}

	public String getDesignacao() {
		return designacao;
	}

	public void setDesignacao(String designacao) {
		this.designacao = designacao;
	}

	public int getTelefone() {
		return telefone;
	}

	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}

	public int getIdDesconto() {
		return idDesconto;
	}

	public void setIdDesconto(int idDesconto) {
		this.idDesconto = idDesconto;
	}

	// não necessitamos de um setter porque o Id é gerado automaticamente.
	public int getIdCliente() {
		return idCliente;
	}


	// actualizar a base de dados
	private static final String inserirClienteSQL = 
			"insert into clientes (npc, designacao, telefone, idDesconto) " +
			"values (?, ?, ?, ?)";

	public void insert () throws PersistenceException {
		PreparedStatement comando = null;
		try {
			// obter comando
			comando = DataSource.INSTANCE.prepareGetGenKey(inserirClienteSQL);			
			// set statement arguments
			comando.setInt(1, npc);
			comando.setString(2, designacao);
			comando.setInt(3, telefone);
			comando.setInt(4, idDesconto);
			// executa SQL
			comando.executeUpdate();
			// obter o idCliente gerado automaticamente
			ResultSet rs = comando.getGeneratedKeys();
			rs.next(); 
			idCliente = rs.getInt(1);
			rs.close();
		} catch (SQLException e) {
			throw new PersistenceException ("Erro interno!", e);
		} finally {
			DataSource.INSTANCE.close(comando);
		}
	}
	
	private static final String obterClienteNPC = 
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
	public static ClientesRowGateway getClientePorNPC (int npc) throws PersistenceException {
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			// obter comando
			comando = DataSource.INSTANCE.prepare(obterClienteNPC);			
			// set statement arguments
			comando.setInt(1, npc);
			// executa SQL
			rs = comando.executeQuery();
			// cria um novo cliente
			return carregarCliente(rs);
		} catch (SQLException e) {
			throw new PersistenceException("Erro interno ao adicionar cliente", e);
		} finally {
			DataSource.INSTANCE.close(rs);
			DataSource.INSTANCE.close(comando);
		}
	}
	
	private static final String	obterClienteId =
				"select idCliente, npc, designacao, telefone, idDesconto " +
					"from clientes " +
					"where idCliente = ?";

	/**
	 * Gets a client by its Id number
	 * 
	 * @param idCliente The id of the client to search for
	 * @return The new object that represents an in-memory client
	 * @throws PersistenceException
	 */
	public static ClientesRowGateway getClientePorId(int idCliente) throws PersistenceException {
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			// obter comando
			comando = DataSource.INSTANCE.prepare(obterClienteId);			
			// set statement arguments
			comando.setInt(1, idCliente);
			// executa SQL
			rs = comando.executeQuery();
			// cria um novo cliente
			return carregarCliente(rs);
		} catch (SQLException e) {
			throw new PersistenceException("Erro interno ao adicionar cliente", e);
		} finally {
			DataSource.INSTANCE.close(rs);
			DataSource.INSTANCE.close(comando);
		}
	}
	
	private static ClientesRowGateway carregarCliente(ResultSet rs) throws RecordNotFoundException {
		try {
			rs.next();
			ClientesRowGateway novoCliente = new ClientesRowGateway();
			novoCliente.idCliente = rs.getInt("idCliente");
			novoCliente.npc = rs.getInt("npc");
			novoCliente.designacao = rs.getString("designacao");
			novoCliente.telefone = rs.getInt("telefone");
			novoCliente.idDesconto = rs.getInt("idDesconto");
			return novoCliente;
		} catch (SQLException e) {
			throw new RecordNotFoundException ("Cliente não existe", e);
		}
	}
}
