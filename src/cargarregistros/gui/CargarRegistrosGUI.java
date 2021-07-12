package cargarregistros.gui;

import cargarregistros.ComunicacionInterfazRegistros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class CargarRegistrosGUI extends JFrame {
    private JButton btnRegistrarSintomas;
    private JTable tblRegistroSintomas;
    private JButton btnFinalizarRegistro;
    private JList<String> listFechas;
    private JList<String> listSintomasRegistrados;
    private JPanel panelRegistros;
    private JLabel labelRecomendacion;

    private final DefaultTableModel tblModel;
    private final DefaultListModel<String> listFechasModel;
    private final DefaultListModel<String> listSintomasModel;
    private final ComunicacionInterfazRegistros comunicacionInterfazRegistros;
    private final FinalizarRegistros finalizarRegistros;

    public CargarRegistrosGUI(ComunicacionInterfazRegistros comunicacionInterfazRegistros) {
        super("Registrar sintomas");

        this.tblModel = (DefaultTableModel) tblRegistroSintomas.getModel();
        this.listFechasModel = new DefaultListModel<>();
        this.listSintomasModel = new DefaultListModel<>();
        this.comunicacionInterfazRegistros = comunicacionInterfazRegistros;
        this.finalizarRegistros = new FinalizarRegistros(this);

        setContentPane(panelRegistros);
        addWindowListener(finalizarRegistros);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        listFechas.setModel(listFechasModel);
        listSintomasRegistrados.setModel(listSintomasModel);
        tblModel.setColumnIdentifiers(new String[]{"Sintomas", ""});
        insertarCheckBoxATabla(1, tblRegistroSintomas);
        for (String sintoma : comunicacionInterfazRegistros.cargarSintomasAElegir()) {
            tblModel.addRow(new String[]{sintoma});
        }
        mostrarFechasRegistros();

        labelRecomendacion.setText("");
        if (comunicacionInterfazRegistros.getIndicadorEstadoFase().existe()) {
            if (comunicacionInterfazRegistros.getIndicadorEstadoFase().leerIndicador().equals("Fase1")) {
                labelRecomendacion.setText("Usted debe visitar al medico y monitorear sus sintomas diariamente");
            } else if (comunicacionInterfazRegistros.getIndicadorEstadoFase().leerIndicador().equals("Fase2")) {
                labelRecomendacion.setText("VAYA AL MEDICO URGENTEMENTE, Y HAGASE UNA PRUEBA PCR");
            }
        }
        labelRecomendacion.setForeground(Color.RED);

        listFechas.addMouseListener(new SintomasFechas(this));
        btnFinalizarRegistro.addActionListener(e -> finalizarRegistros.finalizarRegistro());
        btnRegistrarSintomas.addActionListener(new NuevoRegistroListener(this));
    }

    public void mostrarFechasRegistros() {
        List<String> registros = comunicacionInterfazRegistros.cargarRegistrosGuardados();
        Collections.reverse(registros);
        for (String r : registros) {
            listFechasModel.addElement(r);
        }
    }

    private void insertarCheckBoxATabla(int idColumna, JTable tabla) {
        TableColumn tc = tabla.getColumnModel().getColumn(idColumna);
        tc.setCellEditor(tabla.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(tabla.getDefaultRenderer(Boolean.class));
    }

    public DefaultListModel<String> getListSintomasModel() {
        return listSintomasModel;
    }

    public ComunicacionInterfazRegistros getComunicacionInterfazRegistros() {
        return comunicacionInterfazRegistros;
    }

    public JList<String> getListFechas() {
        return listFechas;
    }

    public DefaultTableModel getTblModel() {
        return tblModel;
    }

    public JTable getTblRegistroSintomas() {
        return tblRegistroSintomas;
    }

    public DefaultListModel<String> getListFechasModel() {
        return listFechasModel;
    }
}
