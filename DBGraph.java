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
			//durchgehen fr die ersten stellen des einzelnen reads 
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
			
		}*/
		//Aufbau von Kanten zu jedem read mit den dazugehrigen Knoten
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
		
		// Verschmelzen von gleichen Knoten (NOCH NICHT FERTIG!!!)
		for(RelationshipEdge edge : g.edgeSet()){
			String target = edge.getV2().toString();
			for(RelationshipEdge edge2 :g.edgeSet()){
				String source = edge2.getV1().toString();
				
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
