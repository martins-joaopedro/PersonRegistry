package br.ufjf.swing;

import br.ufjf.models.Person;
import br.ufjf.utils.Utils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TablePanel extends JScrollPane {

    private static JTable table;
    private static DefaultTableModel model = new DefaultTableModel(new Object[]{"Nome", "Data de Nascimento", "Idade atual", "CPF"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public TablePanel() {

        LineBorder roundedLine = new LineBorder(Color.gray, 2, true);
        TitledBorder tableTitledBorder = new TitledBorder(roundedLine, "Tabela de Dados");
        setBorder(tableTitledBorder);

        this.table = new JTable(model);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        setViewportView(table);
        setVisible(true);

    }

    public static void refreshTable() {
        table.setVisible(false);
        model.setRowCount(0);
        for (Person person : Window.service.getAll()) {
            model.addRow(new Object[]{person.getName(), person.getBirthDate(), Utils.getYearsFromBirthDate(person.getBirthDate()), person.getCPF()});
        }
        table.setVisible(true);
    }

    public static int getSelectedRow() {
        return table.getSelectedRow();
    }


}
