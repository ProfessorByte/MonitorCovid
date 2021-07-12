package cargarregistros.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;

public class SintomasFechas extends MouseAdapter {
    private final CargarRegistrosGUI gui;

    public SintomasFechas(CargarRegistrosGUI gui) {
        this.gui = gui;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mostrarSintomasRegistros();
    }

    public void mostrarSintomasRegistros() {
        gui.getListSintomasModel().removeAllElements();
        List<String> sintomasRegistro = gui.getComunicacionInterfazRegistros().cargarSintomasRegistro(gui.getListFechas().getSelectedIndex());
        Collections.sort(sintomasRegistro);
        for (String s : sintomasRegistro) {
            gui.getListSintomasModel().addElement(s);
        }
    }
}
