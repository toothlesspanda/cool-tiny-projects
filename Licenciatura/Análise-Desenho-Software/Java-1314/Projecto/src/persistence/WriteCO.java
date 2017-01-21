package persistence;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import domain.Ordem;

public class WriteCO {
	
	public void write(Ordem o) throws IOException{
	
		PrintWriter pw = new PrintWriter(new FileWriter("co20140521.csv",true));
		
		
			String client = o.orderClient().getNome()+ " ; Rua:";
			String moradaOri = o.ori().getRua() + " Porta:"
					+ o.ori().getPorta() + " Andar:"
					+ o.ori().getAndar() + " CP:"
					+ o.ori().getCp() + " Local:"
					+ o.ori().getLocal() + " DistEmp:"
					+ o.ori().getDistEmp() + "; Rua:";

			String moradaDest = o.dest().getRua() + " Porta:"
					+ o.dest().getPorta() + " Andar:"
					+ o.dest().getAndar() + " CP:"
					+ o.dest().getCp() + " Local:"
					+ o.dest().getLocal() + " DistEmp:"
					+ o.dest().getDistEmp() + ";";
			String produtos = "";
			for (int pro = 0; pro < o.orderProdut()
					.getPro().size(); pro++) {
		
				produtos += o.orderProdut().getPro()
						.get(pro).getDesc()
						+ " Preco:"
						+ o.orderProdut().getPro()
								.get(pro).getPreco() + ";";
			}

			String viatura = o.orderViatura().getMatr()
					+ ";";
			String motorista = o.orderMotorista()
					.getName()
					+ "\n";
			
			int pagatipo = o.orderPay().getTipo();  // passar para string
			String tipo="";
			if(pagatipo == 1){
				tipo = "dinheiro|";
			}else{
				tipo="transferencia|";
			}
			
			String pagavalor= Double.toString(o.orderPay().getPagamento())+";";

			String s = (client + moradaOri + moradaDest + produtos + pagavalor + tipo
					+ viatura + motorista
						);
		
			pw.write(s);
			
			pw.close();

		
		
	}

}
