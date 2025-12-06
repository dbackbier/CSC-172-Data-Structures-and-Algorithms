// Dane Backbier - dbackbie - dbackbie@u.rochester.edu - Project 3 - Street Mapping

import java.util.*;

public class StreetMap {
    static class NodeDist implements Comparable<NodeDist> {
        String id;
        double dist;

        NodeDist(String id, double dist) {
            this.id = id;
            this.dist = dist;
        }

        @Override
        public int compareTo(NodeDist other) {
            return Double.compare(this.dist, other.dist);
        }

        public String id() {
            return id;
        }

        public double dist() {
            return dist;
        }
    }

    public static class Pair<L, R> {
        private final L left;
        private final R right;

        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public L getLeft() { return left; }

        public R getRight() { return right; }
    }

    public static double getDistance(URGraphNode fromNode, URGraphNode toNode) {
        double earthRadius = 3958.8; // in miles

        double fromLat = Math.toRadians(fromNode.latitude());
        double fromLong = Math.toRadians(fromNode.longitude());
        double toLat = Math.toRadians(toNode.latitude());
        double toLong = Math.toRadians(toNode.longitude());
        
        double deltaLat = toLat - fromLat;
        double deltaLong = toLong - fromLong;

        double hav = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.cos(fromLat) * Math.cos(toLat) * Math.sin(deltaLong / 2) * Math.sin(deltaLong / 2);
        double theta = 2 * Math.atan2(Math.sqrt(hav), Math.sqrt(1 - hav));

        return earthRadius * theta; // d = r * theta
    } // constant time, O(1)

    public static boolean isConnected(URGraph graph, String start, String target) {
        HashSet<String> seen = new HashSet<>();
        Queue<String> q = new LinkedList<>();
        q.add(start);
        seen.add(start);

        while (!q.isEmpty()) {
            String u = q.poll();
            if (u.equals(target)) { return true; }

            List<UREdge> adj = graph.adjacencyList.get(u);
            if (adj == null) continue;

            for (UREdge e : adj) {
                String v = e.fromNode().equals(u) ? e.toNode() : e.fromNode();
                if (!seen.contains(v)) {
                    seen.add(v);
                    q.add(v);
                }
            }
        }
        return false;
    }

    /*
    for each Vertex v {
    v.dist = inf;
    v.known = false;
    }

    while (there's an unknown vertex) {
        v = delMin(); // get the next one off the queue
        v.known = true;
        for each Vertex w adjacent to v {
            if (!w.known) {
                if (v.dist + dist_v2w < w.dist) {
                    w.dist = v.dist + dist_v2w;
                    enqueue(w);
                }
            }
        }
    }
    */
    public static Pair<ArrayList<URGraphNode>, ArrayList<String>> dijkstras(String startID, String endID, URGraph graph) {
        URGraphNode[] nodes = graph.nodes();
        UREdge[] edges = graph.edges();

        HashMap<String, Double> dist = new HashMap<>();
        HashSet<String> visited = new HashSet<>();
        HashMap<String, String> prev = new HashMap<>();

        for (URGraphNode n : nodes) {
            dist.put(n.id(), Double.POSITIVE_INFINITY);
            prev.put(n.id(), null);
        } // O(V)
        dist.put(startID, 0.0);

        minHeap<NodeDist> pq = new minHeap<>();
        pq.insert(new NodeDist(startID, 0.0));

        System.out.println("Nodes: " + nodes.length + ". Edges: " + edges.length + ".");
        while (!pq.isEmpty()) {
            NodeDist nd = pq.deleteMin();
            String u = nd.id();

            if (nd.dist > dist.get(u)) { continue; }

            if (visited.contains(u)) { continue; }
            visited.add(u);
            
            if (u.equals(endID)) { break; }

            List<UREdge> adj = graph.adjacencyList.get(u); // one average O(1) lookup
            if (adj == null) { continue; }

            for (UREdge edge : adj) {
                String v = edge.fromNode().equals(u) ? edge.toNode() : edge.fromNode();

                if (visited.contains(v)) { continue; }

                double newDist = dist.get(u) + edge.weight();
                if (newDist < dist.get(v)) { // found a shorter path to v
                    dist.put(v, newDist);
                    prev.put(v, u);
                    pq.insert(new NodeDist(v, newDist));
                    // System.out.println("PQ added: " + v + " with dist " + newDist); // debugging
                }
            }
        }
        ArrayList<URGraphNode> path = new ArrayList<>();
        ArrayList<String> pathIDs = new ArrayList<>();
        if (dist.get(endID) == Double.POSITIVE_INFINITY) {
            System.out.println("No path found.");
            return new Pair<>(path, pathIDs);
        }
        String curr = endID;
        while (curr != null) {
            path.add(0, graph.getNode(curr));
            pathIDs.add(curr);
            curr = prev.get(curr);
        }
        return new Pair<>(path, pathIDs);
    } // O((V + E) log V) runtime
    
