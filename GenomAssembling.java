import java.util.*;
import java.io.*;

class GenomAssembling{
	public static void main(String[] args) throws IOException{
	
		FileReader fr = new FileReader("test.txt");
		BufferedReader br = new BufferedReader(fr);
		
		List<String> reads = new ArrayList<String>();
		
		for(int i = 0; i <= 33609; i++){
			reads.add(br.readLine());
			
		}
		br.close();
		
		int k = 85;
		ArrayList<ArrayList<String>> brokenReads = new ArrayList<ArrayList<String>>();
		
		//Bilden der reads breaks
		
		// EVT INERFFIZIENT
		
		//nehmen des ersten reads
		for(int l = 0; l < reads.size(); l++){
			ArrayList<String> temp = new ArrayList<String>();
			String read = reads.get(l);
			//durchgehen für die ersten stellen des einzelnen reads 
			for(int i = 0; i < reads.get(l).length()-k ; i++){
				String pattern = "";
				//bildung dessen kmeres
				for(int j = 0; j<k ; j++){
					pattern += read.charAt(i+j);
				}
				temp.add(pattern);
			}
			brokenReads.add(temp);
		}	
	}
}