package expensetracker.transactions;

import java.text.ParseException;

public class CustDate implements Comparable<CustDate>{
    int day;
    int month;
    int year;

    int[] DAY_COUNTS = {31,28,31,30,31,30,31,31,30,31,30,31};

    public CustDate(String dateRaw) throws ParseException {
        String[] tokens = dateRaw.split("\\.");
        day = Integer.parseInt(tokens[0]);
        month = Integer.parseInt(tokens[1]);
        year = Integer.parseInt(tokens[2]);

        if (day < 1 || month < 1 || year < 1940 || month > 12) {
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

    @Override
    public int compareTo(CustDate o) {
        if (this.year > o.year) {
            return 1;
        } else {
            if (this.month > o.month) {
                return 1;
            } else {
                if (this.day > o.day) {
                    return 1;
                } else {
                    if (this.day == o.day && this.month == o.month && this.year == o.year) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            }
        }
    }
}
