package view;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.Prajitura;
import model.repository.CakeRepository;
import presenter.PrajituraPresenter;

public class EditareNextCake extends JDialog implements Serializable {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton saveButton;
    private JButton backButton;
    private JLabel text2;
    private JLabel text3;
    private JLabel text4;
    private JLabel text1;
    private JTextField textField5;
    private JTextField textField6;
    private JLabel text5;
    private JLabel text6;
    private Connection conexiune;
    private EditCake parentPage;
    private Prajitura prajitura;
    private PrajituraPresenter prajituraPresenter;
    public EditareNextCake(PrajituraPresenter prajituraPresenter, EditCake parent, Connection conexiune, Prajitura findProduct){
        super(parent);
        this.prajituraPresenter = prajituraPresenter;
        this.conexiune = conexiune;
        this.parentPage = parent;
        this.prajitura = findProduct;

        setTitle("Edit cake page");
        setMinimumSize(new Dimension(450, 474));
        setModal(true);

        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Edit cake page", JLabel.CENTER);
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

        // Nume prajitura
        gbc.gridx = 0;
        gbc.gridy++;
        text1 = new JLabel("Nume prajitura: ");
        add(text1, gbc);

        gbc.gridx = 1;
        textField1 = new JTextField(20);
        add(textField1, gbc);

        // Descriere
        gbc.gridx = 0;
        gbc.gridy++;
        text2 = new JLabel("Descriere: ");
        add(text2, gbc);

        gbc.gridx = 1;
        textField2 = new JTextField(20);
        add(textField2, gbc);

        // Pret
        gbc.gridx = 0;
        gbc.gridy++;
        text3 = new JLabel("Pret: ");
        add(text3, gbc);

        gbc.gridx = 1;
        textField3 = new JTextField(20);
        add(textField3, gbc);

        // Data productie
        gbc.gridx = 0;
        gbc.gridy++;
        text4 = new JLabel("Data productie: ");
        add(text4, gbc);

        gbc.gridx = 1;
        textField4 = new JTextField(20);
        add(textField4, gbc);

        // Data expirare
        gbc.gridx = 0;
        gbc.gridy++;
        text5 = new JLabel("Data expirare: ");
        add(text5, gbc);

        gbc.gridx = 1;
        textField5 = new JTextField(20);
        add(textField5, gbc);

        // Imagine
        gbc.gridx = 0;
        gbc.gridy++;
        text6 = new JLabel("Imagine: ");
        add(text6, gbc);

        gbc.gridx = 1;
        textField6 = new JTextField(20);
        add(textField6, gbc);

        // Buttons Section
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 10, 0);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add( saveButton);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(backButton);

        add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            String name = textField1.getText();
            String descriere = textField2.getText();
            BigDecimal pret = new BigDecimal(textField3.getText());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate data_productie = LocalDate.parse(textField4.getText(), formatter);
            LocalDate data_expirare = LocalDate.parse(textField5.getText(), formatter);
            String imagine = textField6.getText();

            prajitura.setNume_prajitura(name);
            prajitura.setDescriere(descriere);
            prajitura.setPret(pret);
            prajitura.setData_productie(data_productie);
            prajitura.setData_expirare(data_expirare);
            prajitura.setImagine(imagine);

            boolean updateSuccess = prajituraPresenter.updatePrajitura(prajitura);

            if (updateSuccess) {
                JOptionPane.showMessageDialog(EditareNextCake.this, "Cake details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(EditareNextCake.this, "Failed to update cake details!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });
    }
}
