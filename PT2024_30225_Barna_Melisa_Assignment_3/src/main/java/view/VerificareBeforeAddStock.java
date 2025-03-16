package view;

import model.repository.CakeRepository;
import model.repository.CofetarieRepository;
import presenter.CofetariePresenter;
import presenter.PrajituraPresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.Connection;

public class VerificareBeforeAddStock extends JDialog implements Serializable {
    private JTextField textField1;
    private JButton addStockButton;
    private JButton backButton;
    private JLabel text1;
    private JTextField textField2;
    private JLabel text2;
    private JTextField textField3;
    private JLabel text3;
    private CofetarieView parentPage;
    private Connection connection;
    private CofetariePresenter cofetariePresenter;
    private PrajituraPresenter prajituraPresenter;

    public VerificareBeforeAddStock(CofetarieView parentPage, Connection connection, CofetariePresenter cofetariePresenter, PrajituraPresenter prajituraPresenter){
        super(parentPage);
        this.parentPage = parentPage;
        this.connection = connection;
        this.cofetariePresenter = cofetariePresenter;
        this.prajituraPresenter = prajituraPresenter;

        setTitle("Add stock page");
        setMinimumSize(new Dimension(700, 600));
        setModal(true);

        setLocationRelativeTo(parentPage);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Add stock page", JLabel.CENTER);
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

        text1 = new JLabel("Id prajitura: ");
        add(text1, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 10, 10, 20);
        textField1 = new JTextField(25);
        add(textField1, gbc);

        text2 = new JLabel("Id cofetarie: ");
        add(text2, gbc);

        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 20);
        textField2 = new JTextField(25);
        add(textField2, gbc);

        text3 = new JLabel("Stoc: ");
        add(text3, gbc);

        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 20);
        textField3 = new JTextField(25);
        add(textField3, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = new Insets(10, 20, 10, 10);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addStockButton = new JButton("Add stoc");
        addStockButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(addStockButton);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(backButton);

        add(buttonPanel, gbc);

        addStockButton.addActionListener(e -> {
            String prajituraIdText = textField1.getText();
            String cofetarieId = textField2.getText();
            String stocText = textField3.getText();
            if (!prajituraIdText.isEmpty() && !cofetarieId.isEmpty() && !stocText.isEmpty()) {
                try {
                    int prajituraId = Integer.parseInt(prajituraIdText);
                    int cofetarieIdF = Integer.parseInt(cofetarieId);
                    int stoc = Integer.parseInt(stocText);

                    model.Prajitura foundCake = prajituraPresenter.findById(prajituraId, "prajitura_id");

                    model.Cofetarie foundCofetarie = cofetariePresenter.findById(cofetarieIdF, "id_cofetarie");

                    if (foundCake != null && foundCofetarie != null) {
                        boolean success = cofetariePresenter.updateStock(prajituraId, cofetarieIdF, stoc);
                        if (success) {
                            JOptionPane.showMessageDialog(VerificareBeforeAddStock.this,
                                    "Stock updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(VerificareBeforeAddStock.this,
                                    "Failed to update stock!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(VerificareBeforeAddStock.this,
                                "Cake or Cofetarie not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(VerificareBeforeAddStock.this,
                            "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(VerificareBeforeAddStock.this,
                            "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(VerificareBeforeAddStock.this,
                        "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });
    }
}
