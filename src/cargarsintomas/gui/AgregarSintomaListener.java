package cargarsintomas.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgregarSintomaListener implements ActionListener {
    private final CargarSintomasGUI gui;

    public AgregarSintomaListener(CargarSintomasGUI gui) {
        this.gui = gui;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        agregarNuevoSintoma();
    }

    private void agregarNuevoSintoma() {
        if (gui.getComunicacionInterfazSintomas().insertarSintoma(gui.getTfNuevoSintoma().getText(), gui.getCbNuevoSintoma().getSelectedItem().toString())) {
            gui.actualizarListaSintomas();
            gui.getLabelEstado().setText("Sintoma insertado");
            gui.getLabelEstado().setForeground(Color.green);
        } else {
            gui.getLabelEstado().setText("Sintoma NO insertado");
            gui.getLabelEstado().setForeground(Color.red);
        }
    }
}
