package expensetracker.forms;

import expensetracker.dialogs.AddDialog;
import expensetracker.filters.AllFilter;
import expensetracker.filters.ExpenseFilter;
import expensetracker.filters.IncomeFilter;
import expensetracker.table.Loader;
import expensetracker.table.Saver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

/**
 * Part of the main frame.
 * Contains all the control elements, i.e. the add and save/load buttons and a combo box for selecting desired filter.
 * There is a listener on each of the control elements.
 */
public class ActionArea extends JPanel {

    JButton addButton;
    JButton saveButton;
    JButton loadButton;
    JComboBox<RowFilter> filterCombo;

    /**
     * Constructs the control area GUI.
     * Also adds listeners to each of the elements - pressing the add buttons open the AddDialog,
     * pressing save/load button saves/loads the transactions and selecting different filter in combo box
     * updates the table's row filter accordingly.
     * @param main main form
     */
    public ActionArea(MainForm main) {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(4,4,4,4);

        addButton = new JButton("Přidat transakci");
        add(addButton, gbc);
        gbc.gridy++;

        saveButton = new JButton("Uložit");
        add(saveButton, gbc);
        gbc.gridy++;

        loadButton = new JButton("Načíst");
        add(loadButton, gbc);
        gbc.gridy++;


        filterCombo = new JComboBox<>(new RowFilter[]{new AllFilter(), new IncomeFilter(), new ExpenseFilter()});
        add(filterCombo);

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                AddDialog dialog = new AddDialog(main);
            }
        });

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                Saver.save(main.getExpenseView().getTableModel());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(main, "Při ukládání došlo k chybě.");
                }
            }
        });

        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    Loader.load(main.getExpenseView().getTableModel());
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(main, "Soubor \"transactions.csv\" neexistuje.");
                } catch (ParseException | NumberFormatException | IOException ex) {
                    JOptionPane.showMessageDialog(main, "Soubor je poškozený.");
                }
            }
        });

        filterCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RowFilter filter = (RowFilter) filterCombo.getSelectedItem();
                main.getExpenseView().getTable().setFilter(filter);
            }
        });
    }
}
