import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class edge{
	public String Id, Label;
}

public class node{
	public String Source,Target, Weight;
}

public class Graph{
	ArrayList<ArrayList<String>> adj;   // adjacency list of the the given graph
	ArrayList<edge> edgelist;			// list of edges
	ArrayList<node> nodelist;			// list of nodes

	public double AVG(){				// Part 1
		return roundOff = Math.round(( (edgelist.size()) / (nodelist.size())) * 100.0) / 100.0;
	}

	public void qsort(int[] arr, int l, int r){

	}

	public void rank(){					// Part 2
		int n = adj.size();
		int[] size = new int[n];
		for(int i=0; i<n; i++){			// size array
			size[i] = adj.get(i).size();
		}
		this.qsort(size, 0, n-1);		// sorting acc. to size 
		for(int i=0; i<n: i++){
			System.out.print(adj.get(i).Label + ",");	
		}					
	} 


}

