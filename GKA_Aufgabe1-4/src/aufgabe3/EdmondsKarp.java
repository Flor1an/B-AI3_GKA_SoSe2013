package aufgabe3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;


public class EdmondsKarp {
	
	private static Map<DefaultWeightedEdge, Double> kantenFluss = null;
	private static Map<String, Markierung> markiert	= null;
	private static Set<String>	inspiziert	= null;
	private static Graph<String, DefaultWeightedEdge> grah = null;
	public static long laufzeit = 0;

	
	protected class Markierung {

		private boolean vorzeichen	= false; //Vorzeichen des Vorgaengers: True = + | False = -
		private String vorgaenger	= null;
		private Double delta		= 0.0;
		
		Markierung(boolean vz, String vg, Double d){
			this.vorzeichen	= vz;
			this.vorgaenger	= vg;
			this.delta		= d;
		}

		public boolean isVorzeichen() {
			return vorzeichen;
		}

		public void setVorzeichen(boolean vorzeichen) {
			this.vorzeichen = vorzeichen;
		}

		public String getVorgaenger() {
			return vorgaenger;
		}

		public void setVorgaenger(String vorgaenger) {
			this.vorgaenger = vorgaenger;
		}

		public Double getDelta() {
			return delta;
		}

		public void setDelta(Double delta) {
			this.delta = delta;
		}
		
		public String toString(){
			String vz;
			if (vorzeichen=true)
				vz="+";
			else
				vz="-";
			return "("+vz  + vorgaenger + ", " + delta + ")";
		}
	}
	
