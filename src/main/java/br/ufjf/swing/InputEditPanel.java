package br.ufjf.swing;

import br.ufjf.models.Person;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class InputEditPanel extends JPanel {

    GridBagConstraints gbc;
    boolean isEditing = false;
    JTextField name;
    JFormattedTextField birthday;
    JFormattedTextField cpf;
    JLabel labelName;
    JLabel labelBirthday;
    JLabel labelCPF;

    JButton addButton;
    JButton removeButton;
    JButton editButton;

    InputEditPanel() {
        LineBorder roundedLine = new LineBorder(Color.gray, 2, true);
        TitledBorder inputTitledBorder = new TitledBorder(roundedLine, "Cadastro de Pessoas");
        setBorder(inputTitledBorder);
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1;
        gbc.weighty = 0.8;
        gbc.insets = new Insets(5, 5, 5, 5);

        labelName = new JLabel("Digite aqui seu nome: ");
        labelBirthday = new JLabel("Digite aqui sua Data de Nascimento: ");
        labelCPF = new JLabel("Digite aqui seu CPF: ");

        name = new JTextField(20);

        try {
            MaskFormatter formatter = new MaskFormatter("##/##/####");
            formatter.setPlaceholder("_");
            birthday = new JFormattedTextField(formatter);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            MaskFormatter formatter = new MaskFormatter("###.###.###-##");
            formatter.setPlaceholder("_");
            cpf = new JFormattedTextField(formatter);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        addComponent(labelName, 0, 0);
        addComponent(name, 0, 1);

        addComponent(labelBirthday, 0, 2);
        addComponent(birthday, 0, 3);

        addComponent(labelCPF, 0, 4);
        addComponent(cpf, 0, 5);

        JPanel buttonsPanel = new JPanel();
        GridLayout buttonsPanelLayout = new GridLayout(3, 1);
        buttonsPanelLayout.setVgap(15);
        buttonsPanel.setLayout(buttonsPanelLayout);

        addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> addController());

        removeButton = new JButton("Remover");
        removeButton.addActionListener(e -> removeController());

        editButton = new JButton("Editar");
        editButton.addActionListener(e -> switchToEditionController());

        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(editButton);
        addComponent(buttonsPanel, 0,6);
        setVisible(true);
    }

    private void addComponent(JComponent c, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        add(c, gbc);
    }

    private void addController() {
        Window.service.add(name.getText(), birthday.getText(), cpf.getText());
        TablePanel.refreshTable();
    }

    public void switchToEditionController() {
        this.isEditing = !this.isEditing;
        try {
            if(isEditing) {
                int row = TablePanel.getSelectedRow();
                Person p = Window.service.getByRow(row);
                labelBirthday.setText("Edite a Data de Nascimento: ");
                birthday.setText(p.getBirthDate().toString());
                labelCPF.setText("Edite o CPF");
                cpf.setText(p.getCPF());
                labelName.setText("Edite o Nome: ");
                name.setText(p.getName());
                editButton.setText("Salvar");
                addButton.setVisible(false);
                removeButton.setVisible(false);
            } else {
                // confirma as atualizações
                confirmEdition();

                // encerra as configurações de edição
                closeEditionMode();
            }
        } catch (IndexOutOfBoundsException e) {
            Window.showError("Selecione uma linha para editar!");
        }
    }

    private void confirmEdition() {
        try {
            int row = TablePanel.getSelectedRow();
            Person p = new Person(name.getText(), birthday.getText(), cpf.getText());
            Window.service.updateByRow(row, p);
            TablePanel.refreshTable();
        } catch (IndexOutOfBoundsException e) {
            Window.showError("Selecione uma linha para editar!");
            closeEditionMode();
        }
    }

    private void closeEditionMode() {
        this.isEditing = false;
        addButton.setVisible(true);
        removeButton.setVisible(true);
        labelBirthday.setText("Digite uma Data de Nascimento: ");
        birthday.setText("");
        labelCPF.setText("Digite um CPF");
        cpf.setText("");
        labelName.setText("Digite um Nome: ");
        name.setText("");
        editButton.setText("Editar");
    }

    private void removeController() {
        int row = TablePanel.getSelectedRow();

        try {
            Person p = Window.service.getByRow(row);
            Window.service.remove(p);
            TablePanel.refreshTable();
        } catch (IndexOutOfBoundsException e) {
            Window.showError("Selecione uma linha para editar!");
        }
    }
}
