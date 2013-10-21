package aufgabe3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.*;

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
     * The directive for a weighted graph.
     */
    private static final String weightedGraphDirective = "#weighted";

    /**
     * The directive for an unweighted graph.
     */
    private static final String unweightedGraphDirective = "#unweighted";


    /**
     * The graph that the vertices and edges from
     * the file are added to.
     */
    private Graph<String, ? extends DefaultEdge> graph = null;

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
     * Returns the weight of the edge in the given string.
     * I.e.: "vertex1,vertex2,10;"
     * would return: 10
     *
     * @param str The string to extract the weight from.
     * @return The edge weight.
     */
    public static double getEdgeWeightFromString(String str) {
        String[] data = str.split(",|;");

        return Double.valueOf(data[data.length - 1]);
    }

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
    public GraphReader(Graph<String, ? extends DefaultEdge> graphInstance) {
        this.graph = graphInstance;
    }

    /**
     * Builds the graph from the adjacency list in the given file.
     *
     * @param file The file containing the graph's adjacency list.
     * @return The resulting graph.
     * @throws IOException
     */
    public Graph<String, ? extends DefaultEdge> readFromFile(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String directedDirective = br.readLine();
        String weightDirective = br.readLine();
        if (graph == null) {
            instantiateGraph(weightDirective, directedDirective);
        }

        String currentLine;
        while ((currentLine = br.readLine()) != null) {
            String[] vertices = getVerticesFromString(currentLine);

            if (isGraphWeighted()) {
                double edgeWeight = getEdgeWeightFromString(currentLine);
                addVerticesWithWeight(vertices, edgeWeight);
            } else {
                addVertices(vertices);
            }
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
     * Adds the given vertices to the graph and connects
     * them with an edge having the given weight.
     *
     * @param vertices The vertices to add and connect.
     * @param edgeWeight The weight of the incident edge.
     */
    @SuppressWarnings("unchecked")
    public void addVerticesWithWeight(String[] vertices, double edgeWeight) {
        this.graph.addVertex(vertices[0]);
        if (vertices.length > 1) {
            this.graph.addVertex(vertices[1]);
            DefaultWeightedEdge edge = (DefaultWeightedEdge) graph.addEdge(vertices[0], vertices[1]);
            if (edge==null){
            	edge = (DefaultWeightedEdge) graph.getEdge(vertices[0], vertices[1]);
            }else{
            ((WeightedGraph<String, DefaultWeightedEdge>)graph).setEdgeWeight(edge, edgeWeight);
        }}
    }

    /**
     * Getter for the graph instance.
     *
     * @return The graph instance.
     */
    public Graph<String, ? extends DefaultEdge> getGraph() {
        return this.graph;
    }

    /**
     * Whether the graph is weighted or not.
     *
     * @return True if it is, false if not.
     */
    private boolean isGraphWeighted() {
        return graph instanceof WeightedGraph;
    }

    /**
     * Creates an instance of an undirected or directed graph
     * depending on the given directive.
     *
     * @param weightDirective Either "#weighted" or "#unweighted".
     */
    private void instantiateGraph(String weightDirective, String directedDirective) {
        if (directedDirective.equals(directedGraphDirective)) {
            if (weightDirective.equals(weightedGraphDirective)) {
                graph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
            } else if (directedDirective.equals(unweightedGraphDirective)) {
                graph = new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
            } else {
                throw new IllegalArgumentException("The given directive '" + directedDirective + "' is none of #directed or #undirected");
            }
        } else if (directedDirective.equals(undirectedGraphDirective)) {
            if (weightDirective.equals(weightedGraphDirective)) {
                graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
            } else if (directedDirective.equals(unweightedGraphDirective)) {
                graph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
            } else {
                throw new IllegalArgumentException("The given directive '" + directedDirective + "' is none of #directed or #undirected");
            }
        } else {
            throw new IllegalArgumentException("The given directive '" + weightDirective + "' is none of #weighted or #unweighted.");
        }
    }

}
