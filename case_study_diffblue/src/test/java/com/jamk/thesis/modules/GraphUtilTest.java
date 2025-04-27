package com.jamk.thesis.modules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GraphUtilTest {
    /**
     * Test {@link GraphUtil#hasCycle(Map)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()} add one.</li>
     *   <li>When {@link HashMap#HashMap()} one is {@link ArrayList#ArrayList()}.</li>
     *   <li>Then return {@code true}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#hasCycle(Map)}
     */
    @Test
    @DisplayName("Test hasCycle(Map); given ArrayList() add one; when HashMap() one is ArrayList(); then return 'true'")
    void testHasCycle_givenArrayListAddOne_whenHashMapOneIsArrayList_thenReturnTrue() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);

        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, integerList);

        // Act and Assert
        assertTrue(GraphUtil.hasCycle(graph));
    }

    /**
     * Test {@link GraphUtil#hasCycle(Map)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()} add two.</li>
     *   <li>When {@link HashMap#HashMap()} one is {@link ArrayList#ArrayList()}.</li>
     *   <li>Then return {@code false}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#hasCycle(Map)}
     */
    @Test
    @DisplayName("Test hasCycle(Map); given ArrayList() add two; when HashMap() one is ArrayList(); then return 'false'")
    void testHasCycle_givenArrayListAddTwo_whenHashMapOneIsArrayList_thenReturnFalse() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);

        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, integerList);

        // Act and Assert
        assertFalse(GraphUtil.hasCycle(graph));
    }

    /**
     * Test {@link GraphUtil#hasCycle(Map)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()} add two.</li>
     *   <li>When {@link HashMap#HashMap()} one is {@link ArrayList#ArrayList()}.</li>
     *   <li>Then return {@code false}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#hasCycle(Map)}
     */
    @Test
    @DisplayName("Test hasCycle(Map); given ArrayList() add two; when HashMap() one is ArrayList(); then return 'false'")
    void testHasCycle_givenArrayListAddTwo_whenHashMapOneIsArrayList_thenReturnFalse2() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        integerList.add(2);

        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, integerList);

        // Act and Assert
        assertFalse(GraphUtil.hasCycle(graph));
    }

    /**
     * Test {@link GraphUtil#hasCycle(Map)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()}.</li>
     *   <li>When {@link HashMap#HashMap()} one is {@link ArrayList#ArrayList()}.</li>
     *   <li>Then return {@code false}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#hasCycle(Map)}
     */
    @Test
    @DisplayName("Test hasCycle(Map); given ArrayList(); when HashMap() one is ArrayList(); then return 'false'")
    void testHasCycle_givenArrayList_whenHashMapOneIsArrayList_thenReturnFalse() {
        // Arrange
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, new ArrayList<>());

        // Act and Assert
        assertFalse(GraphUtil.hasCycle(graph));
    }

    /**
     * Test {@link GraphUtil#hasCycle(Map)}.
     * <ul>
     *   <li>When {@link HashMap#HashMap()}.</li>
     *   <li>Then return {@code false}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#hasCycle(Map)}
     */
    @Test
    @DisplayName("Test hasCycle(Map); when HashMap(); then return 'false'")
    void testHasCycle_whenHashMap_thenReturnFalse() {
        // Arrange, Act and Assert
        assertFalse(GraphUtil.hasCycle(new HashMap<>()));
    }

    /**
     * Test {@link GraphUtil#topologicalSort(Map)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()} add one.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#topologicalSort(Map)}
     */
    @Test
    @DisplayName("Test topologicalSort(Map); given ArrayList() add one; then throw IllegalArgumentException")
    void testTopologicalSort_givenArrayListAddOne_thenThrowIllegalArgumentException() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);

        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, integerList);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> GraphUtil.topologicalSort(graph));
    }

    /**
     * Test {@link GraphUtil#topologicalSort(Map)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()} add two.</li>
     *   <li>Then return size is two.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#topologicalSort(Map)}
     */
    @Test
    @DisplayName("Test topologicalSort(Map); given ArrayList() add two; then return size is two")
    void testTopologicalSort_givenArrayListAddTwo_thenReturnSizeIsTwo() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);

        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, integerList);

        // Act
        List<Integer> actualTopologicalSortResult = GraphUtil.topologicalSort(graph);

        // Assert
        assertEquals(2, actualTopologicalSortResult.size());
        assertEquals(1, actualTopologicalSortResult.get(0).intValue());
        assertEquals(2, actualTopologicalSortResult.get(1).intValue());
    }

    /**
     * Test {@link GraphUtil#topologicalSort(Map)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()} add two.</li>
     *   <li>Then return size is two.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#topologicalSort(Map)}
     */
    @Test
    @DisplayName("Test topologicalSort(Map); given ArrayList() add two; then return size is two")
    void testTopologicalSort_givenArrayListAddTwo_thenReturnSizeIsTwo2() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        integerList.add(2);

        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, integerList);

        // Act
        List<Integer> actualTopologicalSortResult = GraphUtil.topologicalSort(graph);

        // Assert
        assertEquals(2, actualTopologicalSortResult.size());
        assertEquals(1, actualTopologicalSortResult.get(0).intValue());
        assertEquals(2, actualTopologicalSortResult.get(1).intValue());
    }

    /**
     * Test {@link GraphUtil#topologicalSort(Map)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()}.</li>
     *   <li>When {@link HashMap#HashMap()} one is {@link ArrayList#ArrayList()}.</li>
     *   <li>Then return size is one.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#topologicalSort(Map)}
     */
    @Test
    @DisplayName("Test topologicalSort(Map); given ArrayList(); when HashMap() one is ArrayList(); then return size is one")
    void testTopologicalSort_givenArrayList_whenHashMapOneIsArrayList_thenReturnSizeIsOne() {
        // Arrange
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, new ArrayList<>());

        // Act
        List<Integer> actualTopologicalSortResult = GraphUtil.topologicalSort(graph);

        // Assert
        assertEquals(1, actualTopologicalSortResult.size());
        assertEquals(1, actualTopologicalSortResult.get(0).intValue());
    }

    /**
     * Test {@link GraphUtil#topologicalSort(Map)}.
     * <ul>
     *   <li>When {@link HashMap#HashMap()}.</li>
     *   <li>Then return Empty.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#topologicalSort(Map)}
     */
    @Test
    @DisplayName("Test topologicalSort(Map); when HashMap(); then return Empty")
    void testTopologicalSort_whenHashMap_thenReturnEmpty() {
        // Arrange and Act
        List<Integer> actualTopologicalSortResult = GraphUtil.topologicalSort(new HashMap<>());

        // Assert
        assertTrue(actualTopologicalSortResult.isEmpty());
    }

    /**
     * Test {@link GraphUtil#isBipartite(Map)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()} add one.</li>
     *   <li>When {@link HashMap#HashMap()} one is {@link ArrayList#ArrayList()}.</li>
     *   <li>Then return {@code false}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#isBipartite(Map)}
     */
    @Test
    @DisplayName("Test isBipartite(Map); given ArrayList() add one; when HashMap() one is ArrayList(); then return 'false'")
    void testIsBipartite_givenArrayListAddOne_whenHashMapOneIsArrayList_thenReturnFalse() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);

        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, integerList);

        // Act and Assert
        assertFalse(GraphUtil.isBipartite(graph));
    }

    /**
     * Test {@link GraphUtil#isBipartite(Map)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()} add two.</li>
     *   <li>When {@link HashMap#HashMap()} one is {@link ArrayList#ArrayList()}.</li>
     *   <li>Then return {@code true}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#isBipartite(Map)}
     */
    @Test
    @DisplayName("Test isBipartite(Map); given ArrayList() add two; when HashMap() one is ArrayList(); then return 'true'")
    void testIsBipartite_givenArrayListAddTwo_whenHashMapOneIsArrayList_thenReturnTrue() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);

        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, integerList);

        // Act and Assert
        assertTrue(GraphUtil.isBipartite(graph));
    }

    /**
     * Test {@link GraphUtil#isBipartite(Map)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()} add two.</li>
     *   <li>When {@link HashMap#HashMap()} one is {@link ArrayList#ArrayList()}.</li>
     *   <li>Then return {@code true}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#isBipartite(Map)}
     */
    @Test
    @DisplayName("Test isBipartite(Map); given ArrayList() add two; when HashMap() one is ArrayList(); then return 'true'")
    void testIsBipartite_givenArrayListAddTwo_whenHashMapOneIsArrayList_thenReturnTrue2() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        integerList.add(2);

        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, integerList);

        // Act and Assert
        assertTrue(GraphUtil.isBipartite(graph));
    }

    /**
     * Test {@link GraphUtil#isBipartite(Map)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()}.</li>
     *   <li>When {@link HashMap#HashMap()} one is {@link ArrayList#ArrayList()}.</li>
     *   <li>Then return {@code true}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#isBipartite(Map)}
     */
    @Test
    @DisplayName("Test isBipartite(Map); given ArrayList(); when HashMap() one is ArrayList(); then return 'true'")
    void testIsBipartite_givenArrayList_whenHashMapOneIsArrayList_thenReturnTrue() {
        // Arrange
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, new ArrayList<>());

        // Act and Assert
        assertTrue(GraphUtil.isBipartite(graph));
    }

    /**
     * Test {@link GraphUtil#isBipartite(Map)}.
     * <ul>
     *   <li>When {@link HashMap#HashMap()}.</li>
     *   <li>Then return {@code true}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#isBipartite(Map)}
     */
    @Test
    @DisplayName("Test isBipartite(Map); when HashMap(); then return 'true'")
    void testIsBipartite_whenHashMap_thenReturnTrue() {
        // Arrange, Act and Assert
        assertTrue(GraphUtil.isBipartite(new HashMap<>()));
    }

    /**
     * Test {@link GraphUtil#shortestPath(Map, int, int)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()} add one.</li>
     *   <li>When {@link HashMap#HashMap()} one is {@link ArrayList#ArrayList()}.</li>
     *   <li>Then return Empty.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#shortestPath(Map, int, int)}
     */
    @Test
    @DisplayName("Test shortestPath(Map, int, int); given ArrayList() add one; when HashMap() one is ArrayList(); then return Empty")
    void testShortestPath_givenArrayListAddOne_whenHashMapOneIsArrayList_thenReturnEmpty() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(1);

        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, integerList);

        // Act
        List<Integer> actualShortestPathResult = GraphUtil.shortestPath(graph, 1, 3);

        // Assert
        assertTrue(actualShortestPathResult.isEmpty());
    }

    /**
     * Test {@link GraphUtil#shortestPath(Map, int, int)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()} add three.</li>
     *   <li>Then return size is two.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#shortestPath(Map, int, int)}
     */
    @Test
    @DisplayName("Test shortestPath(Map, int, int); given ArrayList() add three; then return size is two")
    void testShortestPath_givenArrayListAddThree_thenReturnSizeIsTwo() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(3);
        integerList.add(2);

        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, integerList);

        // Act
        List<Integer> actualShortestPathResult = GraphUtil.shortestPath(graph, 1, 3);

        // Assert
        assertEquals(2, actualShortestPathResult.size());
        assertEquals(1, actualShortestPathResult.get(0).intValue());
        assertEquals(3, actualShortestPathResult.get(1).intValue());
    }

    /**
     * Test {@link GraphUtil#shortestPath(Map, int, int)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()} add three.</li>
     *   <li>Then return size is two.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#shortestPath(Map, int, int)}
     */
    @Test
    @DisplayName("Test shortestPath(Map, int, int); given ArrayList() add three; then return size is two")
    void testShortestPath_givenArrayListAddThree_thenReturnSizeIsTwo2() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        integerList.add(3);
        integerList.add(2);

        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, integerList);

        // Act
        List<Integer> actualShortestPathResult = GraphUtil.shortestPath(graph, 1, 3);

        // Assert
        assertEquals(2, actualShortestPathResult.size());
        assertEquals(1, actualShortestPathResult.get(0).intValue());
        assertEquals(3, actualShortestPathResult.get(1).intValue());
    }

    /**
     * Test {@link GraphUtil#shortestPath(Map, int, int)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()} add two.</li>
     *   <li>When {@link HashMap#HashMap()} one is {@link ArrayList#ArrayList()}.</li>
     *   <li>Then return Empty.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#shortestPath(Map, int, int)}
     */
    @Test
    @DisplayName("Test shortestPath(Map, int, int); given ArrayList() add two; when HashMap() one is ArrayList(); then return Empty")
    void testShortestPath_givenArrayListAddTwo_whenHashMapOneIsArrayList_thenReturnEmpty() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);

        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, integerList);

        // Act
        List<Integer> actualShortestPathResult = GraphUtil.shortestPath(graph, 1, 3);

        // Assert
        assertTrue(actualShortestPathResult.isEmpty());
    }

    /**
     * Test {@link GraphUtil#shortestPath(Map, int, int)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()}.</li>
     *   <li>When {@link HashMap#HashMap()} one is {@link ArrayList#ArrayList()}.</li>
     *   <li>Then return Empty.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#shortestPath(Map, int, int)}
     */
    @Test
    @DisplayName("Test shortestPath(Map, int, int); given ArrayList(); when HashMap() one is ArrayList(); then return Empty")
    void testShortestPath_givenArrayList_whenHashMapOneIsArrayList_thenReturnEmpty() {
        // Arrange
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, new ArrayList<>());

        // Act
        List<Integer> actualShortestPathResult = GraphUtil.shortestPath(graph, 1, 3);

        // Assert
        assertTrue(actualShortestPathResult.isEmpty());
    }

    /**
     * Test {@link GraphUtil#shortestPath(Map, int, int)}.
     * <ul>
     *   <li>When {@link HashMap#HashMap()}.</li>
     *   <li>Then return Empty.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#shortestPath(Map, int, int)}
     */
    @Test
    @DisplayName("Test shortestPath(Map, int, int); when HashMap(); then return Empty")
    void testShortestPath_whenHashMap_thenReturnEmpty() {
        // Arrange and Act
        List<Integer> actualShortestPathResult = GraphUtil.shortestPath(new HashMap<>(), 1, 3);

        // Assert
        assertTrue(actualShortestPathResult.isEmpty());
    }

    /**
     * Test {@link GraphUtil#shortestPath(Map, int, int)}.
     * <ul>
     *   <li>When {@link HashMap#HashMap()}.</li>
     *   <li>Then return size is one.</li>
     * </ul>
     * <p>
     * Method under test: {@link GraphUtil#shortestPath(Map, int, int)}
     */
    @Test
    @DisplayName("Test shortestPath(Map, int, int); when HashMap(); then return size is one")
    void testShortestPath_whenHashMap_thenReturnSizeIsOne() {
        // Arrange and Act
        List<Integer> actualShortestPathResult = GraphUtil.shortestPath(new HashMap<>(), 3, 3);

        // Assert
        assertEquals(1, actualShortestPathResult.size());
        assertEquals(3, actualShortestPathResult.get(0).intValue());
    }
}
