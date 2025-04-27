package com.jamk.thesis.modules;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class GraphUtilTest {

    private Map<Integer, List<Integer>> graph;

    @BeforeEach
    void setUp() {
        graph = new HashMap<>();
    }

    @Nested
    class HasCycleTests {
        @Test
        void hasCycle_EmptyGraph_ReturnsFalse() {
            assertFalse(GraphUtil.hasCycle(graph));
        }

        @Test
        void hasCycle_SingleNode_ReturnsFalse() {
            graph.put(1, new ArrayList<>());
            assertFalse(GraphUtil.hasCycle(graph));
        }

        @Test
        void hasCycle_SelfLoop_ReturnsTrue() {
            graph.put(1, Arrays.asList(1));
            assertTrue(GraphUtil.hasCycle(graph));
        }

        @Test
        void hasCycle_CyclicGraph_ReturnsTrue() {
            graph.put(1, Arrays.asList(2));
            graph.put(2, Arrays.asList(3));
            graph.put(3, Arrays.asList(1));
            assertTrue(GraphUtil.hasCycle(graph));
        }

        @Test
        void hasCycle_AcyclicGraph_ReturnsFalse() {
            graph.put(1, Arrays.asList(2, 3));
            graph.put(2, Arrays.asList(4));
            graph.put(3, Arrays.asList(4));
            graph.put(4, new ArrayList<>());
            assertFalse(GraphUtil.hasCycle(graph));
        }

        @Test
        void hasCycle_DisconnectedComponents_WithCycle_ReturnsTrue() {
            // Component 1 (acyclic)
            graph.put(1, Arrays.asList(2));
            graph.put(2, new ArrayList<>());
            // Component 2 (cyclic)
            graph.put(3, Arrays.asList(4));
            graph.put(4, Arrays.asList(3));
            
            assertTrue(GraphUtil.hasCycle(graph));
        }
    }

    @Nested
    class TopologicalSortTests {
        @Test
        void topologicalSort_EmptyGraph_ReturnsEmptyList() {
            List<Integer> result = GraphUtil.topologicalSort(graph);
            assertTrue(result.isEmpty());
        }

        @Test
        void topologicalSort_SingleNode_ReturnsSingleNode() {
            graph.put(1, new ArrayList<>());
            List<Integer> result = GraphUtil.topologicalSort(graph);
            assertEquals(Arrays.asList(1), result);
        }

        @Test
        void topologicalSort_LinearGraph_ReturnsCorrectOrder() {
            graph.put(1, Arrays.asList(2));
            graph.put(2, Arrays.asList(3));
            graph.put(3, new ArrayList<>());
            
            List<Integer> result = GraphUtil.topologicalSort(graph);
            assertEquals(Arrays.asList(1, 2, 3), result);
        }

        @Test
        void topologicalSort_CyclicGraph_ThrowsException() {
            graph.put(1, Arrays.asList(2));
            graph.put(2, Arrays.asList(1));
            
            assertThrows(IllegalArgumentException.class, () -> GraphUtil.topologicalSort(graph));
        }

        @Test
        void topologicalSort_ComplexDAG_ReturnsValidOrder() {
            graph.put(1, Arrays.asList(2, 3));
            graph.put(2, Arrays.asList(4));
            graph.put(3, Arrays.asList(4));
            graph.put(4, new ArrayList<>());
            
            List<Integer> result = GraphUtil.topologicalSort(graph);
            assertEquals(4, result.size());
            // Verify topological ordering constraints
            assertTrue(result.indexOf(1) < result.indexOf(2));
            assertTrue(result.indexOf(1) < result.indexOf(3));
            assertTrue(result.indexOf(2) < result.indexOf(4));
            assertTrue(result.indexOf(3) < result.indexOf(4));
        }
    }

    @Nested
    class BipartiteTests {
        @Test
        void isBipartite_EmptyGraph_ReturnsTrue() {
            assertTrue(GraphUtil.isBipartite(graph));
        }

        @Test
        void isBipartite_SingleNode_ReturnsTrue() {
            graph.put(1, new ArrayList<>());
            assertTrue(GraphUtil.isBipartite(graph));
        }

        @Test
        void isBipartite_BipartiteGraph_ReturnsTrue() {
            graph.put(1, Arrays.asList(2, 4));
            graph.put(2, Arrays.asList(1, 3));
            graph.put(3, Arrays.asList(2, 4));
            graph.put(4, Arrays.asList(1, 3));
            
            assertTrue(GraphUtil.isBipartite(graph));
        }

        @Test
        void isBipartite_NonBipartiteGraph_ReturnsFalse() {
            // Triangle graph cannot be bipartite
            graph.put(1, Arrays.asList(2, 3));
            graph.put(2, Arrays.asList(1, 3));
            graph.put(3, Arrays.asList(1, 2));
            
            assertFalse(GraphUtil.isBipartite(graph));
        }

        @Test
        void isBipartite_DisconnectedComponents_ReturnsTrue() {
            // Two separate bipartite components
            graph.put(1, Arrays.asList(2));
            graph.put(2, Arrays.asList(1));
            graph.put(3, Arrays.asList(4));
            graph.put(4, Arrays.asList(3));
            
            assertTrue(GraphUtil.isBipartite(graph));
        }
    }

    @Nested
    class ShortestPathTests {
        @Test
        void shortestPath_SameStartAndEnd_ReturnsSingleNode() {
            graph.put(1, new ArrayList<>());
            List<Integer> path = GraphUtil.shortestPath(graph, 1, 1);
            assertEquals(Arrays.asList(1), path);
        }

        @Test
        void shortestPath_NoPath_ReturnsEmptyList() {
            graph.put(1, Arrays.asList(2));
            graph.put(2, new ArrayList<>());
            graph.put(3, new ArrayList<>());
            
            List<Integer> path = GraphUtil.shortestPath(graph, 1, 3);
            assertTrue(path.isEmpty());
        }

        @Test
        void shortestPath_LinearPath_ReturnsCorrectPath() {
            graph.put(1, Arrays.asList(2));
            graph.put(2, Arrays.asList(3));
            graph.put(3, new ArrayList<>());
            
            List<Integer> path = GraphUtil.shortestPath(graph, 1, 3);
            assertEquals(Arrays.asList(1, 2, 3), path);
        }

        @Test
        void shortestPath_MultiplePaths_ReturnsShortestPath() {
            // Path 1: 1->2->3->5
            // Path 2: 1->4->5 (shorter)
            graph.put(1, Arrays.asList(2, 4));
            graph.put(2, Arrays.asList(3));
            graph.put(3, Arrays.asList(5));
            graph.put(4, Arrays.asList(5));
            graph.put(5, new ArrayList<>());
            
            List<Integer> path = GraphUtil.shortestPath(graph, 1, 5);
            assertEquals(Arrays.asList(1, 4, 5), path);
        }

        @Test
        void shortestPath_EmptyGraph_ReturnsEmptyList() {
            List<Integer> path = GraphUtil.shortestPath(graph, 1, 2);
            assertTrue(path.isEmpty());
        }

        @Test
        void shortestPath_NonexistentNodes_ReturnsEmptyList() {
            graph.put(1, Arrays.asList(2));
            graph.put(2, new ArrayList<>());
            
            List<Integer> path = GraphUtil.shortestPath(graph, 3, 4);
            assertTrue(path.isEmpty());
        }
    }
}