package domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import services.persistence.DataSource;
import services.persistence.tableDataGateway.ProdutosTableGateway;

public enum Produto {
	INSTANCE;
	
	public int getProdutoId (int codProd) throws ApplicationException {
		ResultSet rs = null;
		try {
			rs = ProdutosTableGateway.INSTANCE.getProdutoPorCodProd(codProd);
			int idProduto = 0;
			if (rs.next()) 
				idProduto = rs.getInt("idProduto");
			else 
				throw new ApplicationException("Não existe produtos com CodProd " + codProd);
			return idProduto;
		} catch (SQLException e) {
			throw new ApplicationException ("Erro interno ao obter produto com CodProd " + codProd, e);
		} finally {
			DataSource.INSTANCE.close(rs);			
		}
	}

	public void diminuirExistencia (int idProduto, double qty) 
			throws ApplicationException {
		// se tem quantidade suficiente...
		double qtyStock = getQty (idProduto);
		if (qtyStock >= qty) {
			// diminuir valor ao inventario
			try {
				ProdutosTableGateway.INSTANCE.actualizarValorInventario (idProduto, qtyStock - qty);
			} catch (SQLException e) {
				throw new ApplicationException("Erro interno diminuir existências ao produto " + idProduto, e);			
			}
		} else
			throw new ApplicationException("O produto " + idProduto + " tem quantidade ("  + 
					qtyStock + ") insuficiente para o valor pedido " + qty);
	}
	
	public double getQty(int idProduto) throws ApplicationException {
		return getCampoProdutoDouble (idProduto, "qty");
	}

	public double getValorUnitario(int idProduto) throws ApplicationException {
		return getCampoProdutoDouble (idProduto, "valorUnitario");
	}
	
	private double getCampoProdutoDouble (int idProduto, String campo) throws ApplicationException {
		ResultSet rs = null;
		try {
			rs = ProdutosTableGateway.INSTANCE.getProdutoPorId(idProduto);
			if (rs.next()) 
				return rs.getDouble(campo);				
			else 
				throw new ApplicationException("O produto com id: " + idProduto + " não existe.");
		} catch (SQLException e) {
			throw new ApplicationException("Erro interno ao aceder ao produto " + idProduto, e);
		} finally {
			DataSource.INSTANCE.close(rs);
		}
	}

}
