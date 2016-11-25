package domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import services.persistence.DataSource;
import services.persistence.tableDataGateway.VendasTableGateway;

public enum Venda {
	INSTANCE;

	public int novaVenda (int idCliente) throws ApplicationException {
		try {
			return VendasTableGateway.INSTANCE.novaVenda(idCliente);
		} catch (SQLException e) {
			throw new ApplicationException("Erro interno ao adicionar a venda", e);
		}
	}
	
	public boolean isEmAberto(int idVenda) throws ApplicationException {
		ResultSet rs = null;
		try {
			rs = getVenda (idVenda);
			rs.next(); 
			return rs.getString("emAberto").equals("S");
		} catch (SQLException e) {
			throw new ApplicationException("Erro interno ao aceder ˆ venda " + idVenda, e);
		} finally {
			DataSource.INSTANCE.close(rs);
		}
	}
	
	public void acrescentarProdutoAVenda(int idVenda, int idProduto, double qty) 
			throws ApplicationException {
		// S— Ž v‡lido acrescentar produtos para vendas em curso (em aberto)
		if (!isEmAberto (idVenda))
			throw new ApplicationException("N‹o se podem acrescentar artigos a uma venda fechada.");		
		// diminuir valor ao inventario
		Produto.INSTANCE.diminuirExistencia (idProduto, qty);
		// adicionar o produto ˆ venda 
		try {
			VendasTableGateway.INSTANCE.acrescentarArtigoAVenda(idVenda, idProduto, qty);
		} catch (SQLException e) {
			throw new ApplicationException("Erro interno ao vender o produto " + idProduto, e);			
		}
	}
	
	public double getValorDoDesconto (int idVenda) throws ApplicationException {
		// se a venda est‡ fechada, devolve o valor de desconto j‡ calculado e guardado
		if (!isEmAberto (idVenda)) 
			return getDescontoDB(idVenda);
		
		// calcula o desconto

		// obter o nœmero de cliente a partir da venda
		int idCliente = getCliente(idVenda);

		double desconto = 0;

		try {
			// obter os produtos vendidos
			ResultSet rs = VendasTableGateway.INSTANCE.getProdutosDaVenda(idVenda);
		
			switch (Cliente.INSTANCE.getTipoDesconto(idCliente)) {
			case 1:
				desconto = 0;
			case 2:
				desconto = calcularPercentagemTotal (rs);
				break;
			case 3:
				desconto = calcularPercentagemProdutoElegivel (rs);
				break;
			}
		} catch (SQLException e) {
			throw new ApplicationException("Erro interno calcular desconta para a vender " + idVenda, e);			
		}

		return desconto;
	}
	
	public int getCliente (int idVenda) 
			throws ApplicationException {
		ResultSet rs = null;
		try {
			rs = getVenda (idVenda);
			rs.next(); 
			return rs.getInt("idCliente");
		} catch (SQLException e) {
			throw new ApplicationException("Erro interno ao aceder ˆ venda " + idVenda, e);
		} finally {
			DataSource.INSTANCE.close(rs);
		}
	}

	/**
	 * Computes the type 1 discount
	 * @param rs The result set with the sold products 
	 * @return The discount value
	 * @throws SQLException When some unexpected error occurs.
	 * @throws ApplicationException 
	 */
	private double calcularPercentagemTotal(ResultSet rs) 
				throws SQLException, ApplicationException {
		double totalVenda = 0;
		while (rs.next()) {
			totalVenda += rs.getDouble("valorUnitario") * 
					Produto.INSTANCE.getValorUnitario(rs.getInt("idProduto"));
		}
		
		return totalVenda > Configuracao.INSTANCE.getLimiteTotal() ? 
					totalVenda * Configuracao.INSTANCE.getPercentagemTotal() : 0;
	}
	

	/**
	 * Computes the type 2 discount
	 * @param rs The result set with the sold products 
	 * @return The discount value
	 * @throws SQLException When some unexpected error occurs.
	 * @throws ApplicationException 
	 */
	private double calcularPercentagemProdutoElegivel(ResultSet rs) 
					throws SQLException, ApplicationException {
		double totalElegivel = 0;
		while (rs.next()) {
			if (rs.getString("ElegivelDesconto").equals("S"))
				totalElegivel += rs.getDouble("valorUnitario") * 
									Produto.INSTANCE.getValorUnitario(rs.getInt("idProduto"));
		}

		return totalElegivel * Configuracao.INSTANCE.getPercentagemElegivel();
	}
	
	private double getDescontoDB(int idVenda)
			throws ApplicationException {
		ResultSet rs = null;
		try {
			rs = getVenda (idVenda);
			rs.next(); 
			return rs.getDouble("desconto");
		} catch (SQLException e) {
			throw new ApplicationException("Erro interno ao aceder ˆ venda " + idVenda, e);
		} finally {
			DataSource.INSTANCE.close(rs);
		}
	}
	
	
	private ResultSet getVenda (int idVenda) throws ApplicationException {
		try {
			ResultSet rs = VendasTableGateway.INSTANCE.getVendaPorId(idVenda);
			if (rs.getFetchSize() == 0) 
				throw new ApplicationException("A venda com id: " + idVenda + " n‹o existe.");
			return rs;
		} catch (SQLException e) {
			throw new ApplicationException("Erro interno ao aceder ˆ venda " + idVenda, e);
		} 
	}
}
