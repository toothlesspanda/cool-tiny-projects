package domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CatalogoGrandezas {

	private EntityManagerFactory emf;
	
	public CatalogoGrandezas(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public Grandeza getGrandeza (int idGrandeza) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		Grandeza grandeza = em.find(Grandeza.class, idGrandeza);
		em.close();
		if (grandeza != null)
			return grandeza;
		else
			throw new ApplicationException ("Grandeza " + idGrandeza + " n‹o existe");
	}
	
}
