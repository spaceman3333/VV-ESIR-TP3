package fr.istic.vv;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class BinaryHeap<T> {

    private final Comparator<T> comp; // comparator, used for comparisons
    private final ArrayList<T> data;  // stores all the elements of the heap

    // Constructor (creates heap and sets comparator)
    public BinaryHeap(Comparator<T> comp) {
        this.comp = comp;
        this.data = new ArrayList<>();
    }

    // Adds an element to the Heap
    public void push(T element) {
        if (element == null) {
            throw new NullPointerException("Cannot insert null into the heap");
        }
        data.add(element); // just add to the end for now
        int newElementPosition = data.size() - 1;
        while (newElementPosition > 0) {
            int parent = (newElementPosition - 1) / 2; // find parent
            if (comp.compare(data.get(newElementPosition), data.get(parent)) < 0) { // if child is smaller
                T tmp = data.get(newElementPosition); // swap
                data.set(newElementPosition, data.get(parent));
                data.set(parent, tmp);
                newElementPosition = parent; // move up
            } else {
                break; // done, heap is correct
            }
        }
    }

    // Removes the top (smallest/biggest element depending on comparator)
    public T pop() {
        if (data.isEmpty()) {
            throw new NoSuchElementException("empty!"); // can't pop from empty heap
        }
        T top = data.get(0); // root element
        T last = data.remove(data.size() - 1); // remove last element
        if (!data.isEmpty()) { // if there is anything left
            data.set(0, last); // move last to root
            int i = 0; // start bubbling down
            while (true) {
                int left = 2 * i + 1;
                int smallest = i;

                if (left < data.size() && comp.compare(data.get(left), data.get(i)) < 0) {
                    smallest = left;
                }

                if (smallest == i) { // if nothing to swap
                    break;
                }

                // swap
                T tmp = data.get(i);
                data.set(i, data.get(smallest));
                data.set(smallest, tmp);

                i = smallest; // move to the position of the swapped child
            }
        }
        return top; // return the minimum/maximum as per heap type
    }

    // Returns but does not remove the smallest (or biggest) element
    public T peek() {
        if (data.isEmpty()) {
            throw new NoSuchElementException("nothing to peek at"); // trying to peek empty heap
        }
        return data.get(0); // root is always the smallest
    }

    // Returns the number of elements in the heap
    public int count() {
        return data.size(); // size of underlying array list is the count
    }

    @Override
    public String toString() {
        return "BinaryHeap: " + data;
    }
}