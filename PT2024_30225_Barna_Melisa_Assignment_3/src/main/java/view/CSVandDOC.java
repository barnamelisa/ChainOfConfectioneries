package view;

import presenter.CSVandDOCPresenter;
import presenter.interfata.CSVandDOCI;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class CSVandDOC extends JDialog implements Serializable, CSVandDOCI {
    private JTextField textField1;
    private JButton saveButton;
    private JButton backButton;
    private JLabel text1;
    private FirstPage parentPage;
    private CSVandDOCPresenter presenter;
    public CSVandDOC(FirstPage parentPage){
        super(parentPage);
        this.parentPage = parentPage;
        this.presenter = new CSVandDOCPresenter(this);

        presenter.showView();

        setTitle("Search By Availability Or Validity");
        setMinimumSize(new Dimension(700, 600));
        setModal(true);
        setLocationRelativeTo(parentPage);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Search By Availability Or Validity", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.gridwidth = 2;
        titleConstraints.insets = new Insets(20, 0, 20, 0);
        add(titleLabel, titleConstraints);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 10);
        text1 = new JLabel("Id cofetărie: ");
        add(text1, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 10, 10, 20);
        textField1 = new JTextField(20);
        add(textField1, gbc);

        saveButton.addActionListener(e -> {
            String cofetarieIdText = textField1.getText();
            presenter.processCofetarieId(cofetarieIdText);
        });

        gbc.gridy = 3;
        add(saveButton, gbc);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(270, 40));

        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });

        gbc.gridy = 4;
        add(backButton, gbc);
    }

    @Override
    public void showView() {
        setVisible(true);
    }

    @Override
    public String getCofetarieId() {
        return text1.getText();
    }
    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Eroare", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showInformationMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Informație", JOptionPane.INFORMATION_MESSAGE);
    }
}
