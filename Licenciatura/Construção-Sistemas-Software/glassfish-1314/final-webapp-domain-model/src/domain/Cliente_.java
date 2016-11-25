package domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-11-27T14:49:21.878+0000")
@StaticMetamodel(Cliente.class)
public class Cliente_ {
	public static volatile SingularAttribute<Cliente, Integer> idCliente;
	public static volatile SingularAttribute<Cliente, Integer> npc;
	public static volatile SingularAttribute<Cliente, String> designacao;
	public static volatile SingularAttribute<Cliente, Integer> telefone;
	public static volatile SingularAttribute<Cliente, Desconto> desconto;
}
