package aufgabe3;

import org.jgraph.JGraph;
import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        GraphReader reader = new GraphReader();
        Graph<String, DefaultWeightedEdge> graph;
        try {
            graph = ( Graph<String, DefaultWeightedEdge>) reader.readFromFile(new File("graph5.gka"));

            // eigener FordFulkerson
            FordFulkerson ff = new FordFulkerson();
            System.out.println(ff.ffStart(graph, "v1", "v6"));
            System.out.println("Laufzeit: " + ff.laufzeit);
            
            // eigener EdmondsKarp
            EdmondsKarp ek = new EdmondsKarp();
            System.out.println(ek.ekStart(graph, "v1", "v6"));
            System.out.println("Laufzeit: " + ek.laufzeit);
            

//            Edmonds_Karp_Algorithm ek = new Edmonds_Karp_Algorithm();
//            ek.edmonds_karp_alg(graph, "q", "s");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