    public static void main(String[] args) {
        if (args.length <= 1) {
            throw new IllegalArgumentException("Please provide a file name, should be of the form: java StreetMap map.txt [--show] [--directions startIntersection endIntersection]");
        }
        boolean show = false;
        boolean directions = false;
        String startID = null;
        String endID = null;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--show")) {
                show = true;
            } else if (args[i].equals("--directions")) {
                directions = true;
                if (i + 2 < args.length) {
                    startID = args[i + 1];
                    endID = args[i + 2];
                } else {
                    throw new IllegalArgumentException("Incorrect --directions arguments, must have a startID and endID.");
                }
            }
        } // O(n), n is length of args (very small)

        URGraph graph = new URGraph(args[0]);
        if (!directions && show) {
            GraphGUI gui = new GraphGUI(graph);
            gui.setVisible(true);
        } else if (isConnected(graph, startID, endID)) {
            if (show) {
                if (directions) {
                    // directions
                    Pair<ArrayList<URGraphNode>, ArrayList<String>> p = dijkstras(startID, endID, graph);
                    ArrayList<URGraphNode> path = p.getLeft();
                    ArrayList<String> pathIDs = p.getRight();
                    if (!path.isEmpty()) {
                        System.out.println("\n Directions from " + startID + " to " + endID + ":");
                        double totalDist = 0;
                        for (int i = 0; i < path.size() - 1; i++) {
                            URGraphNode fromNode = path.get(i);
                            URGraphNode toNode = path.get(i + 1);
                            totalDist += getDistance(fromNode, toNode);
                            System.out.println("From " + fromNode.id() + " to " + toNode.id());
                        } // O(n), n is length of path
                        System.out.println("Last node: " + path.get(path.size() - 1).id());
                        System.out.println("Total distance: " + totalDist + " miles.");
                        // show
                        GraphGUI gui = new GraphGUI(graph, path, pathIDs);
                        gui.setVisible(true);
                    }
                }
            } else {
                if (directions) {
                    Pair<ArrayList<URGraphNode>, ArrayList<String>> p = dijkstras(startID, endID, graph);
                    ArrayList<URGraphNode> path = p.getLeft();
                    if (!path.isEmpty()) {
                        double totalDist = 0;
                        for (int i = 0; i < path.size() - 1; i++) {
                            URGraphNode fromNode = path.get(i);
                            URGraphNode toNode = path.get(i + 1);
                            totalDist += getDistance(fromNode, toNode);
                            System.out.println("From " + fromNode.id() + " to " + toNode.id());
                        } // O(n), n is length of path
                        System.out.println("Last node: " + path.get(path.size() - 1).id());
                        System.out.println("Total distance: " + totalDist + " miles.");
                        // no GUI
                    }
                }
            }
        } else {
            System.out.println(startID + " and " + endID + " are not connected.");
        }
    }
}
