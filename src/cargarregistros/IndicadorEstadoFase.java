package cargarregistros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class IndicadorEstadoFase {
    private File indicadorEstado;

    public IndicadorEstadoFase() {
        indicadorEstado = new File("indicadorEstado_PabloPardo.txt");
    }

    public String leerIndicador() {
        String indicadorLinea[] = new String[2];
        try {
            BufferedReader br = new BufferedReader(new FileReader(indicadorEstado));
            indicadorLinea = br.readLine().split(",");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indicadorLinea[0];
    }

    public boolean existe() {
        return indicadorEstado.exists();
    }
}
