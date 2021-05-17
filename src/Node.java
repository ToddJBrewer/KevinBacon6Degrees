import java.util.LinkedList;

public class Node {
    public static boolean visited;
    public static int n;
    public static String name;
    public static LinkedList<Integer> edges;

    Node(int num, String Name) {
        n = num;
        name = Name;
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
