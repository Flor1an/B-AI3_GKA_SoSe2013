package aufgabe4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.EulerianCircuit;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

public class MST {

	Graph<String, DefaultWeightedEdge> graph = null;

	List<String> finalPath;
	
	public MST(Graph<String, DefaultWeightedEdge> g) {
		this.graph = g;
	}

	public Integer getWegLaenge() {
		WeightedMultigraph<String, DefaultWeightedEdge> minimalesGeruest = new WeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		double[] weightArray = entfernungenSpeichern();

		// MINIMALGERUEST bauen
		minimalesGeruest = getMinimalGeruest(weightArray);

		// MULTIKANTEN einfuegen
		minimalesGeruest = multikantenEinfuegen(minimalesGeruest);

		// TOUR suchen
		//System.out.println(minimalesGeruest);
		int laenge = tourSuchen(minimalesGeruest);
		
		return laenge;

	}

	private double[] entfernungenSpeichern() {
		// Array fuer alle Kantengewichte
		double[] weightArray = new double[graph.edgeSet().size()];
		int i = 0;

		for (DefaultWeightedEdge e : graph.edgeSet()) {

			weightArray[i] = graph.getEdgeWeight(e);
			i++;

		}

		Arrays.sort(weightArray);
		// Kantengewichte nach kleinster zuerst sortieren, weil kleinste zuerst
		// eingefuegt wird
		return weightArray;
	}

	private WeightedMultigraph<String, DefaultWeightedEdge> getMinimalGeruest(double[] weightArray) {
		WeightedMultigraph<String, DefaultWeightedEdge> minimalesGeruest = new WeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		KruskalMinimumSpanningTree<String, DefaultWeightedEdge> krus = new KruskalMinimumSpanningTree<>(graph);
		
		
		for (DefaultWeightedEdge e : krus.getEdgeSet()) {
			String v1 = graph.getEdgeSource(e);
			minimalesGeruest.addVertex(v1);
			
			String v2= graph.getEdgeTarget(e);
			minimalesGeruest.addVertex(v2);
			
			DefaultWeightedEdge dwe = graph.getEdge(v1, v2);
			minimalesGeruest.addEdge(v1, v2, dwe);
			

		}
		
		return minimalesGeruest;
	}

	private WeightedMultigraph<String, DefaultWeightedEdge> multikantenEinfuegen(
			WeightedMultigraph<String, DefaultWeightedEdge> minimalesGeruest) {

		Set<DefaultWeightedEdge> edges = new HashSet<DefaultWeightedEdge>();

		for (DefaultWeightedEdge e : minimalesGeruest.edgeSet()) {
			edges.add(e);
		}

		for (DefaultWeightedEdge e : edges) {

			minimalesGeruest.addEdge(minimalesGeruest.getEdgeTarget(e),minimalesGeruest.getEdgeSource(e));
			minimalesGeruest.setEdgeWeight(minimalesGeruest.getEdge(minimalesGeruest.getEdgeTarget(e),minimalesGeruest.getEdgeSource(e)), minimalesGeruest.getEdgeWeight(e));

		}
		return minimalesGeruest;
	}

	private Integer tourSuchen(WeightedMultigraph<String, DefaultWeightedEdge> minimalesGeruest) {
		
		List<String> eulerKreis = EulerianCircuit.getEulerianCircuitVertices(minimalesGeruest);
		
		List<String> temp = new ArrayList<String>();

		//bei Mehrfach besuchten Knoten Vorgaenger und Nachfolger direkt verbinden
		temp.add("v1");
		for (String v: eulerKreis){
			if(!temp.contains(v)){
				temp.add(v);
			}
		}
		temp.add("v1");
		finalPath=new ArrayList<String>(temp);
		return getLenght(temp);
		
	}

	private Integer getLenght(List<String> path) {

		Integer lenght = 0;
		for (int j = 0; j < path.size() - 1; j++) {
			DefaultWeightedEdge dwe = graph.getEdge(path.get(j),path.get(j + 1));
			lenght += (int) graph.getEdgeWeight(dwe);
		}

		return lenght;
	}
}
