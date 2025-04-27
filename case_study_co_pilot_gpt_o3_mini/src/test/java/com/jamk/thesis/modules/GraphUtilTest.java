package com.jamk.thesis.modules;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.*;


public class GraphUtilTest {

    // Tests for hasCycle
    @Test
    void testHasCycle_EmptyGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        assertThat(GraphUtil.hasCycle(graph)).isFalse();
    }

    @Test
    void testHasCycle_NoCycle() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(3));
        graph.put(3, Arrays.asList(4));
        graph.put(4, new ArrayList<>());
        assertThat(GraphUtil.hasCycle(graph)).isFalse();
    }

    @Test
    void testHasCycle_WithCycle() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(3));
        graph.put(3, Arrays.asList(1)); // cycle here
        assertThat(GraphUtil.hasCycle(graph)).isTrue();
    }

    @Test
    void testHasCycle_WithSelfLoop() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(1)); // self-loop
        assertThat(GraphUtil.hasCycle(graph)).isTrue();
    }

    // Tests for topologicalSort
    @Test
    void testTopologicalSort_ValidDAG() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        // Create a DAG: 5->2, 5->0, 4->0, 4->1, 2->3, 3->1
        graph.put(5, Arrays.asList(2, 0));
        graph.put(4, Arrays.asList(0, 1));
        graph.put(2, Arrays.asList(3));
        graph.put(3, Arrays.asList(1));
        // Ensure isolated nodes are included as keys even if no outgoing edges.
        graph.put(0, new ArrayList<>());
        graph.put(1, new ArrayList<>());
        
        List<Integer> sorted = GraphUtil.topologicalSort(graph);
        // Verify that for every edge u -> v, u appears before v in sorted order.
        Map<Integer, Integer> position = new HashMap<>();
        for (int i = 0; i < sorted.size(); i++) {
            position.put(sorted.get(i), i);
        }
        for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
            int u = entry.getKey();
            for (Integer v : entry.getValue()) {
                assertThat(position.get(u)).isLessThan(position.get(v));
            }
        }
    }

    @Test
    void testTopologicalSort_GraphWithCycle() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(3));
        graph.put(3, Arrays.asList(1)); // cycle exists
        Throwable thrown = catchThrowable(() -> GraphUtil.topologicalSort(graph));
        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Graph contains a cycle. Topological sort not possible.");
    }

    // Tests for isBipartite
    @Test
    void testIsBipartite_EmptyGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        assertThat(GraphUtil.isBipartite(graph)).isTrue();
    }

    @Test
    void testIsBipartite_SingleNode() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, new ArrayList<>());
        assertThat(GraphUtil.isBipartite(graph)).isTrue();
    }

    @Test
    void testIsBipartite_BipartiteGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        // Bipartite graph: 1 connected to 2 and 3, 2 and 3 not connected.
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(1, 4));
        graph.put(3, Arrays.asList(1, 4));
        graph.put(4, Arrays.asList(2, 3));
        assertThat(GraphUtil.isBipartite(graph)).isTrue();
    }

    @Test
    void testIsBipartite_NonBipartiteGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        // Non-bipartite graph: triangle 1-2, 2-3, 3-1
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(1, 3));
        graph.put(3, Arrays.asList(1, 2));
        assertThat(GraphUtil.isBipartite(graph)).isFalse();
    }

    // Tests for shortestPath
    @Test
    void testShortestPath_StartEqualsEnd() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2)); // arbitrary
        List<Integer> path = GraphUtil.shortestPath(graph, 1, 1);
        assertThat(path).containsExactly(1);
    }

    @Test
    void testShortestPath_ValidPath() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        // Graph: 1->2, 2->3, 1->4, 4->3
        graph.put(1, Arrays.asList(2, 4));
        graph.put(2, Arrays.asList(3));
        graph.put(4, Arrays.asList(3));
        graph.put(3, new ArrayList<>());
        List<Integer> path = GraphUtil.shortestPath(graph, 1, 3);
        // Possible shortest path: [1,2,3] or [1,4,3]
        assertThat(path).hasSize(3);
        assertThat(path.get(0)).isEqualTo(1);
        assertThat(path.get(2)).isEqualTo(3);
    }

    @Test
    void testShortestPath_NoPath() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        // Disconnected graph: 1->2, 3 isolated
        graph.put(1, Arrays.asList(2));
        graph.put(2, new ArrayList<>());
        graph.put(3, new ArrayList<>());
        List<Integer> path = GraphUtil.shortestPath(graph, 1, 3);
        assertThat(path).isEmpty();
    }
}