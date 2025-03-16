package view;

import model.connection.Connection;
import model.repository.CakeRepository;
import model.repository.CofetarieRepository;
import presenter.CofetariePresenter;
import presenter.PrajituraPresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class FirstPage extends JDialog implements Serializable {
    private JButton cofetariiButton;
    private JButton prajituriButton;
    private JButton saveCakesListButton;
    private Connection conexiune;

    public FirstPage(JFrame parent){
        super(parent);

        setTitle("Chain of Confectioneries");
        setMinimumSize(new Dimension(450, 474));
        setModal(true);

        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Chain of Confectioneries", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.gridwidth = 2;
        titleConstraints.insets = new Insets(20, 0, 20, 0);
        add(titleLabel, titleConstraints);

        cofetariiButton = new JButton("Confectioneries");
        cofetariiButton.setPreferredSize(new Dimension(270, 40));

        prajituriButton = new JButton("Cakes");
        prajituriButton.setPreferredSize(new Dimension(270, 40));

        saveCakesListButton = new JButton("Save Cakes List (Expired or Out of Stock) as CSV and DOC");
        saveCakesListButton.setPreferredSize(new Dimension(270, 40));

        cofetariiButton.addActionListener(e -> {
            dispose();
            CofetarieView clientView = new CofetarieView(FirstPage.this, conexiune);
            clientView.setVisible(true);
        });

        prajituriButton.addActionListener(e -> {
            dispose();
            Prajitura prajituraView = new Prajitura(FirstPage.this, conexiune);
            prajituraView.setVisible(true);
        });


        saveCakesListButton.addActionListener(e -> {
            dispose();
            CSVandDOC docView = new CSVandDOC(FirstPage.this, (java.sql.Connection) conexiune);
            docView.setVisible(true);
        });

        // Adding buttons to layout
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        buttonConstraints.insets = new Insets(0, 0, 20, 0);
        add(cofetariiButton, buttonConstraints);

        buttonConstraints.gridy = 2;
        add(prajituriButton, buttonConstraints);

        buttonConstraints.gridy = 3;
        add(saveCakesListButton, buttonConstraints);

        setVisible(true);
    }
}
