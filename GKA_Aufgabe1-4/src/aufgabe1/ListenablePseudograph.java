package aufgabe1;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.Pseudograph;

public class ListenablePseudograph<V, E>
	extends DefaultListenableGraph<V, E>
	implements UndirectedGraph<V, E>
{
	
	private static final long serialVersionUID = 1L;
	
	public ListenablePseudograph(Class<E> edgeClass) {
		super(new Pseudograph<V, E>(edgeClass));
	}
}
