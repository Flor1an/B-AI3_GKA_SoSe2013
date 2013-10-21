package aufgabe3.test;

import static org.junit.Assert.*;

import java.io.File;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Before;
import org.junit.Test;

import aufgabe3.EdmondsKarp;
import aufgabe3.FordFulkerson;
import aufgabe3.GraphReader;

public class CompareAlgorithmusTest {
	GraphReader reader = new GraphReader();
	Graph<String, DefaultWeightedEdge> graph4;
	Graph<String, DefaultWeightedEdge> graph5;
	Graph<String, DefaultWeightedEdge> graphBigNet;
	
	FordFulkerson ff = new FordFulkerson();
	EdmondsKarp ek = new EdmondsKarp();
	
	
	@Before
	public void setUp() throws Exception {

		graph4 = (Graph<String, DefaultWeightedEdge>) reader.readFromFile(new File("graph4.gka"));
		graph5 = (Graph<String, DefaultWeightedEdge>) reader.readFromFile(new File("graph5.gka"));
		
		graphBigNet = (Graph<String, DefaultWeightedEdge>) reader.readFromFile(new File("50-800.gka"));
//		graphBigNet = (Graph<String, DefaultWeightedEdge>) reader.readFromFile(new File("800-300000.gka"));
//		graphBigNet = (Graph<String, DefaultWeightedEdge>) reader.readFromFile(new File("2500-2000000.gka"));
	}

	@Test
	public void compareGraph4ResultTest() {
		String quelle = "q";
		String senke = "s";
		
//		System.out.println(ff.ffStart(graph4, quelle, senke));
//		System.out.println(ek.ekStart(graph4, quelle, senke));
 
        assertEquals(ff.ffStart(graph4, quelle, senke),ek.ekStart(graph4, quelle, senke),0.0);
	}
	
	@Test
	public void compareGraph5ResultTest() {
		String quelle = "v1";
		String senke = "v6";
		
//		System.out.println(ff.ffStart(graph5, quelle, senke));
//		System.out.println(ek.ekStart(graph5, quelle, senke));
 
        assertEquals(ff.ffStart(graph5, quelle, senke),ek.ekStart(graph5, quelle, senke),0.0);
	}
	
	
	@Test
	public void compareGraphBigNetResultTest() {
		String quelle = "q";
		String senke = "s";
		
		System.out.println(ff.ffStart(graphBigNet, quelle, senke) + " \t | \t" + ff.laufzeit);
		System.out.println(ek.ekStart(graphBigNet, quelle, senke) + " \t | \t" + ek.laufzeit);
		
		
 
        assertEquals(ff.ffStart(graphBigNet, quelle, senke),ek.ekStart(graphBigNet, quelle, senke),0.0);
	}
	
	


}
