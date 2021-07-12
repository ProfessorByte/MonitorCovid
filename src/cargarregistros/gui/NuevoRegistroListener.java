package cargarregistros.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NuevoRegistroListener implements ActionListener {
    private final CargarRegistrosGUI gui;

    public NuevoRegistroListener(CargarRegistrosGUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        insertarNuevoRegistro();
    }

    private void insertarNuevoRegistro() {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < gui.getTblModel().getRowCount(); i++) {
            if (gui.getTblRegistroSintomas().getValueAt(i, 1) == Boolean.TRUE) {
                indices.add(i);
            }
        }
        if (!indices.isEmpty()) {
            gui.getComunicacionInterfazRegistros().crearRegistro(indices);
            gui.getListFechasModel().removeAllElements();
            gui.mostrarFechasRegistros();
        }
    }
}
