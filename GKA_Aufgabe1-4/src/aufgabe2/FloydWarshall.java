package aufgabe2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class FloydWarshall {
	/**
	 * The graph containing the start and end vertex.
	 */
	private WeightedGraph<String, DefaultWeightedEdge> graph;
	private double[][] distanceMatrix;
	private Integer[][] transitMatrix;
	private List<String> vertexList=new ArrayList<String>();

    /**
     * Whether the number of hits on the graph should be counted or not.
     */
    private boolean countHits = false;

    /**
     * Counts the number of hits on the graph.
     */
    private int hits = 0;

    
    private String generatedPath;


	public FloydWarshall(WeightedGraph<String, DefaultWeightedEdge> original) {
		graph = original;
		distanceMatrix = new double[original.vertexSet().size()][original.vertexSet().size()];
		transitMatrix = new Integer[original.vertexSet().size()][original.vertexSet().size()];
		vertexList.addAll(graph.vertexSet());
        incrementHits();
	}

	private void fillDistanceMatrix() {
		for (int i = 0; i < graph.vertexSet().size(); i++) {
			for (int j = 0; j < graph.vertexSet().size(); j++) {
                incrementHits();
				if ((graph.getEdge(vertexList.get(i), vertexList.get(j)) != null) && (i != j)) {
					distanceMatrix[i][j] = graph.getEdgeWeight(graph.getEdge(vertexList.get(i), vertexList.get(j)));
                    incrementHits();
                    incrementHits();
                    incrementHits();
                } else if (i == j) {
					distanceMatrix[i][j] = 0;
                } else {
					distanceMatrix[i][j] = Double.POSITIVE_INFINITY;
                }
			}
		}
	}

	private void fillTransitMatrix() {
        incrementHits();
		for (int i = 0; i < graph.vertexSet().size(); i++) {
			for (int j = 0; j < graph.vertexSet().size(); j++) {
				transitMatrix[i][j] = null;
			}
		}
        incrementHits();
	}
	
	public double getShortestPathDistance(){
		String[] shortestPath = generatedPath.split(" ");
		double shortestPathDistance = 0;
		
		for (int i = 0; i < shortestPath.length-1; i++) {
			shortestPathDistance += graph.getEdgeWeight(graph.getEdge(shortestPath[i], shortestPath[i+1]));
		}
			
		
		
		return shortestPathDistance;
	}

	public String findShortestPath(String startVertex, String endVertex) {
		fillDistanceMatrix();
		fillTransitMatrix();

		for (int i = 0; i < graph.vertexSet().size(); i++) {
			for (int j = 0; j < graph.vertexSet().size(); j++) {
				if (i!=j){
					for (int k = 0; k < graph.vertexSet().size(); k++) {
						if (k!=j){
							// Setze d[i][k] := min{d[i][k}; d[i][j] + d[j][k]}.
							double oldValue=distanceMatrix[i][k];
							double newValue=Math.min(distanceMatrix[i][k], (distanceMatrix[i][j] + distanceMatrix[j][k]));

							distanceMatrix[i][k]=newValue;
							//Falls d[i][k] ver�andert wurde, setze t[i][k] := j.
							if (oldValue!=newValue){
								transitMatrix[i][k]=j;
							}
						}
						//Falls d[i][i] < 0 ist, brich den Algorithmus vorzeitig ab.
						if (distanceMatrix[i][i]<0)
							try {
								throw new Exception("negativ result");
							} catch (Exception e) {
								e.printStackTrace();
							}

					}
				}

			}
		}


//		for (Integer[] elem : transitMatrix) {
//			System.out.println(Arrays.toString(elem));
//		}

		Integer startVertexIndex =null;
		Integer endVertexIndex=null;

		for (int i = 0; i < vertexList.size(); i++) {
			if (vertexList.get(i).equals(startVertex)) {
				startVertexIndex=i;
                incrementHits();
            }
			if (vertexList.get(i).equals(endVertex)) {
				endVertexIndex=i;
                incrementHits();
            }
		}

		generatedPath = startVertex  + getPath(startVertexIndex, endVertexIndex) + endVertex;
		return generatedPath;
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
     * Increments the number of hits on the graph if they should be counted.
     */
    private void incrementHits() {
        if (countHits)
            hits++;
    }

	public String getPath(Integer i, Integer j){
		if (distanceMatrix[i][j] == Double.POSITIVE_INFINITY )
			return "no path";

		Integer intermediate = transitMatrix[i][j];

		if (intermediate==null)
			return " ";
		else
			return getPath(i, intermediate) + vertexList.get(intermediate) + getPath(intermediate, j);

	}




}
