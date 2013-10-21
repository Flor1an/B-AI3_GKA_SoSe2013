package aufgabe1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class BreadthFirstSearch {
	
	/**
	 * The graph containing the start and end vertex.
	 */
	private Graph<String, DefaultEdge> graph;
	
	/**
	 * The already visited vertices.
	 */
	private Set<String> visitedVertices;
	
	/**
	 * The queue of vertices to visit next.
	 */
	private LinkedList<String> queue;
	
	/**
	 * Relation of (childVertex, parentVertex).
	 * This is needed to reconstruct the shortest path.
	 */
	private Map<String, String> vertexParents;
	
	/**
	 * Will hold the shortest path after getShortestPath()
	 * has been called.
	 */
	private String[] shortestPath = null;
	
	/**
	 * Holds the number of hits on the graph.
	 */
	private int hits = 0;
	
	/**
	 * The constructor.
	 * 
	 * @param graphInstance The graph that should be traversed.
	 */
	public BreadthFirstSearch(Graph<String, DefaultEdge> graphInstance) {
		this.graph = graphInstance;
		this.visitedVertices = new HashSet<String>();
		this.queue = new LinkedList<String>();
		this.vertexParents = new HashMap<String, String>();
	}

	/**
	 * Returns the shortest path form the start vertex to the end vertex
	 * after traverse() has been called.
	 * 
	 * @return The shortest path.
	 */
	public String[] getShortestPath(String startVertex, String endVertex) {
		this.traverse(startVertex, endVertex);

		LinkedList<String> path = new LinkedList<String>();
		String currentVertex = endVertex;
		
		do {
			path.addFirst(currentVertex);
		} while((currentVertex = this.getParentVertexOf(currentVertex)) != null);
		
		String[] pathAry = path.toArray(new String[path.size()]);
		this.shortestPath = pathAry;
		
		return pathAry;
	}
	
	/**
	 * Returns the number of edges needed for traversing the
	 * graph using the shortest path.
	 *
	 * @return The number of edges.
	 */
	public int getNeededEdges() {
		if (this.shortestPath == null)
			throw new RuntimeException("You must run getShortestPath() before running this method.");

		return this.shortestPath.length - 1;
	}
	
	/**
	 * Returns all vertices adjacent to the given vertex in an array.
	 * 
	 * @param vertex The source vertex.
	 * @return The adjacent vertices.
	 */
	public String[] getAdjacentVerticesOf(String vertex) {
		Set<String> vertices = new HashSet<String>();

		this.hits++;
		for (DefaultEdge edge : this.graph.edgesOf(vertex)) {
			String sourceVertex = this.graph.getEdgeSource(edge);
			String targetVertex = this.graph.getEdgeTarget(edge);
			this.hits += 2;
			
			// If the graph is undirected, we want to get the adjacent
			// vertices in both directions, but not the given vertex itself
			if (!vertex.equals(sourceVertex) && !this.graphIsDirected())
				vertices.add(sourceVertex);
			
			if (!vertex.equals(targetVertex))
				vertices.add(targetVertex);
		}

		return vertices.toArray(new String[vertices.size()]);
	}
	
	/**
	 * Checks if the graph is directed or not.
	 * 
	 * @return Whether it is directed or not.
	 */
	public boolean graphIsDirected() {
		return this.graph instanceof DirectedGraph<?, ?>;
	}
	
	/**
	 * Returns the number of hits on the graph.
	 * 
	 * @return Number of hits.
	 */
	public int getHits() {
		return this.hits;
	}
	
	/**
	 * Traverses the graph beginning with the start vertex until
	 * the given end vertex is found.
	 * 
	 * @param startVertex The vertex to start searching from.
	 * @param endVertex The searched vertex.
	 * @return Whether the searched vertex could be found or not.
	 */
	private boolean traverse(String startVertex, String endVertex) {
		if (!this.graph.containsVertex(startVertex) || !this.graph.containsVertex(endVertex))
			throw new IllegalArgumentException("The start vertex and end vertex must be contained in the given graph!");
		
		// add the start vertex to the queue and mark it as visited
		this.queue.add(startVertex);
		this.visitedVertices.add(startVertex);
		
		while (!this.queue.isEmpty()) {			
			String vertex = this.queue.removeFirst();
			
			if (vertex.equals(endVertex)) {
				return true; // vertex found
			} else {
				this.addAdjacentVerticesToQueue(vertex);
			}
		}
		
		return false;
	}
	
	/**
	 * Adds all vertices adjacent to the given vertex
	 * to the queue, saves it as their parent and marks them as visited.
	 * 
	 * @param vertex The parent vertex.
	 */
	private void addAdjacentVerticesToQueue(String vertex) {
		for (String childVertex : this.getAdjacentVerticesOf(vertex)) {
			if (!this.visitedVertices.contains(childVertex)) {
				this.queue.add(childVertex);
				this.vertexParents.put(childVertex, vertex);
				this.visitedVertices.add(childVertex);
			}
		}
	}
	
	/**
	 * Returns the parent vertex of the given child vertex.
	 * 
	 * @param childVertex The child vertex.
	 * @return The parent vertex.
	 */
	private String getParentVertexOf(String childVertex) {
		return this.vertexParents.get(childVertex);
	}
}
