import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
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

	public sizeNname(String name){
		this.size = 1;
		this.name = name;
	}

	public sizeNname(int size, String name){
		this.name = name;
		this.size = size;
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
	ArrayList<ArrayList<sizeNname>> adj;   // adjacency list of the the given graph
	ArrayList<edge> edgelist;			// list of edges
	ArrayList<node> nodelist;			// list of nodes

	public Graph(ArrayList<edge> edgelist, ArrayList<node> nodelist){
		this.edgelist = edgelist;
		this.nodelist = nodelist;
		// System.out.println("nodes");
		//  for (int i = 0; i < nodelist.size(); i++) {
  //     		System.out.print(nodelist.get(i).Id);
		//  }
		//  System.out.println(" ");
		//  		 System.out.print("edges");
		//  		 System.out.println(" " + edgelist.size());
		//  for (int i = 0; i < edgelist.size(); i++) {
  //     		System.out.print(edgelist.get(i).Source);
		//  }
		//  	 System.out.println(" ");
		this.adj = new ArrayList<ArrayList<sizeNname>>();
		 for (int i = 0; i < nodelist.size(); i++) {
      		ArrayList<sizeNname> adjnodes = new ArrayList<sizeNname>();
      		adjnodes.add(new sizeNname(nodelist.get(i).getIndex() , nodelist.get(i).getId()));
      		for(int j = 0; j< edgelist.size(); j++){
      			if(edgelist.get(j).getS().compareTo(nodelist.get(i).getId()) == 0){
      				int index = -1;
      				for(int k =0; k< nodelist.size(); k++){
      					if(nodelist.get(k).getId().compareTo(edgelist.get(j).getT()) == 0){
      						index = nodelist.get(k).getIndex();
      					}
      				}

      				adjnodes.add( new sizeNname(index, edgelist.get(j).getT()));
      			}
      			else if(edgelist.get(j).getT().compareTo(nodelist.get(i).getId()) == 0){
      				int index = -1;
      				for(int k =0; k< nodelist.size(); k++){
      					if(nodelist.get(k).getId().compareTo(edgelist.get(j).getS()) == 0){
      						index = nodelist.get(k).getIndex();
      					}
      				}

      				adjnodes.add( new sizeNname(index, edgelist.get(j).getS()));
      			}
      		}
      		adj.add(adjnodes);
    	}

	}

	public void printadj(){
		for(int i=0 ;i<adj.size();i++ ){

			for(int j=0; j< adj.get(i).size();j++){
				System.out.print(adj.get(i).get(j).name + ",");
			}

			System.out.println(adj.get(i).get(0).size);
		}
	}

	public void aver(){				// Part 1
		double a = ((double)this.edgelist.size())/this.nodelist.size();
		double roundOff = Math.round(a*100.0)/100.0;

    	System.out.println(roundOff);
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
		int n = adj.size();
		sizeNname[] sNn = new sizeNname[n];
		for(int i=0; i<n; i++){			// size array
			int m = this.edgelist.size();
			int cooccur = 0;
			for(int j =0; j< m; j++){

				if(edgelist.get(j).getS().compareTo(adj.get(i).get(0).name) == 0 || edgelist.get(j).getT().compareTo(adj.get(i).get(0).name) == 0){
					cooccur = cooccur + edgelist.get(j).getW();
				}
			}
	
			sNn[i] = new sizeNname( cooccur, adj.get(i).get(0).name);
			
			
		}

		// for(int i=0; i<n; i++){
		// 	System.out.print(sNn.get(i).size + ",");	
		// }
		this.qsort(sNn, 0, n-1);		// sorting acc. to size 
		for(int i=0; i<n; i++){
			System.out.print(sNn[i].name + ",");

		}					
	}  

	public void DFS(sizeNname v, boolean[] visited, ArrayList<sizeNname> res){
		res.add(new sizeNname( v.name));
		visited[v.size] = true;
		for(int i = 0; i< adj.get(v.size).size();i++){
			if(visited[adj.get(v.size).get(i).size] == false){
				DFS(adj.get(v.size).get(i),visited,res);
			}
		}
	}
	public void independant_storylines_dfs(){
		int n = this.nodelist.size();
		boolean[] visited = new boolean[n];
		for(int i=0; i< n; i++){
			visited[i] = false;
		}

		for(int i=0; i<n;i++){
			ArrayList<sizeNname> res = new ArrayList<sizeNname>();
				
			if(visited[i] == false){

				DFS(adj.get(i).get(0),visited,res);
				sizeNname[] resarr = new sizeNname[res.size()];
				for(int j=0; j<res.size();j++){
					resarr[j] = res.get(j);
				}
				this.qsort(resarr,0,res.size() -1);
				for(int j=0; j<res.size();j++){
					System.out.print(resarr[j].name + ",");
				}
				System.out.println();
			}
			

		}
	}

}

public class assignment4{
	public static void main(String[] args) throws IOException {
		
		String line = "";  
		String splitBy = ",";
		BufferedReader br = new BufferedReader(new FileReader("nodes_test.csv"));
		BufferedReader br2 = new BufferedReader(new FileReader("edges_test.csv"));

		ArrayList<node> nodelist = new ArrayList<node>();
		ArrayList<edge> edgelist = new ArrayList<edge>();
		int i=0;
		while ((line = br.readLine()) != null){
			String[] new_node = line.split(splitBy);
			nodelist.add(new node(new_node[0], new_node[1],i));
			i++;
		}
		i=0;
		while ((line = br2.readLine()) != null){
			String[] new_edge = line.split(splitBy);
			edgelist.add(new edge(new_edge[0], new_edge[1],Integer.parseInt(new_edge[2])));
			i++;
		}


		Graph G = new Graph(edgelist,nodelist);

		// G.aver();	
		// System.out.println("--------------");
	

		// G.rank();
		// System.out.println("");
		// System.out.println("--------------");


		// G.printadj();
		// System.out.println("");
		// System.out.println("--------------");

		G.independant_storylines_dfs();
	}
}