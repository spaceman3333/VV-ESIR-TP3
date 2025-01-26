# Test the Date class

Implement a class `Date` with the interface shown below:

```java
class Date implements Comparable<Date> {

    public Date(int day, int month, int year) { ... }

    public static boolean isValidDate(int day, int month, int year) { ... }

    public static boolean isLeapYear(int year) { ... }

    public Date nextDate() { ... }

    public Date previousDate { ... }

    public int compareTo(Date other) { ... }

}
```

The constructor throws an exception if the three given integers do not form a valid date.

`isValidDate` returns `true` if the three integers form a valid year, otherwise `false`.

`isLeapYear` says if the given integer is a leap year.

`nextDate` returns a new `Date` instance representing the date of the following day.

`previousDate` returns a new `Date` instance representing the date of the previous day.

`compareTo` follows the `Comparable` convention:

* `date.compareTo(other)` returns a positive integer if `date` is posterior to `other`
* `date.compareTo(other)` returns a negative integer if `date` is anterior to `other`
* `date.compareTo(other)` returns `0` if `date` and `other` represent the same date.
* the method throws a `NullPointerException` if `other` is `null` 

Design and implement a test suite for this `Date` class.
You may use the test cases discussed in classes as a starting point. 
Also, feel free to add any extra method you may need to the `Date` class.


Use the following steps to design the test suite:

1. With the help of *Input Space Partitioning* design a set of initial test inputs for each method. Write below the characteristics and blocks you identified for each method. Specify which characteristics are common to more than one method.
2. Evaluate the statement coverage of the test cases designed in the previous step. If needed, add new test cases to increase the coverage. Describe below what you did in this step.
3. If you have in your code any predicate that uses more than two boolean operators check if the test cases written to far satisfy *Base Choice Coverage*. If needed add new test cases. Describe below how you evaluated the logic coverage and the new test cases you added.
4. Use PIT to evaluate the test suite you have so far. Describe below the mutation score and the live mutants. Add new test cases or refactor the existing ones to achieve a high mutation score.

Use the project in [tp3-date](../code/tp3-date) to complete this exercise.

## Answer


### 1. Design Initial Test Cases using Input Space Partitioning

Using **Input Space Partitioning**, the test cases can be identified based on valid and invalid ranges for inputs for each method. Characteristics and their blocks are detailed below:

1. Constructor: `new Date(day, month, year)`
    **Characteristics**:
   - **1:** Valid and invalid ranges for the `day` input:
       - Block 1: `day` within valid range (e.g., 1 to the number of days in the month)
       - Block 2: `day` out of range (e.g., less than 1 or greater than the allowed maximum)

   - **2:** Valid and invalid ranges for the `month` input:
       - Block 1: `month` within valid range (1-12)
       - Block 2: `month` out of range (e.g., `<1` or `>12`)

   - **3:** Valid and invalid ranges for the `year` input:
       - Block 1: Valid positive `year`
       - Block 2: Invalid `year` (e.g., `year < 1`)

    **Test Cases for found characteristics**:
    
    | Test Case | Day | Month | Year | Expected Output  |
    |-----------|-----|-------|------|------------------|
    | 1         | 15  | 5     | 2023 | Valid date       |
    | 2         | 32  | 5     | 2023 | Exception thrown |
    | 3         | 0   | 5     | 2023 | Exception thrown |
    | 4         | 15  | 0     | 2023 | Exception thrown |
    | 5         | 15  | 13    | 2023 | Exception thrown |
    | 6         | 15  | 5     | -1   | Exception thrown |

---

2. `isValidDate(day, month, year)`
    **Characteristics**:
   - Same as the constructor, testing all valid and invalid input combinations, but this time focusing on return values of `true/false`.

    **Test Cases**:
    
    | Test Case | Day | Month | Year | Expected Output |
    |-----------|-----|-------|------|-----------------|
    | 1         | 29  | 2     | 2020 | `true`          |
    | 2         | 29  | 2     | 2019 | `false`         |
    | 3         | 31  | 4     | 2023 | `false`         |
    | 4         | 15  | 5     | 2023 | `true`          |

---

3. `isLeapYear(year)`
    **Characteristics**:
   - **1:** Whether the year is a leap year or not:
     - Block 1: Divisible by 4 and not 100, or divisible by 400
     - Block 2: Not divisible by 4, or divisible by 100 but not 400

    **Test Cases for found characteristics**:
    
    | Test Case | Year | Expected Output |
    |-----------|------|-----------------|
    | 1         | 2020 | `true`          |
    | 2         | 1900 | `false`         |
    | 3         | 2000 | `true`          |
    | 4         | 2023 | `false`         |

---

4. `nextDate()`
    **Characteristics**:
   - **1:** Transition between days, months, and years:
     - Block 1: Normal next day
     - Block 2: End of month transition
     - Block 3: Leap year February transition
     - Block 4: End of year transition
    
    **Test Cases for found characteristics**:

    | Test Case | Current Date | Expected Next Date |
    |-----------|--------------|--------------------|
    | 1         | 14/05/2023   | 15/05/2023         |
    | 2         | 31/05/2023   | 01/06/2023         |
    | 3         | 28/02/2020   | 29/02/2020 (Leap)  |
    | 4         | 31/12/2023   | 01/01/2024         |

