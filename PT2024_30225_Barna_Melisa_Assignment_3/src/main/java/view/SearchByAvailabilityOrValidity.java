package view;

import model.repository.CakeRepository;
import presenter.PrajituraPresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SearchByAvailabilityOrValidity extends JDialog implements Serializable {
    private JComboBox<String> comboBox1;
    private JTextField textField1;
    private JButton searchButton;
    private JButton backButton;
    private JLabel text1;
    private Prajitura parentPage;
    private Connection conexiune;
    private PrajituraPresenter prajituraPresenter;

    public SearchByAvailabilityOrValidity(Prajitura parentPage, Connection conexiune,  PrajituraPresenter prajituraPresenter) {
        super(parentPage);
        this.parentPage = parentPage;
        this.conexiune = conexiune;
        this.prajituraPresenter = prajituraPresenter;

        setTitle("Search By Availability Or Validity");
        setMinimumSize(new Dimension(700, 600));
        setModal(true);
        setLocationRelativeTo(parentPage);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Search By Availability Or Validity", JLabel.CENTER);
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
        text1 = new JLabel("Id cofetărie: ");
        add(text1, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 10, 10, 20);
        textField1 = new JTextField(20);
        add(textField1, gbc);

        JLabel criteriaLabel = new JLabel("Selectați criteriul:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 20, 10, 10);
        add(criteriaLabel, gbc);

        comboBox1 = new JComboBox<>(new String[]{"Disponibilitate", "Valabilitate"});
        gbc.gridx = 1;
        add(comboBox1, gbc);

        searchButton = new JButton("Căutare");
        searchButton.setPreferredSize(new Dimension(270, 40));

        searchButton.addActionListener(e -> {
            try {
                int idCofetarie = Integer.parseInt(textField1.getText().trim());
                String selectedCriteria = (String) comboBox1.getSelectedItem();

                boolean cofetarieExists = prajituraPresenter.checkCofetarieExists(idCofetarie);

                if (!cofetarieExists) {
                    JOptionPane.showMessageDialog(SearchByAvailabilityOrValidity.this,
                            "Cofetăria cu ID-ul " + idCofetarie + " nu există!",
                            "Eroare", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (selectedCriteria != null) {
                    List<model.Prajitura> prajituri;

                    if ("Disponibilitate".equals(selectedCriteria)) {
                        prajituri = prajituraPresenter.findCakesByAvailability(idCofetarie);
                    }
                    else if ("Valabilitate".equals(selectedCriteria)) {
                        prajituri = prajituraPresenter.findCakesByValidity(idCofetarie, LocalDate.now());
                    }
                    else {
                        prajituri = List.of(); // Listă goală dacă nu e selectat un criteriu valid
                    }

                    if (!prajituri.isEmpty()) {
                        StringBuilder result = new StringBuilder("Prăjituri din cofetăria cu ID-ul " + idCofetarie + ":\n");
                        for (model.Prajitura prajitura : prajituri) {
                            result.append(prajitura.toString()).append("\n");
                        }
                        JOptionPane.showMessageDialog(SearchByAvailabilityOrValidity.this,
                                result.toString(), "Rezultate Căutare", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(SearchByAvailabilityOrValidity.this,
                                "Nu au fost găsite prăjituri care să îndeplinească condițiile selectate.",
                                "Fără rezultate", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(SearchByAvailabilityOrValidity.this,
                        "Eroare: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridy = 3;
        add(searchButton, gbc);

        backButton = new JButton("Înapoi");
        backButton.setPreferredSize(new Dimension(270, 40));

        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });

        gbc.gridy = 4;
        add(backButton, gbc);
    }

}
