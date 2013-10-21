package aufgabe1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;

public class GraphVisualizer extends JApplet {
	
	/**
	 * JApplet requires this.
	 */
	private static final long serialVersionUID = -5501995581704290235L;
	
	/**
	 * The window size.
	 */
	private static final Dimension windowDimension = new Dimension(800, 600);
	
	/**
	 * The background color.
	 */
	private static final Color backgroundColor = Color.white;
	
	/**
	 * The graph to visualize.
	 */
	private ListenableGraph<String, DefaultEdge> graph = null;
	
	/**
	 * The JGraph adpater needed for the visualization.
	 */
	private JGraphModelAdapter<String, DefaultEdge> adapter = null;
	
	/**
	 * The constructor.
	 * @param graphInstance The graph to visualize.
	 */
	public GraphVisualizer(ListenableGraph<String, DefaultEdge> graphInstance) {
		this.graph = graphInstance;
		this.adapter = new JGraphModelAdapter<String, DefaultEdge>(graph);
		this.setupJGraph();
		this.positionVertices();
		this.setupFrame();
	}
	
	/**
	 * Setup JGraph.
	 */
	public void setupJGraph() {
    	JGraph jgraph = new JGraph(this.adapter);
    	jgraph.setPreferredSize(windowDimension);
    	jgraph.setBackground(backgroundColor);

    	this.getContentPane().add(jgraph);
    	this.resize(windowDimension);
	}
	
	/**
	 * Setup the JFrame where this applet will be displayed in.
	 */
	public void setupFrame() {
        JFrame frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setTitle("Graph Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
	}
	
	/**
	 * Randomly positions the vertices within the window's boundaries.
	 */
	public void positionVertices() {
		Random random = new Random();
		for (String vertex : this.graph.vertexSet()) {
			 // vertices must not be positioned below 50 on any axis
			int x = Math.max(50, random.nextInt(windowDimension.width - 100));
			int y = Math.max(50, random.nextInt(windowDimension.height - 100));
			this.positionVertexAt(vertex, x, y);
		}
	}
	
	/**
	 * Positions the vertex at the given position.
	 * 
	 * @param vertex The vertex to position.
	 * @param x The vertex's position on the x-axis.
	 * @param y The vertex's position on the y-axis.
	 */
	@SuppressWarnings("unchecked")
	private void positionVertexAt(Object vertex, int x, int y)
    {
        DefaultGraphCell cell = this.adapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        Rectangle2D bounds = GraphConstants.getBounds(attr);

        Rectangle2D newBounds =
            new Rectangle2D.Double(
                x,
                y,
                bounds.getWidth(),
                bounds.getHeight());

        GraphConstants.setBounds(attr, newBounds);

        // TODO: Clean up generics once JGraph goes generic
        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        this.adapter.edit(cellAttr, null, null, null);
    }
}