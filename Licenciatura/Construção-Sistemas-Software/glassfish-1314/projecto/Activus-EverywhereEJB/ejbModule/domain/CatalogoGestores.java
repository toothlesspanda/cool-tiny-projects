package domain;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CatalogoGestores {

	@PersistenceContext private EntityManager em;

	
	
	
	public Gestor getGestorByEMAIL(String email) throws ApplicationException{
	
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.findByEMAIL", Gestor.class);
		query.setParameter("email",email);
		try{
			return query.getSingleResult();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o gestor pelo email "+email);
		}	
	}
	
	public Gestor getGestorByNAME(String nome) throws ApplicationException{
		
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.findByNAME", Gestor.class);
		query.setParameter("nome",nome);
		try{
			return query.getSingleResult();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o gestor pelo nome "+nome);
		}	
	}
	public Gestor getGestorByID(int id) throws ApplicationException{
		
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.findByID", Gestor.class);
		query.setParameter("idGestor",id);
		try{
			return query.getSingleResult();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o gestor pelo identificador "+id);
		}	
	}
	public int getBancoIDByGestorID(int id) throws ApplicationException{
		
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.findByID", Gestor.class);
		query.setParameter("idGestor",id);
		try{
			return query.getSingleResult().getIDBanco();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o gestor pelo identificador "+id);
		}	
	}
	
	
	
	public void updateGestorEmail(String email,int id) throws ApplicationException{
		
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
		} 
	}
	public void updateGestorTelefone(int telefone,int id) throws ApplicationException{
		
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
		} 
	}
	public void deleteGestor(int id) throws ApplicationException{
		
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
		} 
	}
	public List<Gestor> getGestores() throws ApplicationException{
		
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.findALL",Gestor.class);
		try{
			return query.getResultList();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter as contas");
		}	
	}
	public List<Conta> getContasDeGestor(int id) throws ApplicationException{
		
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.findByID", Gestor.class);
		query.setParameter("idGestor",id);
		
		try{
			
			Gestor g = query.getSingleResult();	
			return g.getContas();
			
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o gestor pelo identificador "+id);
		}	
	}
	
	public void associaContaGestor(int idGestor,int idConta) throws ApplicationException{
		
		TypedQuery<Gestor> query = em.createNamedQuery("Gestor.findByID", Gestor.class);
		query.setParameter("idGestor",idGestor);
		
		TypedQuery<Conta> query2 = em.createNamedQuery("Conta.findByID", Conta.class);
		query2.setParameter("idConta",idConta);
		
		
		try{
			Conta c = query2.getSingleResult();
			Gestor g = query.getSingleResult();	
			g.addConta(c);
			try {
				em.getTransaction().begin();
				em.persist(g);
				em.getTransaction().commit();
				return;
			} catch (Exception e) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				throw new ApplicationException("Erro ao adicionar conta ao gestor", e);
			}
			
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o gestor pelo identificador "+idGestor);
		}
	}
	
	

}
