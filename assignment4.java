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

	// public void printadj(){
	// 	for(int i=0 ;i<adj.size();i++ ){

	// 		for(int j=0; j< adj.get(i).size();j++){
	// 			System.out.print(adj.get(i).get(j).name + ",");
	// 		}
	// 		System.out.println(" ");
	// 	}
	// }

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
			while(i <= r && (arr[i].size > p.size || (arr[i].size == p.size && arr[i].name.compareTo(p.name) <= 0))){
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
			System.out.println(sNn[i].size + ",");	
		}					
	} 

	public void independantStorylines(){
		int n = this.nodelist.size();

		sizeNname[] connected = new sizeNname[n];
		for(int i=0; i<n; i++){
			connected[i] = adj.get(i).get(0);
			connected[i].size = 0;
		}
		int num = 1;
		for(int i =0; i<n; i++){
			if(connected[i].size == 0 ){
				ArrayList<sizeNname> onecc = new ArrayList<sizeNname>();

				ArrayList<sizeNname> cc = new ArrayList<sizeNname>();
				
				cc.add(connected[i]);
				while(!cc.isEmpty()){
					
					connected[cc.get(0).size].size = num;
					onecc.add(new sizeNname(cc.get(0).name));
					cc.remove(0);

					for(int x=0; x< adj.get(i).size(); x++){
						if(connected[adj.get(i).get(x).size].size == 0){
							cc.add(adj.get(i).get(x));
						}
					}
				}
				sizeNname[] concomp = new sizeNname[onecc.size()];
				for(int z=0; z < onecc.size();z++){
					concomp[z] = onecc.get(z);
				}
				// this.qsort(concomp,0,onecc.size());
				for(int z=0; z < onecc.size();z++){
					System.out.println(concomp[z].name + ",");
				}
				System.out.println();
				num++;
			}
		}


	}

}

public class assignment4{
	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		ArrayList<node> nodelist = new ArrayList<node>();
		ArrayList<edge> edgelist = new ArrayList<edge>();
		node temp = new node("aa","aa",0);
		nodelist.add(temp);
		temp = new node("cc","cc",1);
		nodelist.add(temp);

		temp = new node("bb","bb",2);
		nodelist.add(temp);

		temp = new node("dd","dd",3);
		nodelist.add(temp);
		temp = new node("ee","ee",4);
		nodelist.add(temp);
		temp = new node("ff","ff",5);
		nodelist.add(temp);
		temp = new node("gg","gg",6);
		nodelist.add(temp);


		edge temp2 = new edge("aa","bb",2);
		edgelist.add(temp2);
		temp2 = new edge("aa","dd",1);
		edgelist.add(temp2);
		temp2 = new edge("bb","dd",1);
		edgelist.add(temp2);
		temp2 = new edge("cc","aa",1);
		edgelist.add(temp2);
		temp2 = new edge("cc","bb",1);
		edgelist.add(temp2);

		temp2 = new edge("ee","ff",100);
		edgelist.add(temp2);
		temp2 = new edge("ff","gg",1);
		edgelist.add(temp2);
		temp2 = new edge("gg","ee",1);
		edgelist.add(temp2);
		

		Graph G = new Graph(edgelist,nodelist);
		// G.aver();	
		// G.rank();
		// G.printadj();
		G.independantStorylines();
	}
}