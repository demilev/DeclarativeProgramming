package task4;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/*
 * Обект, който представлява 
 * S (veritcesToTraverse) < {2, . . . , N} of vertices and, 
 * for c (lastVertex) from S, let D(S, c) be the minimum distance (price), 
 * starting at vertex 1(startingVertex from class HeldKarpAlgorithm), 
 * visiting all vertices in S and finishing at city c.
 * minPrevious - the second-to-last vertex to x from set S. 
 * Used for constructing the TSP path back at the end.
 * (пазим откъде сме дошли, за да можем да възстановим пътя)
 * source : wikipedia
 * не успях да избера подходящо име за класа
 */
public class VertexSetLast {
	private Integer lastVertex;
	private Set<Integer> veritcesToTraverse;
	private VertexSetLast minPrevious;
	private Integer price;

	public VertexSetLast(Integer lastVertex, Set<Integer> verticesToTraverse, Integer price) {
		this.lastVertex = lastVertex;
		this.veritcesToTraverse = verticesToTraverse;
		minPrevious = null;
		this.price = price;
	}

	public VertexSetLast(Integer lastVertex, Set<Integer> verticesToTraverse, VertexSetLast minPrevious,
			Integer price) {
		this.lastVertex = lastVertex;
		this.veritcesToTraverse = verticesToTraverse;
		this.minPrevious = minPrevious;
		this.price = price;
	}

	/*
	 * функцията връща поток от върховете на минималния път, тук влиза в
	 * употреба minPrevious
	 */
	public Stream<Integer> getPath() {
		Iterable<Integer> getPathIterable = new Iterable<Integer>() {
			@Override
			public Iterator<Integer> iterator() {
				return new Iterator<Integer>() {
					private VertexSetLast current = new VertexSetLast(lastVertex, veritcesToTraverse, minPrevious,
							price);

					@Override
					public boolean hasNext() {
						return current != null;
					}

					@Override
					public Integer next() {
						Integer next = current.lastVertex;
						current = current.minPrevious;
						return next;
					}
				};
			}
		};
		return StreamSupport.stream(getPathIterable.spliterator(), false);
	}

	public Integer getPrice() {
		return price;
	}

	public Integer getLast() {
		return lastVertex;
	}

}
