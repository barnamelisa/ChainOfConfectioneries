package view;

import presenter.PrajituraPresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.Connection;

public class EditNextViewByName extends JDialog implements Serializable {
    private JTextArea textArea1;
    private JButton backButton;
    private Connection conexiune;
    private SearchCakeByName parentPage;

    public EditNextViewByName(SearchCakeByName parent, Connection conexiune, String cakeDetails) {
        super(parent, "View cake by Name", true);
        this.conexiune = conexiune;
        this.parentPage = parent;

        setSize(400, 300);
        setLayout(new BorderLayout());

        textArea1 = new JTextArea();
        textArea1.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea1);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        textArea1.setText(cakeDetails);
    }
}

