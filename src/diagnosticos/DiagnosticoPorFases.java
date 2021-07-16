package diagnosticos;

import monitor.*;

public class DiagnosticoPorFases extends FuncionDiagnostico {
    private Fase faseActual;
    private Sintomas sintomas;

    public DiagnosticoPorFases(Sintomas s, Fase faseActual) {
        super(s);
        this.sintomas = s;
        this.faseActual = faseActual;
    }

    @Override
    public int diagnostico(Registros registros) {
        int res = 0;
        if (!registros.isEmpty()) {
            if (faseActual.getNombre().equals("FaseCero") && cantSintomasEnRegistro(registros.peek()) >= cantSintomasPrimeraFase() / 2) {
                faseActual.setNombre("PrimeraFase");
                faseActual.setDia(faseActual.getDia() + 1);
                res = 1;
            } else if (faseActual.getNombre().equals("PrimeraFase") && cantSintomasEnRegistro(registros.peek()) < cantSintomasPrimeraFase() / 2) {
                faseActual.setNombre("FaseCero");
                faseActual.setDia(0);
            } else if (faseActual.getNombre().equals("PrimeraFase") && faseActual.getDia() >= 2) {
                faseActual.setNombre("SegundaFase");
                faseActual.setDia(faseActual.getDia() + 1);
                res = 2;
            } else if (faseActual.getNombre().equals("PrimeraFase") && faseActual.getDia() < 2) {
                faseActual.setNombre("PrimeraFase");
                faseActual.setDia(faseActual.getDia() + 1);
                res = 1;
            } else if (faseActual.getNombre().equals("SegundaFase") && faseActual.getDia() < 5) {
                faseActual.setDia(faseActual.getDia() + 1);
                res = 2;
            } else if (faseActual.getNombre().equals("SegundaFase") && faseActual.getDia() >= 5 && cantSintomasRegSegFase(registros.peek()) >= cantSintomasSegundaFase() / 2) {
                faseActual.setDia(faseActual.getDia() + 1);
                res = 3;
            } else if (faseActual.getNombre().equals("SegundaFase")) {
                faseActual.setDia(faseActual.getDia() + 1);
                res = 2;
            }
            (new DatosFase()).guardarDatosFase(faseActual);
        }
        return res;
    }

    private int cantSintomasPrimeraFase() {
        int res = 0;
        for (Sintoma s: this.sintomas) {
            try {
                if (Class.forName("sintomas.PrimeraFase").isInstance(s)) {
                    res++;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    private int cantSintomasSegundaFase() {
        int res = 0;
        for (Sintoma s: this.sintomas) {
            try {
                if (Class.forName("sintomas.SegundaFase").isInstance(s)) {
                    res++;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    private int cantSintomasEnRegistro(Registro registro) {
        int res = 0;
        for (Sintoma s: registro.getSintomas()) {
            res++;
        }
        return res;
    }

    private int cantSintomasRegSegFase(Registro registro) {
        int res = 0;
        for (Sintoma s: registro.getSintomas()) {
            try {
                if (Class.forName("sintomas.SegundaFase").isInstance(s)) {
                    res++;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
