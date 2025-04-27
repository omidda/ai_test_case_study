package com.jamk.thesis.modules;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GraphUtilTest {

    @Test
    public void testHasCycleInEmptyGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        assertFalse(GraphUtil.hasCycle(graph));
    }

    @Test
    public void testHasCycleInGraphWithCycle() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(3));
        graph.put(3, Arrays.asList(1)); // Cycle here
        assertTrue(GraphUtil.hasCycle(graph));
    }

    @Test
    public void testHasCycleInGraphWithoutCycle() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(3));
        graph.put(3, new ArrayList<>());
        assertFalse(GraphUtil.hasCycle(graph));
    }

    @Test
    public void testHasCycleInGraphWithSelfLoop() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(1)); // Self-loop
        assertTrue(GraphUtil.hasCycle(graph));
    }

    @Test
    public void testTopologicalSortInEmptyGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        List<Integer> sorted = GraphUtil.topologicalSort(graph);
        assertTrue(sorted.isEmpty());
    }

    @Test
    public void testTopologicalSortInGraphWithDisconnectedComponents() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(3, Arrays.asList(4));
        List<Integer> sorted = GraphUtil.topologicalSort(graph);
        assertTrue(sorted.indexOf(1) < sorted.indexOf(2));
        assertTrue(sorted.indexOf(3) < sorted.indexOf(4));
    }

    @Test
    public void testTopologicalSortInGraphWithCycle() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(3));
        graph.put(3, Arrays.asList(1)); // Cycle here
        assertThrows(IllegalArgumentException.class, () -> {
            GraphUtil.topologicalSort(graph);
        });
    }

    @Test
    public void testIsBipartiteInEmptyGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        assertTrue(GraphUtil.isBipartite(graph));
    }

    @Test
    public void testIsBipartiteInBipartiteGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(1, 3));
        graph.put(3, Arrays.asList(2));
        assertTrue(GraphUtil.isBipartite(graph));
    }

    @Test
    public void testIsBipartiteInNonBipartiteGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(1, 3));
        graph.put(3, Arrays.asList(1, 2));
        assertFalse(GraphUtil.isBipartite(graph));
    }

    @Test
    public void testShortestPathInEmptyGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        List<Integer> path = GraphUtil.shortestPath(graph, 1, 2);
        assertTrue(path.isEmpty());
    }

    @Test
    public void testShortestPathInGraphWithPath() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(3));
        graph.put(3, Arrays.asList(4));
        List<Integer> path = GraphUtil.shortestPath(graph, 1, 4);
        assertEquals(Arrays.asList(1, 2, 3, 4), path);
    }

    @Test
    public void testShortestPathInGraphWithoutPath() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(3, Arrays.asList(4));
        List<Integer> path = GraphUtil.shortestPath(graph, 1, 4);
        assertTrue(path.isEmpty());
    }

    @Test
    public void testShortestPathStartEqualsEnd() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        List<Integer> path = GraphUtil.shortestPath(graph, 1, 1);
        assertEquals(Arrays.asList(1), path);
    }
} 