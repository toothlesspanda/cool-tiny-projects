package services.persistence.tableDataGateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.persistence.DataSource;

/**
 * Representação da tabela de produtos.
 * 
 * Chamadas de atenção:
 * 1. Ver classe ClientesDataGateway
 *  
 * @author fmartins
 *
 */
public enum ProdutosTableGateway {
	INSTANCE;
	
	private static String obterProdutoCodProd = 
		    "select idProduto, codProd, descricao, valorUnitario, qty, elegivelDesconto, idGrandeza " +
				"from produtos " +
				"where codProd = ?";
	
	public ResultSet getProdutoPorCodProd (int codProd) throws SQLException {
		// obter comando
		PreparedStatement comando = DataSource.INSTANCE.prepare(obterProdutoCodProd);			
		// set statement arguments
		comando.setInt(1, codProd);
		// executa SQL
		return comando.executeQuery();
	}
	
	private static String obterProdutoId = 
		    "select idProduto, codProd, descricao, valorUnitario, qty, elegivelDesconto, idGrandeza " +
				"from produtos " +
				"where idProduto = ?";
	
	public ResultSet getProdutoPorId (int idProduto) throws SQLException {
		// obter comando
		PreparedStatement comando = DataSource.INSTANCE.prepare(obterProdutoId);			
		// set statement arguments
		comando.setInt(1, idProduto);
		// executa SQL
		return comando.executeQuery();
	}
	
	private static String actualizarInventario =
			"update produtos " +
			   "set qty = ? " +
			   "where idProduto = ?";
	
	public void actualizarValorInventario(int idProduto, double qty) throws SQLException {
		// obter comando
		PreparedStatement comando = DataSource.INSTANCE.prepare(actualizarInventario);			
		// set statement arguments
		comando.setDouble(1, qty);
		comando.setInt(2, idProduto);
		// executa SQL
		comando.executeUpdate();
	}
}
