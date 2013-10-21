package aufgabe3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class BigNet {

	static int KNOTEN_ANZAHL = 2500;
	static int KANTEN_ANZAHL = 2000000;
	static SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(
			DefaultWeightedEdge.class);

	public static void main(String[] args) {

		Random r = new Random();


		graph.addVertex("q");
		for (int i = 1; i < KNOTEN_ANZAHL - 1; i++) {
			graph.addVertex("v" + i);
		}
		graph.addVertex("s");

		// mit Quelle verbinden
		int verbindungenMitQuelle = r.nextInt(10) + 1;
		for (int i = 0; i < verbindungenMitQuelle; i++) {
			addWeightEdge("q", "v" + r.nextInt(KNOTEN_ANZAHL), r.nextInt(100));
		}
		

		// mit Senke verbinden
		int verbindungenMitSenke = r.nextInt(10) + 1;
		for (int i = 0; i < verbindungenMitSenke; i++) {
			addWeightEdge("v" + r.nextInt(KNOTEN_ANZAHL), "s", r.nextInt(100));
		}
		


		// mitte verbinden
		while (graph.edgeSet().size() < KANTEN_ANZAHL) {
			String vOne = "v" + r.nextInt(KNOTEN_ANZAHL);
			String vTwo = "v" + r.nextInt(KNOTEN_ANZAHL);

			if (vOne.compareTo(vTwo) != 0) {
				addWeightEdge(vOne, vTwo, r.nextInt(100));
			}
		}

		System.out.println("Vertex: " + graph.vertexSet().size());
		System.out.println("Edge: " + graph.edgeSet().size());

		GraphWriter gw = new GraphWriter(graph);
		File tmp = new File("2500-2000000.gka");

		try {
			gw.writeToFile(tmp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static void addWeightEdge(String source, String target, int weight) {
		try {

			DefaultWeightedEdge dwe = graph.addEdge(source, target);
			if (dwe != null)
				graph.setEdgeWeight(dwe, weight);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
