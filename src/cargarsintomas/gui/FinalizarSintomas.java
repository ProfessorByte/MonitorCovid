package cargarsintomas.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FinalizarSintomas extends WindowAdapter {
    private final CargarSintomasGUI gui;

    public FinalizarSintomas(CargarSintomasGUI gui) {
        this.gui = gui;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        terminar();
    }

    public void terminar() {
        gui.getComunicacionInterfazSintomas().getEjecucionSintomas().continuarEjecucion();
        gui.dispose();
    }
}
