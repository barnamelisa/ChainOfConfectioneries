package view;

import model.repository.CakeRepository;
import presenter.PrajituraPresenter;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.Connection;

public class ViewAllCakes extends JDialog implements Serializable {
    private JTextArea textArea1;
    private JButton backButton;
    private Connection conexiune;
    private Prajitura parentPage;
    private PrajituraPresenter prajituraPresenter;
    public ViewAllCakes(Prajitura parent, Connection conexiune, PrajituraPresenter prajituraPresenter){
        super(parent, "Cakes List", true);
        this.conexiune = conexiune;
        this.parentPage = parent;
        this.prajituraPresenter = prajituraPresenter;

        setSize(400, 300);
        setLayout(new BorderLayout());

        textArea1 = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea1);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.addActionListener(e -> {
            dispose();
            parentPage.setVisible(true);
        });
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        String cakesData = prajituraPresenter.displayObjectsFromDatabase("prajitura");
        textArea1.setText(cakesData);
    }

}
