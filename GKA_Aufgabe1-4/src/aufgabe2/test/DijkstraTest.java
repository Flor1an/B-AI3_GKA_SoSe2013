package aufgabe2.test;
import aufgabe2.*;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

public class DijkstraTest {

    Dijkstra dijkstra;
    WeightedGraph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);


    @Before
    public void setUp() {

        graph.addVertex("v1");
        graph.addVertex("v2");
        graph.addVertex("v3");
        graph.addVertex("v4");
        graph.addVertex("v5");
        graph.addVertex("v6");
        graph.addVertex("v7");

        // the longest path
        graph.setEdgeWeight(graph.addEdge("v1", "v2"), 100);
        graph.setEdgeWeight(graph.addEdge("v2", "v3"), 200);
        graph.setEdgeWeight(graph.addEdge("v3", "v4"), 200);
        graph.setEdgeWeight(graph.addEdge("v4", "v5"), 100);
        graph.setEdgeWeight(graph.addEdge("v5", "v6"), 200);

        // the shortest path
        graph.setEdgeWeight(graph.addEdge("v1", "v3"), 10);
        graph.setEdgeWeight(graph.addEdge("v3", "v5"), 10);

        dijkstra = new Dijkstra(graph);
    }

    @Test
    public void testFindShortestPath() {
        String[] shortestPath = new String[] { "v1", "v3", "v5", "v6" };
        assertArrayEquals(shortestPath, dijkstra.findShortestPath("v1", "v6"));
    }

    @Test(expected = Dijkstra.NoPathException.class)
    public void testRaiseNoPathException() {
        dijkstra.findShortestPath("v1", "v7");
    }
    
}
