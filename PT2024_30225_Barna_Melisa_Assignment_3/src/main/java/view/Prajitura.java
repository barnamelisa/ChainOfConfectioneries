package view;

import model.connection.Connection;
import model.repository.CakeRepository;
import presenter.PrajituraPresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Prajitura extends  JDialog implements Serializable {
    private JButton addNewCakeButton;
    private JButton editCakeButton;
    private JButton deleteCakeButton;
    private JButton viewAllCakesButton;
    private JButton backButton;
    private JButton searchByNameButton;
    private JButton searchByAvailabilityOrButton;
    private Connection conexiune;
    private FirstPage parentPage;

    public Prajitura(FirstPage parent, Connection conexiune){
        super(parent);
        this.conexiune= conexiune;
        this.parentPage = parent;

        setTitle("Cake Page");
        setMinimumSize(new Dimension(700, 600));
        setModal(true);

        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Cake Page", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.gridwidth = 2;
        titleConstraints.insets = new Insets(20, 0, 20, 0);
        add(titleLabel, titleConstraints);

        addNewCakeButton = new JButton("Add new cake");
        addNewCakeButton.setPreferredSize(new Dimension(270, 40));

        editCakeButton = new JButton("Edit cake");
        editCakeButton.setPreferredSize(new Dimension(270, 40));

        deleteCakeButton = new JButton("Delete cake");
        deleteCakeButton.setPreferredSize(new Dimension(270, 40));

        viewAllCakesButton = new JButton("View all cakes");
        viewAllCakesButton.setPreferredSize(new Dimension(270, 40));

        searchByNameButton = new JButton("Searh By Name");
        searchByNameButton.setPreferredSize(new Dimension(270, 40));

        searchByAvailabilityOrButton = new JButton("Search by availability or validity");
        searchByAvailabilityOrButton.setPreferredSize(new Dimension(270, 40));

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(270, 40));

        PrajituraPresenter prajituraPresenter = new PrajituraPresenter(new CakeRepository());

        addNewCakeButton.addActionListener(e ->  {
            dispose();
            AddNewCake addNewCakeView = new AddNewCake(Prajitura.this, (java.sql.Connection) conexiune, prajituraPresenter);
            addNewCakeView.setVisible(true);
        });

        editCakeButton.addActionListener(e ->  {
            dispose();
            EditCake cake=new EditCake(Prajitura.this, (java.sql.Connection) conexiune, prajituraPresenter);
            cake.setVisible(true);
        });

        deleteCakeButton.addActionListener(e ->  {
            dispose();
            DeleteCake cake=new DeleteCake(Prajitura.this, (java.sql.Connection) conexiune, prajituraPresenter);
            cake.setVisible(true);
        });

        viewAllCakesButton.addActionListener(e ->  {
            dispose();
            ViewAllCakes cake=new ViewAllCakes(Prajitura.this, (java.sql.Connection) conexiune, prajituraPresenter);
            cake.setVisible(true);
        });

        searchByNameButton.addActionListener(e ->  {
            dispose();
            SearchCakeByName cakeByName=new SearchCakeByName(Prajitura.this, (java.sql.Connection) conexiune, prajituraPresenter);
            cakeByName.setVisible(true);
        });

        searchByAvailabilityOrButton.addActionListener(e ->  {
            dispose();
            SearchByAvailabilityOrValidity cakeByName=new SearchByAvailabilityOrValidity(Prajitura.this, (java.sql.Connection) conexiune, prajituraPresenter);
            cakeByName.setVisible(true);
        });

        backButton.addActionListener(e -> {
            this.dispose();
            parentPage.setVisible(true);
        });

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        buttonConstraints.insets = new Insets(0, 0, 20, 0);
        add(addNewCakeButton, buttonConstraints);

        buttonConstraints.gridy = 2;
        add(editCakeButton, buttonConstraints);

        buttonConstraints.gridy = 3;
        add(deleteCakeButton, buttonConstraints);

        buttonConstraints.gridy = 4;
        add(viewAllCakesButton, buttonConstraints);

        buttonConstraints.gridy = 5;
        add(searchByNameButton, buttonConstraints);

        buttonConstraints.gridy = 6;
        add(searchByAvailabilityOrButton, buttonConstraints);

        buttonConstraints.gridy = 7;
        add(backButton, buttonConstraints);

        setVisible(true);
        setLocationRelativeTo(parent);

    }
}
