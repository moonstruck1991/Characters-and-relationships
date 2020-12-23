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
		this.adj = new ArrayList<ArrayList<String>>();
		 for (int i = 0; i < nodelist.size(); i++) {
      		ArrayList<String> adjnodes = new ArrayList<String>();
      		for(int j = 0; j< edgelist.size(); j++){
      			if(edgelist.get(j).getS() == nodelist.get(i).getId()){
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

	public void qsort(ArrayList<sizeNname> arr, int l, int r){
		if(l>=r){
			return;
		}

		sizeNname p = arr.get(l);
		int i = l;
		int j = r;
		while(i < j && i <=r ){
			while(i <= r && (arr.get(i).size > p.size || (arr.get(i).size == p.size && arr.get(i).name.compareTo(p.name) >= 0))){
				i++;
			}
			while(arr.get(j).size < p.size && j >=l ){
				j--;
			}
			if(i<j){
				sizeNname temp = arr.get(i);
                arr.get(i).size = arr.get(j).size;
                arr.get(i).name = arr.get(j).name;
                arr.get(j).size = temp.size;
                arr.get(j).name = temp.name;
			}
			System.out.println(i);
					
		}
		arr.get(l).size = arr.get(j).size;
		arr.get(l).name = arr.get(j).name;
		
		arr.get(j).size = p.size;
		arr.get(j).name = p.name;
		qsort(arr,l,j-1);
		qsort(arr,j+1,r);
	}
		
	

	public void rank(){					// Part 2
		int n = adj.size();
		ArrayList<sizeNname> sNn = new ArrayList<>();
		for(int i=0; i<n; i++){			// size array
			sizeNname temp = new sizeNname(adj.get(i).size(), adj.get(i).get(0));
			sNn.add(temp);
		}

		for(int i=0; i<n; i++){
			System.out.print(sNn.get(i).size + ",");	
		}
		// this.qsort(sNn, 0, n-1);		// sorting acc. to size 
		for(int i=0; i<n; i++){
			System.out.print(sNn.get(i).name + ",");	
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


		edge temp2 = new edge("aa","bb",1);
		edgelist.add(temp2);
		temp2 = new edge("aa","dd",1);
		edgelist.add(temp2);
		temp2 = new edge("bb","aa",1);
		edgelist.add(temp2);
		temp2 = new edge("bb","dd",1);
		edgelist.add(temp2);
		temp2 = new edge("cc","aa",1);
		edgelist.add(temp2);
		temp2 = new edge("dd","aa",1);
		edgelist.add(temp2);
		temp2 = new edge("dd","bb",1);
		edgelist.add(temp2);
		temp2 = new edge("ee","ff",1);
		edgelist.add(temp2);
		temp2 = new edge("ff","gg",1);
		edgelist.add(temp2);
		temp2 = new edge("gg","ee",1);
		edgelist.add(temp2);
		

		Graph G = new Graph(edgelist,nodelist);
		G.aver();
		G.rank();
	}
}