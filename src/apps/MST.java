package apps;

import structures.*;
import structures.Vertex.Neighbor;

import java.util.ArrayList;

import apps.PartialTree.Arc;
import apps.PartialTreeList.Node;

public class MST {

	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 *
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {

		//Create an empty list L of partial trees
		PartialTreeList L = new PartialTreeList();


		//For every length I create a partial tree for every vertex. The getRoot finds the parent of every vertex
		// Then I add the partial tree to the partialTreeList which holds all the vertices.

		for(int i = 0; i<graph.vertices.length; i++){

			PartialTree T = new PartialTree(graph.vertices[i]); //Created a partial tree only consisting of the vertices
			MinHeap<PartialTree.Arc> P = T.getArcs(); // Created a min heap for all the edges of each partial

			for(Vertex.Neighbor ptr = graph.vertices[i].neighbors; ptr!=null;ptr = ptr.next){
				PartialTree.Arc edge = new PartialTree.Arc(graph.vertices[i], ptr.vertex, ptr.weight); //Created an edge for each neighbor of the vertex
				P.insert(edge); //Inserted the edge into the min heap which automatically is organized through the minheap class
			}
			L.append(T); //Added each partial tree to the Partial Tree List

		}
		return L;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 *
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {

		ArrayList<PartialTree.Arc> MST = new ArrayList<PartialTree.Arc>(); // I create a new Minimum Spanning Tree ArrayList which will be used to report the component

		int counter=ptlist.size();

		while(counter > 1){

			PartialTree PTX = ptlist.remove(); //I removed the first partial tree from the partial tree list.
			MinHeap<Arc> PQX = PTX.getArcs(); //PQX is PTX's priority queue

			PartialTree.Arc a = PQX.deleteMin(); //I remove the top priority queue from the heap
			Vertex v1 = a.v1;
			Vertex v2 = a.v2;

			if(v2.getRoot() == v1.getRoot()){
				a = PQX.deleteMin();	//Find the next priority in the heap
				v2 = a.v2.parent;
			}
			MST.add(a); //I add (Report) a to the arrayList of the minimum spanning tree
			PartialTree PTY = ptlist.removeTreeContaining(v2); //I removed the partial tree containing v2
			PTY.getRoot().parent = PTX.getRoot();
			PTX.merge(PTY);//Merge priority queues into one priority queue of PQX
			ptlist.append(PTX); //Append the updated priority queue in the partial tree and append the partial tree into the PartialTreeList
			counter--;
		}

		return MST;
	}
}