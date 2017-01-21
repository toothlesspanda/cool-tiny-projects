package domain;

import java.util.HashMap;

public class Moradas {
		
		private HashMap<Integer,Morada> hashM;
		
		public Moradas(){
			hashM = new HashMap<Integer,Morada>();
		}
		
		public HashMap<Integer,Morada> getHashM(){
			return hashM;
		}
		
		public void addMorada(Morada m){
			hashM.put(hashM.size()+1, m);
		}
		
		public void delMorada(int id){
			hashM.remove(id);
		}

		
}
