package aufgabe1;

import java.io.File;
import java.io.IOException;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultEdge;

public class Main {

	public static void main(String[] args) {
		GraphReader reader1 = new GraphReader();
		ListenableGraph<String, DefaultEdge> graph1 = null;
		GraphReader reader2 = new GraphReader();
		ListenableGraph<String, DefaultEdge> graph2 = null;

		try {
			graph1 = reader1.readFromFile(new File("graph1.gka"));
			graph2 = reader2.readFromFile(new File("graph2.gka"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new GraphVisualizer(graph1);
		new GraphVisualizer(graph2);
	}

}
