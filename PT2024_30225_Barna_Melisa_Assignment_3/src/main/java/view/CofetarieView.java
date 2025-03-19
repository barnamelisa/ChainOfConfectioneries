package view;

import presenter.CofetariePresenter;
import presenter.CofetarieViewPresenter;
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
    private FirstPage parentPage;

    public CofetarieView(FirstPage parent){
        super(parent);
        this.parentPage = parent;

        CofetarieViewPresenter presenter = new CofetarieViewPresenter(this);
        presenter.showViewCofetarie();

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

        CofetariePresenter cofetariePresenter = new CofetariePresenter();
        PrajituraPresenter prajituraPresenter = new PrajituraPresenter();

        addNewCofetarie.addActionListener(e ->  {
            JDialog addNewDialog = new JDialog(CofetarieView.this, "Add New Confectionery", true);
            addNewDialog.setMinimumSize(new Dimension(450, 474));
            addNewDialog.setLocationRelativeTo(CofetarieView.this);
            addNewDialog.getContentPane().setBackground(new Color(173, 216, 230));
            addNewDialog.setLayout(new GridBagLayout());

            JLabel titleLabel1 = new JLabel("Add new confectionery", JLabel.CENTER);
            titleLabel1.setFont(new Font("Arial", Font.BOLD, 18));
            GridBagConstraints titleConstraints1 = new GridBagConstraints();
            titleConstraints1.gridx = 0;
            titleConstraints1.gridy = 0;
            titleConstraints1.gridwidth = 2;
            titleConstraints1.insets = new Insets(20, 0, 20, 0);
            addNewDialog.add(titleLabel1, titleConstraints1);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(10, 20, 10, 10);
            JLabel text1 = new JLabel("Id Confectionery: ");
            addNewDialog.add(text1, gbc);

            gbc.gridx = 1;
            gbc.insets = new Insets(10, 10, 10, 20);
            JTextField textField1 = new JTextField(20);
            addNewDialog.add(textField1, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.insets = new Insets(10, 20, 10, 10);
            JLabel text3 = new JLabel("Address: ");
            addNewDialog.add(text3, gbc);

            gbc.gridx = 1;
            gbc.insets = new Insets(10, 10, 10, 20);
            JTextField textField3 = new JTextField(20);
            addNewDialog.add(textField3, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(20, 0, 10, 0);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton insertButton = new JButton("Add Confectionery");
            insertButton.setPreferredSize(new Dimension(120, 40));
            buttonPanel.add(insertButton);

            JButton backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(120, 40));
            buttonPanel.add(backButton);

            addNewDialog.add(buttonPanel, gbc);

            insertButton.addActionListener(insertEvent -> {
                try {
                    int id = Integer.parseInt(textField1.getText());
                    String address = textField3.getText();

                    model.Cofetarie newConfectionery = new model.Cofetarie(id, address);
                    model.Cofetarie insertedConfectionery = cofetariePresenter.insertCofetarie(newConfectionery);

                    if (insertedConfectionery != null) {
                        JOptionPane.showMessageDialog(addNewDialog, "Confectionery added successfully with ID: " + insertedConfectionery.getId());
                    } else {
                        JOptionPane.showMessageDialog(addNewDialog, "Failed to add confectionery", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(addNewDialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            backButton.addActionListener(e2 -> {
                addNewDialog.dispose();
                setVisible(true);
            });

            addNewDialog.setVisible(true);
        });

        editCofetarieButton.addActionListener(e -> {
            JDialog editDialog = new JDialog(CofetarieView.this, "Find Confectionery", true);
            editDialog.setMinimumSize(new Dimension(450, 500));
            editDialog.setLocationRelativeTo(CofetarieView.this);
            editDialog.getContentPane().setBackground(new Color(173, 216, 230));
            editDialog.setLayout(new GridBagLayout());

            JLabel titleLabel1 = new JLabel("Find Confectionery", JLabel.CENTER);
            titleLabel1.setFont(new Font("Arial", Font.BOLD, 18));
            GridBagConstraints titleConstraints1 = new GridBagConstraints();
            titleConstraints1.gridx = 0;
            titleConstraints1.gridy = 0;
            titleConstraints1.gridwidth = 2;
            titleConstraints1.insets = new Insets(20, 0, 20, 0);
            editDialog.add(titleLabel1, titleConstraints1);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(10, 20, 10, 10);

            JLabel text1 = new JLabel("Id: ");
            editDialog.add(text1, gbc);

            gbc.gridx = 1;
            gbc.insets = new Insets(10, 10, 10, 20);
            JTextField textField1 = new JTextField(25);
            editDialog.add(textField1, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.insets = new Insets(10, 20, 10, 10);
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton findCofetarieButton = new JButton("Find Confectionery");
            findCofetarieButton.setPreferredSize(new Dimension(120, 40));
            buttonPanel.add(findCofetarieButton);

            JButton backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(120, 40));
            buttonPanel.add(backButton);

            editDialog.add(buttonPanel, gbc);

            findCofetarieButton.addActionListener(event -> {
                String clientIdText = textField1.getText();
                if (!clientIdText.isEmpty()) {
                    try {
                        int clientId = Integer.parseInt(clientIdText);
                        model.Cofetarie foundConfectionery = cofetariePresenter.findById(clientId, "id_cofetarie");

                        if (foundConfectionery != null) {
                            JDialog editNextDialog = new JDialog(CofetarieView.this, "Edit Confectionery", true);
                            editNextDialog.setMinimumSize(new Dimension(450, 474));
                            editNextDialog.setLocationRelativeTo(editDialog);
                            editNextDialog.getContentPane().setBackground(new Color(173, 216, 230));
                            editNextDialog.setLayout(new GridBagLayout());

                            GridBagConstraints gbc1 = new GridBagConstraints();
                            gbc1.insets = new Insets(10, 20, 10, 10);  // Adăugăm margini pentru spațiu

                            JLabel titleLabel2 = new JLabel("Edit Confectionery", JLabel.CENTER);
                            titleLabel2.setFont(new Font("Arial", Font.BOLD, 18));
                            GridBagConstraints titleConstraints2 = new GridBagConstraints();
                            titleConstraints2.gridx = 0;
                            titleConstraints2.gridy = 0;
                            titleConstraints2.gridwidth = 2;
                            titleConstraints2.insets = new Insets(20, 0, 20, 0);
                            editNextDialog.add(titleLabel2, titleConstraints2);

                            JLabel text3 = new JLabel("Address: ");
                            gbc1.gridx = 0;
                            gbc1.gridy = 1;
                            gbc1.anchor = GridBagConstraints.WEST;
                            editNextDialog.add(text3, gbc1);

                            gbc1.gridx = 1;
                            gbc1.insets = new Insets(10, 10, 10, 20); // Spațiu între câmpul de text și marginea din dreapta
                            JTextField textField3 = new JTextField(20);
                            textField3.setText(foundConfectionery.getAddress());
                            editNextDialog.add(textField3, gbc1);

                            JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
                            GridBagConstraints buttonConstraints = new GridBagConstraints();
                            buttonConstraints.gridx = 0;
                            buttonConstraints.gridy = 2;
                            buttonConstraints.gridwidth = 2; // Ocupă întreaga lățime
                            buttonConstraints.insets = new Insets(10, 0, 20, 0); // Spațiu între butoane și restul
                            buttonConstraints.fill = GridBagConstraints.HORIZONTAL; // Face ca butoanele să se întindă pe toată lățimea

                            JButton saveButton = new JButton("Save");
                            saveButton.setPreferredSize(new Dimension(120, 40));
                            buttonPanel2.add(saveButton);

                            JButton backButton2 = new JButton("Back");
                            backButton2.setPreferredSize(new Dimension(120, 40));
                            buttonPanel2.add(backButton2);

                            editNextDialog.add(buttonPanel2, buttonConstraints);

                            editNextDialog.add(buttonPanel2, buttonConstraints);

                            saveButton.addActionListener(saveEvent -> {
                                String address = textField3.getText();
                                foundConfectionery.setAddress(address);
                                boolean updateSuccess = cofetariePresenter.updateCofetarie(foundConfectionery);

                                if (updateSuccess) {
                                    JOptionPane.showMessageDialog(editNextDialog, "Confectionery details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    editNextDialog.dispose();
                                    editDialog.dispose();
                                } else {
                                    JOptionPane.showMessageDialog(editNextDialog, "Failed to update confectionery details!", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            });

                            backButton2.addActionListener(backEvent -> {
                                editNextDialog.dispose();
                                editDialog.setVisible(true); // Redeschidem dialogul anterior
                            });

                            editNextDialog.setVisible(true); // Afișăm dialogul de editare detalii cofetărie
                        } else {
                            JOptionPane.showMessageDialog(editDialog, "Confectionery not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(editDialog, "Invalid Confectionery ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (RuntimeException ex) {
                        JOptionPane.showMessageDialog(editDialog, "Error finding confectionery: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(editDialog, "Please enter a confectionery ID!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            backButton.addActionListener(event -> {
                editDialog.dispose();
                setVisible(true);
            });

            editDialog.setVisible(true);
        });

        deleteCofetarieButton.addActionListener(e -> {
            JDialog deleteDialog = new JDialog(CofetarieView.this, "Delete Confectionery", true);
            deleteDialog.setMinimumSize(new Dimension(450, 474));
            deleteDialog.setLocationRelativeTo(CofetarieView.this);
            deleteDialog.getContentPane().setBackground(new Color(173, 216, 230));
            deleteDialog.setLayout(new GridBagLayout());

            JLabel titleLabel2 = new JLabel("Delete Confectionery", JLabel.CENTER);
            titleLabel2.setFont(new Font("Arial", Font.BOLD, 18));
            GridBagConstraints titleConstraints2 = new GridBagConstraints();
            titleConstraints2.gridx = 0;
            titleConstraints2.gridy = 0;
            titleConstraints2.gridwidth = 2;
            titleConstraints2.insets = new Insets(20, 0, 20, 0);
            deleteDialog.add(titleLabel2, titleConstraints2);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(10, 20, 10, 10);

            JLabel text1 = new JLabel("Id: ");
            deleteDialog.add(text1, gbc);

            gbc.gridx = 1;
            gbc.insets = new Insets(10, 10, 10, 20);
            JTextField textField1 = new JTextField(20);
            deleteDialog.add(textField1, gbc);

            gbc.gridy++;

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton deleteButton = new JButton("Delete Confectionery");
            deleteButton.setPreferredSize(new Dimension(120, 40));
            buttonPanel.add(deleteButton);

            JButton backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(120, 40));
            buttonPanel.add(backButton);

            deleteDialog.add(buttonPanel, gbc);

            deleteButton.addActionListener(deleteEvent -> {
                try {
                    int id = Integer.parseInt(textField1.getText());

                    boolean cofetarieExists = cofetariePresenter.doesCofetarieExist(id);

                    if (cofetarieExists) {
                        boolean deleteSuccess = cofetariePresenter.deleteCofetarieById(id, "id_cofetarie");

                        if (deleteSuccess) {
                            JOptionPane.showMessageDialog(deleteDialog, "Confectionery was deleted successfully.");
                            deleteDialog.dispose();
                        } else {
                            JOptionPane.showMessageDialog(deleteDialog, "Error deleting confectionery.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(deleteDialog, "Confectionery not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(deleteDialog, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            backButton.addActionListener(backEvent -> deleteDialog.dispose());

            deleteDialog.setVisible(true);
        });

        addStockButton.addActionListener(e -> {
            JDialog addStockDialog = new JDialog(CofetarieView.this, "Add stock page", true);
            addStockDialog.setMinimumSize(new Dimension(700, 600));
            addStockDialog.setLocationRelativeTo(CofetarieView.this);
            addStockDialog.getContentPane().setBackground(new Color(173, 216, 230));
            addStockDialog.setLayout(new GridBagLayout());

            JLabel titleLabel3 = new JLabel("Add stock page", JLabel.CENTER);
            titleLabel3.setFont(new Font("Arial", Font.BOLD, 18));
            GridBagConstraints titleConstraints3 = new GridBagConstraints();
            titleConstraints3.gridx = 0;
            titleConstraints3.gridy = 0;
            titleConstraints3.gridwidth = 2;
            titleConstraints3.insets = new Insets(20, 0, 20, 0);
            addStockDialog.add(titleLabel3, titleConstraints3);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy++; // Începem de la un rând nou
            JLabel text1 = new JLabel("Id prajitura: ");
            addStockDialog.add(text1, gbc);

            gbc.gridx = 1;
            JTextField textField1 = new JTextField(25);
            addStockDialog.add(textField1, gbc);

            gbc.gridx = 0;
            gbc.gridy++; // Mutăm pe un rând nou
            JLabel text2 = new JLabel("Id cofetarie: ");
            addStockDialog.add(text2, gbc);

            gbc.gridx = 1;
            JTextField textField2 = new JTextField(25);
            addStockDialog.add(textField2, gbc);

            gbc.gridx = 0;
            gbc.gridy++; // Mutăm pe un rând nou
            JLabel text3 = new JLabel("Stoc: ");
            addStockDialog.add(text3, gbc);

            gbc.gridx = 1;
            JTextField textField3 = new JTextField(25);
            addStockDialog.add(textField3, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton confirmButton = new JButton("Add stoc");
            confirmButton.setPreferredSize(new Dimension(120, 40));
            buttonPanel.add(confirmButton);

            JButton backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(120, 40));
            buttonPanel.add(backButton);

            addStockDialog.add(buttonPanel, gbc);

            confirmButton.addActionListener(ev -> {
                String prajituraIdText = textField1.getText();
                String cofetarieId = textField2.getText();
                String stocText = textField3.getText();
                if (!prajituraIdText.isEmpty() && !cofetarieId.isEmpty() && !stocText.isEmpty()) {
                    try {
                        int prajituraId = Integer.parseInt(prajituraIdText);
                        int cofetarieIdF = Integer.parseInt(cofetarieId);
                        int stoc = Integer.parseInt(stocText);

                        model.Prajitura foundCake = prajituraPresenter.findById(prajituraId, "prajitura_id");
                        model.Cofetarie foundCofetarie = cofetariePresenter.findById(cofetarieIdF, "id_cofetarie");

                        if (foundCake != null && foundCofetarie != null) {
                            boolean success = cofetariePresenter.updateStock(prajituraId, cofetarieIdF, stoc);
                            if (success) {
                                JOptionPane.showMessageDialog(addStockDialog, "Stock updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(addStockDialog, "Failed to update stock!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(addStockDialog, "Cake or Cofetarie not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(addStockDialog, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (RuntimeException ex) {
                        JOptionPane.showMessageDialog(addStockDialog, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(addStockDialog, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            backButton.addActionListener(ev -> addStockDialog.dispose());

            addStockDialog.setVisible(true);
        });


        viewAllCofetariiButton.addActionListener(e -> {
            JDialog viewAllDialog = new JDialog(CofetarieView.this, "List of Confectioneries", true);
            viewAllDialog.setSize(400, 300);
            viewAllDialog.setLayout(new BorderLayout());

            JTextArea textArea1 = new JTextArea();
            textArea1.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea1);
            viewAllDialog.add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            JButton backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(120, 40));
            backButton.addActionListener(ev -> {
                viewAllDialog.dispose();
                CofetarieView.this.setVisible(true);
            });

            buttonPanel.add(backButton);
            viewAllDialog.add(buttonPanel, BorderLayout.SOUTH);

            String cakesData = cofetariePresenter.displayObjectsFromDatabase("cofetarie");
            textArea1.setText(cakesData);

            viewAllDialog.setLocationRelativeTo(CofetarieView.this);
            viewAllDialog.setVisible(true);
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
