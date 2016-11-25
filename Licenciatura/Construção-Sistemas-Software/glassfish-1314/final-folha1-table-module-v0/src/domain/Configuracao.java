package domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import services.persistence.DataSource;
import services.persistence.tableDataGateway.ConfiguracoesTableGateway;

public enum Configuracao {
	INSTANCE;
		
	public double getLimiteTotal () throws ApplicationException {
		return getCampoProdutoDouble ("limiteTotal");
	}
	
	public double getPercentagemTotal () throws ApplicationException {
		return getCampoProdutoDouble ("percentagemTotal");
	}

	public double getPercentagemElegivel () throws ApplicationException {
		return getCampoProdutoDouble ("percentagemElegivel");
	}
	
	private double getCampoProdutoDouble (String campo) throws ApplicationException {
		ResultSet rs = null;
		try {
			rs = ConfiguracoesTableGateway.INSTANCE.getConfiguracoes();
			if (rs.next()) 
				return rs.getDouble(campo);	
			else 
				throw new ApplicationException("Erro interno ao aceder à configuração da aplicação");
		} catch (SQLException e) {
			throw new ApplicationException("Erro interno ao aceder à configuração da aplicação", e);
		} finally {
			DataSource.INSTANCE.close(rs);
		}
	}

}
