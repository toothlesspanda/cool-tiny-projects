package domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-11-27T14:49:22.193+0000")
@StaticMetamodel(Produto.class)
public class Produto_ {
	public static volatile SingularAttribute<Produto, Integer> idProduto;
	public static volatile SingularAttribute<Produto, Integer> codProd;
	public static volatile SingularAttribute<Produto, String> descricao;
	public static volatile SingularAttribute<Produto, Double> valorUnitario;
	public static volatile SingularAttribute<Produto, Double> qty;
	public static volatile SingularAttribute<Produto, Boolean> elegivelDesconto;
	public static volatile SingularAttribute<Produto, Grandeza> grandeza;
}
