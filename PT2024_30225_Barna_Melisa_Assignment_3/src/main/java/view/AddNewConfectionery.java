package view;

import model.repository.CofetarieRepository;
import presenter.CofetariePresenter;
import presenter.PrajituraPresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.Connection;

public class AddNewConfectionery extends JDialog implements Serializable {
    private JTextField textField1;
    private JTextField textField3;
    private JButton insertButton;
    private JButton backButton;
    private JLabel text1;
    private JLabel text3;
    private Connection conexiune;
    private CofetarieView parentPage;
    private CofetariePresenter cofetariePresenter;
    public AddNewConfectionery(CofetarieView parent, Connection conexiune, CofetariePresenter cofetariePresenter){
        super(parent);
        this.conexiune = conexiune;
        this.parentPage = parent;
        this.cofetariePresenter = cofetariePresenter;

        setTitle("Add new confectionery page");
        setMinimumSize(new Dimension(450, 474));
        setModal(true);

        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Add new confectionery page", JLabel.CENTER);
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

        text1 = new JLabel("Id Confectionery: ");
        add(text1, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 10, 10, 20);
        textField1 = new JTextField(20);
        add(textField1, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = new Insets(10, 20, 10, 10);
        text3 = new JLabel("Address: ");
        add(text3, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 10, 10, 20);
        textField3 = new JTextField(20);
        add(textField3, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 10, 0);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        insertButton = new JButton("Add Confectionery");
        insertButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(insertButton);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(backButton);

        add(buttonPanel, gbc);

        insertButton.addActionListener(e -> {
            int id = Integer.parseInt(textField1.getText());
            String address = textField3.getText();

            model.Cofetarie newClient = new model.Cofetarie(id, address);
            model.Cofetarie insertedClient = cofetariePresenter.insertCofetarie(newClient);

            if (insertedClient != null) {
                JOptionPane.showMessageDialog(AddNewConfectionery.this, "Confectionery added successfully with ID: " + insertedClient.getId());
            } else {
                JOptionPane.showMessageDialog(AddNewConfectionery.this, "Failed to add confectionery", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });
    }
}
