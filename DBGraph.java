package org.jgrapht.demo;

import java.net.*;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import java.util.*;
import java.io.*;

public final class DBGraph {

	private DBGraph(){
		
	}
	
	public static void main(String[] args){
		buildDBGraph();

		
	}
	
	private static ArrayList<ArrayList<String>> generateReads() throws IOException{
	
		FileReader fr = new FileReader("C:\\Users\\Acedon\\Desktop\\Uni\\albi2\\HelloJGraphT\\src\\org\\jgrapht\\demo\\test.txt");

		BufferedReader br = new BufferedReader(fr);
		
		List<String> reads = new ArrayList<String>();
		
		for(int i = 1; i <=33609; i++){
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
				String pattern = "";   //read.substring(i,(k-1+i));
				for(int j = 0; j<k ; j++){
					pattern += read.charAt(i+j);
				}
				temp.add(pattern);
			}
			brokenReads.add(temp);
		}
		return brokenReads;
		
	}
	
	public static String buildPrefix(String read){
		String prefix = "";
		for(int i = 0;i<=read.length()-2;i++){
			prefix += read.charAt(i);
		}
		return prefix;
	}
	
	public static String buildSuffix(String read){
		String suffix = "";
		for(int i = 1; i<=read.length()-1;i++){
			suffix += read.charAt(i);
		}
		return suffix;
	}
	
	public static boolean isPrefix(String prefix, String read){
		for(int i = 0; i <= read.length()-1;i++){
			if(!(read.charAt(i) == prefix.charAt(i))){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isSuffix(String suffix, String read){
		for(int i = 1; i< read.length();i++){
			if(!(read.charAt(i)==suffix.charAt(i-1))){
				return false;
			}
		}
		return true;
	}
	
	//FremdCode
	public static class RelationshipEdge<V> extends DefaultEdge {
        private V v1;
        private V v2;
        private String label;

        public RelationshipEdge(V v1, V v2, String label) {
            this.v1 = v1;
            this.v2 = v2;
            this.label = label;
        }

        public V getV1() {
            return v1;
        }

        public V getV2() {
            return v2;
        }

        public String toString() {
            return label;
        }
    }
	//FremCode Ende
	
	private static DirectedGraph<String, RelationshipEdge> buildDBGraph(){
		
		//Fremdcode
        DirectedGraph<String, RelationshipEdge> g =
                new DirectedMultigraph<String, RelationshipEdge>(
                        new ClassBasedEdgeFactory<String, RelationshipEdge>(RelationshipEdge.class));
		//FC Ende
		
        //Test Knoten
		/*g.addVertex("v1");
		g.addVertex("v2");
		g.addEdge("v1", "v2", new RelationshipEdge("v1","v2","e1"));
		*/

		
		try{
		ArrayList<ArrayList<String>> reads = generateReads();

		/* Schleife zum Testen ob reads richtig generiert wurden
		for(int i = 0; i< reads.size();i++){
			ArrayList<String> temp = reads.get(i);
			for(int j = 0; j<temp.size();j++){
				System.out.println(temp.get(j));
			}
			
		}
		//Aufbau von Kanten zu jedem read mit den dazugehörigen Knoten
		for(int i = 0; i<reads.size();i++){
			ArrayList<String> temp = reads.get(i);
			for(int j = 0; j< temp.size(); j++){
				String read = temp.get(j);
				String prefix = buildPrefix(read);
				String suffix = buildSuffix(read);
				g.addVertex(prefix);
				g.addVertex(suffix);
				g.addEdge(prefix,suffix, new RelationshipEdge(prefix,suffix,read));
			}
		}
		
		// Verschmelzen von gleichen Knoten
		for(RelationshipEdge edge : g.edgeSet()){
			String target = edge.getV2().toString();
			for(RelationshipEdge edge2 :g.edgeSet()){
				String source = edge2.getV1().toString();
				
			}
		}*/
		//Aufbau der Liste der k-1 mere 
		ArrayList<String> knotenListe = new ArrayList<String>();
		for(int i = 0; i<reads.size();i++){
			ArrayList<String> temp = reads.get(i);
			for(int j = 0; j< temp.size(); j++){
				knotenListe.add(buildPrefix(temp.get(i)));
				knotenListe.add(buildSuffix(temp.get(i)));
			}
		}
		// Löschen von dublikaten durch abspeichern in ein set, dass keine dublikate erlaubt 
		// und dann rückführen ind die knotenListe   knotenListe enthält alle k-1 mere der Reads, also alle suffixe und prefixe
		Set<String> hs = new HashSet<>();
		hs.addAll(knotenListe);
		knotenListe.clear();
		knotenListe.addAll(hs);
		
		//Liste der kmere von einer ArrayList ausArrayLists in eine einfach ArrayList umgewandelt splicedreads beinhaltet alle 85mere der reads
		ArrayList<String> splicedReads = new ArrayList<String>();
		for(int i = 0; i<reads.size();i++){
			ArrayList<String> temp = reads.get(i);
			for(int j = 0; j< temp.size(); j++){
				splicedReads.add(temp.get(j));
			}
		}
		
		//Aufbau des Graphen
		for(int i=0;i<knotenListe.size();i++){
			String temp1 = knotenListe.get(i);
			knotenListe.remove(i);
			for(int j = 0; j<knotenListe.size();j++){
				String temp2 = knotenListe.get(j);
				for(int k =0; k< splicedReads.size();k++){
					if((isPrefix(temp1,splicedReads.get(k))) && ( isSuffix(temp2,splicedReads.get(k) ) ) ){
						g.addEdge(temp1,temp2, new RelationshipEdge(temp1,temp2,(splicedReads.get(k))));
					}else{
						if((isPrefix(temp2,splicedReads.get(k))) && ( isSuffix(temp1,splicedReads.get(k) ) )){
							g.addEdge(temp2,temp1, new RelationshipEdge(temp2,temp1,(splicedReads.get(k))));
						}
					}
				}
			}
		}
		
		//Test Ausgabe
		for(RelationshipEdge edge : g.edgeSet()){
		System.out.println("KANTEN LABEL!!!" + edge.toString());
		}
		
		
		
		
		}catch(IOException e){
			e.printStackTrace();
		}
		return g;
		
	}
	
	
}
