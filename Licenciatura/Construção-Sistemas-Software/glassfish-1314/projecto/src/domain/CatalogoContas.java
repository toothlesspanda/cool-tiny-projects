package domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;


public class CatalogoContas {

	private EntityManagerFactory emf;

	public CatalogoContas(EntityManagerFactory emf){
		this.emf =emf;
	}
	
	public List<Conta> getContas() throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Conta> query = em.createNamedQuery("Conta.findALL",Conta.class);
		try{
			return query.getResultList();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter as contas");
		}	
	}
	public Conta getConta(int id) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Conta> query = em.createNamedQuery("Conta.findByID",Conta.class);
		query.setParameter("idConta",id);
		try{
			return query.getSingleResult();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter a conta" + id);
		}	
	}
	public List<PlanoPagamento> getPlanos() throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		TypedQuery<PlanoPagamento> query = em.createNamedQuery("PlanoPagamento.findALL",PlanoPagamento.class);
		try{
			return query.getResultList();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter os planos de pagamento");
		}	
	}
	public PlanoPagamento getPlanoByID(int id) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		TypedQuery<PlanoPagamento> query = em.createNamedQuery("PlanoPagamento.findByID",PlanoPagamento.class);
		query.setParameter("idPlanoPagamento",id);
		try{
			return query.getSingleResult();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o plano de pagamento com id: " + id);
		}	
	}
	public List<Prestacao> getPrestacoesDoPlanoByID(int id) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		TypedQuery<PlanoPagamento> query = em.createNamedQuery("PlanoPagamento.findByID",PlanoPagamento.class);
		query.setParameter("idPlanoPagamento",id);
		
		try{
			PlanoPagamento p = query.getSingleResult();
			return p.getPrestacoes();
			
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o plano de pagamento com id: " + id);
		}	
	}
	
	public void pagaPrestacao(int id) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		TypedQuery<Prestacao> query = em.createNamedQuery("Prestacao.findByID",Prestacao.class);
		query.setParameter("idPrestacao",id);
		
		try{
			Prestacao p = query.getSingleResult();
			p.pagar();
			p.getPlano().actualizaPagas();
			try {
				em.getTransaction().begin();
				em.merge(p);
				em.getTransaction().commit();
				return;
			} catch (Exception e) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				throw new ApplicationException("Erro ao pagar prestacao", e);
			} finally {
				em.close();
			}
		}catch(Exception e){
			throw new ApplicationException("Erro ao pagar prestacao com id:" + id);
		}	
		
	}
	public List<String> mapaFluxos() throws ApplicationException{
		 EntityManager em = emf.createEntityManager();
		  List<String> ls = new LinkedList<String>(); 
		  String sb = new String();
		  double total = 0.0;
		  TypedQuery<Banco> query = em.createNamedQuery("Banco.findAll", Banco.class);
		  try{
				List<Banco> lb =  query.getResultList();
				for(Banco b:lb){
					List<Conta> lc = b.getListaContas();
					for(Conta c:lc){
						sb = c.descricao();
						total = total + c.calculaFluxo();
						ls.add(sb);
					}
				}
				ls.add(String.valueOf(total));
				return ls;
			}catch(Exception e){
				throw new ApplicationException("Erro ao obter os bancos todos "+ e.getMessage());
			}	
	}
	
	
}
