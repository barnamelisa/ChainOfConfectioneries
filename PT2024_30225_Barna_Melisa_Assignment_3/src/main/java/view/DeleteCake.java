package view;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.Connection;

import model.repository.CakeRepository;
import presenter.PrajituraPresenter;

public class DeleteCake extends JDialog implements Serializable {
    private JTextField textField1;
    private JButton deleteButton;
    private JButton backButton;
    private JLabel text1;
    private Connection conexiune;
    private Prajitura parentPage;
    private PrajituraPresenter prajituraPresenter;
    public DeleteCake(Prajitura parent, Connection conexiune, PrajituraPresenter prajituraPresenter){
        super(parent);
        this.conexiune = conexiune;
        this.parentPage = parent;
        this.prajituraPresenter = prajituraPresenter;

        setTitle("Delete product page");
        setMinimumSize(new Dimension(450, 474));
        setModal(true);

        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Delete product page", JLabel.CENTER);
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
        deleteButton = new JButton("Delete product");
        deleteButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(deleteButton);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(backButton);

        add(buttonPanel, gbc);

        deleteButton.addActionListener(e -> {
            String cakeIdText = textField1.getText();

            if (!cakeIdText.isEmpty()) {
                try {
                    int cakeId = Integer.parseInt(cakeIdText);

                    model.Prajitura foundCake = prajituraPresenter.findById(cakeId, "prajitura_id");

                    if (foundCake != null) {
                        boolean deletionSuccess = prajituraPresenter.deletePrajitura(foundCake, "prajitura_id");

                        if (deletionSuccess) {
                            JOptionPane.showMessageDialog(this, "Prajitura a fost stearsă cu succes.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Ștergerea prăjiturii a eșuat.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Prăjitura nu a fost găsită!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID invalid! Introduceți un număr valid.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Introduceți un ID de prăjitură!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });
    }
}
