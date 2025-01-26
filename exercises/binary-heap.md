# Implementing and testing a binary heap

A [*binary heap*](https://en.wikipedia.org/wiki/Binary_heap) is a data structure that contains comparable objects and it is able to efficiently return the lowest element.
This data structure relies on a binary tree to keep the insertion and deletion operations efficient. It is the base of the [*Heapsort* algorithm](https://en.wikipedia.org/wiki/Heapsort).

Implement a `BinaryHeap` class with the following interface:

```java
class BinaryHeap<T> {

    public BinaryHeap(Comparator<T> comparator) { ... }

    public T pop() { ... }

    public T peek() { ... }

    public void push(T element) { ... }

    public int count() { ... }

}
```

A `BinaryHeap` instance is created using a `Comparator` object that represents the ordering criterion between the objects in the heap.
`pop` returns and removes the minimum object in the heap. If the heap is empty it throws a `NotSuchElementException`.
`peek` similar to `pop`, returns the minimum object but it does not remove it from the `BinaryHeap`.
`push` adds an element to the `BinaryHeap`.
`count` returns the number of elements in the `BinaryHeap`.

Design and implement a test suite for this `BinaryHeap` class.
Feel free to add any extra method you may need.

Use the following steps to design the test suite:

1. With the help of *Input Space Partitioning* design a set of initial test inputs for each method. Write below the characteristics and blocks you identified for each method. Specify which characteristics are common to more than one method.
2. Evaluate the statement coverage of the test cases designed in the previous step. If needed, add new test cases to increase the coverage. Describe below what you did in this step.
3. If you have in your code any predicate that uses more than two boolean operators check if the test cases written to far satisfy *Base Choice Coverage*. If needed add new test cases. Describe below how you evaluated the logic coverage and the new test cases you added.
4. Use PIT to evaluate the test suite you have so far. Describe below the mutation score and the live mutants. Add new test cases or refactor the existing ones to achieve a high mutation score.

Use the project in [tp3-heap](../code/tp3-heap) to complete this exercise.

## Answer

1.
   1. **`push(T element)`** :
      - Characteristics :
        - Type of element : valid, invalid (`null`, incompatible type not allowed by comparator).
        - Position of the element: smallest, largest, middle value.
      - Blocks Identified :
        - Valid element (inserted into an empty heap and heap with existing elements).
        - Invalid element (`null`).
        - Inserting duplicate elements.
    
   2. **`pop()`** :
      - Characteristics :
        - Heap state : empty, contains one element, contains multiple elements.
      - Blocks Identified :
        - Empty heap (expect `NoSuchElementException`).
        - Heap with one element.
        - Heap with multiple elements (ensures heap property after removal).
    
   3. **`peek()`** :
   - Characteristics :
     - Heap state : empty, non-empty (valid inputs).
   - Blocks Identified :
     - Empty heap (expect `NoSuchElementException`).
     - Non-empty heap (should always return the root without modification).
    
   4. **`count()`** :
      - Characteristics :
        - Heap size : zero, more than zero.
      - Blocks Identified :
        - Empty heap.
        - Counting after multiple operations (`push`, `pop`).

    Common Characteristics :
   - **Heap state** applies across all methods.
   - **Input validity** for all methods that take input (`push`).

2. For statement coverage we've used the `Run BinaryHeapTest with Coverage` command in InteliJ. 
Using the result of the coverage we have added new test cases to reach uncovered branches or scenarios.

Before adding new test use cases, the coverage was the following :

| Class,%    | Method,%   | Line,%       | Branch,%    |
|------------|------------|--------------|-------------|
| 100% (1/1) | 100% (6/6) | 100% (37/37) | 94% (17/18) |

The problem is in the `pop()` method where this condition is no fully covered :

```java
if (left < data.size() && comp.compare(data.get(left), data.get(i)) < 0) {
    smallest = left;
}
```
```
Hits: 10
left < data.size()
true hits: 5
false hits: 5
comp.compare(data.get(left), data.get(i)) < 0
true hits: 5
false hits: 0
```

After adding new test use case, the coverage is the following :

| Class,%    | Method,%   | Line,%       | Branch,%     |
|------------|------------|--------------|--------------|
| 100% (1/1) | 100% (6/6) | 100% (37/37) | 100% (18/18) |



