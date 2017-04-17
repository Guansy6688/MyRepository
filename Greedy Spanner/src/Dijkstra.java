import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.jgraph.graph.Edge;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Dijkstra {
	SimpleWeightedGraph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(
			DefaultWeightedEdge.class);
	IdentityHashMap<Integer, Integer> IhashMap = new IdentityHashMap<Integer, Integer>();
	int i = 0;
	int[][] pointArr;
	double ratio=1;
	int first,second;

	public Dijkstra(List<Entry<Entry<Integer, Integer>, Double>> dList,
			float t, int pointNum) {
		int pSize = pointNum;
		pointArr = new int[pSize][pSize];
		String start = null;
		String end = null;

		Iterator iter = ((List<Entry<Entry<Integer, Integer>, Double>>) dList)
				.iterator();

		while (iter.hasNext()) {
			Map.Entry me = (Map.Entry) iter.next();
			Double value = (Double) me.getValue();
			HashMap meKey = (HashMap) me.getKey();
			Set set = meKey.entrySet();
			// Display elements
			// Get an iterator
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) it.next();
				start = mapEntry.getKey().toString();
				end = mapEntry.getValue().toString();
				System.out.println("start:" + start);
				System.out.println("end:" + end);
				graph.addVertex(start);
				graph.addVertex(end);

				DijkstraShortestPath<String, DefaultWeightedEdge> path = new DijkstraShortestPath<>(
						graph, start, end);
				System.out.println("shortest path:" + path.getPathLength()
						+ " t*value:" + t * value);

				if (path.getPathLength() > t * value) {
					System.out.print(">t");
					DefaultWeightedEdge edge = graph.addEdge(start, end);
					graph.setEdgeWeight(edge, (double) me.getValue());
					pointArr[Integer.parseInt(start)][Integer.parseInt(end)] = 1;
					System.out.println(pointArr.length);
					
				}else{
				if(path.getPathLength()/value>ratio){
					ratio=path.getPathLength()/value;
					System.out.println("ratio:"+ratio);
					first=Integer.parseInt(start);
					second=Integer.parseInt(end);
				}
				}

			}

		}

	}

	public int[][] getEdge() {
		return pointArr;
	}
	
	public int getWorstPairFirst() {
		return first;
	}
	
	public int getWorstPairSecond() {
		return second;
	}
}
