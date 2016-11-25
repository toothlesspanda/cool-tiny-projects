package services.persistence.rowDataGateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.persistence.DataSource;
import services.persistence.PersistenceException;
import services.persistence.RecordNotFoundException;

/**
 * A representação de um registo da tabela de produtos como um
 * objeto em memória.
 *	
 * Chamadas de atenção:
 * 1. Ver classe ClientesRowGateway
 * 
 * @author fmartins
 *
 */
public class ProdutosRowGateway {
	// atributos que representam os campos da tabela
	private int idProduto;
	private int codProd;
	private String descricao;
	private double valorUnitario;
	private double qty;
	private String elegivelDesconto;
	private int idGrandeza;
	
	// Getters e setters para os atributos
	public int getCodProd() {
		return codProd;
	}

	public void setCodProd(int codProd) {
		this.codProd = codProd;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public String getElegivelDesconto() {
		return elegivelDesconto;
	}

	public void setElegivelDesconto(String elegivelDesconto) {
		this.elegivelDesconto = elegivelDesconto;
	}

	public int getIdGrandeza() {
		return idGrandeza;
	}

	public void setIdGrandeza(int idGrandeza) {
		this.idGrandeza = idGrandeza;
	}

	// não necessitamos de um setter porque o Id é gerado automaticamente.
	public int getIdProduto() {
		return idProduto;
	}

	// actualizar a base de dados
	
	// a aplicação não insere novos artigos, por isso não há necessidade
	// de um método insert
	
	
	private static final String obterProdutoCodProdSQL = 
		    "select idProduto, codProd, descricao, valorUnitario, qty, elegivelDesconto, idGrandeza " +
				"from produtos " +
				"where codProd = ?";
		
	/**
	 * Gets a product given its codProd 
	 * 
	 * @param codProd The codProd of the product to search for
	 * @return The in-memory representation of the product
	 * @throws PersistenceException 
	 */
	public static ProdutosRowGateway getProdutoPorCodProd (int codProd) 
				throws PersistenceException {
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			// obter comando
			comando = DataSource.INSTANCE.prepare(obterProdutoCodProdSQL);			
			// set statement arguments
			comando.setInt(1, codProd);
			// executa SQL
			rs = comando.executeQuery();
			// cria um novo cliente
			return carregarProduto(rs);
		} catch (SQLException e) {
			throw new PersistenceException("Erro interno ao obter o produto " + codProd, e);
		} finally {
			DataSource.INSTANCE.close(rs);
			DataSource.INSTANCE.close(comando);
		}
	}
	
	private static final String obterProdutoIdSQL = 
		    "select idProduto, codProd, descricao, valorUnitario, qty, elegivelDesconto, idGrandeza " +
				"from produtos " +
				"where idProduto = ?";
	
	/**
	 * Gets a product given its product id 
	 * 
	 * @param idProduto The id of the product to search for
	 * @return The in-memory representation of the product
	 * @throws PersistenceException 
	 */
	public ProdutosRowGateway getProdutoPorId(int idProduto) 
			throws PersistenceException {
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			// obter comando
			comando = DataSource.INSTANCE.prepare(obterProdutoIdSQL);			
			// set statement arguments
			comando.setInt(1, idProduto);
			// executa SQL
			rs = comando.executeQuery();
			// cria um novo cliente
			return carregarProduto(rs);
		} catch (SQLException e) {
			throw new PersistenceException("Erro interno ao obter o produto " + idProduto, e);
		} finally {
			DataSource.INSTANCE.close(rs);
			DataSource.INSTANCE.close(comando);
		}
	}

	
	private static final String	actualizarInventarioSQL =
			"update produtos " +
					   "set qty = ? " +
					   "where idProduto = ?";
	
	/**
	 * Updates the product quantity
	 * 
	 * @throws PersistenceException
	 */
	public void actualizarValorInventario () throws PersistenceException {
		PreparedStatement comando = null;
		try {
			// obter comando
			comando = DataSource.INSTANCE.prepare(actualizarInventarioSQL);			
			// set statement arguments
			comando.setDouble(1, qty);
			comando.setInt(2, idProduto);
			// executa SQL
			comando.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException("Erro interno ao actualizar o produto " + idProduto, e);
		} finally {
			DataSource.INSTANCE.close(comando);
		}
	}
	
	private static ProdutosRowGateway carregarProduto(ResultSet rs) throws RecordNotFoundException {
		try {
			rs.next();
			ProdutosRowGateway novoProduto = new ProdutosRowGateway();
			novoProduto.idProduto = rs.getInt("idProduto");
			novoProduto.codProd = rs.getInt("codProd");
			novoProduto.descricao = rs.getString("descricao");
			novoProduto.valorUnitario = rs.getDouble("valorUnitario");
			novoProduto.qty = rs.getDouble("qty");
			novoProduto.elegivelDesconto = rs.getString("elegivelDesconto");
			novoProduto.idGrandeza = rs.getInt("idGrandeza");
			return novoProduto;
		} catch (SQLException e) {
			throw new RecordNotFoundException ("Produto não existe", e);
		}
	}
}
