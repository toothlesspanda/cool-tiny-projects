package domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class CatalogoProdutos {

	private EntityManagerFactory emf;
	
	public CatalogoProdutos(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public Produto getProduto (int codProd) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<Produto> query = em.createNamedQuery("Produto.findByCodProd", Produto.class);
			query.setParameter("codProd", codProd);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new ApplicationException ("Produto " + codProd + " n‹o existe");
		} finally {
			em.close();
		}
	}	
}
