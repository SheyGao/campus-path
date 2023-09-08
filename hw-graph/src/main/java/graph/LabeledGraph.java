package graph;

import java.util.*;

/**
 * A class that implements a directed labeled graph.
 * The nodes are uniquely identified by their data contents.
 * The edges are pointing from parent node to a child node with
 * a label on it.
 * <p>
 * Nodes are uniquely identified by their data contents:
 * that is, no two nodes store entirely equal data.
 */

public class LabeledGraph<T, E> {
    // Representation Invariant:
    // graph != null
    // all the node in graph should not be null
    // the edge of a given node should not be null.

    // Abstraction Function:
    // AF(this) = directed labeled graph such that it satisfies
    // graph = {
    //    "A": {
    //        "B": {"L11", "L12", ...},
    //        "C": {"L21", "L22", ...}
    //    }
    // }
    // where "A" is a parent node,
    // {"L11", "L12", ...} and {"L21", "L22", ...} are sets containing the labels of the outgoing edges from "A" to "B"
    // and "A" to "C", respectively.


    public class Nodes {
        public final T data;

        /**
         * Create a new Nodes object with the given data contents
         *
         * @param data the data contents stored in the node
         * @spec.requires data cannot be null
         * @spec.effects create a new node object
         */
        public Nodes(T data) {
            this.data = data;
        }

        /**
         * Return the data contents of this
         *
         * @return the data stored in this
         */
        public T getData() {
            return data;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            LabeledGraph<?, ?>.Nodes other = (LabeledGraph<?, ?>.Nodes) obj;
            return Objects.equals(data, other.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);

        }

        @Override
        public String toString() {
            return data.toString();
        }


    }

    public class Edges {
        private Nodes child;
        private E label;

        /**
         * Create a new Edges object with the given parent node, child node, and a label.
         *
         * @param label the label value of this
         * @param child the node this points to
         * @spec.requires parent, child, and label cannot be null
         * @spec.effects create a new Edge object
         */
        public Edges(Nodes child, E label) {
            this.child = child;
            this.label = label;
        }

        /**
         * Return the label node of this
         *
         * @return String label of this
         */
        public E getLabel() {
            return label;
        }

        /**
         * Return the child node of this
         *
         * @return Node child of this
         */
        public Nodes getChild() {
            return child;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            LabeledGraph<?, ?>.Edges other = (LabeledGraph<?, ?>.Edges) obj;
            return Objects.equals(label, other.label) && Objects.equals(child, other.child);
        }

        @Override
        public int hashCode() {
            return Objects.hash(child, label);
        }
    }

    /**
     * Create a new LabeledGraph object, an empty graph
     *
     * @spec.effects create an empty map
     */


    private final Map<Nodes, HashSet<Edges>> graph;

    public LabeledGraph() {
        graph = new HashMap<>();
        checkRep();
    }

    /**
     * Return all the Nodes of the graph
     *
     * @return all the Nodes of graph
     */
    public HashSet<Nodes> getNodes() {
        HashSet<Nodes> nodes = new HashSet<>();
        for (Nodes n : graph.keySet()) {
            nodes.add(n);
        }
        return nodes;
    }


    /**
     * Return all the child Nodes of a given parent node
     *
     * @param parent a Nodes object
     * @return all the dest Nodes of node
     * @spec.requires node should not be null
     */
    public HashSet<Nodes> getChildNode(Nodes parent) {
        HashSet<Edges> childLabel = graph.get(parent);
        HashSet<Nodes> childNode = new HashSet<Nodes>();
        int i = 0;
        for (Edges n : childLabel) {
            childNode.add(n.getChild());
            i++;
        }
        return childNode;
    }

    /**
     * Return edges associated with given parent and child
     *
     * @param parent a Nodes object
     * @return all the edges of the given parent and child node
     * @spec.requires parent and child cannot be null
     */
    public HashSet<Edges> getEdges(Nodes parent) {
        assert parent != null;

        HashSet<Edges> childLabel = graph.get(parent);

        HashSet<Edges> edges = new HashSet<>();
        for (Edges edge : childLabel) {
            edges.add(edge);

        }
        return edges;
    }


    /**
     * add a given new node to the graph if it doesn't exist yet
     *
     * @param newNode a Nodes object
     * @return true if addNode successfully
     * @spec.requires newNode should not be null
     * @spec.modifies this
     * @spec.effects this is updated with adding a newNode
     */
    public boolean addNode(Nodes newNode) {
        assert newNode != null;
        checkRep();
        boolean success = false;

        if (hasNode(newNode)) {
            success = false;
        }

        if (!hasNode(newNode)) {
            graph.put(newNode, new HashSet<Edges>());
            success = true;
        }
        checkRep();
        return success;
    }

