package task2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Graph {
	private HashSet<Integer> vertices = new HashSet<Integer>();
	private Hashtable<Integer, HashSet<Edge>> edges = new Hashtable<Integer, HashSet<Edge>>();

	public Graph(Edge... edges) {
		for (Edge edge : edges)
			addEdge(edge);
	}

	private void addEdge(Edge edge) {
		addVertex(edge.from());
		addVertex(edge.to());
		edges.get(edge.from()).add(edge);
		//добавяме и противоположното ребро, за да може графът да е неориентиран
		edges.get(edge.to()).add(edge.oposite()); 
	}

	private void addVertex(Integer vertex) {
		vertices.add(vertex);
		if (!edges.containsKey(vertex))
			edges.put(vertex, new HashSet<Edge>());
	}

	public Stream<Edge> primAlgorithmTraversal(Integer start) {
		Iterable<Edge> primAlgorithmIterable = new Iterable<Edge>() {
			@Override
			public Iterator<Edge> iterator() {
				return new Iterator<Edge>() {
					private HashSet<Integer> visitedVertices = new HashSet<Integer>(Arrays.asList(start));

					@Override
					public boolean hasNext() {
						return !vertices.equals(visitedVertices);
					}

					@Override
					public Edge next() {
						HashSet<Edge> cheapestEdgesToUnvisitedVerices = visitedVertices.stream()
								.map(vertex -> edges.get(vertex.intValue()))
								.map(listOfEdges -> getPossibleEdges(listOfEdges))
								.map(listOfPossibleEdges -> getCheapestEdge(listOfPossibleEdges))
								.collect(Collectors.toCollection(HashSet::new));
						Edge result=Collections.min(cheapestEdgesToUnvisitedVerices);
						visitedVertices.add(result.to());
						return result;
					}

					private HashSet<Edge> getPossibleEdges(HashSet<Edge> listOfEdges) {
						HashSet<Edge> result = new HashSet<Edge>(
								listOfEdges.stream().
								filter(edge -> !visitedVertices.contains(edge.to())).
								collect(Collectors.toCollection(HashSet::new))
								);
						if(result.isEmpty())
							result.add(new Edge(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE));
						
						return result;
					}
					private Edge getCheapestEdge(HashSet<Edge> listOfPossibleEdges) {
						return Collections.min(listOfPossibleEdges);
					}
				};
			}

		};
		return StreamSupport.stream(primAlgorithmIterable.spliterator(), false);
	}
}
