package domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

public class CatalogoClientes {

	private EntityManagerFactory emf;
	
	public CatalogoClientes(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	public Cliente getClienteNPC (int npc) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Cliente> query = em.createNamedQuery("Clientes.findByNPC", Cliente.class);
		query.setParameter("npc", npc);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException ("Erro ao obter o cliente com NPC " + npc);
		} finally {
			em.close();
		}
	}
	
	public void adicionarCliente (int npc, String designacao, int telefone, Desconto desconto) 
			throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		try {
			Cliente novoCliente = new Cliente(npc, designacao, telefone, desconto);
			em.getTransaction().begin();
			em.persist(novoCliente);
			em.getTransaction().commit();
			return;
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Erro ao adicionar o cliente", e);
		} finally {
			em.close();
		}
	}
}
