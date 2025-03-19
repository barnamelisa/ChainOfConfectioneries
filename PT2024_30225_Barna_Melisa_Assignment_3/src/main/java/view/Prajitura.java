package view;

import presenter.PrajituraPresenter;
import presenter.interfata.PrajituraI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;
import java.util.List;

public class Prajitura extends JDialog implements Serializable, PrajituraI {
    private JButton addNewCakeButton, editCakeButton, deleteCakeButton, viewAllCakesButton, backButton, searchByNameButton, searchByAvailabilityOrButton;
    private FirstPage parentPage;
    private PrajituraPresenter prajituraPresenter;

    public Prajitura(FirstPage parent) {
        super(parent);
        this.parentPage = parent;
        this.prajituraPresenter = new PrajituraPresenter(this);

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
        addNewCakeButton.addActionListener(e -> openAddCakeForm());

        editCakeButton = new JButton("Edit cake");
        editCakeButton.setPreferredSize(new Dimension(270, 40));
        editCakeButton.addActionListener(e -> openEditCakeForm());

        deleteCakeButton = new JButton("Delete cake");
        deleteCakeButton.setPreferredSize(new Dimension(270, 40));
        deleteCakeButton.addActionListener(e -> {
            dispose();
            JDialog deleteDialog = new JDialog(Prajitura.this, "Delete product page", true);
            deleteDialog.setMinimumSize(new Dimension(450, 474));
            deleteDialog.setLocationRelativeTo(Prajitura.this);
            deleteDialog.getContentPane().setBackground(new Color(173, 216, 230));
            deleteDialog.setLayout(new GridBagLayout());

            JLabel titleLabel1 = new JLabel("Delete product page", JLabel.CENTER);
            titleLabel1.setFont(new Font("Arial", Font.BOLD, 18));
            GridBagConstraints titleConstraints1 = new GridBagConstraints();
            titleConstraints1.gridx = 0;
            titleConstraints1.gridy = 0;
            titleConstraints1.gridwidth = 2;
            titleConstraints1.insets = new Insets(20, 0, 20, 0);
            deleteDialog.add(titleLabel1, titleConstraints1);

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
            JButton deleteButton = new JButton("Delete product");
            deleteButton.setPreferredSize(new Dimension(120, 40));
            buttonPanel.add(deleteButton);

            JButton backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(120, 40));
            buttonPanel.add(backButton);

            deleteDialog.add(buttonPanel, gbc);

            deleteButton.addActionListener(event -> {
                String cakeIdText = textField1.getText(); // getText integrat in interfata, dupa in presenter apelez metoda iar in view folosesc presenter

                if (!cakeIdText.isEmpty()) {
                    try {
                        int cakeId = Integer.parseInt(cakeIdText);
                        model.Prajitura foundCake = prajituraPresenter.findById(cakeId, "prajitura_id");

                        if (foundCake != null) {
                            boolean deletionSuccess = prajituraPresenter.deletePrajitura(foundCake, "prajitura_id");
                            if (deletionSuccess) {
                                JOptionPane.showMessageDialog(deleteDialog, "Prajitura a fost stearsă cu succes.");
                            } else {
                                JOptionPane.showMessageDialog(deleteDialog, "Ștergerea prăjiturii a eșuat.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(deleteDialog, "Prăjitura nu a fost găsită!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(deleteDialog, "ID invalid! Introduceți un număr valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(deleteDialog, "Introduceți un ID de prăjitură!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            backButton.addActionListener(event -> {
                deleteDialog.dispose();
                Prajitura.this.setVisible(true);
            });

            deleteDialog.setVisible(true);
        });

        viewAllCakesButton = new JButton("View all cakes");
        viewAllCakesButton.setPreferredSize(new Dimension(270, 40));

        viewAllCakesButton.addActionListener(e -> {
            dispose();

            JDialog viewAllDialog = new JDialog(Prajitura.this, "Cakes List", true);
            viewAllDialog.setSize(400, 300);
            viewAllDialog.setLayout(new BorderLayout());

            JTextArea textArea1 = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(textArea1);
            viewAllDialog.add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(120, 40));
            backButton.addActionListener(backEvent -> {
                viewAllDialog.dispose();
                Prajitura.this.setVisible(true);
            });
            buttonPanel.add(backButton);
            viewAllDialog.add(buttonPanel, BorderLayout.SOUTH);

            String cakesData = prajituraPresenter.displayObjectsFromDatabase("prajitura");
            textArea1.setText(cakesData);

            viewAllDialog.setVisible(true);
        });

        searchByNameButton = new JButton("Search By Name");
        searchByNameButton.setPreferredSize(new Dimension(270, 40));

        searchByNameButton.addActionListener(e -> {
            dispose(); // Close the current dialog

            JDialog searchDialog = new JDialog(Prajitura.this, "Find Cake by Name", true);
            searchDialog.setSize(450, 500);
            searchDialog.setLocationRelativeTo(Prajitura.this);
            searchDialog.setLayout(new GridBagLayout());
            searchDialog.getContentPane().setBackground(new Color(173, 216, 230));

            JLabel titleLabelll = new JLabel("Find Cake by Name", JLabel.CENTER);
            titleLabelll.setFont(new Font("Arial", Font.BOLD, 18));
            GridBagConstraints titleConstraintsss = new GridBagConstraints();
            titleConstraintsss.gridx = 0;
            titleConstraintsss.gridy = 0;
            titleConstraintsss.gridwidth = 2;
            titleConstraintsss.insets = new Insets(20, 0, 20, 0);
            searchDialog.add(titleLabelll, titleConstraintsss);

            JTextField textField1 = new JTextField(25);
            JLabel text1 = new JLabel("Cake Name: ");
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(10, 20, 10, 10);
            searchDialog.add(text1, gbc);

            gbc.gridx = 1;
            gbc.insets = new Insets(10, 10, 10, 20);
            searchDialog.add(textField1, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.insets = new Insets(10, 20, 10, 10);
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton searchButton = new JButton("Search by Name");
            searchButton.setPreferredSize(new Dimension(120, 40));
            buttonPanel.add(searchButton);

            JButton backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(120, 40));
            buttonPanel.add(backButton);

            searchDialog.add(buttonPanel, gbc);

            searchButton.addActionListener(event -> {
                String cakeName = textField1.getText();
                if (!cakeName.isEmpty()) {
                    try {
                        String result = prajituraPresenter.findCakeByName(cakeName);

                        if (result != null && !result.isEmpty()) {
                            JDialog editNextViewDialog = new JDialog(searchDialog, "Cake Details", true);
                            editNextViewDialog.setSize(400, 300);
                            editNextViewDialog.setLayout(new BorderLayout());

                            JTextArea textArea1 = new JTextArea();
                            textArea1.setEditable(false);
                            JScrollPane scrollPane = new JScrollPane(textArea1);
                            editNextViewDialog.add(scrollPane, BorderLayout.CENTER);

                            JPanel buttonPanelForBack = new JPanel(new FlowLayout(FlowLayout.CENTER));
                            JButton backButtonForDetails = new JButton("Back");
                            backButtonForDetails.setPreferredSize(new Dimension(120, 40));
                            backButtonForDetails.addActionListener(backEvent -> {
                                editNextViewDialog.dispose();
                                searchDialog.setVisible(true);  // Show the search dialog again
                            });
                            buttonPanelForBack.add(backButtonForDetails);

                            editNextViewDialog.add(buttonPanelForBack, BorderLayout.SOUTH);

                            textArea1.setText(result); // Display the cake details in the text area
                            editNextViewDialog.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(searchDialog, "Cake not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (RuntimeException ex) {
                        JOptionPane.showMessageDialog(searchDialog, "Error finding cake: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(searchDialog, "Please enter a cake name!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            backButton.addActionListener(event -> {
                searchDialog.dispose();
                Prajitura.this.setVisible(true);  // Show the parent page again
            });

            searchDialog.setVisible(true);  // Show the search dialog
        });

        searchByAvailabilityOrButton = new JButton("Search by availability or validity");
        searchByAvailabilityOrButton.setPreferredSize(new Dimension(270, 40));

        searchByAvailabilityOrButton.addActionListener(e -> {
            dispose();

            JDialog searchDialog = new JDialog(Prajitura.this, "Search By Availability Or Validity", true);
            searchDialog.setMinimumSize(new Dimension(700, 600));
            searchDialog.setLocationRelativeTo(Prajitura.this);
            searchDialog.getContentPane().setBackground(new Color(173, 216, 230));
            searchDialog.setLayout(new GridBagLayout());

            JLabel titleLabel1 = new JLabel("Search By Availability Or Validity", JLabel.CENTER);
            titleLabel1.setFont(new Font("Arial", Font.BOLD, 18));
            GridBagConstraints titleConstraints1 = new GridBagConstraints();
            titleConstraints1.gridx = 0;
            titleConstraints1.gridy = 0;
            titleConstraints1.gridwidth = 2;
            titleConstraints1.insets = new Insets(20, 0, 20, 0);
            searchDialog.add(titleLabel1, titleConstraints1);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(10, 20, 10, 10);
            JLabel text1 = new JLabel("Id cofetărie: ");
            searchDialog.add(text1, gbc);

            gbc.gridx = 1;
            gbc.insets = new Insets(10, 10, 10, 20);
            JTextField textField1 = new JTextField(20);
            searchDialog.add(textField1, gbc);

            JLabel criteriaLabel = new JLabel("Selectați criteriul:");
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.insets = new Insets(10, 20, 10, 10);
            searchDialog.add(criteriaLabel, gbc);

            JComboBox<String> comboBox1 = new JComboBox<>(new String[]{"Disponibilitate", "Valabilitate"});
            gbc.gridx = 1;
            searchDialog.add(comboBox1, gbc);

            JButton searchButton = new JButton("Căutare");
            searchButton.setPreferredSize(new Dimension(270, 40));

            searchButton.addActionListener(searchEvent -> {
                try {
                    // Check if the ID input is a valid number
                    String idText = textField1.getText().trim();
                    if (idText.isEmpty()) {
                        JOptionPane.showMessageDialog(searchDialog, "ID-ul cofetăriei nu poate fi gol!", "Eroare", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int idCofetarie = Integer.parseInt(idText);
                    String selectedCriteria = (String) comboBox1.getSelectedItem();

                    boolean cofetarieExists = prajituraPresenter.checkCofetarieExists(idCofetarie);
                    if (!cofetarieExists) {
                        JOptionPane.showMessageDialog(searchDialog,
                                "Cofetăria cu ID-ul " + idCofetarie + " nu există!",
                                "Eroare", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    List<model.Prajitura> prajituri = List.of();
                    if ("Disponibilitate".equals(selectedCriteria)) {
                        prajituri = prajituraPresenter.findCakesByAvailability(idCofetarie);
                    } else if ("Valabilitate".equals(selectedCriteria)) {
                        prajituri = prajituraPresenter.findCakesByValidity(idCofetarie, LocalDate.now());
                    }

                    if (!prajituri.isEmpty()) {
                        StringBuilder result = new StringBuilder("Prăjituri din cofetăria cu ID-ul " + idCofetarie + ":\n");
                        for (model.Prajitura prajitura : prajituri) {
                            result.append(prajitura.toString()).append("\n");
                        }
                        JOptionPane.showMessageDialog(searchDialog,
                                result.toString(), "Rezultate Căutare", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(searchDialog,
                                "Nu au fost găsite prăjituri care să îndeplinească condițiile selectate.",
                                "Fără rezultate", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(searchDialog,
                            "ID-ul cofetăriei trebuie să fie un număr valid!", "Eroare", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(searchDialog,
                            "Eroare: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            gbc.gridy = 3;
            searchDialog.add(searchButton, gbc);

            JButton backButton = new JButton("Înapoi");
            backButton.setPreferredSize(new Dimension(270, 40));

            backButton.addActionListener(backEvent -> {
                searchDialog.dispose();
                Prajitura.this.setVisible(true);
            });

            gbc.gridy = 4;
            searchDialog.add(backButton, gbc);

            searchDialog.setVisible(true);
        });

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(270, 40));
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
    }

    private void openAddCakeForm() {
        JDialog addDialog = new JDialog(this, "Add New Cake", true);
        addDialog.setSize(400, 450);  // Increase size to accommodate image preview
        addDialog.setLayout(new GridLayout(11, 2));  // Increase the number of rows for image and buttons
        addDialog.setLocationRelativeTo(this);

        // Define text fields for the form
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();
        JTextField cofetarieField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField productionDateField = new JTextField();
        JTextField expiryDateField = new JTextField();
        JTextField imageField = new JTextField();

        // Create a label to display the image preview
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Center the image

        // Add labels and text fields to the dialog
        addDialog.add(new JLabel("ID:"));
        addDialog.add(idField);
        addDialog.add(new JLabel("Name:"));
        addDialog.add(nameField);
        addDialog.add(new JLabel("Description:"));
        addDialog.add(descField);
        addDialog.add(new JLabel("Cofetarie ID:"));
        addDialog.add(cofetarieField);
        addDialog.add(new JLabel("Price:"));
        addDialog.add(priceField);
        addDialog.add(new JLabel("Production Date (yyyy-MM-dd):"));
        addDialog.add(productionDateField);
        addDialog.add(new JLabel("Expiry Date (yyyy-MM-dd):"));
        addDialog.add(expiryDateField);
        addDialog.add(new JLabel("Image URL:"));
        addDialog.add(imageField);

        // Add the image preview label
        addDialog.add(new JLabel("Image Preview:"));
        addDialog.add(imageLabel);

        // Create the buttons
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        imageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    String imageUrl = imageField.getText();
                    ImageIcon icon = new ImageIcon(imageUrl);
                    Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(image);
                    imageLabel.setIcon(icon);
                } catch (Exception ex) {
                    imageLabel.setIcon(null);
                }
            }
        });

        saveButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String description = descField.getText();
                int cofetarieId = Integer.parseInt(cofetarieField.getText());
                BigDecimal price = new BigDecimal(priceField.getText());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate productionDate = LocalDate.parse(productionDateField.getText(), formatter);
                LocalDate expiryDate = LocalDate.parse(expiryDateField.getText(), formatter);
                String imageUrl = imageField.getText();

                model.Prajitura newCake = new model.Prajitura(id, name, description, cofetarieId, price, productionDate, expiryDate, imageUrl);
                model.Prajitura insertedCake = prajituraPresenter.insertPrajitura(newCake);

                if (insertedCake != null) {
                    JOptionPane.showMessageDialog(addDialog, "Cake added successfully with ID: " + insertedCake.getId());
                    addDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(addDialog, "Failed to add cake", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addDialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> addDialog.dispose());

        addDialog.add(saveButton);
        addDialog.add(cancelButton);
        addDialog.setVisible(true);
    }


    private void openEditCakeForm() {
        JDialog editDialog = new JDialog(this, "Edit Cake", true);
        editDialog.setSize(400, 200);
        editDialog.setLayout(new GridLayout(3, 2));
        editDialog.setLocationRelativeTo(this);

        JTextField idField = new JTextField();
        editDialog.add(new JLabel("Enter Cake ID:"));
        editDialog.add(idField);

        JButton findButton = new JButton("Find");
        JButton cancelButton = new JButton("Cancel");

        findButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                model.Prajitura foundCake = prajituraPresenter.findById(id, "prajitura_id");
                if (foundCake != null) {
                    editDialog.dispose();
                    openEditCakeDetails(foundCake);
                } else {
                    JOptionPane.showMessageDialog(editDialog, "Cake not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editDialog, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> editDialog.dispose());

        editDialog.add(findButton);
        editDialog.add(cancelButton);
        editDialog.setVisible(true);
    }

    private void openEditCakeDetails(model.Prajitura prajitura) {
        JDialog editDetailsDialog = new JDialog(this, "Edit Cake Details", true);
        editDetailsDialog.setSize(400, 400);
        editDetailsDialog.setLayout(new GridLayout(7, 2));
        editDetailsDialog.setLocationRelativeTo(this);

        JTextField nameField = new JTextField(prajitura.getNume_prajitura());
        JTextField descField = new JTextField(prajitura.getDescriere());
        JTextField cofetarieField = new JTextField(String.valueOf(prajitura.getCofetarie_id()));
        JTextField priceField = new JTextField(String.valueOf(prajitura.getPret()));
        JTextField productionDateField = new JTextField(prajitura.getData_productie().toString());
        JTextField expiryDateField = new JTextField(prajitura.getData_expirare().toString());
        JTextField imageField = new JTextField(prajitura.getImagine());

        editDetailsDialog.add(new JLabel("Name:"));
        editDetailsDialog.add(nameField);
        editDetailsDialog.add(new JLabel("Description:"));
        editDetailsDialog.add(descField);
        editDetailsDialog.add(new JLabel("Cofetarie ID:"));
        editDetailsDialog.add(cofetarieField);
        editDetailsDialog.add(new JLabel("Price:"));
        editDetailsDialog.add(priceField);
        editDetailsDialog.add(new JLabel("Production Date:"));
        editDetailsDialog.add(productionDateField);
        editDetailsDialog.add(new JLabel("Expiry Date:"));
        editDetailsDialog.add(expiryDateField);
        editDetailsDialog.add(new JLabel("Image URL:"));
        editDetailsDialog.add(imageField);

        JButton saveButton = new JButton("Save Changes");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            try {
                prajitura.setNume_prajitura(nameField.getText());
                prajitura.setDescriere(descField.getText());
                prajitura.setCofetarie_id(Integer.parseInt(cofetarieField.getText()));
                prajitura.setPret(new BigDecimal(priceField.getText()));
                prajitura.setData_productie(LocalDate.parse(productionDateField.getText()));
                prajitura.setData_expirare(LocalDate.parse(expiryDateField.getText()));
                prajitura.setImagine(imageField.getText());

                boolean isUpdated = prajituraPresenter.updatePrajitura(prajitura);
                if (isUpdated) {
                    JOptionPane.showMessageDialog(editDetailsDialog, "Cake updated successfully!");
                    editDetailsDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(editDetailsDialog, "Failed to update cake.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editDetailsDialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> editDetailsDialog.dispose());

        editDetailsDialog.add(saveButton);
        editDetailsDialog.add(cancelButton);
        editDetailsDialog.setVisible(true);
    }

    @Override
    public void showView() {
        setVisible(true);
    }

    @Override
    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
