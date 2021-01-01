import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

class node{
	public String Id, Label;
	public int Index;


	public node(String Id, String Label, int Index){
		this.Id = Id;
		this.Label = Label;
		this.Index = Index;
	}

	public String getId(){
		return this.Id;
	}

	public String getLabel(){
		return this.Label;
	}

	public int getIndex(){
		return this.Index;
	}

}


class sizeNname{
	int size;
	String name;
	boolean visited;

	public sizeNname(String name){
		this.size = 1;
		this.name = name;
		this.visited = false;
	}

	public sizeNname(int size, String name){
		this.name = name;
		this.size = size;
		this.visited = false;
	}
}

class edge{
	public String Source,Target;
	int Weight;

	public edge(String Source, String Target, int Weight){
		this.Source = Source;
		this.Target = Target;
		this.Weight = Weight;
	}

	public String getS(){
		return this.Source;
	}

	public String getT(){
		return this.Target;
	}
	public int getW(){
		return this.Weight;
	}
}

class Graph{
	ArrayList<node> nodelist;
	ArrayList<edge> edgelist;
	int n;
	int m;
	HashMap<String, ArrayList<sizeNname>> adj;

	public Graph(ArrayList<node> nodelist, ArrayList<edge> edgelist){
		this.nodelist = nodelist;
		this.edgelist = edgelist;
		this.n = nodelist.size();
		this.m = edgelist.size();
		this.adj = new HashMap<>();

		for(int i=0;i<n;i++){
			ArrayList<sizeNname> adjnodes = new ArrayList<sizeNname>();
			adjnodes.add(new sizeNname( 0,nodelist.get(i).getId()));
			adj.put(nodelist.get(i).getId(), adjnodes);
		}

		for(int i=0; i<m;i++){
			edge temp = edgelist.get(i);
			

			adj.get(temp.getS()).add(new sizeNname( temp.getW(),temp.getT()));
			adj.get(temp.getT()).add(new sizeNname( temp.getW(),temp.getS()));
		}

	}

	public void aver(){
		double a = 2*((double)m)/n;
		double roundOff = Math.round(a*100.0)/100.0;

    	System.out.println(roundOff);
	}


	public void printadj(){
		for(String i: adj.keySet()){
			for(int j=0; j< adj.get(i).size();j++){
				System.out.print(adj.get(i).get(j).name);
			}
			System.out.println("");
		}
	}


	public void qsort(sizeNname[] arr, int l, int r){
		if(l>=r){
			return;
		}

		sizeNname p = arr[l];
		int i = l;
		int j = r;
		while(i < j && i <=r ){
			while(i <= r && (arr[i].size > p.size || (arr[i].size == p.size && arr[i].name.compareTo(p.name) >= 0))){
				i++;
				
				
			}
			while(j >= l && (arr[j].size < p.size || (arr[j].size == p.size && arr[j].name.compareTo(p.name) < 0))){
				j--;
			
			}
			if(i<j){
				sizeNname temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
  			}
			
					
		}
		arr[l] = arr[j];
		arr[j] = p;
		
		qsort(arr,l,j-1);
		qsort(arr,j+1,r);
	}

	public void rank(){					// Part 2
		
		sizeNname[] sNn = new sizeNname[n];
		int k=0;
		for(String i: adj.keySet()){			// size array
			int cooccur = 0;
			for(int j=0; j< adj.get(i).size(); j++){
				cooccur = cooccur + adj.get(i).get(j).size;
			}
			sNn[k] = new sizeNname(cooccur, adj.get(i).get(0).name);
			k++;
		}

		// for(int i=0; i<n; i++){
		// 	System.out.print(sNn.get(i).size + ",");	
		// }
		this.qsort(sNn, 0, n-1);		// sorting acc. to size 
		for(int i=0; i<n; i++){
			if(i == n-1){
				System.out.print(sNn[i].name);				
			}
			else{
			System.out.print(sNn[i].name + ",");
			}
		}
		System.out.println("");					
	}


	public void DFS(sizeNname v, ArrayList<sizeNname> res){
		res.add(new sizeNname(v.name));
		// System.out.print(v.name);
		// System.out.println(v.visited);
		adj.get(v.name).get(0).visited = true;
		for(int i = 0; i< adj.get(v.name).size();i++){
			if(adj.get(adj.get(v.name).get(i).name).get(0).visited == false){
				DFS(adj.get(v.name).get(i),res);
			}
		}
	}

	public void independant_storylines_dfs(){
		
		for(String i: adj.keySet()){
			ArrayList<sizeNname> res = new ArrayList<sizeNname>();
				
			 
			if(adj.get(i).get(0).visited == false){
				
				DFS(adj.get(i).get(0),res);
				int x = res.size();
				sizeNname[] resarr = new sizeNname[x];
				for(int j=0; j<x;j++){
					resarr[j] = res.get(j);
				}
				this.qsort(resarr,0,x -1);
				for(int j=0; j<x;j++){
					if(j == x-1){
						System.out.print(resarr[j].name);
					}
					else{
					System.out.print(resarr[j].name + ",");}
				}
				System.out.println();
			}
			

		}
	}  


}

public class assignment4{
	public static void main(String[] args) throws IOException {
		
	String nodesFile = args[0];
		String edgesFile = args[1];
		String func = args[2];
		String line = "";
		
		BufferedReader br = new BufferedReader(new FileReader(nodesFile));
		BufferedReader br2 = new BufferedReader(new FileReader(edgesFile));

		ArrayList<node> nodelist = new ArrayList<node>();
		ArrayList<edge> edgelist = new ArrayList<edge>();
		int i=0;
		line = br.readLine();
		while ((line = br.readLine()) != null){
			String[] new_node = new String[2];
			new_node[0] = "";
			new_node[1] = "";
			boolean insidequotes = false;
			int j=0;
			for(int k=0; k< line.length();k++){
				if(line.charAt(k) == ',' && insidequotes == false){
					j++;
				}
				else if(line.charAt(k) == '"'){
					if(insidequotes){
						insidequotes = false;

					}
					else{
						insidequotes = true;
					} 

				}
				else{
					new_node[j] = new_node[j] + line.charAt(k);
				}
			}
			nodelist.add(new node(new_node[0], new_node[1],i));
			i++;
		}
		
		line = br2.readLine();		
		while ((line = br2.readLine()) != null){
			String[] new_edge = new String[3];
			new_edge[0] = "";
			new_edge[1] = "";
			new_edge[2] = "";
			int j = 0;
			boolean insidequotes = false;
			for(int k=0; k< line.length();k++){
				if(line.charAt(k) == ',' && insidequotes == false){
					j++;
				}
				else if(line.charAt(k) == '"'){
					if(insidequotes){
						insidequotes = false;

					}
					else{
						insidequotes = true;
					}

				}
				else{
					new_edge[j] = new_edge[j] + line.charAt(k);
				}
			}

			edgelist.add(new edge(new_edge[0], new_edge[1],Integer.parseInt(new_edge[2])));
			
		}


		Graph G = new Graph(nodelist,edgelist);

		if(func.compareTo("average")==0){
			G.aver();	
		}

		else if(func.compareTo("rank") == 0){
			 G.rank();
		}


		else if(func.compareTo("independent_storylines_dfs")==0){
			G.independant_storylines_dfs();
		}
		
		
	}
}