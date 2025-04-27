package com.jamk.thesis.modules;


import java.util.*;

public class GraphUtil {

    /**
     * Checks if the given directed graph contains a cycle.
     * The graph is represented as a Map where each key is a node, and the value is a list of adjacent nodes.
     *
     * Edge Cases:
     * - An empty graph (returns false).
     * - A graph with isolated nodes.
     * - A graph with self-loops.
     *
     * @param graph the directed graph
     * @return true if a cycle exists, false otherwise.
     */
    public static boolean hasCycle(Map<Integer, List<Integer>> graph) {
        Set<Integer> visited = new HashSet<>();
        Set<Integer> recStack = new HashSet<>();
        for (Integer node : graph.keySet()) {
            if (hasCycleUtil(node, graph, visited, recStack)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasCycleUtil(Integer node, Map<Integer, List<Integer>> graph,
                                        Set<Integer> visited, Set<Integer> recStack) {
        if (recStack.contains(node)) {
            return true;
        }
        if (visited.contains(node)) {
            return false;
        }
        visited.add(node);
        recStack.add(node);
        List<Integer> neighbors = graph.get(node);
        if (neighbors != null) {
            for (Integer neighbor : neighbors) {
                if (hasCycleUtil(neighbor, graph, visited, recStack)) {
                    return true;
                }
            }
        }
        recStack.remove(node);
        return false;
    }

    /**
     * Returns a topological ordering of the nodes in a directed acyclic graph.
     * If the graph contains a cycle, an IllegalArgumentException is thrown.
     *
     * Edge Cases:
     * - Empty graph.
     * - Graph with disconnected components.
     * - Graph with nodes that do not have outgoing edges.
     *
     * @param graph the directed graph
     * @return a list of nodes in topologically sorted order.
     */
    public static List<Integer> topologicalSort(Map<Integer, List<Integer>> graph) {
        if (hasCycle(graph)) {
            throw new IllegalArgumentException("Graph contains a cycle. Topological sort not possible.");
        }

        Set<Integer> visited = new HashSet<>();
        LinkedList<Integer> sortedList = new LinkedList<>();
        for (Integer node : graph.keySet()) {
            if (!visited.contains(node)) {
                topologicalSortUtil(node, graph, visited, sortedList);
            }
        }
        // Note: isolated nodes might not appear as keys; ensure they are handled in your actual use-case.
        return sortedList;
    }

    private static void topologicalSortUtil(Integer node, Map<Integer, List<Integer>> graph,
                                            Set<Integer> visited, LinkedList<Integer> sortedList) {
        visited.add(node);
        List<Integer> neighbors = graph.get(node);
        if (neighbors != null) {
            for (Integer neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    topologicalSortUtil(neighbor, graph, visited, sortedList);
                }
            }
        }
        sortedList.addFirst(node);
    }

    /**
     * Checks whether the given undirected graph is bipartite.
     * The graph is represented as a Map where each key is a node, and the value is a list of its neighbors.
     *
     * Edge Cases:
     * - Empty graph.
     * - Single node graph.
     * - Disconnected graph.
     *
     * @param graph the undirected graph
     * @return true if the graph is bipartite, false otherwise.
     */
    public static boolean isBipartite(Map<Integer, List<Integer>> graph) {
        Map<Integer, Integer> colors = new HashMap<>(); // Colors: 0 or 1
        for (Integer node : graph.keySet()) {
            if (!colors.containsKey(node)) {
                if (!isBipartiteUtil(node, graph, colors)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isBipartiteUtil(Integer start, Map<Integer, List<Integer>> graph, Map<Integer, Integer> colors) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        colors.put(start, 0);
        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            List<Integer> neighbors = graph.get(node);
            if (neighbors != null) {
                for (Integer neighbor : neighbors) {
                    if (!colors.containsKey(neighbor)) {
                        colors.put(neighbor, 1 - colors.get(node));
                        queue.add(neighbor);
                    } else if (colors.get(neighbor).equals(colors.get(node))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Finds the shortest path between two nodes in an unweighted graph using Breadth-First Search (BFS).
     *
     * Edge Cases:
     * - Start node is the same as the end node.
     * - No path exists between the nodes.
     * - Graph is empty.
     *
     * @param graph the graph represented as a Map
     * @param start the starting node
     * @param end   the destination node
     * @return a list representing the shortest path (inclusive of start and end); empty list if no path exists.
     */
    public static List<Integer> shortestPath(Map<Integer, List<Integer>> graph, int start, int end) {
        if (start == end) {
            return Arrays.asList(start);
        }
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> parent = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);
        boolean found = false;
        while (!queue.isEmpty() && !found) {
            int node = queue.poll();
            List<Integer> neighbors = graph.get(node);
            if (neighbors != null) {
                for (Integer neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        parent.put(neighbor, node);
                        if (neighbor == end) {
                            found = true;
                            break;
                        }
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
        }

        if (!found) {
            return new ArrayList<>();
        }

        // Reconstruct path from end to start using parent map.
        LinkedList<Integer> path = new LinkedList<>();
        for (Integer at = end; at != null; at = parent.get(at)) {
            path.addFirst(at);
        }
        return path;
    }
}
