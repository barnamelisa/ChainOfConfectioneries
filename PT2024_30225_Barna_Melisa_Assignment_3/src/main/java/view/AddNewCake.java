package view;

import model.repository.CakeRepository;
import presenter.PrajituraPresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddNewCake extends JDialog implements Serializable {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton addButton;
    private JButton backButton;
    private JLabel text1;
    private JLabel text2;
    private JLabel text3;
    private JLabel text4;
    private JLabel text5;
    private JTextField textField6;
    private JTextField textField7;
    private JLabel text6;
    private JLabel text7;
    private JTextField textField8;
    private JLabel text8;
    private Connection conexiune;
    private Prajitura parentPage;
    private PrajituraPresenter prajituraPresenter;

    public AddNewCake(Prajitura parent, Connection conexiune, PrajituraPresenter prajituraPresenter) {
        super(parent);
        this.conexiune = conexiune;
        this.parentPage = parent;
        this.prajituraPresenter = prajituraPresenter;

        setTitle("Add new cake page");
        setMinimumSize(new Dimension(700, 600));
        setModal(true);

        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Add new cake page", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.gridwidth = 2;
        titleConstraints.insets = new Insets(20, 0, 20, 0);
        add(titleLabel, titleConstraints);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 10);

        text1 = new JLabel("Id Prajitura: ");
        gbc.gridy = 1;
        add(text1, gbc);

        gbc.gridx = 1;
        textField1 = new JTextField(20);
        add(textField1, gbc);

        // Nume prajitura
        gbc.gridx = 0;
        gbc.gridy++;
        text2 = new JLabel("Nume prajitura: ");
        add(text2, gbc);

        gbc.gridx = 1;
        textField2 = new JTextField(20);
        add(textField2, gbc);

        // Descriere
        gbc.gridx = 0;
        gbc.gridy++;
        text3 = new JLabel("Descriere: ");
        add(text3, gbc);

        gbc.gridx = 1;
        textField3 = new JTextField(20);
        add(textField3, gbc);

        // Cofetarie Id
        gbc.gridx = 0;
        gbc.gridy++;
        text4 = new JLabel("Cofetarie Id: ");
        add(text4, gbc);

        gbc.gridx = 1;
        textField4 = new JTextField(20);
        add(textField4, gbc);

        // Pret
        gbc.gridx = 0;
        gbc.gridy++;
        text5 = new JLabel("Pret: ");
        add(text5, gbc);

        gbc.gridx = 1;
        textField5 = new JTextField(20);
        add(textField5, gbc);

        // Data productie
        gbc.gridx = 0;
        gbc.gridy++;
        text6 = new JLabel("Data productie: ");
        add(text6, gbc);

        gbc.gridx = 1;
        textField6 = new JTextField(20);
        add(textField6, gbc);

        // Data expirare
        gbc.gridx = 0;
        gbc.gridy++;
        text7 = new JLabel("Data expirare: ");
        add(text7, gbc);

        gbc.gridx = 1;
        textField7 = new JTextField(20);
        add(textField7, gbc);

        // Imagine
        gbc.gridx = 0;
        gbc.gridy++;
        text8 = new JLabel("Imagine: ");
        add(text8, gbc);

        gbc.gridx = 1;
        textField8 = new JTextField(20);
        add(textField8, gbc);

        // Buttons Section
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 10, 0);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add cake");
        addButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(addButton);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(backButton);

        add(buttonPanel, gbc);

        addButton.addActionListener(e -> {
            try {
                // Preluăm datele din UI
                int id = Integer.parseInt(textField1.getText());
                String name = textField2.getText();
                String descriere = textField3.getText();
                int cofetarieId = Integer.parseInt(textField4.getText());
                BigDecimal pret = new BigDecimal(textField5.getText());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate data_productie = LocalDate.parse(textField6.getText(), formatter);
                LocalDate data_expirare = LocalDate.parse(textField7.getText(), formatter);
                String imagineUrl = textField8.getText();

                model.Prajitura newProduct = new model.Prajitura(id, name, descriere, cofetarieId, pret, data_productie, data_expirare, imagineUrl);
                model.Prajitura insertedProduct = prajituraPresenter.insertPrajitura(newProduct);

                if (insertedProduct != null) {
                    JOptionPane.showMessageDialog(AddNewCake.this, "Cake added successfully with ID: " + insertedProduct.getId());
                } else {
                    JOptionPane.showMessageDialog(AddNewCake.this, "Failed to add cake", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(AddNewCake.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Back Button Action
        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });
    }
}
