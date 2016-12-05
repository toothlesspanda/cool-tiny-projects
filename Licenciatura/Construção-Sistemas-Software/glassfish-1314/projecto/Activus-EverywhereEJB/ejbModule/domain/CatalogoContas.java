package domain;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CatalogoContas {

	@PersistenceContext private EntityManager em;

	
	public List<Conta> getContas() throws ApplicationException {
		
		TypedQuery<Conta> query = em.createNamedQuery("Conta.findALL",Conta.class);
		try{
			return query.getResultList();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter as contas");
		}	
	}
	public List<Prazo> getContasPrazo() throws ApplicationException {
		
		TypedQuery<Prazo> query = em.createNamedQuery("Conta.findByTipo",Prazo.class);
		query.setParameter("classe",Arrays.asList(Prazo.class));
		try{
			return query.getResultList();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter as contas");
		}	
	}
	
	public List<Prazo> getContasPrazoDoBanco(int id) throws ApplicationException {
		
		TypedQuery<Prazo> query = em.createNamedQuery("Conta.findByBancoWithType",Prazo.class);
		query.setParameter("idBanco",id);
		query.setParameter("classe",Arrays.asList(Prazo.class));
		System.out.println("catalogocontas");
		try{
			
			List<Prazo> lp = query.getResultList();
			System.out.println("inicio");
			for(Prazo p : lp){
				System.out.println(p.descricao());
			}
			System.out.println("fim");
			return query.getResultList();
			
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter as contas");
		}	
	}
	
	public List<Emprestimo> getContasEmprestimo() throws ApplicationException {
		
		TypedQuery<Emprestimo> query = em.createNamedQuery("Conta.findByTipo",Emprestimo.class);
		query.setParameter("classe",Arrays.asList(Emprestimo.class));
		try{
			return query.getResultList();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter as contas");
		}	
	}
	
	public List<Emprestimo> getContasEmprestimoDoBanco(int id) throws ApplicationException {
		
		TypedQuery<Emprestimo> query = em.createNamedQuery("Conta.findByBancoWithType",Emprestimo.class);
		query.setParameter("idBanco",id);
		query.setParameter("classe",Arrays.asList(Emprestimo.class));
		
		try{
			return query.getResultList();
			
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter as contas");
		}	
	}
	
	public Conta getConta(int id) throws ApplicationException {
		
		TypedQuery<Conta> query = em.createNamedQuery("Conta.findByID",Conta.class);
		query.setParameter("idConta",id);
		try{
			return query.getSingleResult();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter a conta" + id);
		}	
	}
	public Prazo getContaPrazo(int id) throws ApplicationException {
		
		TypedQuery<Prazo> query = em.createNamedQuery("Conta.findPrazoByID",Prazo.class);
		query.setParameter("idConta",id);
		try{
			return query.getSingleResult();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter a conta" + id);
		}	
	}
	public List<PlanoPagamento> getPlanos() throws ApplicationException {
		
		TypedQuery<PlanoPagamento> query = em.createNamedQuery("PlanoPagamento.findALL",PlanoPagamento.class);
		try{
			return query.getResultList();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter os planos de pagamento");
		}	
	}
	public PlanoPagamento getPlanoByID(int id) throws ApplicationException {
		
		TypedQuery<PlanoPagamento> query = em.createNamedQuery("PlanoPagamento.findByID",PlanoPagamento.class);
		query.setParameter("idPlanoPagamento",id);
		try{
			return query.getSingleResult();
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o plano de pagamento com id: " + id);
		}	
	}
	public List<Prestacao> getPrestacoesDoPlanoByID(int id) throws ApplicationException {
		
		TypedQuery<PlanoPagamento> query = em.createNamedQuery("PlanoPagamento.findByID",PlanoPagamento.class);
		query.setParameter("idPlanoPagamento",id);
		
		try{
			PlanoPagamento p = query.getSingleResult();
			return p.getPrestacoes();
			
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter o plano de pagamento com id: " + id);
		}	
	}
	public List<Prestacao> getPrestacoesPagasEsteMes() throws ApplicationException {
		
		TypedQuery<Prestacao> query = em.createNamedQuery("Prestacao.findThisMonth",Prestacao.class);
		Calendar inicio = Calendar.getInstance();
		inicio.set(Calendar.DAY_OF_MONTH,1);
		inicio.set(Calendar.HOUR_OF_DAY, 0);
		inicio.clear(Calendar.MINUTE);
		inicio.clear(Calendar.SECOND);
		inicio.clear(Calendar.MILLISECOND);
		Calendar fim = Calendar.getInstance();  
        fim.add(Calendar.MONTH, 1);  
        fim.set(Calendar.DAY_OF_MONTH, 1);  
        fim.add(Calendar.DATE, -1);  
        
		query.setParameter("inicioMes", inicio);
		query.setParameter("fimMes", fim);
		
		try{
			return query.getResultList();
			
			
		}catch(Exception e){
			throw new ApplicationException("Erro ao obter as prestacoes pagas este mes");
		}	
	}
	
	
	public void pagaPrestacao(int id) throws ApplicationException{
		
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
			} 
		}catch(Exception e){
			throw new ApplicationException("Erro ao pagar prestacao com id:" + id);
		}	
		
	}
	public List<String> mapaFluxos() throws ApplicationException{
		
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
						total = total + c.getCalculaFluxo();
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
