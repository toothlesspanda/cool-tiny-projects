package persistence;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import domain.Morada;
import domain.Moradas;

public class WriteMoradas {
	
	public void write(Moradas ms,Morada m) throws IOException{
		
		PrintWriter pw = new PrintWriter(new FileWriter("moradas.csv",true));
			 
			pw.write(m.getRua() + ";"+ Integer.toString(m.getPorta())+";" + 
									m.getAndar() + ";"+ m.getCp()+";"+ m.getLocal()+";"+m.getDistEmp() +"\n");
			pw.close();
}

}
