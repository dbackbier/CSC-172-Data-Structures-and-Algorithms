// Dane Backbier - dbackbie - dbackbie@u.rochester.edu - Project 3 - Street Mapping

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class URGraph {
    URGraphNode[] nodes;
    UREdge[] edges;

    // maps
    private HashMap<String, URGraphNode> nodeMap; // id -> node
    private HashMap<String, UREdge> edgeMap; // id -> edge
    public HashMap<String, List<UREdge>> adjacencyList; // id -> edges
    private HashMap<String, Map<String, UREdge>> adjacencyMatrix; // id -> neighboring nodes

    // constructors
    public URGraph(URGraphNode[] nodes, UREdge[] edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.initMaps();
    }

    public URGraph() {
        this.nodes = null;
        this.edges = null;
        this.initMaps();
    }

    public URGraph(String file) {
        this();
        this.readFromFile(file);
    }

    public void initMaps() {
        this.nodeMap = new HashMap<>();
        this.edgeMap = new HashMap<>();
        this.adjacencyList = new HashMap<>();
        this.adjacencyMatrix = new HashMap<>();
    }

    public void readFromFile(String file) {
        try {
            FileReader fReader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(fReader);
            String line;
            List<URGraphNode> nodeList = new LinkedList<>();
            List<UREdge> edgeList = new LinkedList<>();
            while ((line = bReader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts[0].equals("i")) {
                    String id = parts[1];
                    double lat = Double.parseDouble(parts[2]);
                    double lon = Double.parseDouble(parts[3]);
                    URGraphNode node = new URGraphNode(id, lat, lon);
                    nodeList.add(node);
                    this.nodeMap.put(id, node);
                } else if (parts[0].equals("r")) {
                    String id = parts[1];
                    String fromNodeID = parts[2];
                    String toNodeID = parts[3];
                    UREdge edge = new UREdge(id, StreetMap.getDistance(nodeMap.get(fromNodeID), nodeMap.get(toNodeID)), fromNodeID, toNodeID);
                    edgeList.add(edge);
                    this.edgeMap.put(id, edge);
                    // add nodes & edge to adjacency list
                    if (!adjacencyList.containsKey(fromNodeID)) {
                        adjacencyList.put(fromNodeID, new LinkedList<>());
                    }
                    adjacencyList.get(fromNodeID).add(edge);
                    if (!adjacencyList.containsKey(toNodeID)) {
                        adjacencyList.put(toNodeID, new LinkedList<>());
                    }
                    adjacencyList.get(toNodeID).add(edge);
                    // add nodes & edge to adjacency matrix
                    if (!adjacencyMatrix.containsKey(fromNodeID)) {
                        adjacencyMatrix.put(fromNodeID, new HashMap<>());
                    }
                    adjacencyMatrix.get(fromNodeID).put(toNodeID, edge);
                    if (!adjacencyMatrix.containsKey(toNodeID)) {
                        adjacencyMatrix.put(toNodeID, new HashMap<>());
                    }
                    adjacencyMatrix.get(toNodeID).put(fromNodeID, edge);
                } else {
                    System.out.println("Unknown type: " + parts[0]);
                }
            }
            fReader.close();
            bReader.close();
            this.nodes = nodeList.toArray(new URGraphNode[0]);
            this.edges = edgeList.toArray(new UREdge[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public URGraphNode getNode(String id) {
        return nodeMap.get(id);
    }

    public URGraphNode[] nodes() {
        return nodes;
    }

    public UREdge[] edges() {
        return edges;
    }
}