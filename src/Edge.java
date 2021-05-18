public class Edge implements Comparable<Edge> {
    Node source;
    Node destination;
    double weight;

    Edge(Node s, Node d, double w) {
        source = s;
        destination = d;
        double weight = w;
    }

    @Override
    public int compareTo(Edge o) {
        if (this.weight > o.weight) {
            return 1;
        } else return -1;
    }

    @Override
    public String toString() {
        return String.format("(%s connects to %s, %f)", source.name, destination.name, weight);
    }
}

