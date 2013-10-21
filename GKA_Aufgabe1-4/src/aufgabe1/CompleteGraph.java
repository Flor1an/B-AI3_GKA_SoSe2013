package aufgabe1;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;

public class CompleteGraph<V, E>
	extends AbstractBaseGraph<V, E>
	implements UndirectedGraph<V, E>
{

    public CompleteGraph(Class<? extends E> edgeClass)
    {
        super(new ClassBasedEdgeFactory<V, E>(edgeClass), false, false);
    }

	/**
	 * 
	 */
	private static final long serialVersionUID = -6738837594951673103L;
	
	@Override
	public boolean addVertex(V v) {
		boolean success = super.addVertex(v);
		
		if (success) {
			for (V vertex : super.vertexSet()) {
				if (vertex != v)
					super.addEdge(v, vertex);
			}
		}
		
		return success;
	}
}