---

5. `previousDate()`
    **Characteristics**:
    - Similar to `nextDate()` but in reverse.
    
    **Test Cases**:

    | Test Case | Current Date | Expected Previous Date |
    |-----------|--------------|------------------------|
    | 1         | 15/05/2023   | 14/05/2023             |
    | 2         | 01/03/2020   | 29/02/2020 (Leap)      |
    | 3         | 01/01/2024   | 31/12/2023             |

---

6. `compareTo(other)`
    **Characteristics**:
   - **1:** Date order comparison:
     - Block 1: `date1 == date2`
     - Block 2: `date1 < date2`
     - Block 3: `date1 > date2`
     - Block 4: Comparison to `null`
    
    **Test Cases**:

    | Test Case | Date1      | Date2      | Expected Output        |
    |-----------|------------|------------|------------------------|
    | 1         | 01/01/2023 | 01/01/2023 | 0                      |
    | 2         | 01/01/2023 | 31/12/2022 | Positive Integer       |
    | 3         | 31/12/2022 | 01/01/2023 | Negative Integer       |
    | 4         | 01/01/2023 | null       | `NullPointerException` |

---

### 2. Evaluate Statement Coverage and Add Test Cases

**Evaluation**:
- Based on the initial tests, most of the statements in the methods are covered.
- Additional edge cases to improve coverage:
    - For `isLeapYear`: Test additional edge years like `400`, `100`, and `401`.
    - For `isValidDate`: Add cases where the month is invalid but the day and year are valid.
    - For `compareTo`: Ensure cases are tested where year, month, and day differ independently.

**Added Test Cases**:
1. `isValidDate(29, 2, 2100)` → `false` (Century year not a leap year).
2. `compareTo` for dates differing only in months: `Date(15, 6, 2023).compareTo(Date(15, 12, 2023))`.

---

### 3. Check Base Choice Coverage

**Logic Evaluation**:
- The only compound predicate is in `isLeapYear`:
  ```java
  (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
  ```
    - Base Choice:
        - Base case: true for the first term `(year % 4 == 0)` and false for others.
        - Added cases to satisfy:
            - `(year % 4 == 0 && !(year % 100 != 0)) || !(year % 400 == 0)`

**New Test Cases for Logic Coverage**:

| Test Case | Year | Expected Output |
|-----------|------|-----------------|
| 1         | 400  | `true`          |
| 2         | 100  | `false`         |
| 3         | 401  | `false`         |

---

### Summary:
- Test cases cover the constructor, validation, leap year logic, and transitions for `nextDate` and `previousDate`.
- Edge cases and logical predicate variations are added to ensure both **statement** and **logic coverage** are satisfied.

---

### 4. Mutation Score and Live Mutants Overview

After executing the `pitest:mutationCoverage` command, the report [202501261853](../code/tp3-date/src/pit-reports/202501261853) showed that the mutation score is 78%.

Live Mutants are mutations introduced into the code that are not killed by the test suite, meaning the tests do not detect the change. 
Live mutants suggest gaps in the test coverage or inadequately written test cases.
What we have observed int hre PIT report :
* Many mutants were killed, showing good test coverage for methods like `isLeapYear`, `compareTo`, and `toString`.
* However, there are some mutants that have survived :
  * Conditional and boundary changes in the `equals` method survived, highlighting missing test cases for equality checks.
  * Some survived conditions in the `compareTo` method, suggesting edge cases for day, month, and year comparisons are not tested.
  * Surviving mutations in `isValidDate` indicate incomplete validation for edge cases (e.g., boundary years like 0 or negative years).

Changes that we have made to Improve Mutation Score :

Refactored Existing Tests :
- **Included edge cases for `isValidDate` :**
    - Added tests for invalid combinations, such as leap year checks in constructors (`testConstructorInvalidLeapYear`).
- **For `compareTo`, verified behavior with dates involving :**
    - Same day across different years (`testCompareToSameDayDifferentYear`).
    - Same day and month but in different years (`testCompareToSameDayAndMonthDifferentYear`).
- **Extended tests for `equals` to handle additional scenarios, such as :**
    - Comparing with null (`testEquals_Null`).
    - Comparing with different types (`testEquals_DifferentType`).
    - Confirming when the objects are logically equivalent (`testEquals_EquivalentObjects`).

Added New Test Cases :
- **Constructor :**
    - Included leap year validation within the constructor (`testConstructorInvalidLeapYear`).

- **`equals` method :**
    - Added test for comparing the same object reference (`testEquals_SameObject`).

- **`toString` method :**
    - Added test for handling single-digit day and month formatting correctly (`testToString_SingleDigitDayAndMonth`).

#### Mutation score after changes

After improving the [DateTest.java](../code/tp3-date/src/test/java/fr/istic/vv/DateTest.java) file, and re-executing `pitest:mutationCoverage` command, the report [202501261932](../code/tp3-date/src/pit-reports/202501261932) showed improved mutation score (from 78% to 89%).
There is still a room to improve, but we think that it is already a good score improvement.