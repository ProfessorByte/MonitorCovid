package cargarregistros;

import cargarregistros.gui.CargarRegistrosGUI;

public class EjecucionRegistros {
    private final CargarRegistrosGUI gui;

    public EjecucionRegistros(CargarRegistrosGUI gui) {
        this.gui = gui;
    }

    public void detenerEjecucion() {
        synchronized (gui) {
            try {
                gui.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void continuarEjecucion() {
        synchronized (gui) {
            gui.notify();
        }
    }
}
