package persistence;

import java.io.FileInputStream;
import java.io.IOException;

import domain.CatalogoCliente;
import domain.Cliente;
import domain.Moradas;

public class ReadCC {
	
	
	public void read(FileInputStream inF,FileInputStream inMor, CatalogoCliente cc,Moradas ms) throws IOException{
		
		
		int nreads=0;
        String n2 = "";
        while((nreads = inF.read()) != -1){
        	String s = String.valueOf((char)nreads);
        	n2 += s;
        }
	
	    String[] tokensCc = n2.split("\n");
	    
        Cliente cC ;
        for(int i=0;i< tokensCc.length;i++){
            String[] coise= tokensCc[i].split(";");
        	int idCc = i+1;
        	String nomeCc =coise[0];
        	int contacto = Integer.parseInt(coise[1]);
        	cC = new Cliente(nomeCc, contacto);
       
        	
        	String[] coise1 = coise[2].split("/");
        	
        	for(int as=0; as<coise1.length;as++){
        		if(ms.getHashM().containsKey(Integer.parseInt(coise1[as]))){
        				cC.addMorada(ms.getHashM().get(Integer.parseInt(coise1[as])));     				
        		}
        	}
        	cc.getClientes().put(idCc, cC);
        	
        }
	}

}
