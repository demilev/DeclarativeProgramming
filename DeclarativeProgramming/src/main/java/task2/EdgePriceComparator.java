package task2;

import java.util.Comparator;

public class EdgePriceComparator implements Comparator<Edge> {

	@Override
	public int compare(Edge first, Edge second) {
		return first.price()-second.price();
	}

}
