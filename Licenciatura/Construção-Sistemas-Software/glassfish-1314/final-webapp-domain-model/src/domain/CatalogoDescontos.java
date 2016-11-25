package domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class CatalogoDescontos {

	private EntityManagerFactory emf;
	
	public CatalogoDescontos(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public Desconto getDesconto (int tipoDesconto) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<Desconto> query = em.createNamedQuery("Desconto.findByTipo", Desconto.class);
			query.setParameter("tipoDesconto", tipoDesconto);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new ApplicationException ("Erro ao obter o desconto com tipo " + tipoDesconto);
		} finally {
			em.close();
		}
	}

	public List<Desconto> getDescontos() throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<Desconto> query = em.createNamedQuery("Desconto.findAll", Desconto.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new ApplicationException ("Erro ao obter a lista de descontos", e);
		} finally {
			em.close();
		}
	}
}
