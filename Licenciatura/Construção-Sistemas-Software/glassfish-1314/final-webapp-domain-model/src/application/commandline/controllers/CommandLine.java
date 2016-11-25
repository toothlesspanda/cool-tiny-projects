package application.commandline.controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import domain.ApplicationException;
import domain.DescontoElegivel;
import domain.DescontoPTotal;
import domain.DescontoSemDesconto;
import domain.Grandeza;
import domain.Produto;
import domain.VeA;

/**
 * The big bang class.
 *	
 * @author fmartins
 */
public class CommandLine {

	/**
	 * Fire in the hole!!
	 * 
	 * @param args Command line parameters
	 */
	public static void main(String[] args) {
		EntityManagerFactory emf = null;
		try {
			emf = Persistence.createEntityManagerFactory("domain-model-jpa");
			
			// Cria o objecto da aplica��o
			VeA app = new VeA (emf);

			// Cria os dois servi�os dispon�veis
			ClientesService cs = new ClientesService (app.getClientesHandler());
			VendasService vs = new VendasService(app.getVendasHandler());

			// Efectua um pequeno teste
			try {
				
/*				criarDescontos(emf);
				criarGrandezas(emf);
				criarProdutos(emf);
				Iterator <Desconto> it = app.getDescontosHandler().getDescontos();
				while(it.hasNext()) {
					Desconto d = it.next();
					System.out.println(d.getDescricao());
				}
		
				// adicionar um cliente.
				cs.adicionarCliente(168027852, "Cliente 1", 217500255, 2);
*/				
				// criar uma nova venda
				vs.novaVenda(168027852);

				// adicionar produtos
				vs.acrescentarProdutoAVenda(123, 10);
				vs.acrescentarProdutoAVenda(124, 5);
				
				// obter o valor do desconto
				double desconto = vs.getValorDoDesconto();
				System.out.println(desconto);
	/*			*/
			} catch (ApplicationException e) {
				System.out.println("Erro: " + e.getMessage());
				// para efeitos de depura��o. Tipicamente na aplica��o  
				// podia ficar associado a um bot�o de "detalhes" da mensagem de erro.
				if (e.getCause() != null) 
					System.out.println("Causa: ");
				e.printStackTrace();
			}	
			
			System.out.println("Done");
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally{
			emf.close();
		}
	}

	
	// m�todo para criar os descontos da primeira vez.
	// n�o me preocupo com as exce��es.
	private static void criarDescontos(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(new DescontoSemDesconto(1, "Sem desconto"));
		em.persist(new DescontoPTotal(2, "Percentagem do Total (acima de limiar)", 50, 0.1));
		em.persist(new DescontoElegivel(3, "Percentagem do Total Elegivel", 0.15));
		em.getTransaction().commit();
		em.close();
	}
	
	// m�todo para criar os produtos da primeira vez.
	// n�o me preocupo com as exce��es.
	private static void criarProdutos(EntityManagerFactory emf) 
			throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Grandeza unidades = em.find(Grandeza.class, 2);
		if (unidades == null)
			throw new ApplicationException("Grandeza 2 n�o existe! Nao se podem criar produtos automaticamente!");
		em.persist(new Produto(123, "Prod 1", 100, 500, false, unidades));
		em.persist(new Produto(124, "Prod 2", 35, 1000, true, unidades));
		em.getTransaction().commit();
		em.close();
	}

	
	// m�todo para criar as grandezas da primeira vez.
	// n�o me preocupo com as exce��es.
	private static void criarGrandezas(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(new Grandeza(1, "Quilogramas", "Kg"));
		em.persist(new Grandeza(2, "Unidades", "un"));
		em.getTransaction().commit();
		em.close();
	}

	
}
