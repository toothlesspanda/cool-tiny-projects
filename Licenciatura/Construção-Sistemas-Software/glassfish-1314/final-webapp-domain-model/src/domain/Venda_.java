package domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-11-27T14:49:22.193+0000")
@StaticMetamodel(Venda.class)
public class Venda_ {
	public static volatile SingularAttribute<Venda, Integer> idVenda;
	public static volatile SingularAttribute<Venda, Date> data;
	public static volatile SingularAttribute<Venda, Boolean> emAberto;
	public static volatile SingularAttribute<Venda, Cliente> cliente;
	public static volatile ListAttribute<Venda, ProdutoVenda> produtosVenda;
}
