package view;

import model.Cofetarie;
import model.repository.CofetarieRepository;
import presenter.CofetariePresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.Connection;

public class DeleteConfectionery extends JDialog implements Serializable {
    private JTextField textField1;
    private JButton deleteButton;
    private JButton backButton;
    private JLabel text1;
    private Connection conexiune;
    private CofetarieView parentPage;
    private CofetariePresenter cofetariePresenter;

    public DeleteConfectionery(CofetarieView parent, Connection conexiune, CofetariePresenter cofetariePresenter){
        super(parent);
        this.conexiune = conexiune;
        this.parentPage = parent;
        this.cofetariePresenter = cofetariePresenter;

        setTitle("Delete Confectionery page");
        setMinimumSize(new Dimension(450, 474));
        setModal(true);

        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Delete Confectionery page", JLabel.CENTER);
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
        textField1 = new JTextField(20);
        add(textField1, gbc);

        gbc.gridy++;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        deleteButton = new JButton("Delete Confectionery");
        deleteButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(deleteButton);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(backButton);

        add(buttonPanel, gbc);

        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(textField1.getText());
                Cofetarie cofetarieToDelete = cofetariePresenter.findById(id, "id_cofetarie");

                if (cofetarieToDelete != null) {
                    boolean deleteSuccess = cofetariePresenter.deleteCofetarie(cofetarieToDelete, "id_cofetarie");

                    if (deleteSuccess) {
                        JOptionPane.showMessageDialog(this, "Confectionery was deleted successfully.");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error deleting confectionery.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Confectionery not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });
    }
}
