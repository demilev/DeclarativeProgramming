package task2;

public class PrimAlgorithmTraversel {
	public static final int START_VERTEX=1;
	
	public static void main(String[] args) {
		Graph graph=new Graph(new Edge(1,2,5),
							  new Edge(1,8,6),
							  new Edge(2,3,1),
							  new Edge(2,4,7),
							  new Edge(2,5,3),
							  new Edge(3,4,3),
							  new Edge(4,7,14),
							  new Edge(5,6,10),
							  new Edge(5,7,2),
							  new Edge(6,7,1),
							  new Edge(8,5,1));
		System.out.println("МПД минава през ребра: ");
		graph.primAlgorithmTraversal(START_VERTEX).forEach(edge->System.out.println(edge));
	}	
}
