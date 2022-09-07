package expensetracker.table;

import expensetracker.forms.RepeatedView;
import expensetracker.transactions.Repeated;
import expensetracker.transactions.RepeatedExpense;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * JTable implementation for displaying repeated payments. Formats cells according to their type.
 */
public class RepeatedTable extends JTable {
    RepeatedView repeatedView;

    public RepeatedTable(RepeatedTableModel tableModel, RepeatedView repeatedView) {
        super(tableModel);
        this.repeatedView = repeatedView;

        tableModel.table = this;
        getColumn("Název").setMinWidth(60);
        getColumn("Částka").setMinWidth(60);
        getColumn("Den").setMinWidth(20);
    }

    /**
     * Altering the display of the table - sets bigger font and if the row contains an expense, paints it red, otherwise green.
     * @param renderer  the <code>TableCellRenderer</code> to prepare
     * @param row       the row of the cell to render, where 0 is the first row
     * @param column    the column of the cell to render,
     *                  where 0 is the first column
     * @return
     */
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        Repeated repeated = repeatedView.getTableModel().getRepeatedAt(row);

        c.setFont(new Font("font", Font.PLAIN, 14));

        if (repeated.getClass().equals(RepeatedExpense.class)){
            c.setBackground(new Color(255, 112, 77));
        } else {
            c.setBackground(new Color(128, 255, 128));
        }
        return c;
    }
}
