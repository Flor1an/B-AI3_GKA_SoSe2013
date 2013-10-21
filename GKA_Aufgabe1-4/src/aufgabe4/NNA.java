package aufgabe4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class NNA {

	Graph<String, DefaultWeightedEdge> graph = null;

	List<String> finalPath;
	
	public NNA(Graph<String, DefaultWeightedEdge> g) {
		this.graph = g;
	}

	public Integer getWegLaenge() {
		List<String> u = new ArrayList<>();

		Map<List<String>, Integer> w = new HashMap<>();

		String u1 = graph.vertexSet().iterator().next();
		u.add(u1);// beliebiger knoten

		while (u.size() < graph.vertexSet().size()) {

			u.add(getNearestNeighbour(u));

			u.add(u1);
			u = getShortestPath(u);
			// System.out.println("SELECTED: " + u.toString() +"(" + getLenght(u) + ")\n");

			u.remove(u.size() - 1);
		}
		u.add(u1);
		
		finalPath= new ArrayList<String>(u);

		return getLenght(u);
	}

	private List<String> getShortestPath(List<String> path) {

		Integer minPathLenght = Integer.MAX_VALUE;
		List<String> minPath = null;

		if (path.size() > 4) {
			String u1 = path.get(0);

			path.remove(0);
			path.remove(path.size() - 1);

			String un = path.get(path.size() - 1);
			for (int i = 0; i < path.size(); i++) {
				path.remove(un);
				path.add(i, un);
				// System.err.println(path);

				path.add(0, u1);
				path.add(u1);

				Integer tmpLenght = getLenght(path);
				if (tmpLenght < minPathLenght) {
					minPathLenght = tmpLenght;

					minPath = new ArrayList<String>(path);
				}

				 //System.out.println("Option"+(i+1)+ " " + path +"("+getLenght(path)+")");

				path.remove(0);
				path.remove(path.size() - 1);
			}

			path.add(0, u1);
			path.add(u1);
			return minPath;
		}
		return path;
	}

	private Integer getLenght(List<String> path) {

		Integer lenght = 0;
		for (int j = 0; j < path.size() - 1; j++) {
			DefaultWeightedEdge dwe = graph.getEdge(path.get(j),path.get(j + 1));
			lenght += (int) graph.getEdgeWeight(dwe);
		}

		return lenght;
	}

	private String getNearestNeighbour(List<String> vonKnoten) {
		String nearestNeighbourVertex = "";
		Double nearestNeighbourWeight = Double.MAX_VALUE;

		for (String v : vonKnoten) {
			for (DefaultWeightedEdge e : graph.edgeSet()) {

				if (v.compareTo(graph.getEdgeSource(e)) == 0) {
					DefaultWeightedEdge dwe = graph.getEdge(v,
							graph.getEdgeTarget(e));

					if (graph.getEdgeWeight(dwe) < nearestNeighbourWeight
							&& !vonKnoten.contains(graph.getEdgeTarget(dwe))
							&& dwe != null) {
						nearestNeighbourWeight = graph.getEdgeWeight(dwe);
						nearestNeighbourVertex = graph.getEdgeTarget(dwe);
					}
				} else if (v.compareTo(graph.getEdgeTarget(e)) == 0) {
					DefaultWeightedEdge dwe = graph.getEdge(v,
							graph.getEdgeSource(e));

					if (graph.getEdgeWeight(dwe) < nearestNeighbourWeight
							&& !vonKnoten.contains(graph.getEdgeSource(dwe))
							&& dwe != null) {
						nearestNeighbourWeight = graph.getEdgeWeight(dwe);
						nearestNeighbourVertex = graph.getEdgeSource(dwe);
					}
				}
			}
		}
		// System.out.println("add: " + nearestNeighbourVertex);
		return nearestNeighbourVertex;
	}

}
