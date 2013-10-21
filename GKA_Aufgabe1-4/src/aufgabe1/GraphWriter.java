package aufgabe1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultEdge;

public class GraphWriter {
	
	/**
	 * The graph to be written to a file.
	 */
	private AbstractBaseGraph<String, DefaultEdge> graph;
	
	/**
	 * The constructor.
	 * 
	 * @param graphInstance Instance of the graph to be written to a file.
	 */
	public GraphWriter(AbstractBaseGraph<String, DefaultEdge> graphInstance) {
		this.graph = graphInstance;
	}
	
	/**
	 * Writes the graph to the given file.
	 * 
	 * @param file The file to write the graph to.
	 * @throws IOException
	 */
	public void writeToFile(File file) throws IOException {
		FileWriter writer = new FileWriter(file);
		writer.write(this.getDirectiveForGraph() + "\n");
		
		for (DefaultEdge edge : this.graph.edgeSet()) {
			writer.write(this.graph.getEdgeSource(edge) + "," + this.graph.getEdgeTarget(edge) + ";\n");
		}
		
		this.writeLoneVerticesToFile(writer);
		
		writer.close();
	}
	
	private void writeLoneVerticesToFile(FileWriter writer) throws IOException {
		for (String vertex : this.graph.vertexSet()) {
			if (this.graph.degreeOf(vertex) == 0) {
				writer.write(vertex + ";\n");
			}
		}
	}
	
	/**
	 * Returns "#directed" if the graph is directed and
	 * "#undirected" if the graph is undirected.
	 * @return The directive.
	 */
	private String getDirectiveForGraph() {
		return (this.graph instanceof DirectedGraph<?, ?>) ? "#directed" : "#undirected";
	}
}
