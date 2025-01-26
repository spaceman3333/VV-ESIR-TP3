# Detecting test smells with PMD

In folder [`pmd-documentation`](../pmd-documentation) you will find the documentation of a selection of PMD rules designed to catch test smells.
Identify which of the test smells discussed in classes are implemented by these rules.

Use one of the rules to detect a test smell in one of the following projects:

- [Apache Commons Collections](https://github.com/apache/commons-collections)
- [Apache Commons CLI](https://github.com/apache/commons-cli)
- [Apache Commons Math](https://github.com/apache/commons-math)
- [Apache Commons Lang](https://github.com/apache/commons-lang)

Discuss the test smell you found with the help of PMD and propose here an improvement.
Include the improved test code in this file.

## Answer
We have selected the [Apache Commons Lang](https://github.com/apache/commons-lang) project to identify potential code smells.

### Test Smell Detected
Using the PMD rule `JUnitTestContainsTooManyAsserts`, the test case `testToString` in the [`AnnotationUtilsTest.java`](https://github.com/apache/commons-lang/blob/master/src/test/java/org/apache/commons/lang3/AnnotationUtilsTest.java) file from the Apache Commons Lang project was found to contain multiple assertions. Specifically:

- The test checks multiple properties of the `toString` behavior of a custom annotation using several `assertTrue`.
- Each assertion validates a specific part of the generated string, such as:
    - Prefix presence.
    - Suffix presence.
    - Specific content values inside the string.
- This constitutes the "JUnitTestContainsTooManyAsserts" smell, as the test packs multiple logical validations into a single method.

### Proposed Improvements:
To enhance maintainability and test clarity, the `testToString` method should be refactored into smaller, focused test methods. Each method will validate one aspect of the functionality.

### Refactored Approach:
- One test method to validate the string's prefix and suffix.
- Another test method to validate the presence of specific content, such as `expected` values.
- A separate test method to check for the `timeout` value.

---

## Refactored Code
Here’s the proposed refactored version of `testToString`:

### Original Code:
```java
@Test
@TestMethodAnnotation(timeout = 666000)
public void testToString() {
    assertTimeoutPreemptively(Duration.ofSeconds(666L), () -> {
        final TestMethodAnnotation testAnnotation = getClass().getDeclaredMethod("testToString").getAnnotation(TestMethodAnnotation.class);

        final String annotationString = AnnotationUtils.toString(testAnnotation);
        assertTrue(annotationString.startsWith("@org.apache.commons.lang3.AnnotationUtilsTest$TestMethodAnnotation("));
        assertTrue(annotationString.endsWith(")"));
        assertTrue(annotationString.contains("expected=class org.apache.commons.lang3.AnnotationUtilsTest$TestMethodAnnotation$None"));
        assertTrue(annotationString.contains("timeout=666000"));
        assertTrue(annotationString.contains(", "));
    });
}
```

### Refactored Code:
```java
@Test
@TestMethodAnnotation(timeout = 666000)
public void testToStringPrefixAndSuffix() {
    assertTimeoutPreemptively(Duration.ofSeconds(666L), () -> {
        final TestMethodAnnotation testAnnotation = getClass().getDeclaredMethod("testToStringPrefixAndSuffix").getAnnotation(TestMethodAnnotation.class);

        final String annotationString = AnnotationUtils.toString(testAnnotation);
        assertTrue(annotationString.startsWith("@org.apache.commons.lang3.AnnotationUtilsTest$TestMethodAnnotation("));
        assertTrue(annotationString.endsWith(")"));
    });
}

@Test
@TestMethodAnnotation(timeout = 666000)
public void testToStringContainsExpected() {
    assertTimeoutPreemptively(Duration.ofSeconds(666L), () -> {
        final TestMethodAnnotation testAnnotation = getClass().getDeclaredMethod("testToStringContainsExpected").getAnnotation(TestMethodAnnotation.class);

        final String annotationString = AnnotationUtils.toString(testAnnotation);
        assertTrue(annotationString.contains("expected=class org.apache.commons.lang3.AnnotationUtilsTest$TestMethodAnnotation$None"));
    });
}

@Test
@TestMethodAnnotation(timeout = 666000)
public void testToStringContainsTimeout() {
    assertTimeoutPreemptively(Duration.ofSeconds(666L), () -> {
        final TestMethodAnnotation testAnnotation = getClass().getDeclaredMethod("testToStringContainsTimeout").getAnnotation(TestMethodAnnotation.class);

        final String annotationString = AnnotationUtils.toString(testAnnotation);
        assertTrue(annotationString.contains("timeout=666000"));
    });
}
```

---

### Advantages of Refactoring
1. **mproved Readability:** By refactoring, each test is focused on a single behavior, making it easier to read and understand.
2. **Simplified Debugging:** In case of failure, the specific behavior that failed is immediately identifiable without needing to analyze multiple assertions.
3. **Alignment with Best Practices:** The refactored code follows the principle of testing one behavior per test method, resulting in a more robust and maintainable test suite.
