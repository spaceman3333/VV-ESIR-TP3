# Balanced strings

A string containing grouping symbols `{}[]()` is said to be balanced if every open symbol `{[(` has a matching closed symbol `)]}` and the substrings before, after and between each pair of symbols is also balanced. The empty string is considered as balanced.

For example: `{[][]}({})` is balanced, while `][`, `([)]`, `{`, `{(}{}` are not.

Implement the following method:

```java
public static boolean isBalanced(String str) {
    ...
}
```

`isBalanced` returns `true` if `str` is balanced according to the rules explained above. Otherwise, it returns `false`.

Use the coverage criteria studied in classes as follows:

1. Use input space partitioning to design an initial set of inputs. Explain below the characteristics and partition blocks you identified.
2. Evaluate the statement coverage of the test cases designed in the previous step. If needed, add new test cases to increase the coverage. Describe below what you did in this step.
3. If you have in your code any predicate that uses more than two boolean operators, check if the test cases written so far satisfy *Base Choice Coverage*. If needed, add new test cases. Describe below how you evaluated the logic coverage and the new test cases you added.
4. Use PIT to evaluate the test suite you have so far. Describe below the mutation score and the live mutants. Add new test cases or refactor the existing ones to achieve a high mutation score.

Write below the actions you took on each step and the results you obtained.
Use the project in [tp3-balanced-strings](../code/tp3-balanced-strings) to complete this exercise.

## Answer

1. * String Length 
     * Empty string (`""`)
     * Single character (`"{"`, `")"`)
     * Multiple characters (`"{}"`, `"{[]}"`)

   * Balance State 
     * Balanced strings (`"{}"`, `"([])"`, `"{[()]}"`)
     * Unbalanced strings (`"("`, `"([)]"`, `"{(}{})"`)

   * Symbol Type Usage 
     * All three types used (`"{[()]}"`)
     * Only one type used (`"{}"`, `"[][]"`)
     * Mixed but incomplete pairs (`"{[}"`, `"({]"`)

   * Order of Symbols 
     * Correctly nested (`"{[()]}"`)
     * Incorrectly nested (`"([)]"`, `"[{]}"`)
     * Reversed symbols (`"]["`, `")("`)
   * Redundant Symbols
     * Extra opening brackets (`"({{"`)
     * Extra closing brackets (`"())"`)

2. The current implementation has the following critical code segments:
   1. **Loop Initialization and Execution**
       - `do { prev = str; str = str.replace(...) } while (!str.equals(prev));`
   2. **String Replacement for Pair Removal**
       - `str.replace("()", "").replace("[]", "").replace("{}","");`
   3. **Final Check**
       - `return str.isEmpty();`

   Current Test Cases Coverage:
   1. `"{[()]}"` → `true`
      * Covers correct nesting and successful pair removal. ✅

   2. `"([)]"` → `false`
      * Covers incorrect nesting. ✅

   3. `""` → `true`
      * Covers empty input. ✅

   4. `"({{"` → `false`
      * Covers extra opening brackets. ✅

   5. `")("` → `false`
      * Covers reversed order of brackets. ✅

   Uncovered Scenarios:
   1. Single Closing Bracket: `"]"` → Should return `false`.
   2. Single Opening Bracket: `"("` → Should return `false`.
   3. Balanced Repeated Pairs: `"()[]{}"` → Should return `true`.
   4. Long Balanced Nesting: `"({[]})"` → Should return `true`.
   5. Unbalanced with Extra Closing: `"(()))"` → Should return `false`.

    **By adding these cases, we achieve 100% statement coverage and ensure all logical paths are tested.**


3. The critical predicate in the current code is the condition in the while loop:
```java
while (!str.equals(prev));
```
This predicate involves one boolean operator (!), so Base Choice Coverage focuses on ensuring both possible outcomes (true and false) are tested.
The current test cases cover both outcomes of the predicate.\
However, to ensure thoroughness, testing inputs that require multiple iterations to stabilize the string will strengthen coverage.\
Input: `"((()))"` → Expected Output: `true`

This case forces multiple passes through the loop to fully eliminate pairs.
It ensures `str.equals(prev)` transitions from false to true after several iterations.

4. After running PIT, the mutation score achieved was 100%. The line coverage stands at 80% because PIT cannot mutate line 5, which initializes the constructor. However, this is not an issue since the constructor is private. I have committed the PIT report for your review.