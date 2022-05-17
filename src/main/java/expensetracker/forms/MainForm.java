package expensetracker.forms;

import javax.swing.*;
import java.awt.*;

public class MainForm extends Frame{
    ExpenseView expenseView;
    ActionArea actionArea;
    Overview overview;

    public ExpenseView getExpenseView() {
        return expenseView;
    }

    public MainForm() {
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Sledovač výdajů");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();

        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);

        gbc.weightx = 0.3;
        gbc.weighty = 1;

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        actionArea = new ActionArea(this);
        pane.add(actionArea, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        overview = new Overview(this);
        pane.add(overview, gbc);

        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        expenseView = new ExpenseView(this);
        pane.add(expenseView, gbc);

        frame.pack();
        //center the window on screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Overview getOverview() {
        return overview;
    }
}
