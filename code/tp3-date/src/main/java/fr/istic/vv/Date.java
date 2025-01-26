package fr.istic.vv;

import java.time.LocalDate;

public class Date implements Comparable<Date> {
    private final int day;
    private final int month;
    private final int year;

    /**
     * Constructs a Date object with the specified day, month, and year.
     * Validates if the given day, month, and year form a valid date. If the date is invalid,
     * an IllegalArgumentException is thrown.
     *
     * @param day The day of the month (1-31, depending on the month and year).
     * @param month The month of the year (1 for January, 12 for December).
     * @param year The year (must be a positive integer).
     * @throws IllegalArgumentException if the provided date is not valid.
     */
    public Date(int day, int month, int year) {
        if (!isValidDate(day, month, year)) {
            throw new IllegalArgumentException("Invalid date provided: " + day + "/" + month + "/" + year);
        }
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Validates whether the provided day, month, and year combination forms a valid date.
     *
     * @param day The day of the month to validate.
     * @param month The month to validate (1 for January, 12 for December).
     * @param year The year to validate (must be a positive number).
     * @return true if the provided combination represents a valid date, false otherwise.
     */
    public static boolean isValidDate(int day, int month, int year) {
        // Check month range
        if (month < 1 || month > 12) {
            return false;
        }

        // Check year range
        if (year < 1) {
            return false;
        }

        // Days in month logic
        int[] daysInMonth = {31, isLeapYear(year) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        return day >= 1 && day <= daysInMonth[month - 1];
    }

    /**
     * Determines if the specified year is a leap year.
     * A year is a leap year if it is divisible by 4 but not divisible by 100,
     * or if it is divisible by 400.
     *
     * @param year The year to evaluate for being a leap year.
     * @return true if the specified year is a leap year, false otherwise.
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * Computes and returns the next day's date.
     * The method calculates the next date by incrementing the current date
     * by one day, taking into account month-end wrapping and leap years.
     *
     * @return A Date object representing the day following the current date.
     *         The returned date is guaranteed to be valid.
     */
    public Date nextDate() {
        LocalDate currentDate = LocalDate.of(year, month, day);
        LocalDate nextDate = currentDate.plusDays(1);
        return new Date(nextDate.getDayOfMonth(), nextDate.getMonthValue(), nextDate.getYear());
    }

    /**
     * Computes and returns the date corresponding to the previous day.
     * The method calculates the previous date by subtracting one day from the current date.
     *
     * @return A new Date object representing the previous calendar day.
     *         The returned date is validated to ensure it is a valid calendar date.
     */
    public Date previousDate() {
        LocalDate currentDate = LocalDate.of(year, month, day);
        LocalDate previousDate = currentDate.minusDays(1);
        return new Date(previousDate.getDayOfMonth(), previousDate.getMonthValue(), previousDate.getYear());
    }

    /**
     * Compares this Date object with another Date object for order.
     * The comparison is performed first by year, then by month, and finally by day.
     *
     * @param other The other Date object to compare to. Must not be null.
     * @return A negative integer, zero, or a positive integer as this Date
     *         is earlier than, equal to, or later than the specified Date.
     * @throws NullPointerException if the specified Date object is null.
     */
    @Override
    public int compareTo(Date other) {
        if (other == null) {
            throw new NullPointerException("Cannot compare to null");
        }
        if (this.year != other.year) {
            return this.year - other.year;
        }
        if (this.month != other.month) {
            return this.month - other.month;
        }
        return this.day - other.day;
    }

    /**
     * Returns a string representation of the Date object in the format "DD/MM/YYYY".
     *
     * @return A string representing the date in "DD/MM/YYYY" format, with the day and month
     * zero-padded to two digits and the year as a four-digit number.
     */
    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", day, month, year);
    }

    /**
     * Compares this Date object with the specified object for equality.
     * Determines equality by comparing the day, month, and year fields.
     *
     * @param obj The object to compare with this Date instance.
     * @return true if the specified object is a Date instance with the same day,
     *         month, and year as this Date object; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Date date = (Date) obj;
        return day == date.day && month == date.month && year == date.year;
    }

    @Override
    public int hashCode() {
        int result = day;
        result = 31 * result + month;
        result = 31 * result + year;
        return result;
    }
}