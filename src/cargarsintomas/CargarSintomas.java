package cargarsintomas;

import monitor.Sintoma;
import monitor.Sintomas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CargarSintomas {

    private Sintomas sintomas;
    private File sintomasTXT;

    public CargarSintomas() {
        sintomas = new Sintomas();
        sintomasTXT = new File("cargarsintomas\\sintomas.txt");
        if (!sintomasTXT.exists()) {
            try {
                sintomasTXT.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void cargarArchivo() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(sintomasTXT));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] s = linea.split(",");
                sintomas.add((Sintoma) Class.forName("sintomas." + s[1]).getConstructor(String.class).newInstance(s[0]));
            }
            br.close();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void insertarSintoma(String nuevoSintoma, String categoria) {
        try {
            sintomas.add((Sintoma) Class.forName("sintomas." + categoria).getConstructor(String.class).newInstance(nuevoSintoma));
            BufferedWriter bw = new BufferedWriter(new FileWriter(sintomasTXT.getAbsoluteFile(), true));
            bw.write(nuevoSintoma + "," + categoria + "\n");
            bw.flush();
            bw.close();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public List<String> listarCategoriasSintomas() {
        List<String> res = new ArrayList<>();
        File sintomasDir = new File("sintomas");
        if (!sintomasDir.exists()) {
            sintomasDir = new File("out\\production\\MonitorCovid\\sintomas");
        }
        for (File f : sintomasDir.listFiles()) {
            String nombre = f.getName();
            nombre = nombre.substring(0, nombre.indexOf('.'));
            try {
                Class.forName("sintomas." + nombre).asSubclass(Sintoma.class);
                res.add(nombre);
            } catch (ClassCastException | ClassNotFoundException e) {
                //e.printStackTrace();
                continue;
            }
        }
        return res;
    }

    public Sintomas getSintomas() {
        ConsolaAgregarSintomas consolaAgregarSintomas = new ConsolaAgregarSintomas();
        cargarArchivo();
        return sintomas;
    }

    public Sintomas getSintomasConsola() {
        return sintomas;
    }
}
