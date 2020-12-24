import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

class node{
	public String Id, Label;

	public node(String Id, String Label){
		this.Id = Id;
		this.Label = Label;
	}

	public String getId(){
		return this.Id;
	}

	public String getLabel(){
		return this.Label;
	}
}


class sizeNname{
	int size;
	String name;

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
	ArrayList<ArrayList<String>> adj;   // adjacency list of the the given graph
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
		this.adj = new ArrayList<ArrayList<String>>();
		 for (int i = 0; i < nodelist.size(); i++) {
      		ArrayList<String> adjnodes = new ArrayList<String>();
      		adjnodes.add(nodelist.get(i).getId());
      		for(int j = 0; j< edgelist.size(); j++){
      			if(edgelist.get(j).getS().compareTo(nodelist.get(i).getId()) == 0){
      				adjnodes.add(edgelist.get(j).getT());
      			}
      		}
      		adj.add(adjnodes);
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
			while(i <= r && (arr[i].size >= p.size || (arr[i].size == p.size && arr[i].name.compareTo(p.name) <= 0))){
				i++;
			}
			while(j >= l && (arr[j].size < p.size || (arr[j].size == p.size && arr[j].name.compareTo(p.name) > 0))){
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

				if(edgelist.get(j).getS().compareTo(adj.get(i).get(0)) == 0 || edgelist.get(j).getT().compareTo(adj.get(i).get(0)) == 0){
					cooccur = cooccur + edgelist.get(j).getW();
				}
			}
	
			sizeNname temp = new sizeNname( cooccur, adj.get(i).get(0));
			sNn[i]= temp;
			
		}

		// for(int i=0; i<n; i++){
		// 	System.out.print(sNn.get(i).size + ",");	
		// }
		this.qsort(sNn, 0, n-1);		// sorting acc. to size 
		for(int i=0; i<n; i++){
			System.out.print(sNn[i].name + ",");

	
		}					
	} 


}

public class assignment4{
	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		ArrayList<node> nodelist = new ArrayList<node>();
		ArrayList<edge> edgelist = new ArrayList<edge>();
		node temp = new node("aa","aa");
		nodelist.add(temp);
		temp = new node("bb","bb");
		nodelist.add(temp);
		temp = new node("cc","cc");
		nodelist.add(temp);
		temp = new node("dd","dd");
		nodelist.add(temp);
		temp = new node("ee","ee");
		nodelist.add(temp);
		temp = new node("ff","ff");
		nodelist.add(temp);
		temp = new node("gg","gg");
		nodelist.add(temp);


		edge temp2 = new edge("aa","bb",2);
		edgelist.add(temp2);
		temp2 = new edge("aa","dd",1);
		edgelist.add(temp2);
		temp2 = new edge("bb","dd",1);
		edgelist.add(temp2);
		temp2 = new edge("cc","aa",1);
		edgelist.add(temp2);

		temp2 = new edge("ee","ff",100);
		edgelist.add(temp2);
		temp2 = new edge("ff","gg",1);
		edgelist.add(temp2);
		temp2 = new edge("gg","ee",1);
		edgelist.add(temp2);
		

		Graph G = new Graph(edgelist,nodelist);
		
		G.rank();
	}
}