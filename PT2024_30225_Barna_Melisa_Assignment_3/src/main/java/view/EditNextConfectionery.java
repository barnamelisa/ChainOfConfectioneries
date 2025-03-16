package view;

import model.Cofetarie;
import model.repository.CofetarieRepository;
import presenter.CofetariePresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.Connection;

public class EditNextConfectionery extends JDialog implements Serializable {
    private JTextField textField3;
    private JButton saveButton;
    private JButton backButton;
    private JLabel text3;
    private Connection conexiune;
    private EditConfectionery parentPage;
    private Cofetarie client;
    private CofetariePresenter cofetariePresenter;
    public EditNextConfectionery(CofetariePresenter cofetariePresenter,EditConfectionery parent, Connection conexiune, Cofetarie foundClient){
        super(parent);
        this.cofetariePresenter = cofetariePresenter;
        this.conexiune = conexiune;
        this.parentPage = parent;
        this.client = foundClient;

        setTitle("Edit Confectionery page");
        setMinimumSize(new Dimension(450, 474));
        setModal(true);

        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Edit Confectionery page", JLabel.CENTER);
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

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = new Insets(10, 20, 10, 10);
        text3 = new JLabel("Adress: ");
        add(text3, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 10, 10, 20);
        textField3 = new JTextField(20);
        add(textField3, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = new Insets(10, 20, 10, 10);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add( saveButton);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(backButton);

        add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            String address = textField3.getText();

            client.setAddress(address);

            boolean updateSuccess = cofetariePresenter.updateCofetarie(client);

            if (updateSuccess) {
                JOptionPane.showMessageDialog(EditNextConfectionery.this, "Confectionery details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(EditNextConfectionery.this, "Failed to update confectionery details!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });
    }
}
