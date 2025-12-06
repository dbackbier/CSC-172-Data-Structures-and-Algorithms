// Dane Backbier - dbackbie - dbackbie@u.rochester.edu - Project 3 - Street Mapping

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;

public class GraphGUI extends JFrame {

    public static final int HEIGHT = 800;
    public static final int WIDTH = 1200;
    public static final int BIAS = 75;
    private static final int X_OFFSET = 75;

    final private URGraph graph;
    private double minLat;
    private double maxLat;
    private double minLong;
    private double maxLong;
    final private boolean show;
    private ArrayList<URGraphNode> path;
    private ArrayList<String> pathIDs;
    private Set<String> pathIDSet;

    public GraphGUI(URGraph graph) {
        setTitle("Plain Graph");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.graph = graph;
        this.minLat = Double.POSITIVE_INFINITY;
        this.maxLat = Double.NEGATIVE_INFINITY;
        this.minLong = Double.POSITIVE_INFINITY;
        this.maxLong = Double.NEGATIVE_INFINITY;
        this.show = false;
        for (URGraphNode node : graph.nodes()) {
            if (node.latitude() < minLat) {
                minLat = node.latitude();
            }
            if (node.latitude() > maxLat) {
                maxLat = node.latitude();
            }
            if (node.longitude() < minLong) {
                minLong = node.longitude();
            }
            if (node.longitude() > maxLong) {
                maxLong = node.longitude();
            }
        }
    }

    public GraphGUI(URGraph graph, ArrayList<URGraphNode> path, ArrayList<String> pathIDs) {
        setTitle("Graph Result With Path");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.graph = graph;
        this.minLat = Double.POSITIVE_INFINITY;
        this.maxLat = Double.NEGATIVE_INFINITY;
        this.minLong = Double.POSITIVE_INFINITY;
        this.maxLong = Double.NEGATIVE_INFINITY;
        this.show = true;
        this.path = path;
        this.pathIDs = pathIDs;
        this.pathIDSet = new HashSet<>(pathIDs);
        for (URGraphNode node : graph.nodes()) {
            if (node.latitude() < minLat) {
                minLat = node.latitude();
            }
            if (node.latitude() > maxLat) {
                maxLat = node.latitude();
            }
            if (node.longitude() < minLong) {
                minLong = node.longitude();
            }
            if (node.longitude() > maxLong) {
                maxLong = node.longitude();
            }
        }
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        UREdge[] edges = graph.edges();
        double zoom = 0.9 * Math.min(getWidth() / (maxLong - minLong), getHeight() / (maxLat - minLat));
        // pass one: color all edges black
        graphics.setColor(Color.BLACK);
        for (UREdge edge : edges) {
            URGraphNode fromNode = graph.getNode(edge.fromNode());
            URGraphNode toNode = graph.getNode(edge.toNode());

            int x1 = (int) ((fromNode.longitude() - minLong) * zoom) + X_OFFSET;
            int y1 = getHeight() - (int) ((fromNode.latitude() - minLat) * zoom) - BIAS;
            int x2 = (int) ((toNode.longitude() - minLong) * zoom) + X_OFFSET;
            int y2 = getHeight() - (int) ((toNode.latitude() - minLat) * zoom) - BIAS;

            graphics.drawLine(x1, y1, x2, y2);
        }

        if (show) {
            // pass two: color path edges red
            graphics.setColor(Color.RED);
            Graphics2D g2 = (Graphics2D) graphics;
            g2.setStroke(new BasicStroke(2));
            for (UREdge edge : edges) {
                if ((pathIDSet.contains(edge.fromNode()) && pathIDSet.contains(edge.toNode()))) {
                    URGraphNode fromNode = graph.getNode(edge.fromNode());
                    URGraphNode toNode = graph.getNode(edge.toNode());

                    int x1 = (int) ((fromNode.longitude() - minLong) * zoom) + X_OFFSET;
                    int y1 = getHeight() - (int) ((fromNode.latitude() - minLat) * zoom) - BIAS;
                    int x2 = (int) ((toNode.longitude() - minLong) * zoom) + X_OFFSET;
                    int y2 = getHeight() - (int) ((toNode.latitude() - minLat) * zoom) - BIAS;

                    graphics.drawLine(x1, y1, x2, y2);
                }
            }
        }
    } // O(E) runtime
}