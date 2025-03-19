package view;

import presenter.FirstPagePresenter;
import presenter.interfata.FirstPageI;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class FirstPage extends JDialog implements Serializable, FirstPageI {
    private JButton cofetariiButton;
    private JButton prajituriButton;
    private JButton saveCakesListButton;
    private FirstPagePresenter presenter;

    public FirstPage(JFrame parent){
        super(parent);
        this.presenter = new FirstPagePresenter(this);
        presenter.showView();

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

        cofetariiButton.addActionListener(e -> presenter.openCofetarieView());
        prajituriButton.addActionListener(e -> presenter.openPrajituraView());
        saveCakesListButton.addActionListener(e -> presenter.openCSVandDOCView());

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

    @Override
    public void showView() {
        setVisible(true);
    }

    @Override
    public void openCofetarieView() {
        dispose();
        CofetarieView cofetarieView = new CofetarieView(FirstPage.this);
        cofetarieView.setVisible(true);
    }

    @Override
    public void openPrajituraView() {
        dispose();
        Prajitura prajituraView = new Prajitura(FirstPage.this);
        prajituraView.setVisible(true);
    }

    @Override
    public void openCSVandDOCView() {
        dispose();
        CSVandDOC docView = new CSVandDOC(FirstPage.this);
        docView.setVisible(true);
    }
}
