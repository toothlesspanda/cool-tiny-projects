package services.persistence.tableDataGateway;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.persistence.DataSource;


/**
 * Representa�‹o da tabela de Vendas.
 * 
 * Chamadas de aten�‹o:
 * 1. Ver classe ClientesDataGateway 
 *  
 * 2. Uma vez que a entidade ProdutosVenda Ž uma entidade fraca (composi�‹o), 
 * ou seja, a sua cria�‹o, elimina�‹o, consulta, etc. de elementos de ProdutosVenda
 * Ž sempre feita a partir da entidade Vendas, resolvi incluir nesta classe
 * tambŽm o acesso aos elementos da tabela de ProdutosVenda.
 * 
 * 3. Quando se cria uma nova venda (mŽtodo novaVenda) Ž devolvido o id interno
 * da venda, para que seja poss’vel adicionar produtos ˆ venda, sen‹o, n‹o era
 * poss’vel indicar a que venda se destinava o produto a vender. Manter um atributo
 * nesta classe que representasse a venda corrente n‹o Ž uma solu�‹o por diversas
 * raz›es, entre as quais, pode existir mais do que uma venda em aberto e, se 
 * se pretendesse ter uma espŽcie de sess‹o com estado (neste caso a œltima venda
 * inserida) n‹o seria responsabilidade desta classe, mas sim da classe que
 * representa a sess‹o.
 * 
 * 4. Embora a utiliza�‹o de ResultSets seja uma pr‡tica comum neste tipo de
 * interface com a base de dados, tem a desvantagem de n‹o ser tipificado e 
 * de o compilador n‹o poder ajudar e alertar o programador para poss’veis
 * problemas de m‡ utiliza�‹o de campos (tipos errados, nomes inv‡lidos, etc.) 
 * ainda durante a fase de compila�‹o. Todos estes erros aparecem mais tarde
 * aquando da execu�‹o (desejavelmente durante a fase de testes da aplica�‹o)
 * sendo em muitos casos enfadonha (os problemas v‹o aparecendo um a seguir ao
 * outro e Ž necess‡rio repetir o teste para se obter o pr—ximo erro).  
 *  
 * @author fmartins
 *
 */
public enum VendasTableGateway {
	INSTANCE;

	private static String inserirVendaSQL = 
			"insert into vendas (data, total, desconto, emAberto, idCliente) " +
				"values (?, 0, 0, 'S', ?)";
	
	public int novaVenda (int cliente) throws SQLException {
		// obter comando
		PreparedStatement comando = DataSource.INSTANCE.prepareGetGenKey(inserirVendaSQL); // was .prepare(...), also the following
		// set statement arguments
		comando.setDate(1, (new Date(new java.util.Date().getTime())));
		comando.setInt(2, cliente);
		// executa SQL
		comando.executeUpdate();
		// obter o idVenda gerado automaticamente
		ResultSet rs = comando.getGeneratedKeys();
		int idVenda = 0; 
		if (rs.next()) 
			idVenda = rs.getInt(1);
		rs.close();
		return idVenda;
	}

	
	private static String inserirArtigoVendaSQL =
			"insert into produtosVenda (idVenda, idProduto, qty) " +
				"values (?, ?, ?)";
	
	public void acrescentarArtigoAVenda (int idVenda, int idProduto, double qty) throws SQLException {
		// obter comando
		PreparedStatement comando = DataSource.INSTANCE.prepareGetGenKey(inserirArtigoVendaSQL);
		// set statement arguments
		comando.setInt(1, idVenda);
		comando.setInt(2, idProduto);
		comando.setDouble(3, qty);		
		// executa SQL
		comando.executeUpdate();
	}
	
	
	private static String obterVendaSQL = 
			"select idVenda, data, total, desconto, emAberto, idCliente " +
				"from vendas " +
				"where idVenda = ?";
	
	public ResultSet getVendaPorId (int idVenda) throws SQLException {
		// obter comando
		PreparedStatement comando = DataSource.INSTANCE.prepareGetGenKey(obterVendaSQL);		
		// set statement arguments
		comando.setInt(1, idVenda);		
		// executa SQL
		return comando.executeQuery();
	}
	
	
	private static String obterProdutosVenda =
			"select a.idItemVenda, a.idVenda, a.idProduto, a.qty, b.valorUnitario, b.elegivelDesconto " +
				"from produtosVenda a, produtos b " +
				"where a.idProduto = b.idProduto and" +
				"      a.idVenda = ?";
	public ResultSet getProdutosDaVenda (int idVenda) throws SQLException {
		// obter comando
		PreparedStatement comando = DataSource.INSTANCE.prepareGetGenKey(obterProdutosVenda);			
		// set statement arguments
		comando.setInt(1, idVenda);			
		// executa SQL
		return comando.executeQuery();
	}
}
