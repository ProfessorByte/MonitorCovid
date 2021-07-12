package cargarregistros.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FinalizarRegistros extends WindowAdapter {
    private final CargarRegistrosGUI gui;

    public FinalizarRegistros(CargarRegistrosGUI gui) {
        this.gui = gui;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        finalizarRegistro();
    }

    public void finalizarRegistro() {
        gui.getComunicacionInterfazRegistros().getEjecucionRegistros().continuarEjecucion();
        gui.dispose();
    }
}
