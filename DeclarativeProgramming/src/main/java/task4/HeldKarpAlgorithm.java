package task4;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
		 * ако множеството, което трябва да се обходи, не е празно, генерираме
		 * предишните VertexSetLast обекти чрез рекурентно извикване на
		 * solution и от тях намираме минималния
		 */
		Optional<VertexSetLast> min = 
				verticesToTraverse.stream()
		 				  		  .filter(v -> graph.containsEdge(v,lastVertex))
		 				  		  .map(v -> solution(getAllExcept(v,verticesToTraverse),v))
		 				  		  .min((x, y) -> (x.getPrice() + graph.getEdgePrice(x.getLast(),lastVertex)
		 				  								- (y.getPrice() + graph.getEdgePrice(y.getLast(),lastVertex))));
		/*
		 * необходима проверка, защото графът може да не е силно свързан или въобще
		 * може да не е свързан
		 */
		if(!min.isPresent())
			return new VertexSetLast(lastVertex, verticesToTraverse, null, Integer.MAX_VALUE);
		
		VertexSetLast previous = min.get();
		VertexSetLast minDistance = new VertexSetLast(lastVertex, verticesToTraverse, previous,
				previous.getPrice() + graph.getEdgePrice(previous.getLast(),lastVertex));

		return minDistance;
	}
	private Set<Integer> getAllExcept(Integer vertex, Set<Integer> verticesToTraverse){
		Set<Integer> result = new HashSet<>(verticesToTraverse);
		result.remove(vertex);
		return result;
	}

	public void printShortestPath() {
		Set<Integer> vertices = graph.getVertices();
		vertices.remove(startVertex);
		
		VertexSetLast solution = solution(vertices, startVertex);
		
		/*
		 * проверка дали сме върнали коректен път, защото функцията solution не хвърля
		 * exception (защото я викам при map-ването), а връща служебна стойност 
		 */
		if(solution.getPath().count() != vertices.size() + 2){
			System.out.println("The TSP problem has no solution for this graph.");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		solution.getPath().forEach(v -> sb.append(v + " - "));
		// малка игра с форматирането на изхода, защото получаваме пътя наобратно
		System.out.println("The shortest path solving the TSP is : " + sb.reverse().substring(3,sb.length()).replace("-", "->"));
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
		
//		graph.addEdge(0, 1, 3);
//		graph.addEdge(0, 3, 4);
//		
//		graph.addEdge(1, 3, 1);
//		graph.addEdge(1, 0, 2);
//		
//		graph.addEdge(2, 1, 5);
//		graph.addEdge(2, 0, 1);
//		
//		graph.addEdge(3, 2, 7);

		
		new HeldKarpAlgorithm(graph, 0).printShortestPath();
	}
}
