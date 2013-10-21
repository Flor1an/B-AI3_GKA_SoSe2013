package aufgabe1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

public class GraphReader {
	
	/**
	 * The directive for a directed Graph.
	 */
	private static final String directedGraphDirective = "#directed";
	
	/**
	 * The directive for an undirected Graph.
	 */
	private static final String undirectedGraphDirective = "#undirected";
	
	/**
	 * Takes a string in the following format:
	 * "vertex1,vertex2;"
	 * 
	 * And returns the extracted vertices in an array:
	 * { "vertex1", "vertex2" }
	 * 
	 * @param str The raw string.
	 * @return The extracted vertices.
	 */
	public static String[] getVerticesFromString(String str) {
		String[] vertices = str.split(",|;");
		
		if (vertices.length > 1) {
			return new String[] { vertices[0], vertices[1] };
		} else {
			return new String[] { vertices[0] };
		}
	}
	
	/**
	 * The graph that the vertices and edges from
	 * the file are added to.
	 */
	private ListenableGraph<String, DefaultEdge> graph = null;
	
	/**
	 * This is just so the default constructor stays available.
	 */
	public GraphReader() {
		super();
	}
	
	/**
	 * Alternative constructor for testing purposes.
	 * 
	 * @param graphInstance The Graph instance this class should use.
	 */
	public GraphReader(ListenableGraph<String, DefaultEdge> graphInstance) {
		this.graph = graphInstance;
	}
	
	/**
	 * Builds the graph from the adjacency list in the given file.
	 * 
	 * @param file The file containing the graph's adjacency list.
	 * @return The resulting graph.
	 * @throws IOException
	 */
	public ListenableGraph<String, DefaultEdge> readFromFile(File file) throws IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String directive = br.readLine();
		if (this.graph == null) {
			instantiateGraph(directive);
		}
		
		String currentLine;
		while ((currentLine = br.readLine()) != null) {
			String[] vertices = getVerticesFromString(currentLine);
			this.addVertices(vertices);
		}
		
		br.close();
		return this.graph;
	}
	
	/**
	 * Adds the given vertices to the graph and connects
	 * them with an edge.
	 * 
	 * @param vertices The vertices to add and connect.
	 */
	public void addVertices(String[] vertices) {
		this.graph.addVertex(vertices[0]);
		if (vertices.length > 1) {
			this.graph.addVertex(vertices[1]);
			this.graph.addEdge(vertices[0], vertices[1]);
		}
	}
	
	/**
	 * Getter for the graph instance.
	 * 
	 * @return The graph instance.
	 */
	public ListenableGraph<String, DefaultEdge> getGraph() {
		return this.graph;
	}
	
	/**
	 * Creates an instance of an undirected or directed graph
	 * depending on the given directive.
	 * 
	 * @param directive Either "#directed" or "#undirected".
	 */
	private void instantiateGraph(String directive) {
		if (directive.equals(directedGraphDirective)) {
			this.graph = new ListenableDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		} else if (directive.equals(undirectedGraphDirective)) {
			this.graph = new ListenablePseudograph<String, DefaultEdge>(DefaultEdge.class);
		} else {
			throw new IllegalArgumentException("The given directive '" + directive + "' is none of #directed or #undirected.");
		}
	}	
	
}
