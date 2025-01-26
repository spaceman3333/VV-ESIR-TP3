package fr.istic.vv;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryHeapTest {

    @Test
    void testPush_ValidElements() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());
        heap.push(10);
        heap.push(5);
        heap.push(20);

        assertEquals(5, heap.peek()); // Smallest element must be the root
    }

    @Test
    void testPush_DuplicateElements() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());
        heap.push(5);
        heap.push(5);
        heap.push(10);

        assertEquals(5, heap.peek()); // Duplication allowed, still smallest
        assertEquals(3, heap.count()); // Total items in heap
    }

    @Test
    void testPush_NullElement() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());
        assertThrows(NullPointerException.class, () -> heap.push(null));
    }

    @Test
    void testPop_EmptyHeap() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());
        assertThrows(NoSuchElementException.class, heap::pop); // Cannot pop from an empty heap
    }

    @Test
    void testPop_SingleElement() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());
        heap.push(1);

        assertEquals(1, heap.pop()); // Removing the only element
        assertEquals(0, heap.count()); // Heap is empty now
    }

    @Test
    void testPop_MultipleElements() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());
        heap.push(7);
        heap.push(3);
        heap.push(10);
        heap.push(1);

        assertEquals(1, heap.pop()); // Smallest (1) removed
        assertEquals(3, heap.pop()); // Next smallest (3) removed
        assertEquals(2, heap.count()); // Two elements remain
    }

    @Test
    void testPeek_EmptyHeap() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());
        assertThrows(NoSuchElementException.class, heap::peek); // Cannot peek into an empty heap
    }

    @Test
    void testPeek_NonEmptyHeap() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());
        heap.push(9);
        heap.push(6);
        heap.push(11);

        assertEquals(6, heap.peek()); // Smallest element must always be accessible
    }

    @Test
    void testCount_EmptyHeap() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());
        assertEquals(0, heap.count()); // Heap starts empty
    }

    @Test
    void testCount_AfterOperations() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());
        heap.push(4);
        heap.push(12);
        heap.pop();

        assertEquals(1, heap.count()); // One element left
    }

    @Test
    void testHeapProperty_AfterPushAndPop() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());
        heap.push(15);
        heap.push(10);
        heap.push(20);
        heap.push(8);
        heap.push(25);

        assertEquals(8, heap.pop()); // Smallest element removed
        assertEquals(10, heap.pop()); // Next smallest
        assertEquals(3, heap.count()); // Three elements remain
        assertEquals(15, heap.peek()); // Next root
    }

    @Test
    void testToString() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());

        // Initial empty heap
        assertEquals("BinaryHeap: []", heap.toString(), "Empty heap should show as an empty list");

        // Add elements
        heap.push(10);
        heap.push(5);
        heap.push(7);

        // Ensure it outputs the elements in the order they reside in the underlying heap array
        String heapString = heap.toString();
        assertTrue(heapString.startsWith("BinaryHeap: ["));
        assertTrue(heapString.contains("5, 10, 7"));
        assertTrue(heapString.endsWith("]"));
    }

    @Test
    void testCompareRight_TrueAndFalse() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());

        // Push elements to create left and right nodes of the root
        heap.push(10); // root
        heap.push(30); // left child
        heap.push(20); // right child (smaller than left)

        // Pop one element to test comp.compare on `right`
        heap.pop();

        // Push elements to ensure compare(right, smallest) is false
        heap.push(40); // Push high value, bubbling will not swap
        heap.pop();
    }

    @Test
    void testPush_AtBoundaryConditions() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());

        heap.push(3);
        heap.push(1); // should reorder as 1, 3
        heap.push(2); // should reorder as 1, 3, 2 (array may have [1, 3, 2])

        assertEquals(1, heap.peek()); // Verify smallest remains root
        assertEquals(3, heap.count()); // Check correct count
    }

    @Test
    void testPop_EdgeCaseForInfiniteLoop() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());

        // Edge case: Pop from a heap built with edge conditions
        heap.push(40);
        heap.push(30);
        heap.push(20);
        heap.push(10);

        assertEquals(10, heap.pop()); // Remove root
        assertEquals(20, heap.pop()); // Remove next smallest
    }

}