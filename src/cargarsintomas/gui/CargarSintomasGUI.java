package cargarsintomas.gui;

import cargarsintomas.CargarCategorias;
import cargarsintomas.ComunicacionInterfazSintomas;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.util.Collections;
import java.util.List;

public class CargarSintomasGUI extends JFrame {
    private JTextField tfNuevoSintoma;
    private JButton btnInsertarSintoma;
    private JButton btnFinalizar;
    private JLabel labelEstado;
    private JPanel panelSintomas;
    private JTable tblSintomas;
    private JComboBox<String> cbNuevoSintoma;

    private final ComunicacionInterfazSintomas comunicacionInterfazSintomas;
    private final DefaultTableModel tblModel;
    private final FinalizarSintomas finalizarSintomas;
    private final CargarCategorias cargarCategorias;

    public CargarSintomasGUI(ComunicacionInterfazSintomas comunicacionInterfazSintomas) {
        super("Agregar sintomas");

        this.comunicacionInterfazSintomas = comunicacionInterfazSintomas;
        this.tblModel = (DefaultTableModel) tblSintomas.getModel();
        this.finalizarSintomas = new FinalizarSintomas(this);
        this.cargarCategorias = new CargarCategorias();

        setContentPane(panelSintomas);
        addWindowListener(finalizarSintomas);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        labelEstado.setText("");
        tblModel.setColumnIdentifiers(new String[]{"Sintoma", "Categoria"});
        List<String> categorias = cargarCategorias.listarCategorias();
        Collections.sort(categorias);
        for (String categoria : categorias) {
            cbNuevoSintoma.addItem(categoria);
        }
        actualizarListaSintomas();

        btnInsertarSintoma.addActionListener(new AgregarSintomaListener(this));
        btnFinalizar.addActionListener(e -> finalizarSintomas.terminar());
    }

    public void actualizarListaSintomas() {
        tblModel.setRowCount(0);
        for (String[] sintoma : comunicacionInterfazSintomas.getSintomasOrdenados()) {
            tblModel.addRow(sintoma);
        }
    }

    public ComunicacionInterfazSintomas getComunicacionInterfazSintomas() {
        return comunicacionInterfazSintomas;
    }

    public JTextField getTfNuevoSintoma() {
        return tfNuevoSintoma;
    }

    public JComboBox<String> getCbNuevoSintoma() {
        return cbNuevoSintoma;
    }

    public JLabel getLabelEstado() {
        return labelEstado;
    }
}
