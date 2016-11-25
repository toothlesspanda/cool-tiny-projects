package main;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import application.BancosService;
import application.ContasService;
import application.GestoresService;
import domain.Activus;
import domain.Banco;
import domain.Conta;
import domain.Emprestimo;
import domain.Gestor;
import domain.PlanoPagamento;
import domain.Prazo;



public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EntityManagerFactory emf = null;
		try {
			emf = Persistence.createEntityManagerFactory("activus-jpa");
			
			Activus app = new  Activus(emf);
		
			ContasService cs = new ContasService(app.getContasHandler());
			
			
			
			
			/**
			List<PlanoPagamento> pl = cs.getPlanos();
			
			for(PlanoPagamento p:pl){
				System.out.println(p.quantasPrestacoes());	
			}**/
			
			
			
			/**BancosService bs = new BancosService(app.getBancosHandler());
			
			
			System.out.println("aki");
			bs.adicionarBanco("cgd", "caixa geral");
			System.out.println("aki2");
			bs.addGestorBanco(1,"g3@gmail.com","joao3", 213234458);
			System.out.println("aki");
			
			System.out.println("aki2");
			Calendar c = Calendar.getInstance();
			c.set(2015,12,25);
			//Emprestimo e = new Emprestimo(1000.0,0.1,c, null);
			
			GestoresService gs = new GestoresService(app.getGestoresHandler());**/
			
			/**List<Conta> cl = gs.getContasDeGestor(1);
			
			for(Conta con: cl){
				System.out.println(con.getID());
			}**/
			
			//bs.addContaEmprestimoBanco(1, 1000.0,0.1,1,c, null);
			
			//cs.pagaPrestacao(1);
			
			
			
			//Banco b = bs.getBanco(1);
			
			//GestoresService gs = new GestoresService(app.getGestoresHandler());
			
			//gs.associaContaGestor(1, 1);
			
			/**List<Gestor> lg = b.getListaGestor();
			
			Calendar ca = Calendar.getInstance();
			ca.set(2015,11,25);
			
			Prazo p = new Prazo(22.2, 11.1,new Cliente("Cliente 1", 217500255, 242421421),lg.get(0),ca);
			
			bs.addContaBanco(1, p);**/
			
			//List<Conta> lc = b.getListaContas();
			

			/**for(int i = 0;i<lc.size();i++){
				Conta c = lc.get(i);
				System.out.println(c.getCliente().getNome());
			}**/
			
			/**List<Gestor> lg = b.getListaGestor();
			
			for(int i = 0;i<lg.size();i++){
				Gestor g = lg.get(i);
				System.out.println(g.getNome());
			}**/
			
			
			//Get cliente
			
			/**Cliente c1 = cs.devolveClientePorId(1);
			Cliente c2 = cs.devolveClientePorNif(217500254);
			
			System.out.println(c1.getNome());
			System.out.println(c2.getNome());
			**/
			
			
			//Adicionar gestores
			
			/**System.out.println("Aki");
			bs.adicionarGestor("g@gmail.com","joao", 213234456);
			System.out.println("Aki2");
			bs.adicionarGestor("g2@gmail.com","joao2", 213234457);
			System.out.println("Aki3");
			bs.adicionarGestor("g3@gmail.com","joao3", 213234458);**/
			
			//get gestores
			
			/**Gestor g1 = bs.devolveGestorPorEmail("g@gmail.com");
			System.out.println(g1.getEmail());
			Gestor g2 = bs.devolveGestorPorNome("joao2");
			System.out.println(g2.getEmail());
			Gestor g3 = bs.devolveGestorPorID(3);
			System.out.println(g3.getEmail());**/
			
			
			//update Gestor
			//delete
			//bs.deleteGestor(3);
			//bs.adicionarGestor("g3@gmail.com","joao3", 213234458);
			//cs.removeCliente(2);
			
			System.out.println("Done");
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally{
			emf.close();
		}
		
	}

}
