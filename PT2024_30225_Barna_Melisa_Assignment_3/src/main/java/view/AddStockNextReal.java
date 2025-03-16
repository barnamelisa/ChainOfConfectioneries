package view;

import model.PrajituraDinCofetarie;
import model.repository.CakeDAO;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddStockNextReal extends JDialog implements Serializable {
    private JTextField textField1;
    private JLabel text1;
    private JButton addStockButton;
    private JButton backButton;
    private Connection connection;
    private VerificareBeforeAddStock parent;

    public AddStockNextReal(VerificareBeforeAddStock parent,Connection connection){
        super(parent);
        this.parent=parent;
        this.connection=connection;

        setTitle("Add stock page");
        setMinimumSize(new Dimension(700, 600));
        setModal(true);

        setLocationRelativeTo(parent);
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

        text1 = new JLabel("Stoc prajitura: ");
        gbc.gridy = 1;
        add(text1, gbc);

        gbc.gridx = 1;
        textField1 = new JTextField(20);
        add(textField1, gbc);

        // Buttons Section
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 10, 0);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addStockButton = new JButton("Add stok button");
        addStockButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(addStockButton);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(backButton);

        add(buttonPanel, gbc);

        addStockButton.addActionListener(e -> {
            try {
                int stoc = Integer.parseInt(textField1.getText());
                int id_prajitura=(model.PrajituraDinCofetarie);
                int id_cofetarie;
                model.PrajituraDinCofetarie stocf = new PrajituraDinCofetarie(id_prajitura,id_cofetarie,stoc);
        });

        // Back Button Action
        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });
    }
}
