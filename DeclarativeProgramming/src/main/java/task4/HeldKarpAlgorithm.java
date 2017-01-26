package task4;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class HeldKarpAlgorithm {

	private Graph graph;
	private Integer startVertex;

	public HeldKarpAlgorithm(Graph graph, Integer startVertex) {
		this.graph = graph;
		this.startVertex = startVertex;
	}

	/*
	 * рекурсивна функция, която строи VertexSetLast обкета по алгоритъма на
	 * Хелд и Карп
	 */
	private VertexSetLast solution(Set<Integer> verticesToTraverse, Integer lastVertex) {
	
		// дъното на рекусрията
		if (verticesToTraverse.isEmpty())
			return new VertexSetLast(lastVertex, verticesToTraverse, new VertexSetLast(startVertex, null, null, 0),
					graph.getEdgePrice(startVertex, lastVertex));
		/*
		 * ако множеството, което трябва да се обходи не е празно, генерираме
		 * предишните VertexSetLast обекти, чрез рекурентно извикване на
		 * solution
		 */
		List<VertexSetLast> previous = new LinkedList<>();
		verticesToTraverse.forEach(v -> putInPrevious(v, previous, verticesToTraverse));
		
		// от тях намираме минималният
		 
		Optional<VertexSetLast> min = previous.stream()
				.min((x, y) -> (x.getPrice() + graph.getEdgePrice(startVertex, x.getLast())
						- (y.getPrice() + graph.getEdgePrice(startVertex, y.getLast()))));
		/*
		 * и връщаме финалния обект като проверката дали startVertex е различен
		 * от lastVertex е необходима, защото приемаме, че няма примки в графа;
		 * другият вариант за избягване на тази проверка е добавяне на фалшиво
		 * ребро от startVertex към startVertex с цена 0
		 */
		VertexSetLast minDistance = new VertexSetLast(lastVertex, verticesToTraverse, min.get(),
				min.get().getPrice() + (startVertex != lastVertex ? graph.getEdgePrice(startVertex, lastVertex) : 0));

		return minDistance;
	}

	private void putInPrevious(Integer vertex, List<VertexSetLast> previous, Set<Integer> verticesToTraverse) {
		Set<Integer> remainingVerticesToTraverse = new HashSet<>(verticesToTraverse);
		remainingVerticesToTraverse.remove(vertex);
		previous.add(solution(remainingVerticesToTraverse, vertex));
	}

	public void printShortestPath() {
		StringBuilder sb = new StringBuilder();

		Set<Integer> vertices = graph.getVertices();
		vertices.remove(startVertex);
		solution(vertices, startVertex).getPath().forEach(v -> sb.append(v + " -> "));

		System.out.println("The shortest path solving the TSP is : " + sb.substring(0, sb.length() - 4));
	}

	public static void main(String[] args) {
		Graph graph = new Graph();

		graph.addEdge(0, 1, 1);
		graph.addEdge(0, 2, 15);
		graph.addEdge(0, 3, 6);
		
		graph.addEdge(1, 0, 2);
		graph.addEdge(1, 2, 7);
		graph.addEdge(1, 3, 3);
		
		graph.addEdge(2, 0, 9);
		graph.addEdge(2, 1, 6);
		graph.addEdge(2, 3, 12);
		
		graph.addEdge(3, 0, 10);
		graph.addEdge(3, 1, 4);
		graph.addEdge(3, 2, 8);

		new HeldKarpAlgorithm(graph, 0).printShortestPath();
	}
}
