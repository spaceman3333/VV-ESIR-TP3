package fr.istic.vv;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {

    // -------- Constructor Tests --------

    @Test
    void testConstructorWithValidDate() {
        Date date = new Date(15, 5, 2023);
        assertEquals("15/05/2023", date.toString());
    }

    @Test
    void testConstructorInvalidDayOverRange() {
        assertThrows(IllegalArgumentException.class, () -> new Date(32, 5, 2023));
    }

    @Test
    void testConstructorInvalidDayUnderRange() {
        assertThrows(IllegalArgumentException.class, () -> new Date(0, 5, 2023));
    }

    @Test
    void testConstructorInvalidMonthUnderRange() {
        assertThrows(IllegalArgumentException.class, () -> new Date(15, 0, 2023));
    }

    @Test
    void testConstructorInvalidMonthOverRange() {
        assertThrows(IllegalArgumentException.class, () -> new Date(15, 13, 2023));
    }

    @Test
    void testConstructorInvalidYear() {
        assertThrows(IllegalArgumentException.class, () -> new Date(15, 5, -1));
    }

    @Test
    void testConstructorInvalidLeapYear() {
        assertThrows(IllegalArgumentException.class, () -> new Date(29, 2, 2019)); // Non-leap year
    }

    // -------- Tests for isValidDate method --------

    @Test
    void testIsValidDateLeapYear() {
        assertTrue(Date.isValidDate(29, 2, 2020)); // Leap year
    }

    @Test
    void testIsValidDateNonLeapYear() {
        assertFalse(Date.isValidDate(29, 2, 2019)); // Non-leap year
    }

    @Test
    void testIsValidDateInvalidDayForMonth() {
        assertFalse(Date.isValidDate(31, 4, 2023)); // April has only 30 days
    }

    @Test
    void testIsValidDateTypicalValidDate() {
        assertTrue(Date.isValidDate(15, 5, 2023)); // Simple valid date
    }

    @Test
    void testIsValidDateNotLeapYearCentury() {
        assertFalse(Date.isValidDate(29, 2, 2100)); // 2100 is not a leap year
    }

    @Test
    void testIsValidDateInvalidMonthUnderRange() {
        assertFalse(Date.isValidDate(15, 0, 2023)); // Month 0 is invalid
    }

    @Test
    void testIsValidDateInvalidMonthOverRange() {
        assertFalse(Date.isValidDate(15, 13, 2023)); // Month 13 is invalid
    }

    // -------- Tests for isLeapYear method --------

    @Test
    void testIsLeapYearTypicalLeapYear() {
        assertTrue(Date.isLeapYear(2020)); // Divisible by 4, not by 100
    }

    @Test
    void testIsLeapYearNonLeapYearCentury() {
        assertFalse(Date.isLeapYear(1900)); // Divisible by 100, not by 400
    }

    @Test
    void testIsLeapYearLeapCenturyYear() {
        assertTrue(Date.isLeapYear(2000)); // Divisible by 400
    }

    @Test
    void testIsLeapYearNonLeapYearTypical() {
        assertFalse(Date.isLeapYear(2023)); // Not divisible by 4
    }

    @Test
    void testIsLeapYearDivisibleBy400() {
        assertTrue(Date.isLeapYear(400)); // Divisible by 400
    }

    @Test
    void testIsLeapYearDivisibleBy100Only() {
        assertFalse(Date.isLeapYear(100)); // Divisible by 100 but not 400
    }

    @Test
    void testIsLeapYearNotDivisibleBy4() {
        assertFalse(Date.isLeapYear(401)); // Not divisible by 4 at all
    }

    // -------- Tests for nextDate method --------

    @Test
    void testNextDateTypicalDay() {
        Date date = new Date(14, 5, 2023);
        Date next = date.nextDate();
        assertEquals("15/05/2023", next.toString());
    }

    @Test
    void testNextDateEndOfMonth() {
        Date date = new Date(31, 5, 2023);
        Date next = date.nextDate();
        assertEquals("01/06/2023", next.toString());
    }

    @Test
    void testNextDateLeapYearTransition() {
        Date date = new Date(28, 2, 2020); // Leap year
        Date next = date.nextDate();
        assertEquals("29/02/2020", next.toString());
    }

    @Test
    void testNextDateEndOfYear() {
        Date date = new Date(31, 12, 2023);
        Date next = date.nextDate();
        assertEquals("01/01/2024", next.toString());
    }

    // -------- Tests for previousDate method --------

    @Test
    void testPreviousDateTypicalDay() {
        Date date = new Date(15, 5, 2023);
        Date previous = date.previousDate();
        assertEquals("14/05/2023", previous.toString());
    }

    @Test
    void testPreviousDateLeapYearTransition() {
        Date date = new Date(1, 3, 2020); // Leap year
        Date previous = date.previousDate();
        assertEquals("29/02/2020", previous.toString());
    }

    @Test
    void testPreviousDateEndOfYear() {
        Date date = new Date(1, 1, 2024);
        Date previous = date.previousDate();
        assertEquals("31/12/2023", previous.toString());
    }

    // -------- Tests for compareTo method --------

    @Test
    void testCompareToEqualDates() {
        Date date1 = new Date(1, 1, 2023);
        Date date2 = new Date(1, 1, 2023);
        assertEquals(0, date1.compareTo(date2));
    }

    @Test
    void testCompareToDate1AfterDate2() {
        Date date1 = new Date(1, 1, 2023);
        Date date2 = new Date(31, 12, 2022);
        assertTrue(date1.compareTo(date2) > 0);
    }

    @Test
    void testCompareToDate1BeforeDate2() {
        Date date1 = new Date(31, 12, 2022);
        Date date2 = new Date(1, 1, 2023);
        assertTrue(date1.compareTo(date2) < 0);
    }

    @Test
    void testCompareToNull() {
        Date date = new Date(1, 1, 2023);
        assertThrows(NullPointerException.class, () -> date.compareTo(null));
    }

    @Test
    void testCompareToDifferentMonths() {
        Date date1 = new Date(15, 6, 2023);
        Date date2 = new Date(15, 12, 2023);
        assertTrue(date1.compareTo(date2) < 0);
    }

    @Test
    void testCompareToSameDayDifferentYear() {
        Date date1 = new Date(15, 5, 2022);
        Date date2 = new Date(15, 5, 2023);
        assertTrue(date1.compareTo(date2) < 0);
    }

    @Test
    void testCompareToSameDayAndMonthDifferentYear() {
        Date date1 = new Date(15, 5, 2023);
        Date date2 = new Date(15, 5, 2022);
        assertTrue(date1.compareTo(date2) > 0);
    }

    // -------- Tests for equals method --------

    @Test
    void testEquals_SameObject() {
        Date date = new Date(15, 5, 2023);
        assertTrue(date.equals(date)); // Same reference
    }

    @Test
    void testEquals_DifferentType() {
        Date date = new Date(15, 5, 2023);
        assertFalse(date.equals("String")); // Different class
    }

    @Test
    void testEquals_EquivalentObjects() {
        Date date1 = new Date(15, 5, 2023);
        Date date2 = new Date(15, 5, 2023);
        assertTrue(date1.equals(date2)); // Same values
    }

    @Test
    void testEquals_Null() {
        Date date = new Date(15, 5, 2023);
        assertFalse(date.equals(null)); // Comparison with null
    }

    // -------- Tests for toString method --------

    @Test
    void testToString_SingleDigitDayAndMonth() {
        Date date = new Date(1, 1, 2023);
        assertEquals("01/01/2023", date.toString());
    }
}
