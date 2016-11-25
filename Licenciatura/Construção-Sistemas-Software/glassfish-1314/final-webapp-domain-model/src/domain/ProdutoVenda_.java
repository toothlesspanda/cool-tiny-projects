package domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-11-27T14:49:22.193+0000")
@StaticMetamodel(ProdutoVenda.class)
public class ProdutoVenda_ {
	public static volatile SingularAttribute<ProdutoVenda, Integer> idProdutoVenda;
	public static volatile SingularAttribute<ProdutoVenda, Produto> produto;
	public static volatile SingularAttribute<ProdutoVenda, Double> qty;
}
