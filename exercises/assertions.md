# On assertions

Answer the following questions:

1. The following assertion fails `assertTrue(3 * .4 == 1.2)`. Explain why and describe how this type of check should be done.

2. What is the difference between `assertEquals` and `assertSame`? Show scenarios where they produce the same result and scenarios where they do not produce the same result.

3. In classes we saw that `fail` is useful to mark code that should not be executed because an exception was expected before. Find other uses for `fail`. Explain the use case and add an example.

4. In JUnit 4, an exception was expected using the `@Test` annotation, while in JUnit 5 there is a special assertion method `assertThrows`. In your opinion, what are the advantages of this new way of checking expected exceptions?

## Answer

1. This assertion fails, because it verifies floating-point numbers. Their result might not be exactly 1.2 due to the imprecision introduced during the calculation. Instead, the result could be a very close approximation, such as 1.2000000000000002, which causes the comparison 3 * 0.4 == 1.2 to fail.

2. In Java, the concepts of assertEquals and assertSame are used in unit testing
    - `assertEquals(expected, actual)` checks if the two values are equal. It uses the `equals()` method to compare the objects, which means if it `equals()` method does not check the object reference, it will check for value equality only rather than identity equality.
    - `assertSame(expected, actual)` checks if the two values refer to the same object in memory. It uses the equality operator to verify that both actual and expected are pointing to the same object.
