package cargarregistros;

import monitor.Registro;
import monitor.Sintoma;
import monitor.Sintomas;

import java.io.*;
import java.util.*;

public class CargarRegistros {

    private Sintomas sintomas;
    private File registrosTXT;

    public CargarRegistros(Sintomas sintomas) {
        registrosTXT = new File("registros.txt");
        if (!registrosTXT.exists()) {
            try {
                registrosTXT.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.sintomas = sintomas;
    }

    public Sintomas agregarSintomasRegistro(List<String> sintomas) {
        Sintomas res = new Sintomas();
        for (Sintoma sintoma : this.sintomas) {
            for (String s : sintomas) {
                try {
                    if (sintoma.toString().equals(s)) {
                        res.add(sintoma);
                        BufferedWriter bw = new BufferedWriter(new FileWriter(registrosTXT.getAbsoluteFile(), true));
                        bw.write(s + "\n");
                        bw.flush();
                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    private Date registrarTiempo() {
        Calendar hoy = new GregorianCalendar();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(registrosTXT.getAbsoluteFile(), true));
            bw.write("#-" + hoy.get(Calendar.DAY_OF_MONTH) + "-" + (hoy.get(Calendar.MONTH) + 1) + "-" + hoy.get(Calendar.YEAR) + "-" + hoy.get(Calendar.HOUR_OF_DAY) + "-" + hoy.get(Calendar.MINUTE) + "\n");
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hoy.getTime();
    }

    public List<String> listarSintomas() {
        List<String> res = new ArrayList<>();
        for (Sintoma s : sintomas) {
            res.add(s.toString());
        }
        return res;
    }

    public Registro getRegistro() {
        ConsolaAgregarRegistros agregarReg = new ConsolaAgregarRegistros(listarSintomas());
        Date hoy = registrarTiempo();
        Sintomas sintomas = agregarSintomasRegistro(agregarReg.getSintomasAgregados());
        return new Registro(hoy, sintomas);
    }
}
