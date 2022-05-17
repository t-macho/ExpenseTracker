package expensetracker.filters;

import expensetracker.table.ExpenseTableModel;

import javax.swing.*;

public class AllFilter extends RowFilter<ExpenseTableModel, Integer> {
    @Override
    public boolean include(Entry entry) {
        return true;
    }

    @Override
    public String toString() {
        return "vše";
    }
}
