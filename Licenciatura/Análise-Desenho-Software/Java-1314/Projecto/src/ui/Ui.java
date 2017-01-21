package ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

import persistence.ReadCC;
import persistence.ReadFrota;
import persistence.ReadMoradas;
import persistence.ReadMotoristas;
import persistence.WriteCO;
import domain.CatalogoCliente;
import domain.CatalogoOrdens;
import domain.Cliente;
import domain.Frota;
import domain.Morada;
import domain.Moradas;
import domain.Motorista;
import domain.Ordem;
import domain.Pagamento;
import domain.Produto;
import domain.Produtos;
import domain.Servico;
import domain.Viatura;

public class Ui {

	public void run() throws IOException {

		// leitura e escrita de ficheiros
		ReadMoradas rMor = new ReadMoradas();
		ReadCC rCC = new ReadCC();
		ReadFrota rF = new ReadFrota();
		ReadMotoristas rMot = new ReadMotoristas();
		WriteCO wCO = new WriteCO();

		Pagamento p = null;
		Produtos pros = new Produtos();
		Motorista m = null;
		Ordem o = null;
		Produto pro = null;
		Frota f = new Frota();
		Viatura v = null;
		Cliente c = null;
		Servico serv = new Servico();
		Moradas ms = new Moradas();

		CatalogoCliente cc = new CatalogoCliente();
		CatalogoOrdens co = new CatalogoOrdens();

		FileInputStream inF = new FileInputStream("frota.csv");
		FileInputStream inMot = new FileInputStream("motorista.csv");
		FileInputStream inMor = new FileInputStream("morada.csv");
		FileInputStream inCc = new FileInputStream("cc.csv");

		rMor.read(ms, inMor);
		rCC.read(inCc, inMor, cc, ms); // le catalogo cliente
		rMot.read(inMot, f); // le motoristas
		rF.read(inF, f); // le frota

		Scanner sc = new Scanner(System.in);
		boolean ciclo = true;
		while (ciclo) {
			System.out.println("Menu");
			System.out.println("1 - Ver Clientes");
			System.out.println("2 - Ver Moradas");
			System.out.println("3 - Ver Motoristas");
			System.out.println("4 - Ver Frota");
			System.out.println("5 - Fazer Ordem");
			System.out.println("0 - Terminar");
			int opc = sc.nextInt();

			switch (opc) {
			case 0:
					ciclo=false;
					System.out.println("Tenha um bom dia!");
					break;
			case 1:
				verClientes(cc);
				break;
			case 2:
				verMoradas(ms);
				break;
			case 3:
				verMotoristas(f);
				break;
			case 4:
				verFrota(f);
				break;
			case 5:
				fazerOrdem(sc,cc,co,c,f,serv,pro,pros,ms,m,o,v,p,wCO);
				break;
			
			default:
				System.out.println("Opção não existente");
				break;
			}

		}

	}

