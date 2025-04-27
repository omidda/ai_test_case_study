package com.jamk.thesis.modules;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GraphUtilTest {

    // Tests for hasCycle
    @Test
    void testHasCycleInGraphWithCycle() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(3));
        graph.put(3, Arrays.asList(1)); // Cycle: 1 -> 2 -> 3 -> 1

        assertTrue(GraphUtil.hasCycle(graph));
    }

    @Test
    void testHasCycleInGraphWithoutCycle() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(3));
        graph.put(3, Collections.emptyList()); // No cycle

        assertFalse(GraphUtil.hasCycle(graph));
    }

    @Test
    void testHasCycleInEmptyGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        assertFalse(GraphUtil.hasCycle(graph));
    }

    @Test
    void testHasCycleInGraphWithSelfLoop() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(1)); // Self-loop

        assertTrue(GraphUtil.hasCycle(graph));
    }

    // Tests for topologicalSort
    @Test
    void testTopologicalSortValidDAG() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(4));
        graph.put(3, Arrays.asList(4));
        graph.put(4, Collections.emptyList());

        List<Integer> result = GraphUtil.topologicalSort(graph);
        assertEquals(Arrays.asList(1, 3, 2, 4), result); // One valid topological order
    }

    @Test
    void testTopologicalSortGraphWithCycleThrowsException() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(3));
        graph.put(3, Arrays.asList(1)); // Cycle: 1 -> 2 -> 3 -> 1

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> GraphUtil.topologicalSort(graph));
        assertEquals("Graph contains a cycle. Topological sort not possible.", exception.getMessage());
    }

    @Test
    void testTopologicalSortEmptyGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        List<Integer> result = GraphUtil.topologicalSort(graph);
        assertTrue(result.isEmpty());
    }

    // Tests for isBipartite
    @Test
    void testIsBipartiteForBipartiteGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(1, 3));
        graph.put(3, Arrays.asList(2));

        assertTrue(GraphUtil.isBipartite(graph));
    }

    @Test
    void testIsBipartiteForNonBipartiteGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(1, 3));
        graph.put(3, Arrays.asList(1, 2)); // Triangle graph (odd cycle)

        assertFalse(GraphUtil.isBipartite(graph));
    }

    @Test
    void testIsBipartiteForEmptyGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        assertTrue(GraphUtil.isBipartite(graph)); // An empty graph is trivially bipartite
    }

    @Test
    void testIsBipartiteForSingleNodeGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Collections.emptyList());

        assertTrue(GraphUtil.isBipartite(graph)); // A single node is trivially bipartite
    }

    // Tests for shortestPath
    @Test
    void testShortestPathBetweenTwoNodes() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(4));
        graph.put(3, Arrays.asList(4));
        graph.put(4, Collections.emptyList());

        List<Integer> path = GraphUtil.shortestPath(graph, 1, 4);
        assertEquals(Arrays.asList(1, 2, 4), path); // One valid shortest path
    }

    @Test
    void testShortestPathSameStartAndEndNode() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2, 3));

        List<Integer> path = GraphUtil.shortestPath(graph, 1, 1);
        assertEquals(Collections.singletonList(1), path);
    }

    @Test
    void testShortestPathNoPathExists() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(2, Collections.emptyList());
        graph.put(3, Collections.emptyList()); // Node 3 is disconnected

        List<Integer> path = GraphUtil.shortestPath(graph, 1, 3);
        assertTrue(path.isEmpty());
    }

    @Test
    void testShortestPathInEmptyGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        List<Integer> path = GraphUtil.shortestPath(graph, 1, 2);
        assertTrue(path.isEmpty());
    }
}