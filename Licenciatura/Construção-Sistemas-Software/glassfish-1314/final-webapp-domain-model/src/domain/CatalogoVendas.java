package domain;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CatalogoVendas {

	private EntityManagerFactory emf;
	
	public CatalogoVendas(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public Venda novaVenda (Cliente cliente) throws ApplicationException {
		Venda v = new Venda(new Date(), cliente);
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(v);
			em.getTransaction().commit();
 		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Erro ao Venda", e);
		} finally {
			em.close();
		}

		return v;
	}

	// @pre venda.isEmAberto()
	// @pre produto.qty() >= qty
	public void acrescentarProdutoAVenda (Venda venda, Produto produto, double qty) 
				throws ApplicationException {
		// diminuir valor ao inventario
		produto.setQty (produto.getQty() - qty);
		// adicionar o produto a lista de produtos da venda
		ProdutoVenda pv = venda.acrescentarProdutoAVenda(produto, qty);
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			// actualiza o valor do produto
			em.merge(produto);
			// grava o novo produtoVenda
			em.persist(pv);
			// actualiza a venda (tem mais um elemento na lista)
			em.merge(venda);
			em.getTransaction().commit();
 		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Erro ao Produto � Venda", e);
		} finally {
			em.close();
		}
	}

}
