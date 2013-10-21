package aufgabe2.test;

import static org.junit.Assert.*;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Before;
import org.junit.Test;

import aufgabe2.Dijkstra;
import aufgabe2.FloydWarshall;

public class FloydWarshallTest {
	FloydWarshall floydWarshall;
    WeightedGraph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    
    
	@Before
	public void setUp() throws Exception {

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

        floydWarshall = new FloydWarshall(graph);
       
	}

	@Test
    public void testFindShortestPath() {
        String shortestPath ="v1 v3 v5 v6";
        assertEquals(shortestPath, floydWarshall.findShortestPath("v1", "v6"));
  
        
    }

}
