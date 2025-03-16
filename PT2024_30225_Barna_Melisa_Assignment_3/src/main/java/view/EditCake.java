package view;

import model.repository.CakeRepository;
import presenter.PrajituraPresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.Connection;

public class EditCake extends JDialog implements Serializable {
    private JTextField textField1;
    private JButton findProductButton;
    private JButton backButton;
    private JLabel text1;
    private Connection conexiune;
    private Prajitura parentPage;
    private PrajituraPresenter prajituraPresenter;
    public EditCake(Prajitura parent, Connection conexiune, PrajituraPresenter prajituraPresenter){
        super(parent);
        this.conexiune = conexiune;
        this.parentPage = parent;
        this.prajituraPresenter = prajituraPresenter;

        setTitle("Find cake page");
        setMinimumSize(new Dimension(450, 500));
        setModal(true);

        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Find cake page", JLabel.CENTER);
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
        findProductButton = new JButton("Find cake");
        findProductButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(findProductButton);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(backButton);

        add(buttonPanel, gbc);

        findProductButton.addActionListener(e -> {
            String clientIdText = textField1.getText();
            if (!clientIdText.isEmpty()) {
                try {
                    int clientId = Integer.parseInt(clientIdText);

                    model.Prajitura foundCake = prajituraPresenter.findById(clientId, "prajitura_id");

                    if (foundCake != null) {
                        dispose();
                        EditareNextCake editNextClient = new EditareNextCake(prajituraPresenter, EditCake.this, conexiune, foundCake);
                        editNextClient.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(EditCake.this, "Cake not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(EditCake.this, "Invalid cake ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(EditCake.this, "Error finding client: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(EditCake.this, "Please enter a cake ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });
    }
}
