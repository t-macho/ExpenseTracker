package expensetracker.transactions;

import java.text.ParseException;

/**
 * Custom date class. Parses date from string in "dd.mm.yyyy" format.
 */
public class CustDate implements Comparable<CustDate>{
    int day;
    int month;
    int year;
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    int[] DAY_COUNTS = {31,28,31,30,31,30,31,31,30,31,30,31};

    /**
     * Constructor from string of the date.
     * String is parsed separably into day, month and year tokens.
     * Those are then checked for being correct, i.e. if the month is valid,
     * day is correct in terms of number of days in the specified month (accounting for leap years)
     * and that the year is between 1940 and 3000.
     * An exception is thrown when any of the constraints are not satisfied (when the date does not make sense).
     * @param dateRaw string in "dd.mm.yyyy" format
     * @throws ParseException thrown when parsing incorrect date
     */
    public CustDate(String dateRaw) throws ParseException {
        String[] tokens = dateRaw.split("\\.");
        day = Integer.parseInt(tokens[0]);
        month = Integer.parseInt(tokens[1]);
        year = Integer.parseInt(tokens[2]);

        if (day < 1 || month < 1 || year < 1940 || year > 3000 || month > 12) {
            throw new ParseException("", 0);
        }
        if (isLeap(year) && month == 2) {
            if (day > 29) {
                throw new ParseException("", 0);
            }
        } else {
            if (day > DAY_COUNTS[month - 1]) {
                throw new ParseException("", 0);
            }
        }
    }

    /**
     * Checks if the year is leap or not.
     * @param year the year in question
     * @return true for leap year, false otherwise
     */
    private boolean isLeap(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "" + day + "." + month + "." + year;
    }

    /**
     * Implementation of the Comparable interface.
     * Provides correct comparison of two dates.
     * Two dates are equal iff all parts are the same.
     * @param o the object to be compared.
     * @return >0 if this > o, 0 if this == o, < 0 if this < o
     */
    @Override
    public int compareTo(CustDate o) {
        if (this.year != o.year) {
            return this.year - o.year;
        }
        if (this.month != o.month) {
            return this.month - o.month;
        }
        return this.day - o.day;
    }
}
