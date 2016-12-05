package domain;

import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CatalogoBancos {
	
	@PersistenceContext private EntityManager em;
	
	public void addContaBanco(int id,Conta c) throws ApplicationException{
		
		TypedQuery<Banco> query = em.createNamedQuery("Banco.findByID", Banco.class);
		query.setParameter("idBanco",id);
		try{
			Banco b = query.getSingleResult();
			b.addListaContas(c);
			try{
				em.getTransaction().begin();
				em.persist(c);
				em.getTransaction().commit();
			} catch (Exception e) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				throw new ApplicationException("Erro ao adicionar a conta ao banco", e);
			} 
			
			
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o banco pelo ID "+id);
		}	
	}
	public void addContaPrazoBanco(int id,double deposito,double taxaJuro,int idGestor,Calendar limite,boolean renovacaoAutomatica) throws ApplicationException{
	
		TypedQuery<Banco> query = em.createNamedQuery("Banco.findByID", Banco.class);
		query.setParameter("idBanco",id);
		
		TypedQuery<Gestor> query2 = em.createNamedQuery("Gestor.findByID", Gestor.class);
		query2.setParameter("idGestor",idGestor);
		
		Gestor g = query2.getSingleResult();
		
		try{
			Prazo p = new Prazo(deposito, taxaJuro, g, limite, renovacaoAutomatica);
			Banco b = query.getSingleResult();
			
			b.addListaContas(p);
			p.setBanco(b);
			try{
				em.getTransaction().begin();
				em.persist(p);
				em.getTransaction().commit();
			} catch (Exception e) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				throw new ApplicationException("Erro ao adicionar a conta ao banco", e);
			} 
			
			
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o banco pelo ID "+id);
		}	
	}
	public void addContaEmprestimoBanco(int id,double deposito,double taxaJuro,int idGestor,Calendar limite,List<Prazo> lp) throws ApplicationException{
		
		TypedQuery<Banco> query = em.createNamedQuery("Banco.findByID", Banco.class);
		query.setParameter("idBanco",id);
		TypedQuery<Gestor> query2 = em.createNamedQuery("Gestor.findByID", Gestor.class);
		query2.setParameter("idGestor",idGestor);
		
		
		try{
			
			Gestor g = query2.getSingleResult();
			
			Emprestimo ee = new Emprestimo(deposito, taxaJuro, g, limite, lp);
			Banco b = query.getSingleResult();
			System.out.println("Designacao "+b.getDesignacao());
			b.addListaContas(ee);
			ee.setBanco(b);
			try{
				em.getTransaction().begin();
				em.persist(ee);
				em.getTransaction().commit();
			} catch (Exception e) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				throw new ApplicationException("Erro ao adicionar a conta ao banco", e);
			} 
			
			
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o banco pelo ID "+id);
		}	
	}
	
	
	public void addBanco(String sigla,String designacao) throws ApplicationException{
		
		Banco banco = new Banco(sigla,designacao);
		
		try{
			em.getTransaction().begin();
			em.persist(banco);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Erro ao adicionar o gestor", e);
		}
	}
	public Banco getBancoByID(int id) throws ApplicationException{
		
		TypedQuery<Banco> query = em.createNamedQuery("Banco.findByID", Banco.class);
		query.setParameter("idBanco",id);
		try{
			return query.getSingleResult();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o banco pelo ID "+id);
		}	
	}
	public void addGestorBanco(int id,String email,String nome,int telefone) throws ApplicationException{
		
		TypedQuery<Banco> query = em.createNamedQuery("Banco.findByID", Banco.class);
		query.setParameter("idBanco",id);
		try{
			
			Banco b = query.getSingleResult();
			Gestor g = new Gestor(email, nome, telefone,b);
			b.addListaGestor(g);
			try{
				em.getTransaction().begin();
				em.persist(b);
				em.getTransaction().commit();
			} catch (Exception e) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				throw new ApplicationException("Erro ao adicionar o gestor", e);
			} 
			
			
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o banco pelo ID "+id);
		}	
	}

	public Banco getBancoByDESIGNACAO(String designacao) throws ApplicationException{
		
		TypedQuery<Banco> query = em.createNamedQuery("Banco.findByDESIGNACAO", Banco.class);
		query.setParameter("designacao",designacao);
		try{
			return query.getSingleResult();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o banco pela designação "+designacao);
		}	
	}
	
	public Banco getBancoBySIGLA(String sigla) throws ApplicationException{
		
		TypedQuery<Banco> query = em.createNamedQuery("Banco.findBySIGLA", Banco.class);
		query.setParameter("sigla",sigla);
		try{
			return query.getSingleResult();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o banco pela sigla "+sigla);
		}	
	}
	

	public List<Banco> getBancos() throws ApplicationException{
		
		TypedQuery<Banco> query = em.createNamedQuery("Banco.findAll", Banco.class);
		
		try{
			return query.getResultList();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter os bancos todos "+ e.getMessage());
		}	
	}
	
		 public void removeBanco(String designacao) throws ApplicationException{
		  
		  
		  try{
			  em.getTransaction().begin();
			  TypedQuery<Banco> query = em.createNamedQuery("Banco.removeByDesignacao", Banco.class);
			  query.setParameter("designacao",designacao);
			  em.getTransaction().commit();
			} catch (Exception e) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				throw new ApplicationException("Erro no update do telefone do gestor", e);
			} 
		 }
		 public void removeBancoByID(int id) throws ApplicationException{
			 
			  try{
			  em.getTransaction().begin();
			  TypedQuery<Banco> query = em.createNamedQuery("Banco.removeByID", Banco.class);
			  query.setParameter("idBanco",id);
			  em.getTransaction().commit();
			} catch (Exception e) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				throw new ApplicationException("Erro no update do telefone do gestor", e);
			} 
		}
		 
}
