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
		int x = 0;
		for(int i = 0; i <= read.length()-1;i++){
			if(!(read.charAt(i) == prefix.charAt(x))){
				return false;
			}
			if(x<83){
			x+=1;
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
	
	private static ArrayList<ArrayList<String>> generateReads() throws IOException{
	
		FileReader fr = new FileReader("C:\\Users\\Acedon\\Desktop\\Uni\\albi2\\HelloJGraphT\\src\\org\\jgrapht\\demo\\test2.txt");

		BufferedReader br = new BufferedReader(fr);
		
		ArrayList<String> reads = new ArrayList<String>();
		
		for(int i = 1; i <=33609; i++){
			reads.add(br.readLine());
			
		}
		br.close();

		int k = 85;
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		ArrayList<String> brokenReads = new ArrayList<String>();
		ArrayList<String> knotenListe = new ArrayList<String>();
		Set<String> hs = new HashSet<>();
		
		//Bilden der reads breaks
		
		// EVT INERFFIZIENT
		
		//nehmen des ersten reads
		for(int l = 0; l < reads.size(); l++){
			//ArrayList<String> temp = new ArrayList<String>();
			String read = reads.get(l);
			//durchgehen für die ersten stellen des einzelnen reads 
			for(int i = 0; i < reads.get(l).length()-k ; i++){
				String pattern = "";   //read.substring(i,(k-1+i));
				for(int j = 0; j<k ; j++){
					pattern += read.charAt(i+j);
				}
				brokenReads.add(pattern);
				hs.add(buildPrefix(pattern));
				hs.add(buildSuffix(pattern));
				
			}
		}
		knotenListe.addAll(hs);
		output.add(brokenReads);
		output.add(knotenListe);
		return output;
		
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
			
		try{
			ArrayList<ArrayList<String>> arL = generateReads();
			ArrayList<String> reads = arL.get(0);
			ArrayList<String> knotenListe = arL.get(1);

			
		/*   HABE ICH ALLES IN DIE FUNKTION generateReads ausgelagert um die beiden schleifen zu sparen!!!!!!!!!!!!!!
		//Aufbau der Liste der k-1 mere 
		ArrayList<String> knotenListe = new ArrayList<String>();
		for(int i = 0; i<reads.size();i++){
			String temp = reads.get(i);
			for(int j = 0; j< temp.length(); j++){
				knotenListe.add(buildPrefix(temp));
				knotenListe.add(buildSuffix(temp));
			}
		}
		
		
		// Löschen von dublikaten durch abspeichern in ein set, dass keine dublikate erlaubt 
		// und dann rückführen ind die knotenListe   knotenListe enthält alle k-1 mere der Reads, also alle suffixe und prefixe
		Set<String> hs = new HashSet<>();
		hs.addAll(knotenListe);
		knotenListe.clear();
		knotenListe.addAll(hs);
		*/
		System.out.println("TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		
		//Aufbau des Graphen       Im Momentanen Schleifen Format sind das leider über 2069311321980 iterationen
		for(int i=0;i<knotenListe.size();i++){
			String temp1 = knotenListe.get(i);
			knotenListe.remove(i);
			for(int j = 0; j<knotenListe.size();j++){
				String temp2 = knotenListe.get(j);
				for(int k =0; k< reads.size();k++){
					if((isPrefix(temp1,reads.get(k))) && ( isSuffix(temp2,reads.get(k) ) ) ){
						g.addVertex(temp1);
						g.addVertex(temp2);
						g.addEdge(temp1,temp2, new RelationshipEdge(temp1,temp2,(reads.get(k))));
					}else{
						if((isPrefix(temp2,reads.get(k))) && ( isSuffix(temp1,reads.get(k) ) )){
							g.addVertex(temp1);
							g.addVertex(temp2);
							g.addEdge(temp2,temp1, new RelationshipEdge(temp2,temp1,(reads.get(k))));
						}
					}
				}
			}
		}
		/*
		//Test Ausgabe
		for(RelationshipEdge edge : g.edgeSet()){
		System.out.println("SOURCE!!!!" + edge.getV1().toString());
		System.out.println(edge.toString());
		System.out.println("Tagret!!!!" + edge.getV2().toString());
		break;
		}
		*/
		
		
		
		}catch(IOException e){
			e.printStackTrace();
		}
		return g;
		
	}
	
	
}
