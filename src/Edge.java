public class Edge implements Comparable<Edge> {
    public static Node source;
    public static Node destination;
    public static double weight;

    Edge(Node s, Node d) {
        source = s;
        destination = d;
        Double weight = 1.0;
    }

    @Override
    public int compareTo(Edge o) {
        if (this.weight > o.weight) {
            return 1;
        } else return -1;
    }
}

