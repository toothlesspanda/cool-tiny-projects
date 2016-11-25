package services.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Singleton that abstracts a database connection
 *
 * Chamadas de aten�‹o:
 * 1. Note a forma como est‡ implementado o Singleton. A forma usual de implementar
 * n‹o Ž Thread safe (n‹o funciona bem em modo de concorr�ncia) e as implementa�›es 
 * alternativas s‹o mais complicadas. Esta solu�‹o, alŽm de elegante, funciona bem
 * num ambiente concorrente.
 * 
 * 2. A utiliza�‹o de Singletons deve ser evitada, pois n‹o Ž adequada ˆ elabora�‹o
 * de testes unit‡rios. Fiz para ilustrar esta forma de implementar os singletons e
 * porque Ž uma solu�‹o mais simples de entender. Na vers‹o seguinte, apresento uma
 * implementa�‹o alternativa.
 * 
 * @author fmartins
 *
 */
public enum DataSource {
	INSTANCE;
	
	private Connection connection;

	public void connect (String url, String userName, String password) throws PersistenceException {
		try {
			connection = DriverManager.getConnection (url, userName, password);
		} catch (SQLException e) {
			throw new PersistenceException("Nao foi possivel ligar 'a base de dados", e);
		}
}
	public Connection getConnection () {
		return connection;
	}
	
	public PreparedStatement prepare (String sql) throws SQLException {
		return connection.prepareStatement(sql); 
	}
	
	public PreparedStatement prepareGetGenKey (String sql) throws SQLException {
		return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
	}
	
	public void close(PreparedStatement comando) {
		try {
			if (comando != null)
				comando.close();
		} catch (SQLException e) {
			// nothing that we can do about...
		}
	}
	
	public void close(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			// nothing that we can do about...
		}
	}

	public void close () {
		try {
			connection.close();
		} catch (SQLException e) {
			// nothing that we can do about...
		}
	}
	
	public void beginTransaction() throws PersistenceException {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new PersistenceException("Erro ao iniciar transa�‹o", e);
		}
	}
	
	public void commit() throws PersistenceException {
		try {
			connection.commit();
		} catch (SQLException e) {
			throw new PersistenceException("Erro ao efectuar o commit!", e);
		}
		startAutoCommit();
	}

	public void rollback() throws PersistenceException {
		try {
			connection.rollback();
		} catch (SQLException e) {
			throw new PersistenceException("Erro ao efectuar o rollback!", e);
		}
		startAutoCommit();
	}

	private void startAutoCommit() throws PersistenceException {
		try {
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			throw new PersistenceException("Erro ao restabelecer o auto-commit!", e);
		}
	}

}
