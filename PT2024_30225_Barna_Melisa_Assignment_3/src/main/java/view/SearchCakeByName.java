package view;

import model.repository.CakeRepository;
import presenter.PrajituraPresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.Connection;

public class SearchCakeByName extends JDialog implements Serializable {
    private JTextField textField1;
    private JButton searchButton;
    private JButton backButton;
    private JLabel text1;
    private Prajitura parentPage;
    private Connection conexiune;
    private PrajituraPresenter prajituraPresenter;

    public SearchCakeByName(Prajitura prajitura, Connection connection, PrajituraPresenter prajituraPresenter) {
        super(prajitura, "Cakes List", true);
        this.conexiune = connection;
        this.parentPage = prajitura;
        this.prajituraPresenter = prajituraPresenter;

        setTitle("Find cake by name");
        setMinimumSize(new Dimension(450, 500));
        setModal(true);

        setLocationRelativeTo(parentPage);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Find cake by name", JLabel.CENTER);
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

        text1 = new JLabel("Nume prajitura: ");
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
        searchButton = new JButton("Find cake by name");
        searchButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(searchButton);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(backButton);

        add(buttonPanel, gbc);

        searchButton.addActionListener(e -> {
            String numePrajitura = textField1.getText();

            if (!numePrajitura.isEmpty()) {
                try {
                    String result = prajituraPresenter.findCakeByName(numePrajitura);

                    if (result != null && !result.isEmpty()) {
                        dispose();
                        EditNextViewByName editNextView = new EditNextViewByName(SearchCakeByName.this, conexiune, result);
                        editNextView.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(SearchCakeByName.this, "Cake not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(SearchCakeByName.this, "Error finding cake: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(SearchCakeByName.this, "Please enter a cake name!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });



        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });

    }
}
