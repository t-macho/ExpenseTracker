package expensetracker.table;

import expensetracker.forms.RepeatedView;
import expensetracker.transactions.Repeated;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Extension of DefaultTableModel to properly manage repeated payments.
 */
public class RepeatedTableModel extends DefaultTableModel {
    RepeatedView repeatedView;

    public List<Repeated> getRepeatedList() {
        return repeatedList;
    }

    List<Repeated> repeatedList;
    JTable table;

    public RepeatedTableModel() {
        super(new Object[] {"Název", "Částka", "Den"}, 0);
        repeatedList = new ArrayList<>();
    }

    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    }
    public void addRepeated(Repeated repeated) {
        super.addRow(new Object[] {repeated.name, repeated.amount, repeated.day});
        repeatedList.add(repeated);
    }

    public void removeRepeated(Repeated repeated) {
        super.removeRow(repeatedList.indexOf(repeated));
        repeatedList.remove(repeated);
    }

    public Repeated getRepeatedAt(int row) {
        int modelRow = table.convertRowIndexToModel(row);
        return repeatedList.get(modelRow);
    }

}
