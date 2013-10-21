package aufgabe4.test;

import static org.junit.Assert.*;

import java.io.File;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.junit.Before;
import org.junit.Test;


import aufgabe4.GraphReader;
import aufgabe4.MST;
import aufgabe4.NNA;

public class AlgorithmusTest {
	GraphReader reader = new GraphReader();
	WeightedMultigraph<String, DefaultWeightedEdge> graph1;

	

	
	
	@Before
	public void setUp() throws Exception {

		graph1 = (WeightedMultigraph<String, DefaultWeightedEdge>) reader.readFromFile(new File("graphtest.gka"));
	}

	@Test
	public void TestMST() {

		MST mst = new MST(graph1);
        assertEquals((Integer)mst.getWegLaenge(),(Integer)10); //kommt auf das Gerüst draufan 15 oder 26
	}
	
	@Test
	public void TestNNA() {

		NNA nna = new NNA(graph1);
        assertEquals((Integer)nna.getWegLaenge(),(Integer)8);
	}
	



}
