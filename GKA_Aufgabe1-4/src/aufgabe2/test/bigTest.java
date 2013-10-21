package aufgabe2.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Before;
import org.junit.Test;

import aufgabe2.Dijkstra;
import aufgabe2.FloydWarshall;

public class bigTest {
	FloydWarshall floydWarshall;
	Dijkstra dijkstra;
    WeightedGraph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Random r = new Random();

	@Before
	public void setUp() throws Exception {
		for (int i = 0; i < 100; i++) {
			 graph.addVertex("v" + i);
		}


		for (int i = 0; i < 6000; i++) {
			try {
				graph.setEdgeWeight(graph.addEdge("v"+r.nextInt(100), "v"+r.nextInt(100)),r.nextInt(1000));
			} catch (Exception e) {

			}

		}
		floydWarshall = new FloydWarshall(graph);
		dijkstra = new Dijkstra(graph);
	}



	@Test
	public void test() {
		String r1="v"+r.nextInt(100);
		String r2="v"+r.nextInt(100);

		String dijk = "";
		for (String elem : dijkstra.findShortestPath(r1,r2)) {
			dijk+= " " + elem;
		}
        dijk = dijk.trim();

        String fw = floydWarshall.findShortestPath(r1, r2);
       
        assertEquals(floydWarshall.getShortestPathDistance(), dijkstra.getShortestPathDistance(),0.0);
	}

}
