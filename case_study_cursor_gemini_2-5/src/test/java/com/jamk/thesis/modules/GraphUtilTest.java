package com.jamk.thesis.modules;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GraphUtil Class Tests")
class GraphUtilTest {

    // Helper to build graphs easily
    private Map<Integer, List<Integer>> buildGraph(Object... data) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < data.length; i += 2) {
            int node = (int) data[i];
            @SuppressWarnings("unchecked")
            List<Integer> neighbors = (List<Integer>) data[i + 1];
            graph.put(node, new ArrayList<>(neighbors));
        }
        return graph;
    }

    @Nested
    @DisplayName("hasCycle Method Tests (Directed Graphs)")
    class HasCycleTests {

        @Test
        @DisplayName("Should return false for an empty graph")
        void hasCycle_whenGraphIsEmpty_shouldReturnFalse() {
            Map<Integer, List<Integer>> graph = buildGraph();
            assertFalse(GraphUtil.hasCycle(graph));
        }

        @Test
        @DisplayName("Should return false for a graph with a single node")
        void hasCycle_whenGraphHasSingleNode_shouldReturnFalse() {
            Map<Integer, List<Integer>> graph = buildGraph(1, Collections.emptyList());
            assertFalse(GraphUtil.hasCycle(graph));
        }

        @Test
        @DisplayName("Should return true for a graph with a self-loop")
        void hasCycle_whenGraphHasSelfLoop_shouldReturnTrue() {
            Map<Integer, List<Integer>> graph = buildGraph(1, List.of(1));
            assertTrue(GraphUtil.hasCycle(graph));
        }

        @Test
        @DisplayName("Should return false for a Directed Acyclic Graph (DAG)")
        void hasCycle_whenGraphIsDAG_shouldReturnFalse() {
            Map<Integer, List<Integer>> graph = buildGraph(
                    1, List.of(2, 3),
                    2, List.of(4),
                    3, List.of(4),
                    4, Collections.emptyList()
            );
            assertFalse(GraphUtil.hasCycle(graph));
        }

        @Test
        @DisplayName("Should return true for a graph with a simple cycle (1 -> 2 -> 1)")
        void hasCycle_whenGraphHasSimpleCycle_shouldReturnTrue() {
            Map<Integer, List<Integer>> graph = buildGraph(
                    1, List.of(2),
                    2, List.of(1)
            );
            assertTrue(GraphUtil.hasCycle(graph));
        }

        @Test
        @DisplayName("Should return true for a graph with a longer cycle (1 -> 2 -> 3 -> 1)")
        void hasCycle_whenGraphHasLongerCycle_shouldReturnTrue() {
            Map<Integer, List<Integer>> graph = buildGraph(
                    1, List.of(2),
                    2, List.of(3),
                    3, List.of(1)
            );
            assertTrue(GraphUtil.hasCycle(graph));
        }

        @Test
        @DisplayName("Should return true for a graph with a cycle in a disconnected component")
        void hasCycle_whenCycleInDisconnectedComponent_shouldReturnTrue() {
            Map<Integer, List<Integer>> graph = buildGraph(
                    1, List.of(2), // Component 1 (DAG)
                    2, Collections.emptyList(),
                    3, List.of(4), // Component 2 (Cycle)
                    4, List.of(5),
                    5, List.of(3)
            );
            assertTrue(GraphUtil.hasCycle(graph));
        }

        @Test
        @DisplayName("Should return false for a complex DAG")
        void hasCycle_whenGraphIsComplexDAG_shouldReturnFalse() {
             Map<Integer, List<Integer>> graph = buildGraph(
                 0, List.of(1, 2),
                 1, List.of(3),
                 2, List.of(3, 4),
                 3, List.of(5),
                 4, List.of(5),
                 5, Collections.emptyList()
             );
             assertFalse(GraphUtil.hasCycle(graph));
        }
    }

    @Nested
    @DisplayName("topologicalSort Method Tests")
    class TopologicalSortTests {

        @Test
        @DisplayName("Should return an empty list for an empty graph")
        void topologicalSort_whenGraphIsEmpty_shouldReturnEmptyList() {
            Map<Integer, List<Integer>> graph = buildGraph();
            assertTrue(GraphUtil.topologicalSort(graph).isEmpty());
        }

        @Test
        @DisplayName("Should return the single node for a single-node graph")
        void topologicalSort_whenGraphHasSingleNode_shouldReturnNode() {
            Map<Integer, List<Integer>> graph = buildGraph(1, Collections.emptyList());
            assertEquals(List.of(1), GraphUtil.topologicalSort(graph));
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for a graph with a cycle")
        void topologicalSort_whenGraphHasCycle_shouldThrowIllegalArgumentException() {
            Map<Integer, List<Integer>> graph = buildGraph(
                    1, List.of(2),
                    2, List.of(1)
            );
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                GraphUtil.topologicalSort(graph);
            });
            assertEquals("Graph contains a cycle. Topological sort not possible.", exception.getMessage());
        }

        @Test
        @DisplayName("Should return a valid topological sort for a simple DAG")
        void topologicalSort_whenGraphIsSimpleDAG_shouldReturnValidSort() {
            Map<Integer, List<Integer>> graph = buildGraph(
                    1, List.of(3),
                    2, List.of(3),
                    3, Collections.emptyList()
            );
            List<Integer> sorted = GraphUtil.topologicalSort(graph);
            // Possible valid sorts: [1, 2, 3] or [2, 1, 3]
            assertTrue(isValidTopologicalSort(graph, sorted));
            assertEquals(3, sorted.size());
        }

        @Test
        @DisplayName("Should return a valid topological sort for a linear graph")
        void topologicalSort_whenGraphIsLinear_shouldReturnCorrectOrder() {
            Map<Integer, List<Integer>> graph = buildGraph(
                    1, List.of(2),
                    2, List.of(3),
                    3, List.of(4),
                    4, Collections.emptyList()
            );
            List<Integer> expected = List.of(1, 2, 3, 4);
            assertEquals(expected, GraphUtil.topologicalSort(graph));
        }

        @Test
        @DisplayName("Should return a valid topological sort for a disconnected graph")
        void topologicalSort_whenGraphIsDisconnected_shouldReturnValidSort() {
            Map<Integer, List<Integer>> graph = buildGraph(
                    1, List.of(2),
                    2, Collections.emptyList(),
                    3, List.of(4),
                    4, Collections.emptyList()
            );
            List<Integer> sorted = GraphUtil.topologicalSort(graph);
            // Possible sorts include [1, 2, 3, 4], [3, 4, 1, 2], etc.
            assertTrue(isValidTopologicalSort(graph, sorted));
            assertEquals(4, sorted.size());
        }

        @Test
        @DisplayName("Should return a valid sort for a more complex DAG")
        void topologicalSort_whenGraphIsComplexDAG_shouldReturnValidSort() {
            Map<Integer, List<Integer>> graph = buildGraph(
                5, List.of(2, 0),
                4, List.of(0, 1),
                2, List.of(3),
                3, List.of(1),
                0, Collections.emptyList(),
                1, Collections.emptyList()
            );
             List<Integer> sorted = GraphUtil.topologicalSort(graph);
             assertTrue(isValidTopologicalSort(graph, sorted));
             assertEquals(6, sorted.size());
             // Example valid sorts: [5, 4, 2, 3, 1, 0], [4, 5, 2, 3, 1, 0]
        }

        // Helper to validate topological sort (checks if for every edge u -> v, u appears before v)
        private boolean isValidTopologicalSort(Map<Integer, List<Integer>> graph, List<Integer> sorted) {
            Map<Integer, Integer> positions = new HashMap<>();
            for (int i = 0; i < sorted.size(); i++) {
                positions.put(sorted.get(i), i);
            }

            for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
                int u = entry.getKey();
                if (!positions.containsKey(u)) return false; // Node not in sorted list
                int uPos = positions.get(u);
                for (int v : entry.getValue()) {
                    if (!positions.containsKey(v) || positions.get(v) < uPos) {
                        return false; // Neighbor missing or appears before source node
                    }
                }
            }
            return true;
        }
    }

    @Nested
    @DisplayName("isBipartite Method Tests (Undirected Graphs)")
    class IsBipartiteTests {

        @Test
        @DisplayName("Should return true for an empty graph")
        void isBipartite_whenGraphIsEmpty_shouldReturnTrue() {
            Map<Integer, List<Integer>> graph = buildGraph();
            assertTrue(GraphUtil.isBipartite(graph));
        }

        @Test
        @DisplayName("Should return true for a single-node graph")
        void isBipartite_whenGraphHasSingleNode_shouldReturnTrue() {
            Map<Integer, List<Integer>> graph = buildGraph(1, Collections.emptyList());
            assertTrue(GraphUtil.isBipartite(graph));
        }

        @Test
        @DisplayName("Should return true for a simple bipartite graph (line graph)")
        void isBipartite_whenGraphIsSimpleBipartite_shouldReturnTrue() {
            Map<Integer, List<Integer>> graph = buildGraph(
                    1, List.of(2),
                    2, List.of(1, 3), // Assuming undirected, need symmetric edges
                    3, List.of(2)
            );
            assertTrue(GraphUtil.isBipartite(graph));
        }

        @Test
        @DisplayName("Should return false for a graph with an odd cycle (triangle)")
        void isBipartite_whenGraphHasOddCycle_shouldReturnFalse() {
            Map<Integer, List<Integer>> graph = buildGraph(
                    1, List.of(2, 3),
                    2, List.of(1, 3),
                    3, List.of(1, 2)
            );
            assertFalse(GraphUtil.isBipartite(graph));
        }

        @Test
        @DisplayName("Should return true for a bipartite graph with two components")
        void isBipartite_whenGraphIsDisconnectedBipartite_shouldReturnTrue() {
            Map<Integer, List<Integer>> graph = buildGraph(
                    1, List.of(2), // Component 1
                    2, List.of(1),
                    3, List.of(4), // Component 2
                    4, List.of(3)
            );
            assertTrue(GraphUtil.isBipartite(graph));
        }

        @Test
        @DisplayName("Should return false if one component has an odd cycle")
        void isBipartite_whenOneComponentHasOddCycle_shouldReturnFalse() {
            Map<Integer, List<Integer>> graph = buildGraph(
                    1, List.of(2), // Component 1 (Bipartite)
                    2, List.of(1),
                    3, List.of(4, 5), // Component 2 (Odd Cycle)
                    4, List.of(3, 5),
                    5, List.of(3, 4)
            );
            assertFalse(GraphUtil.isBipartite(graph));
        }

        @Test
        @DisplayName("Should return true for a complete bipartite graph K(2,3)")
        void isBipartite_whenCompleteBipartite_shouldReturnTrue() {
             Map<Integer, List<Integer>> graph = buildGraph(
                 1, List.of(3, 4, 5),
                 2, List.of(3, 4, 5),
                 3, List.of(1, 2),
                 4, List.of(1, 2),
                 5, List.of(1, 2)
             );
             assertTrue(GraphUtil.isBipartite(graph));
        }

         @Test
        @DisplayName("Should return false for a graph with self-loop (implies odd cycle of length 1)")
        void isBipartite_whenSelfLoop_shouldReturnFalse() {
             Map<Integer, List<Integer>> graph = buildGraph(
                 1, List.of(1, 2),
                 2, List.of(1)
             );
             assertFalse(GraphUtil.isBipartite(graph));
         }
    }

    @Nested
    @DisplayName("shortestPath Method Tests (Unweighted Graphs)")
    class ShortestPathTests {

        @Test
        @DisplayName("Should return empty list for an empty graph")
        void shortestPath_whenGraphIsEmpty_shouldReturnEmptyList() {
            Map<Integer, List<Integer>> graph = buildGraph();
            assertTrue(GraphUtil.shortestPath(graph, 1, 2).isEmpty());
        }

        @Test
        @DisplayName("Should return start node if start equals end")
        void shortestPath_whenStartEqualsEnd_shouldReturnStartNode() {
            Map<Integer, List<Integer>> graph = buildGraph(1, List.of(2));
            assertEquals(List.of(1), GraphUtil.shortestPath(graph, 1, 1));
        }

        @Test
        @DisplayName("Should return empty list if start node is not in graph")
        void shortestPath_whenStartNotInGraph_shouldReturnEmptyList() {
             Map<Integer, List<Integer>> graph = buildGraph(2, List.of(3));
             assertTrue(GraphUtil.shortestPath(graph, 1, 3).isEmpty());
        }

         @Test
        @DisplayName("Should return empty list if end node is not in graph")
        void shortestPath_whenEndNotInGraph_shouldReturnEmptyList() {
             Map<Integer, List<Integer>> graph = buildGraph(1, List.of(2));
             assertTrue(GraphUtil.shortestPath(graph, 1, 3).isEmpty());
        }

        @Test
        @DisplayName("Should return the direct path if nodes are adjacent")
        void shortestPath_whenNodesAreAdjacent_shouldReturnDirectPath() {
            Map<Integer, List<Integer>> graph = buildGraph(
                    1, List.of(2, 3),
                    2, List.of(4),
                    3, List.of(4),
                    4, Collections.emptyList()
            );
            assertEquals(List.of(1, 2), GraphUtil.shortestPath(graph, 1, 2));
            assertEquals(List.of(1, 3), GraphUtil.shortestPath(graph, 1, 3));
        }

        @Test
        @DisplayName("Should find the shortest path in a simple graph")
        void shortestPath_whenPathExists_shouldReturnShortestPath() {
            Map<Integer, List<Integer>> graph = buildGraph(
                    1, List.of(2, 3),
                    2, List.of(4),
                    3, List.of(4),
                    4, List.of(5),
                    5, Collections.emptyList()
            );
            assertEquals(List.of(1, 2, 4), GraphUtil.shortestPath(graph, 1, 4)); // 1->2->4 is shorter than 1->3->4
            assertEquals(List.of(1, 2, 4, 5), GraphUtil.shortestPath(graph, 1, 5));
        }

        @Test
        @DisplayName("Should return empty list if no path exists between start and end")
        void shortestPath_whenNoPathExists_shouldReturnEmptyList() {
            Map<Integer, List<Integer>> graph = buildGraph(
                    1, List.of(2), // Component 1
                    2, Collections.emptyList(),
                    3, List.of(4), // Component 2
                    4, Collections.emptyList()
            );
            assertTrue(GraphUtil.shortestPath(graph, 1, 3).isEmpty());
        }

         @Test
        @DisplayName("Should find shortest path in a graph with cycles")
        void shortestPath_inGraphWithCycles_shouldReturnShortestPath() {
             Map<Integer, List<Integer>> graph = buildGraph(
                 1, List.of(2, 5),
                 2, List.of(3, 4),
                 3, List.of(1), // Cycle 1-2-3-1
                 4, List.of(5),
                 5, Collections.emptyList()
             );
             // Shortest path 1 -> 5 (direct) is shorter than 1 -> 2 -> 4 -> 5
             assertEquals(List.of(1, 5), GraphUtil.shortestPath(graph, 1, 5));
             assertEquals(List.of(1, 2, 4), GraphUtil.shortestPath(graph, 1, 4));
        }

         @Test
        @DisplayName("Should find shortest path when multiple paths exist")
        void shortestPath_whenMultiplePathsExist_shouldReturnShortest() {
             Map<Integer, List<Integer>> graph = buildGraph(
                 1, List.of(2, 3),
                 2, List.of(4),
                 3, List.of(4, 5),
                 4, List.of(6),
                 5, List.of(6),
                 6, Collections.emptyList()
             );
             // Path 1->2->4->6 (length 3) vs 1->3->4->6 (length 3) vs 1->3->5->6 (length 3)
             // BFS guarantees shortest path length. Actual path depends on neighbor order.
             List<Integer> path = GraphUtil.shortestPath(graph, 1, 6);
             assertEquals(4, path.size()); // Length 3 means 4 nodes
             assertTrue(path.equals(List.of(1, 2, 4, 6)) || path.equals(List.of(1, 3, 4, 6)) || path.equals(List.of(1, 3, 5, 6)));
        }
    }
} 