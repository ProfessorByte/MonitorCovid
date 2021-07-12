package cargarregistros;

import monitor.Registro;
import monitor.Registros;
import monitor.Sintoma;
import monitor.Sintomas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CargarRegistros {
    private final Sintomas sintomas;
    private final Registros registros;
    private final File registrosTXT;

    public CargarRegistros(Sintomas sintomas) {
        this.sintomas = sintomas;
        this.registros = new Registros();
        this.registrosTXT = new File("registros_PabloPardo.txt");

        try {
            if (!registrosTXT.exists()) {
                registrosTXT.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        cargarRegistrosGuardados();
        new ComunicacionInterfazRegistros(this);
    }

    private void cargarRegistrosGuardados() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(registrosTXT));
            Registro registro;
            Sintomas sintomas = new Sintomas();
            String linea;
            while ((linea=br.readLine()) != null) {
                String[] lineaDividida = linea.split(",");
                if (lineaDividida[0].equals("#")) {
                    sintomas = new Sintomas();
                    Calendar date = GregorianCalendar.getInstance();
                    date.setTimeInMillis(Long.parseLong(lineaDividida[1]));
                    registro = new Registro(date.getTime(), sintomas);
                    registros.push(registro);
                } else {
                    sintomas.add((Sintoma) Class.forName("sintomas." + lineaDividida[1]).getConstructor(String.class).newInstance(lineaDividida[0]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public File getRegistrosTXT() {
        return registrosTXT;
    }

    public Registros getRegistros() {
        return registros;
    }

    public Sintomas getTotalSintomas() {
        return sintomas;
    }

    public Registro getRegistro() {
        if (registros.isEmpty()) {
            return new Registro(new Date(), new Sintomas());
        }
        return registros.peek();
    }
}
