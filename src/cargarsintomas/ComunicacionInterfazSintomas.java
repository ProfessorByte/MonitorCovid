package cargarsintomas;

import cargarsintomas.gui.CargarSintomasGUI;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ComunicacionInterfazSintomas {
    private final CargarSintomas cargarSintomas;
    private final Validador validador;
    private final CargarSintomasGUI cargarSintomasGUI;
    private final EjecucionSintomas ejecucionSintomas;

    public ComunicacionInterfazSintomas(CargarSintomas cargarSintomas) {
        this.cargarSintomas = cargarSintomas;
        this.validador = new Validador(cargarSintomas);
        this.cargarSintomasGUI = new CargarSintomasGUI(this);
        this.ejecucionSintomas = new EjecucionSintomas(cargarSintomasGUI);
        ejecucionSintomas.detenerEjecucion();
    }

    public List<String[]> getSintomasOrdenados() {
        return cargarSintomas.getSintomasList();
    }

    public boolean insertarSintoma(String nombreSintoma, String categoria) {
        if (validador.validar(nombreSintoma)) {
            String nombre = validador.getUltimoSintomaValidado();
            cargarSintomas.getSintomasList().add(new String[]{nombre, categoria});
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(cargarSintomas.getSintomasTXT().getAbsoluteFile(), true));
                bw.write(nombre + "," + categoria + "\n");
                bw.flush();
                bw.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public EjecucionSintomas getEjecucionSintomas() {
        return ejecucionSintomas;
    }
}
