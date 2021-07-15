package monitor;

import java.io.*;

public class DatosFase {
    private File datosFaseArchivo;

    public DatosFase() {
        datosFaseArchivo = new File("datosFase_PabloPardo.txt");
        if (!datosFaseArchivo.exists()) {
            try {
                datosFaseArchivo.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(datosFaseArchivo));
                bw.write("FaseCero,0");
                bw.flush();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Fase leerDatosFase() {
        String datosFaseLinea[] = new String[2];
        try {
            BufferedReader br = new BufferedReader(new FileReader(datosFaseArchivo));
            datosFaseLinea = br.readLine().split(",");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Fase(datosFaseLinea[0], Integer.parseInt(datosFaseLinea[1]));
    }

    public void guardarDatosFase(Fase fase) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(datosFaseArchivo));
            bw.write(fase.getNombre() + "," + fase.getDia());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
