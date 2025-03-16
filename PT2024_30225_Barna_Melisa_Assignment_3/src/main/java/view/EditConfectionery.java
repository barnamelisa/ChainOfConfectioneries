package view;

import model.repository.CofetarieRepository;
import presenter.CofetariePresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.Connection;

public class EditConfectionery extends JDialog implements Serializable {
    private JTextField textField1;
    private JButton findClientButton;
    private JButton backButton;
    private JLabel text1;
    private Connection conexiune;
    private CofetarieView parentPage;
    private CofetariePresenter cofetariePresenter;

    public EditConfectionery(CofetarieView parent, Connection conexiune, CofetariePresenter cofetariePresenter){
        super(parent);
        this.conexiune = conexiune;
        this.parentPage = parent;
        this.cofetariePresenter = cofetariePresenter;

        setTitle("Find Confectionery page");
        setMinimumSize(new Dimension(450, 500));
        setModal(true);

        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Find Confectionery page", JLabel.CENTER);
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

        text1 = new JLabel("Id: ");
        add(text1, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 10, 10, 20);
        textField1 = new JTextField(25);
        add(textField1, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = new Insets(10, 20, 10, 10);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        findClientButton = new JButton("Find Confectionery");
        findClientButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(findClientButton);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(backButton);

        add(buttonPanel, gbc);

        findClientButton.addActionListener(e -> {
            String clientIdText = textField1.getText();
            if (!clientIdText.isEmpty()) {
                try {
                    int clientId = Integer.parseInt(clientIdText);
                    model.Cofetarie foundConfectionery = cofetariePresenter.findById(clientId, "id_cofetarie");

                    if (foundConfectionery != null) {
                        dispose();
                        EditNextConfectionery editNextClient = new EditNextConfectionery(cofetariePresenter,EditConfectionery.this, conexiune, foundConfectionery);
                        editNextClient.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(EditConfectionery.this, "Confectionery not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(EditConfectionery.this, "Invalid Confectionery ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(EditConfectionery.this, "Error finding confectionery: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(EditConfectionery.this, "Please enter a confectionery ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });
    }
}
