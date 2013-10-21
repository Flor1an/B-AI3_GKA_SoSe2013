package aufgabe1.test;

import java.io.File;
import java.io.IOException;

import aufgabe1.GraphReader;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphReaderTest {

	private GraphReader reader;
	
	@Before
	public void setUp() {
		ListenableGraph<String, DefaultEdge> graph = new ListenableDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		this.reader = new GraphReader(graph);
	}

	@Test
	public void testGetVerticesFromString() {
		String vertices = "a,b;";
		assertArrayEquals(new String[] { "a", "b" }, GraphReader.getVerticesFromString(vertices));
		
		String vertex = "a;";
		assertArrayEquals(new String[] { "a" }, GraphReader.getVerticesFromString(vertex));
	}
	
	@Test
	public void testAddVertices() {
		String[] vertices = new String[] { "a", "b" };
		assertFalse(this.reader.getGraph().containsEdge(vertices[0], vertices[1]));
		this.reader.addVertices(vertices);
		assertTrue(this.reader.getGraph().containsEdge(vertices[0], vertices[1]));
		
		String[] vertex = new String[] { "a" };
		this.reader.addVertices(vertex);
		assertTrue(this.reader.getGraph().containsVertex("a"));
	}
	
	@Test
	public void testReadFromFile() {
		File file = new File("src/aufgabe1/test/fixture_graph_reader.txt");
		ListenableGraph<String, DefaultEdge> graph = null;

		try {
			graph = this.reader.readFromFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(graph.containsEdge("a", "b"));
		assertTrue(graph.containsEdge("b", "c"));
	}
}
