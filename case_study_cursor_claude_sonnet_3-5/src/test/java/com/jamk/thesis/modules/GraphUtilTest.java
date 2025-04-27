package com.jamk.thesis.modules;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GraphUtilTest {
    private Map<Integer, List<Integer>> graph;

    @BeforeEach
    void setUp() {
        graph = new HashMap<>();
    }

    // Cycle Detection Tests
    @Test
    @DisplayName("Should return false for empty graph when checking cycles")
    void hasCycle_WithEmptyGraph_ShouldReturnFalse() {
        assertFalse(GraphUtil.hasCycle(graph));
    }

    @Test
    @DisplayName("Should detect self-loop as cycle")
    void hasCycle_WithSelfLoop_ShouldReturnTrue() {
        graph.put(1, Arrays.asList(1));
        assertTrue(GraphUtil.hasCycle(graph));
    }

    @Test
    @DisplayName("Should return false for acyclic graph")
    void hasCycle_WithAcyclicGraph_ShouldReturnFalse() {
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(4));
        graph.put(3, Arrays.asList(4));
        graph.put(4, new ArrayList<>());
        assertFalse(GraphUtil.hasCycle(graph));
    }

    @Test
    @DisplayName("Should detect cycle in complex graph")
    void hasCycle_WithCyclicGraph_ShouldReturnTrue() {
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(3));
        graph.put(3, Arrays.asList(4));
        graph.put(4, Arrays.asList(2));
        assertTrue(GraphUtil.hasCycle(graph));
    }

    @Test
    @DisplayName("Should handle isolated nodes when checking cycles")
    void hasCycle_WithIsolatedNodes_ShouldReturnFalse() {
        graph.put(1, new ArrayList<>());
        graph.put(2, new ArrayList<>());
        graph.put(3, new ArrayList<>());
        assertFalse(GraphUtil.hasCycle(graph));
    }

    // Topological Sort Tests
    @Test
    @DisplayName("Should return empty list for empty graph in topological sort")
    void topologicalSort_WithEmptyGraph_ShouldReturnEmptyList() {
        List<Integer> result = GraphUtil.topologicalSort(graph);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should throw exception for cyclic graph in topological sort")
    void topologicalSort_WithCyclicGraph_ShouldThrowException() {
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(3));
        graph.put(3, Arrays.asList(1));

        assertThrows(IllegalArgumentException.class, () -> GraphUtil.topologicalSort(graph));
    }

    @Test
    @DisplayName("Should correctly sort simple DAG")
    void topologicalSort_WithSimpleDAG_ShouldReturnValidOrder() {
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(4));
        graph.put(3, Arrays.asList(4));
        graph.put(4, new ArrayList<>());

        List<Integer> result = GraphUtil.topologicalSort(graph);
        assertEquals(4, result.size());
        assertOrder(result, 1, 4); // 1 should come before 4
        assertOrder(result, 2, 4); // 2 should come before 4
        assertOrder(result, 3, 4); // 3 should come before 4
    }

    @Test
    @DisplayName("Should handle disconnected components in topological sort")
    void topologicalSort_WithDisconnectedComponents_ShouldIncludeAllNodes() {
        graph.put(1, Arrays.asList(2));
        graph.put(2, new ArrayList<>());
        graph.put(3, Arrays.asList(4));
        graph.put(4, new ArrayList<>());

        List<Integer> result = GraphUtil.topologicalSort(graph);
        assertEquals(4, result.size());
        assertOrder(result, 1, 2);
        assertOrder(result, 3, 4);
    }

    // Bipartite Tests
    @Test
    @DisplayName("Should return true for empty graph in bipartite check")
    void isBipartite_WithEmptyGraph_ShouldReturnTrue() {
        assertTrue(GraphUtil.isBipartite(graph));
    }

    @Test
    @DisplayName("Should return true for single node graph in bipartite check")
    void isBipartite_WithSingleNode_ShouldReturnTrue() {
        graph.put(1, new ArrayList<>());
        assertTrue(GraphUtil.isBipartite(graph));
    }

    @Test
    @DisplayName("Should identify simple bipartite graph")
    void isBipartite_WithSimpleBipartiteGraph_ShouldReturnTrue() {
        graph.put(1, Arrays.asList(2, 4));
        graph.put(2, Arrays.asList(1, 3));
        graph.put(3, Arrays.asList(2, 4));
        graph.put(4, Arrays.asList(1, 3));
        assertTrue(GraphUtil.isBipartite(graph));
    }

    @Test
    @DisplayName("Should identify non-bipartite graph")
    void isBipartite_WithNonBipartiteGraph_ShouldReturnFalse() {
        // Triangle - cannot be colored with two colors
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(1, 3));
        graph.put(3, Arrays.asList(1, 2));
        assertFalse(GraphUtil.isBipartite(graph));
    }

    @Test
    @DisplayName("Should handle disconnected components in bipartite check")
    void isBipartite_WithDisconnectedComponents_ShouldCheckAllComponents() {
        // Two components: one bipartite, one non-bipartite
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(1));
        graph.put(3, Arrays.asList(4, 5));
        graph.put(4, Arrays.asList(3, 5));
        graph.put(5, Arrays.asList(3, 4));
        assertFalse(GraphUtil.isBipartite(graph));
    }

    // Shortest Path Tests
    @Test
    @DisplayName("Should return single node path when start equals end")
    void shortestPath_WithSameStartAndEnd_ShouldReturnSingleNodePath() {
        graph.put(1, Arrays.asList(2));
        List<Integer> path = GraphUtil.shortestPath(graph, 1, 1);
        assertEquals(Arrays.asList(1), path);
    }

    @Test
    @DisplayName("Should return empty list when no path exists")
    void shortestPath_WithNoPath_ShouldReturnEmptyList() {
        graph.put(1, Arrays.asList(2));
        graph.put(2, new ArrayList<>());
        graph.put(3, new ArrayList<>());
        List<Integer> path = GraphUtil.shortestPath(graph, 1, 3);
        assertTrue(path.isEmpty());
    }

    @Test
    @DisplayName("Should find correct shortest path in simple graph")
    void shortestPath_WithSimpleGraph_ShouldFindShortestPath() {
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(4));
        graph.put(3, Arrays.asList(4));
        graph.put(4, new ArrayList<>());

        List<Integer> path = GraphUtil.shortestPath(graph, 1, 4);
        assertEquals(Arrays.asList(1, 2, 4), path);
    }

    @Test
    @DisplayName("Should handle unreachable nodes in shortest path")
    void shortestPath_WithUnreachableNodes_ShouldReturnEmptyList() {
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(1));
        graph.put(3, Arrays.asList(4));
        graph.put(4, Arrays.asList(3));

        List<Integer> path = GraphUtil.shortestPath(graph, 1, 3);
        assertTrue(path.isEmpty());
    }

    // Parameterized Tests
    static Stream<Arguments> graphScenarios() {
        return Stream.of(
            // Empty graph
            Arguments.of(new HashMap<Integer, List<Integer>>(), false),
            // Single node
            Arguments.of(new HashMap<Integer, List<Integer>>() {{
                put(1, new ArrayList<>());
            }}, false),
            // Simple cycle
            Arguments.of(new HashMap<Integer, List<Integer>>() {{
                put(1, Arrays.asList(2));
                put(2, Arrays.asList(1));
            }}, true),
            // Complex DAG
            Arguments.of(new HashMap<Integer, List<Integer>>() {{
                put(1, Arrays.asList(2, 3));
                put(2, Arrays.asList(4));
                put(3, Arrays.asList(4));
                put(4, new ArrayList<>());
            }}, false)
        );
    }

    @ParameterizedTest
    @MethodSource("graphScenarios")
    @DisplayName("Should correctly identify cycles in various graph scenarios")
    void hasCycle_WithVariousScenarios_ShouldReturnCorrectResult(
            Map<Integer, List<Integer>> testGraph, boolean expectedHasCycle) {
        assertEquals(expectedHasCycle, GraphUtil.hasCycle(testGraph));
    }

    // Helper method to assert correct ordering in topological sort
    private void assertOrder(List<Integer> sorted, int before, int after) {
        int beforeIndex = sorted.indexOf(before);
        int afterIndex = sorted.indexOf(after);
        assertTrue(beforeIndex < afterIndex, 
            String.format("Expected %d to come before %d in order %s", before, after, sorted));
    }
} 