	private void fazerOrdem(Scanner sc, CatalogoCliente cc,
				CatalogoOrdens co,Cliente c, Frota f,Servico serv,
					Produto pro, Produtos pros, Moradas ms, Motorista m,
					Ordem o,Viatura v, Pagamento p, WriteCO wCO) throws IOException{
		System.out.print("ID Cliente: ");
		int id = sc.nextInt();

		if (cc.getClientes().containsKey(id)) {
			c = cc.getClientes().get(id);
			System.out.println("Bem-vindo " + c.getNome());
		} else {
			System.out.println("Cliente não existe");
			return;
		}

		// verifica se ha alguma viatura disponivel.
		Set<Integer> idsVia = f.getHashF().keySet();
		for (Integer j : idsVia) {
			f.getHashF().get(j);
			if (f.getHashF().get(j).getLivre() == true) {
				v = f.getHashF().get(j);
				System.out.println("A viatura com matrícula: "
						+ v.getMatr() + " esta disponivel.");
				break;
			}

		}
		// motorista disponivel
		Set<Integer> idsMot = f.getHashMot().keySet();
		for (Integer k1 : idsMot) {

			if (f.getHashMot().get(k1).getState() == false) {

				m = f.getHashMot().get(k1);
				System.out
						.println("Será o motorista: " + m.getName() + ".");
				break;
			}

		}

		// regista produtos
		System.out.println("Insira os produtos(descricao, peso): ");
		String descricao = sc.next();
		int peso = sc.nextInt();
		int pesoA = 0;

		double[] a = new double[100];

		if (peso < 2) {
			pesoA = -2;
		} else if (2 <= peso && peso < 50) {
			pesoA = 50;
		} else if (50 <= peso && peso < 100) {
			pesoA = 100;
		} else if (100 <= peso && peso < 500) {
			pesoA = 500;
		} else if (500 <= peso && peso <= 1000) {
			pesoA = 1000;
		}

		a[0] = serv.price(pesoA);
		pro = new Produto(a[0], peso, descricao);

		pros.getPro().add(pro);

		int k = 1;
		while (descricao.compareTo("0") != 0) {
			descricao = sc.next();

			if (descricao.compareTo("0") == 0)
				break;
			peso = sc.nextInt();
			if (peso < 2) {
				pesoA = -2;
			} else if (2 <= peso && peso < 50) {
				pesoA = 50;
			} else if (50 <= peso && peso < 100) {
				pesoA = 100;
			} else if (100 <= peso && peso < 500) {
				pesoA = 500;
			} else if (500 <= peso && peso <= 1000) {
				pesoA = 1000;
			}
			a[k] = serv.price(pesoA);

			pro = new Produto(a[k], peso, descricao);

			pros.getPro().add(pro);
			k++;
		}

		// pagamento

		double precoTotal = 0;

		for (int j = 0; j < a.length; j++) {
			precoTotal += a[j];
		}

		p = new Pagamento(precoTotal);
		System.out.println("Preço total: " + p.getPagamento());
		System.out.println("Tipo de pagamento(1-mb/2-cash): ");
		int tipoPagamento = sc.nextInt();

		if (tipoPagamento == 1) {
			p.mb();
		} else if (tipoPagamento == 2) {
			p.cash();
		}

		// escolher moradas
		System.out.println(c.getMoradas().size());
		System.out.println("Escolhe moradas origem[espaco]destino: ");
		for (int mor = 0; mor < c.getMoradas().size(); mor++) {
			System.out.println(mor + " : "
					+ c.getMoradas().get(mor).getCp());
		}
		int moradaOri = sc.nextInt();
		int moradaDest = sc.nextInt();
		Morada ori= c.getMoradas().get(moradaOri);
		Morada dest=c.getMoradas().get(moradaDest);
		
		// ordem

		o = new Ordem(c, m, v, pros, p, ori, dest);

		o.started();
		System.out.println("Ordem começou");
		co.addOrdem(o);
		m.ocupado();
		if (o.getState() == 1) {
			wCO.write(o);
		}


		
		
	}
	
	private void verClientes(CatalogoCliente cc) {

		System.out.println("ID. Nome	Telefone");
	
		for (int i = 1; i < cc.getClientes().size(); i++) {
			Cliente c = cc.getClientes().get(i);
			System.out.println(+i + ". " + c.getNome() + " - "
					+ c.getTelefone());
		}

	}

	private void verMoradas(Moradas ms) {
		System.out.println("ID. Rua	, Porta,	Andar,	CP,	Localidade,	DistEmp");
		for (int i = 1; i < ms.getHashM().size(); i++) {
			Morada m = ms.getHashM().get(i);
			System.out.println(+i + ". " + m.getRua() + ", " + m.getPorta()
					+ " , " + m.getAndar() + " , " + m.getCp() + " , " + " , "
					+ m.getLocal() + " , " + m.getDistEmp());
		}

	}

	private void verMotoristas(Frota f) {
		System.out.println("ID. Nome	Salario		Numero		 NIF		Ocupado");
		for (int i = 1; i < f.getHashMot().size(); i++) {
			Motorista m = f.getHashMot().get(i);
			System.out.println(+i + ". " + m.getName() + "	" + m.getSal()
					+ "		" + m.getNumber() + "	" + 
					m.getNIB() + "	" 
					+ m.getState());
		}

	}
	private void verFrota(Frota f) {
		System.out.println("ID. Matricula	Ano	Tipo		Kms		Livre");
		for (int i = 1; i < f.getHashF().size(); i++) {
			Viatura v = f.getHashF().get(i);
			System.out.println(+i + ". " + v.getMatr() + "	" + v.getAno()
					+ "	" + v.getTipo() + " 	" + v.getkmActual() + "		 " 
					+ v.getLivre());
		}

	}

}
