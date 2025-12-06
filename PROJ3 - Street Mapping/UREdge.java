// Dane Backbier - dbackbie - dbackbie@u.rochester.edu - Project 3 - Street Mapping

public class UREdge {
    final private String id;
    final private double weight;
    final private String fromNode;
    final private String toNode;

    public UREdge(String id, double weight, String fromNode, String toNode) {
        this.id = id;
        this.weight = weight;
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    public String id() {
        return id;
    }

    public double weight() {
        return weight;
    }

    public String fromNode() {
        return fromNode;
    }
    
    public String toNode() {
        return toNode;
    }
}
