package view;

import model.repository.CofetarieRepository;
import presenter.CofetariePresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.Connection;


public class ViewAllConfectioneries extends JDialog implements Serializable {
    private JTextArea textArea1;
    private JButton backButton;
    private Connection conexiune;
    private CofetarieView parentPage;
    private CofetariePresenter cofetariePresenter;

    public ViewAllConfectioneries(CofetarieView parent, Connection conexiune, CofetariePresenter cofetariePresenter) {
        super(parent, "List of Confectioneries", true);
        this.conexiune = conexiune;
        this.parentPage = parent;
        this.cofetariePresenter = cofetariePresenter;

        setSize(400, 300);
        setLayout(new BorderLayout());

        textArea1 = new JTextArea();
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

        String cakesData = cofetariePresenter.displayObjectsFromDatabase("cofetarie");
        textArea1.setText(cakesData);

    }
}
