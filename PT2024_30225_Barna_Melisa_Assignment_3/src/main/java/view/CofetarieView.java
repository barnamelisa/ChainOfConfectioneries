package view;

import model.connection.Connection;
import model.repository.CakeRepository;
import model.repository.CofetarieRepository;
import presenter.CofetariePresenter;
import presenter.PrajituraPresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class CofetarieView extends JDialog implements Serializable {
    private JButton addNewCofetarie;
    private JButton editCofetarieButton;
    private JButton deleteCofetarieButton;
    private JButton viewAllCofetariiButton;
    private JButton backButton;
    private JButton addStockButton;
    private Connection conexiune;
    private FirstPage parentPage;

    public CofetarieView(FirstPage parent, Connection conexiune){

        super(parent);
        this.conexiune= conexiune;
        this.parentPage = parent;

        setTitle("Confectionery Page");
        setMinimumSize(new Dimension(450, 474));
        setModal(true);

        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Confectionery Page", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.gridwidth = 2;
        titleConstraints.insets = new Insets(20, 0, 20, 0);
        add(titleLabel, titleConstraints);

        addNewCofetarie = new JButton("Add new confectionery");
        addNewCofetarie.setPreferredSize(new Dimension(270, 40));

        editCofetarieButton = new JButton("Update information about confectionery");
        editCofetarieButton.setPreferredSize(new Dimension(270, 40));

        deleteCofetarieButton = new JButton("Delete Confectionery");
        deleteCofetarieButton.setPreferredSize(new Dimension(270, 40));

        addStockButton = new JButton("Add stock for cakes from a confectionery");
        addStockButton.setPreferredSize(new Dimension(270, 40));

        viewAllCofetariiButton = new JButton("View all Confectioneries");
        viewAllCofetariiButton.setPreferredSize(new Dimension(270, 40));

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(270, 40));

        CofetariePresenter cofetariePresenter = new CofetariePresenter(new CofetarieRepository());
        PrajituraPresenter prajituraPresenter = new PrajituraPresenter(new CakeRepository());

        addNewCofetarie.addActionListener(e ->  {
            dispose();
            AddNewConfectionery client = new AddNewConfectionery(CofetarieView.this, (java.sql.Connection) conexiune, cofetariePresenter);
            client.setVisible(true);
        });

        editCofetarieButton.addActionListener(e ->  {
            dispose();
            EditConfectionery clienNou=new EditConfectionery(CofetarieView.this, (java.sql.Connection) conexiune, cofetariePresenter);
            clienNou.setVisible(true);
        });

        deleteCofetarieButton.addActionListener(e ->  {
            dispose();
            DeleteConfectionery sterge=new DeleteConfectionery(CofetarieView.this, (java.sql.Connection) conexiune, cofetariePresenter);
            sterge.setVisible(true);
        });

        addStockButton.addActionListener(e ->  {
            dispose();
            VerificareBeforeAddStock sterge=new VerificareBeforeAddStock(CofetarieView.this, (java.sql.Connection) conexiune, cofetariePresenter, prajituraPresenter);
            sterge.setVisible(true);
        });

        viewAllCofetariiButton.addActionListener(e ->  {
            dispose();
            ViewAllConfectioneries vezi=new ViewAllConfectioneries(CofetarieView.this, (java.sql.Connection) conexiune, cofetariePresenter);
            vezi.setVisible(true);
        });

        backButton.addActionListener(e -> {
            this.dispose();
            parentPage.setVisible(true);
        });

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        buttonConstraints.insets = new Insets(0, 0, 20, 0);
        add(addNewCofetarie, buttonConstraints);

        buttonConstraints.gridy = 2;
        add(editCofetarieButton, buttonConstraints);

        buttonConstraints.gridy = 3;
        add(deleteCofetarieButton, buttonConstraints);

        buttonConstraints.gridy = 4;
        add(addStockButton, buttonConstraints);

        buttonConstraints.gridy = 5;
        add(viewAllCofetariiButton, buttonConstraints);

        buttonConstraints.gridy = 6;
        add(backButton, buttonConstraints);

        setVisible(true);
        setLocationRelativeTo(parent);

    }
}
