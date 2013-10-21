package aufgabe1.test;

import static org.junit.Assert.*;
import org.jgrapht.Graph;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;

import java.io.File;

import aufgabe1.GraphReader;
import aufgabe1.GraphWriter;
import aufgabe1.CompleteGraph;

public class GraphWriterTest {

	@Test
	public void testK7() {
		AbstractBaseGraph<String, DefaultEdge> graph = new CompleteGraph<String, DefaultEdge>(DefaultEdge.class);
		
		for (int i = 1; i <= 25; i++) {
			graph.addVertex("v" + i);
		}
		
		File file = new File("k25.gka");
		GraphWriter writer = new GraphWriter(graph);
		try {
			writer.writeToFile(file);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Graph<String, DefaultEdge> graph2 = null;
		GraphReader reader = new GraphReader();
		try {
			graph2 = reader.readFromFile(file);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		for (String vertex : graph.vertexSet()) {
			assertTrue(graph2.containsVertex(vertex));
		}
	}

}
