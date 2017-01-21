package persistence;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import domain.CatalogoCliente;
import domain.Cliente;

public class WriteCC {
	
	public void write(CatalogoCliente cc,Cliente c) throws IOException{
		
			PrintWriter pw = new PrintWriter(new FileWriter("cc20140521.csv",true));
				 
				String morada = "";
				for (int m = 0; m < c.getMoradas().size(); m++) {
					morada= Integer.toString(m)+"/";
				}
				pw.write(c.getNome() + ";"+Integer.toString(c.getTelefone()) + ";"+morada+"\n");
				pw.close();
	}
}
