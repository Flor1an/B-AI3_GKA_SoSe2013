package aufgabe1.test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Before;
import org.junit.Test;

import aufgabe1.BreadthFirstSearch;
import aufgabe1.CompleteGraph;
import aufgabe1.GraphReader;
import static org.junit.Assert.*;

public class BreadthFirstSearchTest {
	
	BreadthFirstSearch search;
	
	@Before
	public void setUp() {
		Graph<String, DefaultEdge> graph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
		
		graph.addVertex("v1");
		graph.addVertex("v2");
		graph.addVertex("v3");
		graph.addVertex("v4");
		graph.addVertex("v5");
		graph.addVertex("v6");
		
		graph.addEdge("v1", "v2");
		graph.addEdge("v2", "v3");
		graph.addEdge("v3", "v6");
		graph.addEdge("v2", "v4");
		graph.addEdge("v4", "v5");
		graph.addEdge("v5", "v6");
		
		this.search = new BreadthFirstSearch(graph);
	}
	
	@Test
	public void testGraphIsDirected() {
		assertFalse(this.search.graphIsDirected());
	}
	
	@Test
	public void testGetAdjacentVerticesOf() {
		String[] adjacentVertices = new String[] { "v1", "v3", "v4" };
		String[] resultVertices = this.search.getAdjacentVerticesOf("v2");
		Arrays.sort(resultVertices);
		assertArrayEquals(adjacentVertices, resultVertices);
	}
	
	@Test
	public void testFindShortestPath() {
		String[] resultPath = new String[] { "v1", "v2", "v3", "v6" };
		assertArrayEquals(resultPath, this.search.getShortestPath("v1", "v6"));
	}
	
	@Test
	public void testFindShortestPathGraph1() {
		GraphReader reader = new GraphReader();
		Graph<String, DefaultEdge> graph = null;
		try {
			graph = reader.readFromFile(new File("graph1.gka"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] resultPath = new String[] { "b", "j", "a", "h" };
		BreadthFirstSearch bfs = new BreadthFirstSearch(graph);
		
		assertArrayEquals(resultPath, bfs.getShortestPath("b", "h"));
		assertEquals(3, bfs.getNeededEdges());
	}
	
	@Test
	public void testFindShortestPathGraph2() {
		GraphReader reader = new GraphReader();
		Graph<String, DefaultEdge> graph = null;
		try {
			graph = reader.readFromFile(new File("graph2.gka"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] resultPath = new String[] { "a", "g" };
		BreadthFirstSearch bfs = new BreadthFirstSearch(graph);
		
		assertArrayEquals(resultPath, bfs.getShortestPath("a", "g"));
		assertEquals(1, bfs.getNeededEdges());
	}
	
	@Test
	public void testK7() {
		Graph<String, DefaultEdge> graph = new CompleteGraph<String, DefaultEdge>(DefaultEdge.class);
		
		for (int i = 1; i <= 7; i++) {
			graph.addVertex("v" + i);
		}
		
		BreadthFirstSearch bfs = new BreadthFirstSearch(graph);
		bfs.getShortestPath("v1", "v7");
		assertEquals(1, bfs.getNeededEdges());
	}
 
}
