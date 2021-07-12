package cargarsintomas;

import cargarsintomas.gui.CargarSintomasGUI;

public class EjecucionSintomas {
    private final CargarSintomasGUI gui;

    public EjecucionSintomas(CargarSintomasGUI gui) {
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
