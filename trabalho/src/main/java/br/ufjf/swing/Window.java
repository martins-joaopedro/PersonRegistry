package br.ufjf.swing;

import br.ufjf.services.PersonService;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 500;

    public static PersonService service;
    private JPanel mainPanel;

    public Window() {
        this.service = new PersonService();
        setSize(new Dimension(WIDTH, HEIGHT));

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1;
        mainPanel.add(new InputEditPanel(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(new TablePanel(), gbc);

        add(mainPanel);

        setVisible(true);
    }




    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}