package cargarregistros;

import cargarregistros.gui.CargarRegistrosGUI;
import monitor.Registro;
import monitor.Sintoma;
import monitor.Sintomas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ComunicacionInterfazRegistros {
    private final CargarRegistros cargarRegistros;
    private final CargarRegistrosGUI cargarRegistrosGUI;
    private final CargarCategoriasRegistros cargarCategoriasRegistros;
    private final EjecucionRegistros ejecucionRegistros;
    private final IndicadorEstadoFase indicadorEstadoFase;

    public ComunicacionInterfazRegistros(CargarRegistros cargarRegistros) {
        this.indicadorEstadoFase = new IndicadorEstadoFase();
        this.cargarRegistros = cargarRegistros;
        this.cargarRegistrosGUI = new CargarRegistrosGUI(this);
        this.cargarCategoriasRegistros = new CargarCategoriasRegistros();
        this.ejecucionRegistros = new EjecucionRegistros(cargarRegistrosGUI);
        ejecucionRegistros.detenerEjecucion();
    }

    public List<String> cargarSintomasAElegir() {
        List<String> res = new ArrayList<>();
        Sintomas totalSintomas = cargarRegistros.getTotalSintomas();
        if (indicadorEstadoFase.existe()) {
            try {
                if (indicadorEstadoFase.leerIndicador().equals("Fase2")) {
                    for (Sintoma s: totalSintomas) {
                        res.add(s.toString());
                    }
                } else {
                    for (Sintoma s: totalSintomas) {
                        if (Class.forName("sintomas.PrimeraFase").isInstance(s)) {
                            res.add(s.toString());
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            for (Sintoma s: totalSintomas) {
                try {
                    if (Class.forName("sintomas.PrimeraFase").isInstance(s)) {
                        res.add(s.toString());
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        Collections.sort(res);
        return res;
    }

    public List<String> cargarRegistrosGuardados() {
        List<String> res = new ArrayList<>();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy — HH:mm:ss");
        for (Registro r: cargarRegistros.getRegistros()) {
            res.add(formatoFecha.format(r.getFecha()));
        }
        return res;
    }

    public List<String> cargarSintomasRegistro(int idx) {
        List<String> res = new ArrayList<>();
        List<Registro> registros = new ArrayList<>();
        for (Registro r: cargarRegistros.getRegistros()) {
            registros.add(r);
        }
        Collections.reverse(registros);
        for (Sintoma s: registros.get(idx).getSintomas()) {
            res.add(s.toString());
        }
        return res;
    }

    public void crearRegistro(List<Integer> idxSintomas) {
        Sintomas sintomasRegistro = new Sintomas();
        List<Sintoma> sintomas = new ArrayList<>();
        Sintomas totalSintomas = cargarRegistros.getTotalSintomas();
        List<String> sintomasDisponibles = cargarSintomasAElegir();
        for (Sintoma s: totalSintomas) {
            if (sintomasDisponibles.contains(s.toString())) {
                sintomas.add(s);
            }
        }
        Collections.sort(sintomas);
        for (int idx: idxSintomas) {
            sintomasRegistro.add(sintomas.get(idx));
        }
        Calendar fecha = GregorianCalendar.getInstance();
        cargarRegistros.getRegistros().push(new Registro(fecha.getTime(), sintomasRegistro));
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(cargarRegistros.getRegistrosTXT().getAbsoluteFile(), true));
            bw.write("#," + fecha.getTimeInMillis() + "\n");
            for (Sintoma s: sintomasRegistro) {
                List<String> categorias = cargarCategoriasRegistros.listarCategorias();
                for (String categoria: categorias) {
                    try {
                        if (Class.forName("sintomas." + categoria).isInstance(s)) {
                            bw.write(s.toString() + "," + categoria + "\n");
                        }
                    } catch (ClassNotFoundException e) {
                        continue;
                    }
                }
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EjecucionRegistros getEjecucionRegistros() {
        return ejecucionRegistros;
    }

    public IndicadorEstadoFase getIndicadorEstadoFase() {
        return indicadorEstadoFase;
    }
}
