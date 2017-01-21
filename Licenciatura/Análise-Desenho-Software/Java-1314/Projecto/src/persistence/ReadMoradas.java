package persistence;

import java.io.FileInputStream;
import java.io.IOException;


import domain.Morada;
import domain.Moradas;

public class ReadMoradas {

	public void read(Moradas ms, FileInputStream inMor) throws IOException {

		int nreads3 = 0;
		String n3 = "";
		while ((nreads3 = inMor.read()) != -1) {
			String s = String.valueOf((char) nreads3);
			n3 += s;
		}

		String[] tokensMor = n3.split("\n");

		for (int ka = 1; ka < tokensMor.length; ka++) {
			String[] coise123 = tokensMor[ka].split(";");
			String rua = coise123[1];
			int porta = Integer.parseInt(coise123[2]);
			String andar = coise123[3];
			String cp = coise123[4];
			String local = coise123[5];
			int distEmp = Integer.parseInt(coise123[6]);

			Morada m = new Morada(rua, andar, porta, cp, local, distEmp);
			ms.addMorada(m);
			
		}
	}
}
