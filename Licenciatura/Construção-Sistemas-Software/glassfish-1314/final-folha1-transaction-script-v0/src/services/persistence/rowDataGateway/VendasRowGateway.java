package services.persistence.rowDataGateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import services.persistence.DataSource;
import services.persistence.PersistenceException;
import services.persistence.RecordNotFoundException;

/**
 * A representação de um registo da tabela de vendas como um
 * objeto em memória.
 *	
 * Chamadas de atenção:
 * 1. Ver classe ClientesRowGateway
 * 
 * 2. O Java disponibiliza duas classes Date: uma no pacote 
 * java.util, que é a usualmente utilizamos e outro no pacote
 * java.sql, que é uma subclasse da classe java.util.date e
 * que transforma a representação em milisegundos de acordo
 * com o tipo Date "das bases de dados". Mais informação em
 * http://download.oracle.com/javase/6/docs/api/java/sql/Date.html.
 * 
 * 3. Ao inserir uma nova venda só prenchemos os campos data
 * e idCliente e o campo emAberto fica sempre a 'S'. 
 * Os restantes ou são prenchidos automaticamente (idVenda)
 * ou são preenchidos aquando da finalização da venda (total, desconto
 * e passa-se o campo emAberto para 'N'), tipicamente através 
 * da operação pagamento.
 * 
 * @author fmartins
 *
 */
public class VendasRowGateway {
	// atributos que representam os campos da tabela
	private int idVenda;
	private Date data;
	private double total;
	private double desconto;
	private String emAberto;
	private int idCliente;
		
	// Getters e setters para os atributos
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public String getEmAberto() {
		return emAberto;
	}

	public void setEmAberto(String emAberto) {
		this.emAberto = emAberto;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	// não há setIdVenda, porque o seu valor é atribuído
	// automaticamente pela base de dados.
	public int getIdVenda() {
		return idVenda;
	}

	// actualizar a base de dados
	private static final String inserirVendaSQL = 
			"insert into vendas (data, total, desconto, emAberto, idCliente) " +
					"values (?, 0, 0, 'S', ?)";

	public void insert () throws PersistenceException {		
		PreparedStatement comando = null;
		try {
			// obter comando
			comando = DataSource.INSTANCE.prepareGetGenKey(inserirVendaSQL);			
			// set statement arguments
			comando.setDate(1, (new java.sql.Date(data.getTime())));
			comando.setInt(2, idCliente);
			// executa SQL
			comando.executeUpdate();
			// obter o idCliente gerado automaticamente
			ResultSet rs = comando.getGeneratedKeys();
			rs.next(); 
			idVenda = rs.getInt(1);
			rs.close();
		} catch (SQLException e) {
			throw new PersistenceException ("Erro interno!", e);
		} finally {
			DataSource.INSTANCE.close(comando);
		}
	}
	
		
	private static final String obterVendaSQL = 
			"select idVenda, data, total, desconto, emAberto, idCliente " +
				"from vendas " +
				"where idVenda = ?";
		
	/**
	 * Gets a sale by its id 
	 * 
	 * @param idVenda The NPC number of the client to search for
	 * @return The new object that represents an in-memory sale
	 * @throws PersistenceException 
	 */
	public static VendasRowGateway getVendaPorId (int idVenda) throws PersistenceException {
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			// obter comando
			comando = DataSource.INSTANCE.prepare(obterVendaSQL);			
			// set statement arguments
			comando.setInt(1, idVenda);		
			// executa SQL
			rs = comando.executeQuery();
			// cria um novo cliente
			return carregarVenda(rs);
		} catch (SQLException e) {
			throw new PersistenceException("Erro interno ao adicionar venda", e);
		} finally {
			DataSource.INSTANCE.close(rs);
			DataSource.INSTANCE.close(comando);
		} 
	}
		
	private static VendasRowGateway carregarVenda(ResultSet rs) 
					throws RecordNotFoundException {
		try {
			rs.next();
			VendasRowGateway novaVenda = new VendasRowGateway();
			novaVenda.idVenda = rs.getInt("idVenda");
			novaVenda.data = rs.getDate("data");
			novaVenda.total = rs.getDouble("total");
			novaVenda.desconto = rs.getDouble("desconto");
			novaVenda.emAberto = rs.getString("emAberto"); 
			novaVenda.idCliente = rs.getInt("idCliente");
			return novaVenda;
		} catch (SQLException e) {
			throw new RecordNotFoundException ("Venda não existe", e);
		}
	}
}
