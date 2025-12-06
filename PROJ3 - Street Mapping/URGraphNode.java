// Dane Backbier - dbackbie - dbackbie@u.rochester.edu - Project 3 - Street Mapping

public class URGraphNode {
    final private String id;
    final private double latitude;
    final private double longitude;

    public URGraphNode(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String id() {
        return id;
    }

    public double latitude() {
        return latitude;
    }

    public double longitude() {
        return longitude;
    }
}
