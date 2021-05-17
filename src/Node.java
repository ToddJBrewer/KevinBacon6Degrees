import java.util.LinkedList;

public class Node {
    public static boolean visited;
    public static int n;
    public static String name;
    //boolean visited;
    public static LinkedList<Edge> edges;

    Node(int n, String name) {
        this.n = n;
        this.name = name;
        visited = false;
        edges = new LinkedList<>();
    }

    public static boolean isVisited() {
        return visited;
    }

    public static void visit() {
        visited = true;
    }


    @Override
    public String toString() {
        return "Node{" +
                "n=" + n +
                ", name='" + name + '\'' +
                ", edges=" + edges +
                '}';
    }
}
