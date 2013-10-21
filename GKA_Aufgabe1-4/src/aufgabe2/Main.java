package aufgabe2;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        GraphReader reader = new GraphReader();
        SimpleWeightedGraph<String, DefaultWeightedEdge> graph;
        try {
            graph = (SimpleWeightedGraph<String, DefaultWeightedEdge>) reader.readFromFile(new File("graph3.gka"));

            // eigener Dijkstra
            Dijkstra dijkstra = new Dijkstra(graph);
            System.out.println(Arrays.toString(dijkstra.findShortestPath("Hamburg", "Detmold")));

            // JGraphT Dijkstra
            DijkstraShortestPath<String, DefaultWeightedEdge> dijkstra2 = new DijkstraShortestPath<String, DefaultWeightedEdge>(graph, "Hamburg", "Detmold");
            System.out.println(dijkstra2.getPath().toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
