package cargarsintomas;

import monitor.Sintoma;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CargarCategorias {

    public List<String> listarCategorias() {
        List<String> res = new ArrayList<>();
        File sintomasDir = new File("sintomas");
        if (!sintomasDir.exists()) {
            sintomasDir = new File("out\\production\\MonitorCovid\\sintomas");
            if (!sintomasDir.exists()) {
                return listarCategoriasJar();
            }
        }
        for (File f : sintomasDir.listFiles()) {
            String nombre = f.getName();
            nombre = nombre.substring(0, nombre.indexOf('.'));
            try {
                Class.forName("sintomas." + nombre).asSubclass(Sintoma.class);
                res.add(nombre);
            } catch (ClassCastException e) {
                continue;
            } catch (ClassNotFoundException e) {
                continue;
            }
        }
        return res;
    }

    private List<String> listarCategoriasJar() {
        List<String> res = new ArrayList<>();
        try {
            ZipInputStream zip = new ZipInputStream(new FileInputStream("home.jar"));
            for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                String[] direccionCompleta = entry.getName().replace('/', '.').split("\\.");
                if (direccionCompleta.length >= 2 && direccionCompleta[0].equals("sintomas")) {
                    try {
                        Class.forName("sintomas." + direccionCompleta[1]).asSubclass(Sintoma.class);
                        res.add(direccionCompleta[1]);
                    } catch (ClassNotFoundException e) {
                        continue;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
