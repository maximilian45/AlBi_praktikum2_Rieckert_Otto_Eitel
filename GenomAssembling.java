//package aufgabe2;
import java.util.*;
import java.io.*;

class GenomAssembling{
	
	public static void main(String[] args) throws IOException{
	
		FileReader fr = new FileReader("C:\\Users\\Acedon\\Desktop\\Uni\\Albi Prjekt 2\\test.txt");
		BufferedReader br = new BufferedReader(fr);
		
		List<String> reads = new ArrayList<String>();
		
		for(int i = 0; i <= 33609; i++){
		//for(int i = 0; i <= 15; i++){
			reads.add(br.readLine());
			
		}
		br.close();
		
		int k = 50;
		ArrayList<String> brokenReads = new ArrayList<String>();
		ArrayList<String> startNodes = new ArrayList<String>();
		ArrayList<String> endNodes = new ArrayList<String>();
		ArrayList<ArrayList<String>> kanten = new ArrayList<ArrayList<String>>();
		
		//Bilden der reads breaks
		
		//nehmen des ersten reads
		for(int l = 1; l < reads.size(); l++){
			String read = reads.get(l);
			System.out.println(read);
			
			String pattern = "";
			String node = "";
			String node2 = "";
				//bildung dessen kmeres
			for(int j = 0; j<(101-k) ; j++){ //anzupassen bei anderer read-Laenge!
			//for(int j = 0; j<(4-k) ; j++){
				pattern = read.substring(j, j+k);
				node = read.substring(j, j+k-1);
				node2 = read.substring(j+1, j+k);
			}
			brokenReads.add(pattern);
			startNodes.add(node);  // enthält duplikate!
			endNodes.add(node2);
			
		}		
		
		String node = new String();
		ArrayList<String> realStartNodes = new ArrayList<String>();
		
		for (int m = 0; m < startNodes.size(); m++) {
		//for (int m = 0; m < 6; m++) {
			node = startNodes.get(m);
			
			if(!realStartNodes.contains(node)) {
				//alleKanten.clear();
				ArrayList<String> alleKanten = new ArrayList<String>();
				realStartNodes.add(node);
				
				alleKanten.add(brokenReads.get(m));
				
				for (int n = m+1; n < startNodes.size(); n++) {
					if(startNodes.get(n).equals(node)) {
						alleKanten.add(brokenReads.get(n));
					}	
					
				}
				kanten.add(alleKanten);
			} 			
		}
		
		// de Bruijn Graph:
		// Knoten = realStartNodes (+ Knoten ohne ausgehende Kanten)
		// Kanten = kanten
		
		// Anfang von Contig = branching Knoten! + Knoten ohne eingehende Kanten
		// 												= Knoten, der nicht in endNodes enthalten ist
		

		int index = realStartNodes.size() - 1;
		int nextIndex;
		ArrayList<String> contigs = new ArrayList<String>();
		FileWriter file = new FileWriter("C:\\Users\\Acedon\\Desktop\\Uni\\Albi Prjekt 2\\output.fasta"); // FILEREADER ANPAsSEN!!!
		int numContigs = 0;
		// 
		while(index >= 0) {
			int numBranches = kanten.get(index).size();
			if(numBranches > 1) {
				for(int i = 0; i < numBranches; i++) {
					String contig = new String();
					contig = kanten.get(index).get(i); 
					nextIndex = realStartNodes.indexOf(contig.substring(contig.length()-k+1, contig.length())); // Wählen des Nachfolgenen Knotens
					while (nextIndex != -1) { // endnode of last thingy is also startnode
						if(kanten.get(nextIndex).size() == 1 && nextIndex != index) { // next Node is non-branching er keine ausgehenden Kanten mehr hat und nicht der selbe knoten ist
							contig += kanten.get(nextIndex).get(0).charAt(k-1); // Gehen die Kante entlang speichern sie 
							kanten.remove(nextIndex); // und löschen sie
							realStartNodes.remove(nextIndex);// Löschen den Knoten, da er keine Kanten mehr besitzt und somit nicht mehr benötigt wird
							if(nextIndex < index) index--; // Da wir den Knoten davor gelöscht haben rutscht der aktuelle auf und sein index sinkt somit um 1
							nextIndex = realStartNodes.indexOf(contig.substring(contig.length()-k+1, contig.length()));
						} else { // if next node is branching break while + end contig
							nextIndex = -1;
						}
					}
					numContigs++;
					contigs.add(contig);
					file.write(">Contig Nummer "+ numContigs);
					file.write(System.lineSeparator());
					file.write(contig); // FASTA ANPASSUNG
					file.write(System.lineSeparator());
					
				}
				kanten.remove(index);
				realStartNodes.remove(index);
			}
			index--;
		}
		
		
		ArrayList<String> yetToDo = new ArrayList<String>();
		yetToDo.addAll(realStartNodes);		
		
		// these are the starting nodes of all other contigs
		// all of them are non-branching! 
		yetToDo.removeAll(endNodes);	
		
		for(int j = 0; j < yetToDo.size(); j++) {
			String contig = new String();
			index = realStartNodes.indexOf(yetToDo.get(j));
			contig = kanten.get(index).get(0);
			nextIndex = realStartNodes.indexOf(contig.substring(contig.length()-k+1, contig.length())); 
			while (nextIndex != -1) { // endnode of last thingy is also startnode
				if(kanten.get(nextIndex).size() == 1 && nextIndex != index) { // next Node is non-branching
					contig += kanten.get(nextIndex).get(0).charAt(k-1);
					kanten.remove(nextIndex);
					realStartNodes.remove(nextIndex);
					if(nextIndex < index) index--;
					nextIndex = realStartNodes.indexOf(contig.substring(contig.length()-k+1, contig.length()));
				} else { // if next node is branching break while + end contig
					nextIndex = -1;
				}
			}
					numContigs++;
					contigs.add(contig);
					file.write(">Contig Nummer"+ numContigs);
					file.write(System.lineSeparator());
					file.write(contig); // FASTA ANPASSUNG
					file.write(System.lineSeparator());
		}
		file.close();
	}
}
