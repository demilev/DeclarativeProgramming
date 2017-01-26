package task4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// ������������� �� ���������� ����
public class Graph {
	// ��-�� �� �������
	private Set<Integer> vertices;
	/*
	 * ������� �� ���������� ���� ������� �� ������������ (�������) �� �����
	 * ���� ��� map �����, ����� ������� �������� �� ������ ����� �������� ��
	 * ���������� ���� � ����������� �� ����; ��������� ���� �������������,
	 * ������ �� ���������� ��������� � ���-������
	 */
	private Map<Integer, Map<Integer, Integer>> distanceMatrix;

	public Graph() {
		vertices = new HashSet<>();
		distanceMatrix = new HashMap<>();
	}

	public void addVertex(Integer vertex) {
		if (vertices.contains(vertex))
			return;
		vertices.add(vertex);
		distanceMatrix.put(vertex, new HashMap<>());
	}

	public void addEdge(Integer from, Integer to, Integer price) {
		addVertex(from);
		addVertex(to);
		distanceMatrix.get(from).put(to, price);
	}

	public Set<Integer> getVertices() {
		return new HashSet<Integer>(vertices);
	}

	public Integer getEdgePrice(Integer startVertex, Integer lastVertex) {
		return distanceMatrix.get(startVertex).get(lastVertex);
	}

}
