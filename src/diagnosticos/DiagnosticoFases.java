package diagnosticos;

import monitor.*;

import java.io.*;

public class DiagnosticoFases extends FuncionDiagnostico {
    private File faseIndicador;
    private Sintomas sintomas;

    public DiagnosticoFases(Sintomas s) {
        super(s);
        this.sintomas = s;
        faseIndicador = new File("indicadorEstado_PabloPardo.txt");
        try {
            if (!faseIndicador.exists()) {
                faseIndicador.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(faseIndicador));
                bw.write("Fase0,0");
                bw.flush();
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int diagnostico(Registros registros) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(faseIndicador));
            String[] estadoActual = br.readLine().split(",");
            br.close();
            if (estadoActual[0].equals("Fase0") && cantSintomasEnRegistro(registros.peek()) >= cantSintomasPrimeraFase() / 2) {
                estadoActual[0] = "Fase1";
                estadoActual[1] = (Integer.parseInt(estadoActual[1]) + 1) + "";
            } else if (estadoActual[0].equals("Fase1") && cantSintomasEnRegistro(registros.peek()) < cantSintomasPrimeraFase() / 2) {
                estadoActual[0] = "Fase0";
                estadoActual[1] = "0";
            } else if (estadoActual[0].equals("Fase1") && Integer.parseInt(estadoActual[1]) >= 2) {
                estadoActual[0] = "Fase2";
                estadoActual[1] = (Integer.parseInt(estadoActual[1]) + 1) + "";
            } else if (estadoActual[0].equals("Fase1") && Integer.parseInt(estadoActual[1]) < 2) {
                estadoActual[0] = "Fase1";
                estadoActual[1] = (Integer.parseInt(estadoActual[1]) + 1) + "";
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(faseIndicador));
            bw.write(estadoActual[0] + "," + estadoActual[1]);
            bw.flush();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
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

    private int cantSintomasEnRegistro(Registro registro) {
        int res = 0;
        for (Sintoma s: registro.getSintomas()) {
            res++;
        }
        return res;
    }
}
