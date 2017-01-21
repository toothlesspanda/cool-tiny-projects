package persistence;

import java.io.FileInputStream;
import java.io.IOException;

import domain.Frota;
import domain.Viatura;

public class ReadFrota {
	
	public void read(FileInputStream inF, Frota f) throws IOException{
		int nreads=0;
        String n = "";
        while((nreads = inF.read()) != -1){
        	String s = String.valueOf((char)nreads);
        	n += s;
        }
        
        String[] tokensF = n.split("\n");
 
        for(int i=1;i< tokensF.length;i++){
            String[] coise= tokensF[i].split(";");
        	int id = Integer.parseInt(coise[0]);
        	String mat =coise[1];
        	int kmactual = Integer.parseInt(coise[2]);
            int ano = Integer.parseInt(coise[3]);
            String tipo= coise[4];
            boolean livre = Boolean.parseBoolean(coise[5]);
     
            Viatura v = new Viatura(mat,kmactual,0,ano,tipo,livre);
            f.getHashF().put(id, v);
        }
		
	}

}
