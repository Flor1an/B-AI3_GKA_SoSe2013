package aufgabe2;

import org.jgrapht.DirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class Dijkstra {

    /**
     * The graph to traverse.
     */
    private WeightedGraph<String, DefaultWeightedEdge> graph;

    /**
     * The graph's verticesWithMetaData as instances of VertexMetaData.
     */
    private Map<String, VertexMetaData> verticesWithMetaData;

    /**
     * Whether the number of hits on the graph should be counted or not.
     */
    private boolean countHits = false;

    /**
     * Counts the number of hits on the graph.
     */
    private int hits = 0;

    /**
     * Holds the distance of the shortest path.
     */
    private double shortestPathDistance;

    /**
     * The constructor.
     *
     * @param original The graph to traverse.
     */
    public Dijkstra(WeightedGraph<String, DefaultWeightedEdge> original) {
        graph = original;

        // keep all verticesWithMetaData of the given graph as VertexMetaData instances
        Set<String> originalVertices = graph.vertexSet();
        incrementHits();

        verticesWithMetaData = new HashMap<String, VertexMetaData>();
        for (String vertex : originalVertices) {
            verticesWithMetaData.put(vertex, new VertexMetaData());
        }
    }

    /**
     * Finds the shortest path from startVertex to endVertex.
     *
     * @param startVertex
     * @param endVertex
     * @return The verticesWithMetaData that make the path.
     */
    public String[] findShortestPath(String startVertex, String endVertex) {
        VertexMetaData startVertexData = verticesWithMetaData.get(startVertex);
        startVertexData.distance = 0;
        startVertexData.predecessor = startVertex;

        String currentVertex;
        // while there are unvisited verticesWithMetaData
        while ((currentVertex = selectNextVertex()) != null) {

            // set the current vertex as visited
            VertexMetaData currentMetaData = getMetaDataForVertex(currentVertex);
            currentMetaData.visited = true; // there's definitely no shorter path to this vertex

            // calculate distance for adjacent verticesWithMetaData
            updateDistanceOfAdjacentVertices(currentVertex);

        }

        return reconstructShortestPath(startVertex, endVertex);
    }

    /**
     * Updates the adjacent verticesWithMetaData' distance if
     * it is shorter when crossing the current vertex.
     *
     * @param currentVertex The vertex to update the neighbours for.
     */
    private void updateDistanceOfAdjacentVertices(String currentVertex) {
        VertexMetaData currentMetaData = getMetaDataForVertex(currentVertex);

        for (Map.Entry<String, Double> adjacentVertex : getAdjacentVerticesOfVertex(currentVertex).entrySet()) {
            VertexMetaData adjacentVertexMetaData = getMetaDataForVertex(adjacentVertex.getKey());
            double newDistance = currentMetaData.distance + adjacentVertex.getValue();

            if (newDistance < adjacentVertexMetaData.distance) {
                adjacentVertexMetaData.distance = newDistance;
                adjacentVertexMetaData.predecessor = currentVertex;
            }
        }
    }

    /**
     * Returns the meta data for the given vertex.
     *
     * @param vertex The vertex to get the meta data for.
     * @return The meta data.
     */
    private VertexMetaData getMetaDataForVertex(String vertex) {
        return verticesWithMetaData.get(vertex);
    }

    /**
     * Reconstructs the shortest path using the 'verticesWithMetaData' meta data.
     *
     * @param startVertex The start vertex.
     * @param endVertex The end vertex.
     * @return The shortest path from start vertex to end vertex.
     */
    private String[] reconstructShortestPath(String startVertex, String endVertex) {
        List<String> path = new ArrayList<String>();
        shortestPathDistance = 0;
        path.add(endVertex);

        VertexMetaData currentVertexMetaData = getMetaDataForVertex(endVertex);

        String predecessor = currentVertexMetaData.predecessor;
        while (!startVertex.equals(predecessor)) {
            path.add(predecessor);
            shortestPathDistance += currentVertexMetaData.distance;

            currentVertexMetaData = getMetaDataForVertex(predecessor);

            if (currentVertexMetaData == null) {
                throw new NoPathException(startVertex, endVertex);
            } else {
                predecessor = currentVertexMetaData.predecessor;
            }
        }

        path.add(startVertex);

        Collections.reverse(path);

        return path.toArray(new String[path.size()]);
    }

    /**
     * Returns all verticesWithMetaData adjacent to the given vertex and
     * their respective edge weight.
     *
     * @param vertex The source vertex.
     * @return The adjacent verticesWithMetaData and the weight of their incident edges.
     */
    public Map<String, Double> getAdjacentVerticesOfVertex(String vertex) {
        Map<String, Double> vertices = new HashMap<String, Double>();

        incrementHits();
        for (DefaultWeightedEdge edge : graph.edgesOf(vertex)) {
            String sourceVertex = graph.getEdgeSource(edge);
            incrementHits();
            String targetVertex = graph.getEdgeTarget(edge);
            incrementHits();

            // If the graph is undirected, we need to get the adjacent
            // verticesWithMetaData in both directions, but not the given vertex itself
            if (!isGraphDirected() && !vertex.equals(sourceVertex)) {
                vertices.put(sourceVertex, graph.getEdgeWeight(edge));
                incrementHits();
            }

            if (!vertex.equals(targetVertex)) {
                vertices.put(targetVertex, graph.getEdgeWeight(edge));
                incrementHits();
            }
        }

        return vertices;
    }

    /**
     * Checks if the graph is directed or not.
     *
     * @return Whether it is directed or not.
     */
    public boolean isGraphDirected() {
        return graph instanceof DirectedGraph<?, ?>;
    }


    /**
     * Getter for the number of hits on the graph.
     */
    public int getHits() {
        return hits;
    }

    /**
     * Setter for the countHits instance variable.
     *
     * @param val True if hits should be counted, false if not.
     */
    public void setCountHits(boolean val) {
        countHits = val;
    }

    /**
     * Getter for the shortestPathDistance instance variable.
     *
     * @return The distance.
     */
    public double getShortestPathDistance() {
        return shortestPathDistance;
    }

    /**
     * Selects the unvisited vertex with the lowest distance.
     *
     * @return The vertex if one exists or null otherwise.
     */
    private String selectNextVertex() {
        double minDistance = Integer.MAX_VALUE;
        String nextVertex = null;

        for (Map.Entry<String, VertexMetaData> entry : verticesWithMetaData.entrySet()) {
            VertexMetaData metaData = entry.getValue();
            // the vertex must not be visited and its distance must by lower than any value before
            if (!metaData.visited && metaData.distance < minDistance) {
                minDistance = metaData.distance;
                nextVertex = entry.getKey();
            }
        }

        return nextVertex;
    }

    /**
     * Increments the number of hits on the graph if they should be counted.
     */
    private void incrementHits() {
        if (countHits)
            hits++;
    }

    /**
     * Represents the verticesWithMetaData' meta data such as
     * their distance from the start vertex,
     * their respective predecessor and if their
     * is definitely no shorter path to them.
     */
    private class VertexMetaData {

        /**
         * The predecessor of this vertex.
         */
        public String predecessor;

        /**
         * Whether there is definitely
         * no shorter path to this vertex
         * than the current one.
         */
        public boolean visited = false;

        /**
         * This vertex's distance from the starting vertex.
         */
        public double distance = Double.POSITIVE_INFINITY;

        public String toString() {
            return "{ Pred.: " + predecessor + ", Visited: " + visited + ", Dist.:" + distance + " }";
        }

    }

    /**
     * Will be thrown if there is no path between to verticesWithMetaData.
     */
    public class NoPathException extends RuntimeException {

        public NoPathException(String startVertex, String endVertex) {
            super("There is no path between " + startVertex + " and " + endVertex);
        }
    }

}