    /**
     * remove a given node to the graph
     *
     * @param node a Nodes object
     * @return true if removeNode successfully
     * @spec.requires node should not be null
     * @spec.modifies this
     * @spec.effects this is updated with deleting a node
     */
    public boolean removeNode(Nodes node) {
        assert node != null;
        checkRep();

        graph.remove(node);
        // remove it as a child node
        for (Nodes parent : graph.keySet()) {
            graph.get(parent).remove(node);
        }
        checkRep();
        return true;

    }

    /**
     * add a new edge to the graph if it doesn't exist yet
     *
     * @param parent the node this starts from
     * @param child  the node this points to
     * @param label  the value on the edge
     * @return true if addEdge successfully
     * @spec.requires parent, child, label should not be null
     * @spec.modifies this
     * @spec.effects this is updated with adding a new edge
     */
    public boolean addEdge(Nodes parent, Nodes child, E label) {
        assert parent != null;
        assert label != null;
        assert child != null;
        checkRep();

        if (hasEdge(parent, child, label)) {
            return false;
        }

        boolean success = false;

        if (graph.containsKey(parent)) {
            HashSet<Edges> childLabel = graph.get(parent);
            Edges newEdge = new Edges(child, label);
            success = childLabel.add(newEdge);
        } else {
            HashSet<Edges> childLabel = new HashSet<>();
            Edges newEdge = new Edges(child, label);
            success = childLabel.add(newEdge);
            graph.put(parent, childLabel);
        }

        checkRep();
        return success;
    }

    /**
     * remove a edge
     *
     * @param parent the node this starts from
     * @param child  the node this points to
     * @param label  the value of this edge
     * @return true if removeEdge successfully
     * @spec.requires parent, child, label should not be null
     * @spec.modifies this
     * @spec.effects this is updated with removing a given edge
     */
    public boolean removeEdge(Nodes parent, Nodes child, E label) {
        assert parent != null;
        assert child != null;
        assert label != null;
        boolean success = false;

        Edges e = new Edges(child, label);
        if (graph.containsKey(parent)) {
            HashSet<Edges> childEdges = graph.get(parent);
            if (childEdges.contains(e)) {
                childEdges.remove(e);
                success = true;
            }
        }
        checkRep();
        return success;
    }

    /**
     * Return the label of the given parent node and child node
     *
     * @param parent a Nodes object
     * @param child a Nodes object
     * @return label
     * @spec.requires parent, child cannot be null
     */
    public double getEdgeWeight(Nodes parent, Nodes child) {
        assert parent != null;
        assert child != null;

        HashSet<Edges> childEdges = graph.get(parent);
        for (Edges e : childEdges) {
            if (e.getChild().equals(child)) {
                return (double) e.getLabel();
            }
        }
        return Double.POSITIVE_INFINITY;
    }


    /**
     * Return true if node is in the graph
     *
     * @param node a Nodes object
     * @return true if node is in this
     * @spec.requires node cannot be null
     */
    public boolean hasNode(Nodes node) {
        return graph.containsKey(node);
    }

    /**
     * Return true if edge is in the graph
     *
     * @param parent a Nodes object
     * @param child  a Nodes object
     * @param label  a Nodes object
     * @return true if edge is in this
     * @spec.requires parent, child, and label cannot be null
     */
    public boolean hasEdge(Nodes parent, Nodes child, E label) {
        assert parent != null;
        assert child != null;
        assert label != null;
        checkRep();

        if (graph.containsKey(parent)) {
            HashSet<Edges> childEdges = graph.get(parent);
            for (Edges e : childEdges) {
                if (e.getChild().equals(child) && e.getLabel().equals(label)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return true if the graph is empty
     *
     * @return true if graph is empty
     */
    public boolean isEmpty() {
        return graph.isEmpty();
    }

    /**
     * Return number of parent node in the graph
     *
     * @return number of parent nodes
     */
    public int size() {
        return graph.size();
    }

    private void checkRep() {
        if (graph == null) {
            throw new RuntimeException("The graph should not be null.");
        }

        for (Nodes node : graph.keySet()) {
            if (node == null) {
                throw new RuntimeException("Node should not be null.");
            }
            for (Edges e : graph.get(node)) {
                if (e == null) {
                    throw new RuntimeException("Edge cannot be null.");
                }
            }
        }

    }

}




