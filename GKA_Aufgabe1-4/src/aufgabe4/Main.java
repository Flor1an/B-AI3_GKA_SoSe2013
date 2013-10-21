package aufgabe4;

import java.io.File;
import java.io.IOException;

import javax.swing.text.html.MinimalHTMLWriter;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.graph.WeightedMultigraph;




public class Main {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		GraphReader reader = new GraphReader();
		WeightedMultigraph<String, DefaultWeightedEdge> graph5;
		WeightedMultigraph<String, DefaultWeightedEdge> graphK25;

		try {
			graph5 = (WeightedMultigraph<String, DefaultWeightedEdge>) reader.readFromFile(new File("graph5.gka"));
			
				
			MST mst = new MST(graph5);
			System.out.println("MST: " + mst.getWegLaenge() + mst.finalPath);
			
			NNA nna = new NNA(graph5);
			System.out.println("NNA: " + nna.getWegLaenge() + nna.finalPath);
			
			
			
			System.out.println();
			
			graphK25 = (WeightedMultigraph<String, DefaultWeightedEdge>) reader.readFromFile(new File("K25.gka"));
			
			MST mstK25 = new MST(graphK25);
			System.out.println("MST: " + mstK25.getWegLaenge() + mstK25.finalPath);
			
			NNA nnaK25 = new NNA(graphK25);
			System.out.println("NNA: " + nnaK25.getWegLaenge() + nnaK25.finalPath);


		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
