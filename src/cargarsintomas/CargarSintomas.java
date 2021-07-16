package cargarsintomas;

import monitor.Sintoma;
import monitor.Sintomas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CargarSintomas {
    private final List<String[]> sintomas;
    private final File sintomasTXT;

    public CargarSintomas() {
        sintomas = new ArrayList<>();
        sintomasTXT = new File("sintomasPabloPardo.txt");
        if (!sintomasTXT.exists()) {
            try {
                sintomasTXT.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cargarSintomasGuardados();
        new ComunicacionInterfazSintomas(this);
    }

    private void cargarSintomasGuardados() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(sintomasTXT));
            String linea;
            while ((linea=br.readLine()) != null) {
                sintomas.add(linea.split(","));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getSintomasTXT() {
        return sintomasTXT;
    }

    public List<String[]> getSintomasList() {
        sintomas.sort(Comparator.comparing(o -> o[0]));
        return sintomas;
    }

    public Sintomas getSintomas() {
        Sintomas sintomasInstanciados = new Sintomas();
        try{
            for (String[] s: sintomas) {
                sintomasInstanciados.add((Sintoma) Class.forName("sintomas." + s[1]).getConstructor(String.class).newInstance(s[0]));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return sintomasInstanciados;
    }
}
