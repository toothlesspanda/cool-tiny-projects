package services.persistence.rowDataGateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import services.persistence.DataSource;
import services.persistence.PersistenceException;

/**
 * A representação de um registo da tabela de produtosVenda como um
 * objeto em memória.
 *	
 * Chamadas de atenção:
 * 1. Ver comentários na classe ClientesRowGateway
 * 
 * @author fmartins
 *
 */
public class ProdutosVendaRowGateway {
	// atributos que representam os campos da tabela
	private int idItemVenda;
	private int idVenda;
	private int idProduto;
	private double qty;
	
	public ProdutosVendaRowGateway () {
	}
	
	// Getters e setters para os atributos
	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	// não necessitamos de um setter porque o Id é gerado automaticamente.
	public int getIdItemVenda() {
		return idItemVenda;
	}

	
	// actualizar a base de dados
	private static final String inserirProdutoVendaSQL = 
			"insert into produtosVenda (idVenda, idProduto, qty) " +
				"values (?, ?, ?)";
	
	public void insert () throws PersistenceException {
		PreparedStatement comando = null;
		try {
			// obter comando
			comando = DataSource.INSTANCE.prepareGetGenKey(inserirProdutoVendaSQL);			
			// set statement arguments
			comando.setInt(1, idVenda);
			comando.setInt(2, idProduto);
			comando.setDouble(3, qty);		
			// executa SQL
			comando.executeUpdate();
			// obter o idCliente gerado automaticamente
			ResultSet rs = comando.getGeneratedKeys();
			rs.next(); 
			idItemVenda = rs.getInt(1);
			rs.close();
		} catch (SQLException e) {
			throw new PersistenceException ("Erro interno!", e);
		} finally {
			DataSource.INSTANCE.close(comando);
		}
	}
	
	
	private static final String obterProdutosVendaSQL = 
			"select idItemVenda, idVenda, idProduto, qty " +
				"from produtosVenda " +
				"where idVenda = ?";
		
	/**
	 * Gets a client by its NPC 
	 * 
	 * @param npc The NPC number of the client to search for
	 * @return The result set of the query
	 * @throws SQLException 
	 */
	public static Set<ProdutosVendaRowGateway> getProdutosDaVenda (int idVenda) throws PersistenceException {
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			// obter comando
			comando = DataSource.INSTANCE.prepare(obterProdutosVendaSQL);			
			// set statement arguments
			comando.setInt(1, idVenda);			
			// executa SQL
			rs = comando.executeQuery();
			// cria um novo cliente
			return carregarProdutosVenda(rs);
		} catch (SQLException e) {
			throw new PersistenceException("Erro interno ao obter os produtos da venda", e);
		} finally {
			DataSource.INSTANCE.close(rs);
			DataSource.INSTANCE.close(comando);
		}
	}
		
	private static Set<ProdutosVendaRowGateway> carregarProdutosVenda(ResultSet rs) 
					throws SQLException {
		Set<ProdutosVendaRowGateway> resultado = new HashSet<ProdutosVendaRowGateway>();
		while (rs.next()) {
			ProdutosVendaRowGateway novoProdutoVenda = new ProdutosVendaRowGateway();
			novoProdutoVenda.idItemVenda = rs.getInt("idItemVenda");
			novoProdutoVenda.idVenda = rs.getInt("idVenda");
			novoProdutoVenda.idProduto = rs.getInt("idProduto");
			novoProdutoVenda.qty = rs.getDouble("qty");
			resultado.add(novoProdutoVenda);
		}
		return resultado;		
	}
}
