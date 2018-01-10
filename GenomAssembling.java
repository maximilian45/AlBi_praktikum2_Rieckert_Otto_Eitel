import java.util.*;
import java.io.*;

class GenomAssembling{
	public static void main(String[] args) throws IOException{
	
		FileReader fr = new FileReader("test.txt");
		BufferedReader br = new BufferedReader(fr);
		
		List<String> reads = new ArrayList<String>();
		
		for(int i = 0; i </*=*/ 1 /*33609*/; i++){
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
	
	
	int countEdge = 0;
	int countNode= 0;
	Object[] dbgraph = new Object[1];
	//Kanten[] allEdges = new Kanten[1];
	//Knoten[] allNodes = new Knoten[2];
	
	ArrayList<Object> allEdges = new ArrayList<Object>();
	ArrayList<Object> allNodes = new ArrayList<Object>();
	
	for(int i = 0; i < brokenReads.size(); i++){
		ArrayList<String> temp = new ArrayList<String>();
		temp = brokenReads.get(i);
		for(int j = 0; j < temp.size();j++){
			Kante edge = new Kante(countEdge,temp.get(j));
			countEdge += 1;
			Knoten inNode = new Knoten(countNode, temp.get(j).substring(0,(temp.size()-2)));
			countNode += 1;
			Knoten outNode = new Knoten(countNode,temp.get(j).substring(1));
			countNode += 1;
			
			int[] temp2 = new int[1];
			temp2[0] = 0;
			int[] temp3 = new int[1];
			temp3[0] = edge.id;
			edge.startKnotenId = inNode.id;
			edge.endKnotenId = outNode.id;
			inNode.inKantenIds = temp2;
			inNode.outKantenIds = temp3;
			outNode.inKantenIds = temp3;
			outNode.outKantenIds = temp2;
			
			
			//if(j==0){
				
				
				allEdges.add(edge);
				allNodes.add(inNode);
				allNodes.add(outNode);
				
				
			/*}else{
				//Kanten[] newEdges = new Kanten[(allEdges.length())+1];
				//for(int k = 0; k < allEdges.length(); k++){
				//	newEdges.add(allEdges[k]);
				//}
				//newEdges.add(edge);
				//allEdges = newEdges;
				
				
				Knoten[] newNodes = new Knoten[(allNodes.length())+2];
				for(int l = 0; l < allNodes.length();l++){
					newNodes.add(allNodes[l]);
				}
				newNodes.add(inNode);
				newNodes.add(outNode);
				allNodes = newNodes;
			}*/
			
			
		}
	}
	ArrayList<String> temp = new ArrayList<String>();
	temp = brokenReads.get(0);
	String temp2 = temp.get(0);
	Object tempKante = allEdges.get(0);
	Object tempInNode = allNodes.get(0);
	Object tempOutNode = allNodes.get(1);
	System.out.println("READ !!!!!");
	System.out.println(temp2);
	System.out.println("ALL EDGES !!!!!");
	System.out.println(tempKante);
	System.out.println("IN NODE !!!!!");
	System.out.println(tempInNode);
	System.out.println("OUT NODE !!!!!");
	System.out.println(tempOutNode);
	
	}
}