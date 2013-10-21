package aufgabe2.test;
import aufgabe2.*;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

public class GraphReaderTest {

    private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> origGraph;

    @Before
    public void setUp() throws Exception {
        origGraph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

        origGraph.addVertex("a");
        origGraph.addVertex("b");
        origGraph.addVertex("c");

        origGraph.setEdgeWeight(origGraph.addEdge("a", "b"), 10);
        origGraph.setEdgeWeight(origGraph.addEdge("b", "c"), 20);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReadFromFile() throws Exception {
        GraphReader reader = new GraphReader(new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class));
        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph;
        graph = (SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>) reader.readFromFile(new File("src/aufgabe2/test/fixture_graph_reader.txt"));

        assertEquals(graph.getEdgeWeight((DefaultWeightedEdge) graph.outgoingEdgesOf("a").toArray()[0]), 10.0, 0.0);
        assertEquals(graph.getEdgeWeight((DefaultWeightedEdge) graph.outgoingEdgesOf("b").toArray()[0]), 20.0, 0.0);
    }

    @Test
    public void testReadFromFileGraph3() throws Exception {
        SimpleWeightedGraph<String, DefaultWeightedEdge> graph;
        GraphReader reader = new GraphReader(new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class));
        graph = (SimpleWeightedGraph<String, DefaultWeightedEdge>) reader.readFromFile(new File("graph3.gka"));

        FloydWarshall floydWarshall;
    	Dijkstra dijkstra;
        floydWarshall = new FloydWarshall(graph);
        floydWarshall.setCountHits(true);
		dijkstra = new Dijkstra(graph);
        dijkstra.setCountHits(true);

		String dijk = "";
		for (String elem : dijkstra.findShortestPath("Hamburg", "Detmold")) {
			dijk+= " " + elem;
		}
        dijk = dijk.trim();

        String fw = floydWarshall.findShortestPath("Hamburg", "Detmold");
        System.out.println("Zugriffe Dijkstra: " + dijkstra.getHits());
        System.out.println("Zugriffe Floyd-Warshall: " + floydWarshall.getHits());
		assertEquals(fw, dijk.trim() );
    }

}
