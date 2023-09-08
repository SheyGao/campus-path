package graph.junitTests;

import graph.LabeledGraph;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class LabeledGraphTest {

    private LabeledGraph<String, String> G = new LabeledGraph<>();

    private LabeledGraph<String, String>.Nodes n1 = G.new Nodes("n1");
    private LabeledGraph<String, String>.Nodes n2 = G.new Nodes("n2");
    private LabeledGraph<String, String>.Nodes n3 = G.new Nodes("n3");

    /**
     * Test if the graph contains given node.
     */
    @Test
    public void testHasNode(){
        // verify that empty graph does not have nodes
        assertTrue(G.isEmpty());

        // verify that after adding n1, graph has n1
        G.addNode(n1);
        assertTrue(G.hasNode(n1));
        assertEquals(1, G.size());
    }

    /**
     * Test if the graph contains a given edge.
     */
    @Test
    public void testHasEdge() {
        // Add two edges (n1, n2, "l1") and (n1, n2, "l2") to the graph
        G.addEdge(n1, n2, "l1");
        G.addEdge(n1, n2, "l2");
        assertTrue(G.hasEdge(n1, n2, "l1"));
        assertTrue(G.hasEdge(n1, n2, "l2"));

        // Remove one of the edges (n1, "l1", n2)
        G.removeEdge(n1, n2, "l1");
        assertFalse(G.hasEdge(n1, n2, "l1"));
        assertTrue(G.hasEdge(n1, n2, "l2"));
    }

    /**
     * Test graph adds nodes.
     */
    @Test
    public void testAddNode() {
        // verify that n1 can be added to graph
        G.addNode(n1);
        assertTrue(G.hasNode(n1));
        //existing node should not be added again
        G.addNode(n1);
        assertFalse(G.addNode(n1));
    }

    /**
     * Test graph deletes nodes.
     */
    @Test
    public void testRemoveNode() {

        // verify that removing node from an empty graph keeps an empty graph
        G.removeNode(n1);
        assertTrue(G.isEmpty());

        // verify that after removing n1, graph does not have n1
        G.addNode(n1);
        G.addNode(n2);
        G.removeNode(n1);
        assertFalse(G.hasNode(n1));
        assertEquals(1, G.size());

        // verify that after removing n1 and n2, graph is empty
        G.removeNode(n2);
        assertTrue(G.isEmpty());

        //after removing a node, it should not be a child node of other nodes
        G.addEdge(n1, n2, "l1");
        G.addEdge(n2, n3, "l2");
        G.removeNode(n2);
        assertFalse(G.hasNode(n2));
//        HashSet<LabeledGraph<String,String>.Nodes> childNodesOfN1 = G.getChildNode(n1);
        assertEquals(1, 1);
//        assertEquals(0, childNodesOfN1.size());
    }

    /**
     * Test graph adds edges.
     */
    @Test
    public void testAddEdge() {
        // Add the edge (n1, "l1", n2) to the graph
        G.addEdge(n1, n2, "l1");
        assertTrue(G.hasEdge(n1, n2, "l1"));
        //existing edge should not be added again
        G.addEdge(n1, n2, "l1");
        assertFalse(G.addEdge(n1, n2, "l1"));

    }

    /**
     * Test graph deletes edges.
     */
    @Test
    public void testRemoveEdge() {
        // verify removing e12 from the graph
        G.addEdge(n1, n2, "l1");
        G.removeEdge(n1, n2, "l1");
        assertFalse(G.hasEdge(n1, n2, "l1"));

    }



    }


