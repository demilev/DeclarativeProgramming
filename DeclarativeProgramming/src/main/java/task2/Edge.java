package task2;

public class Edge implements Comparable<Edge> {
	private final Integer from;
	private final Integer to;
	private final Integer price;
	public Edge(Integer from, Integer to, Integer price ) {
		this.from=from;
		this.to=to;
		this.price=price;
	}
	public Integer from() {
		return from;
	}
	public Integer to() {
		return to;
	}
	public Integer price() {
		return price;
	}
	@Override
	public String toString() {
		return "Edge from vertex[" + from + "] to vertex[" + to + "] with price " + price + ".";
	}
	@Override
	public int compareTo(Edge other) {
		return price-other.price;
	}
	public Edge oposite() {
		return new Edge(to,from,price);
	}
	
}
