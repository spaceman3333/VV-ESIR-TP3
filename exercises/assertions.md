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
    - `assertSame(expected, actual)` checks if the two values refer to the same object in memory. It uses the equality operator to verify that both actual and expected are pointing to the same object.\
      
      Scenario where they produce the same result:
      ```java
      String s1 = "test";
      String s2 = "test";
      assertEquals(s1, s2); // Passes (content equality)
      assertSame(s1, s2);   // Passes (same reference)
      ```

      Scenario  where they DO NOT produce the same result:
      ```java
      String s1 = new String("test");
      String s2 = "test";
      assertEquals(s1, s2); // Passes (content equality)
      assertSame(s1, s2);   // Fails (different references)
      ```

3. Other Uses for `fail`:
    - We can use `fail` to test behavior in conditional logic or complex flow control. For example:
      ```java
      @Test
      public void testUnreachableCode() {
          int value = 10;

          if (value > 5) {
              // Expected execution
          } else {
              fail("This code should never be reached for value > 5");
          }
      }
      ```
    - If we use test-driven development stategy, we can temporarily mark a test as incomplete to remind the developer to implement the test logic later. Example:
      ```java
      @Test
      public void testFeatureNotImplemented() {
          fail("Test not implemented yet");
      }
      ```
    - We can verify that all elements in a collection meet a specific condition. Example:
      ```java
      @Test
      public void testNoInvalidData() {
          List<String> names = List.of("Alice", "Bob", "Charlie");

          for (String name : names) {
              if (name == null || name.isEmpty()) {
            fail("Name should not be null or empty");
              }
          }
      }
      ```
    - We can ensure that a specific block of code executes without unexpected exceptions. Example:
      ```java
      @Test
      public void testNoExceptionsThrown() {
          try {
              // Code that must not throw exceptions
          } catch (Exception e) {
              fail("Unexpected exception: " + e.getMessage());
          }
      }
      ```
4. This new special assertion method `assertThrows` have some advantages, like:
    - It isolates the exception-throwing code, improving readability and making tests easier to understand.
    - We can test multiple behaviors in a single test. For example:
      ```java
      @Test
      public void testMultipleBehaviors() {
          assertThrows(IllegalArgumentException.class, () -> myService.doSomethingInvalid());
          assertThrows(NullPointerException.class, () -> myService.doSomethingElseInvalid());
      }
      ```
      Although, personally we wouldn't prefer to use unit tests like that, because it complexifies readability.
    - We can verify exception details, such as the message, cause, or custom fields, not only the fact of throwing.
    - Also, we can make some throws directly in test, that won't be considered as the test assertion. Example:\
      In JUnit 4:
      ```java
      @Test(expected = IllegalArgumentException.class)
      public void testFalsePositive() {
          throw new IllegalArgumentException(); // Passes, but this is not the intended place for the exception.
      }
      ```
      In JUnit 5:
      ```java
      @Test
      public void testNoFalsePositive() {
          assertThrows(IllegalArgumentException.class, () -> {
              throw new IllegalArgumentException(); // Explicitly localized
          });
      }
      ```