	public Double ekStart(Graph<String,DefaultWeightedEdge> gr, String quelle, String senke){
		
		
		
		if(!gr.containsVertex(quelle))
			System.err.println("Quelle nicht im Graphen!");
		if(!gr.containsVertex(senke))
			System.err.println("Senke nicht im Graphen!");

		kantenFluss = new HashMap<DefaultWeightedEdge, Double>();
		markiert	= new HashMap<String,Markierung>();
		inspiziert	= new HashSet<String>();
		grah = gr;
	
		//Edges initialisieren (mit flow wert = 0)
		// (1) Initialisierung Jede Kante wird einem Wert f(eij) initialisiert, der die Nebenbedingungen erfullt. Markiere q mit (undef; INFINITY).
		for (DefaultWeightedEdge e: grah.edgeSet()){
			kantenFluss.put(e, 0.0);
		}
		
			//Vertex q initialisieren Vorgaenger,Capacity:(undef, ininity)
			markiert.put(quelle, new Markierung(false, null, Double.POSITIVE_INFINITY));
			boolean ende = false;
			
			
			DijkstraShortestPath<String, DefaultWeightedEdge> dij = new DijkstraShortestPath<String, DefaultWeightedEdge>(grah, quelle, senke);
			dij.getPath();
			
			Set<String> shortesPathKnoten = new HashSet<>();
			for (DefaultWeightedEdge e : dij.getPathEdgeList()) {
				shortesPathKnoten.add(grah.getEdgeSource(e));
				shortesPathKnoten.add(grah.getEdgeTarget(e));
			}
			
			long startZeit = System.currentTimeMillis();			
		
		while(!ende){ // (2.3) Falls alle markierten Knoten inspiziert wurden, gehe nach 4
			
		
			// (2) Wähle einen markierten, aber noch nicht inspizierten Knoten, DER IM KUERZESTEN PFAD VORKOMMT

			
			Set<String> bel = new HashSet<>();
			bel.addAll(markiert.keySet());
			bel.removeAll(inspiziert);
			
			Iterator<String> it1 = bel.iterator();
			String beliebigGewaehlterKnoten = it1.next();
			
			while(! shortesPathKnoten.contains(beliebigGewaehlterKnoten) && it1.hasNext()){
				beliebigGewaehlterKnoten = it1.next();	
			}
	
			
			
			boolean sWurdeMarkiert = false;
			// (2.1-a) Für jede Kante eij € O(vi) ...
		
			for (DefaultWeightedEdge e : grah.edgesOf(beliebigGewaehlterKnoten)) {
		
				
				//(2.2) Falls s markiert ist, gehe zu 3, sonst zu 2.
				if(!sWurdeMarkiert) { 
					
					
					if (grah.getEdgeSource(e).equals(beliebigGewaehlterKnoten)) {
						String v = grah.getEdgeTarget(e); //target = OUT = O
						
						// (2.1-a) ...mit unmarkierter Knoten vj und f(eij) < c(eij)... 
						if (!markiert.containsKey(v)	&& (kantenFluss.get(e) < grah.getEdgeWeight(e))) {
							
							// (2.1-a) ...markiere vj mit (+vi; DELTAj), wobei DELTAj die kleinere der beiden Zahlen c(eij) - f(eij) und DELTAi ist
							markiert.put(v,new Markierung(true, beliebigGewaehlterKnoten, Math.min(markiert.get(beliebigGewaehlterKnoten).getDelta(),(grah.getEdgeWeight(e) - kantenFluss.get(e)))));
						
						}
					} else {
						String v = grah.getEdgeSource(e); //source = IN = I
						 
						// (2.1-b) Fur jede Kante eji € I(vi) mit unmarkiertem Knoten vj und f(eji) > 0 ...
						if (!markiert.containsKey(v) && (kantenFluss.get(e) > 0)) {
							
							// (2.1-b)... markiere vj mit (-vi, DELTAj), wobei DELTAj die kleinere der beiden Zahlen f(eij) und DELTAi ist
							markiert.put(v,new Markierung(false, beliebigGewaehlterKnoten, Math.min(markiert.get(beliebigGewaehlterKnoten).getDelta(),kantenFluss.get(e))));
						
						}
					}
					
					//(2.2) Falls s markiert ist ..
					if (markiert.containsKey(senke)) {
						sWurdeMarkiert = true;
		

						//(2.2) ... gehe zu 3
						vergroesserungDerFlussstaerke(senke);
						//(3) .. Anschließend werden bei allen Knoten mit Ausnahme von q die Markierungen entfernt.
						markiert = new HashMap<String,Markierung>();
						markiert.put(quelle, new Markierung(false, null, Double.POSITIVE_INFINITY));
						inspiziert = new HashSet<String>();
					}// (3) ... Gehe zu 2.
				}
			}
			if(!sWurdeMarkiert)
				inspiziert.add(beliebigGewaehlterKnoten);
	
			// (2.3) Falls alle markierten Knoten inspiziert wurden, gehe nach 4
			if(inspiziert.containsAll(markiert.keySet())){
				ende = true;
			}
		}


		
		
		//(4) Es gibt keinen vergroßernden Weg. Der jetzige Wert von d ist optimal.
		//    Ein Schnitt A(X;-X) mit c(X;-X) = d wird gebildet von genau denjenigen Kanten,
		//    bei denen entweder die Anfangsknoten oder die Endknoten markiert ist
		Double d = 0.0;
		for(DefaultWeightedEdge e: grah.edgesOf(quelle)){
			if((grah.getEdgeSource(e).equals(quelle)) && !grah.getEdgeTarget(e).equals(quelle))
					d += kantenFluss.get(e);
			else
				if((grah.getEdgeTarget(e).equals(quelle)) && !grah.getEdgeSource(e).equals(quelle))
				d -= kantenFluss.get(e);
		}
			
		laufzeit = System.currentTimeMillis() - startZeit;
		
		return d;
		
	}

	//(3) Vergroßerung der Flussstärke 
	private static void vergroesserungDerFlussstaerke(String ziel) {

		// (3)...Bei s beginnend ...
		Double deltaS = markiert.get(ziel).getDelta();

		Markierung markierteSenke = markiert.get(ziel);
		String vorgaenger = markierteSenke.getVorgaenger();
		// (3)...lässt sich anhand der Markierungen der gefundene vergroßernde Weg bis zum Knoten q ruckwarts durchlaufen. 
		while (markierteSenke.getVorgaenger() != null) {
			if (markierteSenke.vorzeichen) {
				assert(vorgaenger!=null);
				assert(ziel!=null);
				DefaultWeightedEdge edge = grah.getEdge(vorgaenger, ziel);
				Double oldFlow = kantenFluss.get(edge);
				// (3) ...Für jede Vorwartskante wird f(eij) um DELTAs erhoht...
				kantenFluss.put(edge, oldFlow + deltaS);
			} else {
				DefaultWeightedEdge edge = grah.getEdge(ziel, vorgaenger);
				Double oldFlow = kantenFluss.get(edge);
				// (3) .. und fur jede Ruckwartskante wird f(eji) um DELTAs	vermindert...
				kantenFluss.put(edge, oldFlow - deltaS);
			}
			ziel = vorgaenger;
			markierteSenke = markiert.get(vorgaenger);
			vorgaenger = markierteSenke.getVorgaenger();
		}
	}
}
