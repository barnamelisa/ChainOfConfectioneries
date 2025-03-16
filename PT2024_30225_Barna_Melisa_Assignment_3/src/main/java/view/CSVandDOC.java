package view;

import model.Prajitura;
import model.repository.CakeRepository;
import presenter.PrajituraPresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class CSVandDOC extends JDialog implements Serializable {
    private JTextField textField1;
    private JButton saveButton;
    private JButton backButton;
    private JLabel text1;
    private FirstPage parentPage;
    private Connection conexiune;
    public CSVandDOC(FirstPage parentPage,Connection conexiune){
        super(parentPage);
        this.parentPage = parentPage;
        this.conexiune = conexiune;

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

        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(270, 40));

        PrajituraPresenter prajituraPresenter = new PrajituraPresenter(new CakeRepository());

        saveButton.addActionListener(e -> {
            String cofetarieIdText = textField1.getText();
            try {
                int cofetarieId = Integer.parseInt(cofetarieIdText);
                boolean cofetarieExists = prajituraPresenter.checkCofetarieExists(cofetarieId);

                if (!cofetarieExists) {
                    JOptionPane.showMessageDialog(CSVandDOC.this, "Cofetăria cu ID-ul " + cofetarieId + " nu există!", "Eroare", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                List<Prajitura> prajituri = prajituraPresenter.findExpiredOrOutOfStockCakes(LocalDate.now());

                if (prajituri.isEmpty()) {
                    JOptionPane.showMessageDialog(CSVandDOC.this, "Nu există prăjituri expirate sau epuizate în stoc.", "Informație", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                prajituraPresenter.createCSV(prajituri);
                JOptionPane.showMessageDialog(CSVandDOC.this, "Fișierul CSV a fost creat cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);

                prajituraPresenter.savePrajituraToDoc(prajituri, "Fisier doc");
                JOptionPane.showMessageDialog(CSVandDOC.this, "Fișierul doc a fost creat cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID-ul cofetăriei trebuie să fie un număr valid!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridy = 3;
        add(saveButton, gbc);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(270, 40));

        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });

        gbc.gridy = 4;
        add(backButton, gbc);
    }

}
