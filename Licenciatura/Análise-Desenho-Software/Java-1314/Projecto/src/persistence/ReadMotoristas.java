package persistence;

import java.io.FileInputStream;
import java.io.IOException;

import domain.Frota;
import domain.Motorista;

public class ReadMotoristas {
	
	public void read(FileInputStream inMot, Frota f) throws IOException{
		int nreads1=0;
        String n1 = "";
        while((nreads1 = inMot.read()) != -1){
        	String s = String.valueOf((char)nreads1);
        	n1 += s;
        }
        
        String[] tokensMot = n1.split("\n");
 
        for(int i=1;i< tokensMot.length;i++){
            String[] coise= tokensMot[i].split(";");
        	int idMot = Integer.parseInt(coise[0]);
        	String nomeMot =coise[1];
        	int nif = Integer.parseInt(coise[2]);
            int telefone = Integer.parseInt(coise[3]);
            int salario= Integer.parseInt(coise[4]);
            int licenca= Integer.parseInt(coise[5]);
            boolean ocupado= Boolean.parseBoolean(coise[6]);
            
            Motorista m = new Motorista(idMot, nomeMot, nif, telefone, salario,licenca,ocupado);
            f.addMotorista(m);
        }
	}

}
