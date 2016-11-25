package domain;

import java.util.Calendar;
import java.util.List;

import javax.persistence.*;

public class CatalogoGestores {

	private EntityManagerFactory emf;

	public CatalogoGestores(EntityManagerFactory emf){
		this.emf=emf;
	}
	
	
	public Gestor getGestorByEMAIL(String email) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.findByEMAIL", Gestor.class);
		query.setParameter("email",email);
		try{
			return query.getSingleResult();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o gestor pelo email "+email);
		}	
	}
	
	public Gestor getGestorByNAME(String nome) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.findByNAME", Gestor.class);
		query.setParameter("nome",nome);
		try{
			return query.getSingleResult();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o gestor pelo nome "+nome);
		}	
	}
	public Gestor getGestorByID(int id) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.findByID", Gestor.class);
		query.setParameter("idGestor",id);
		try{
			return query.getSingleResult();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o gestor pelo identificador "+id);
		}	
	}
	public int getBancoIDByGestorID(int id) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.findByID", Gestor.class);
		query.setParameter("idGestor",id);
		try{
			return query.getSingleResult().getIDBanco();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o gestor pelo identificador "+id);
		}	
	}
	
	
	
	public void updateGestorEmail(String email,int id) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		try{
		em.getTransaction().begin();
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.updateEMAIL", Gestor.class);
		query.setParameter("email",email);
		query.setParameter("idGestor",id);
		query.executeUpdate();
		em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Erro no update do email do gestor", e);
		} finally {
			em.close();
		}
	}
	public void updateGestorTelefone(int telefone,int id) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		try{
		em.getTransaction().begin();
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.updateTELEFONE", Gestor.class);
		query.setParameter("telefone",telefone);
		query.setParameter("idGestor",id);
		query.executeUpdate();
		em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Erro no update do telefone do gestor", e);
		} finally {
			em.close();
		}
	}
	public void deleteGestor(int id) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		try{
		em.getTransaction().begin();
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.delete", Gestor.class);
		query.setParameter("idGestor",id);
		query.executeUpdate();
		em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Erro no update do telefone do gestor", e);
		} finally {
			em.close();
		}
	}
	public List<Gestor> getGestores() throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.findALL",Gestor.class);
		try{
			return query.getResultList();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter as contas");
		}	
	}
	public List<Conta> getContasDeGestor(int id) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.findByID", Gestor.class);
		query.setParameter("idGestor",id);
		
		try{
			
			Gestor g = query.getSingleResult();	
			System.out.println("ID " + g.getID());
			return g.getContas();
			
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o gestor pelo identificador "+id);
		}	
	}
	
	public void associaContaGestor(int idGestor,int idConta) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.findByID", Gestor.class);
		query.setParameter("idGestor",idGestor);
		
		try{
			CatalogoContas catalogoContas = new CatalogoContas(emf);
			Conta c = catalogoContas.getConta(idConta);
			Gestor g = query.getSingleResult();	
			g.addConta(c);
			System.out.println("aqui11111");
			try {
				em.getTransaction().begin();
				em.persist(g);
				em.getTransaction().commit();
				return;
			} catch (Exception e) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				throw new ApplicationException("Erro ao adicionar conta ao gestor", e);
			} finally {
				em.close();
			}
			
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o gestor pelo identificador "+idGestor);
		}
	}
	
	
	/**public List<Conta> getContas(int id) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT c FROM Conta c,Gestor g WHERE g.idGestor = :idGestor");
		q.setParameter("idGestor",id);
		try{
			return q.getResultList();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter as contas");
		}	
	}**/
}
