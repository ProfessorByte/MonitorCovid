package cargarregistros;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsolaAgregarRegistros {
    List<String> sintomasDisponibles;
    List<String> sintomasAgregados;
    Scanner sc;

    public ConsolaAgregarRegistros(List<String> sintomasDisponibles) {
        this.sintomasDisponibles = sintomasDisponibles;
        this.sintomasAgregados = new ArrayList<>();
        int opcion = 0;
        while (true) {
            sc = new Scanner(System.in);
            for (int i = 0; i < this.sintomasDisponibles.size(); i++) {
                System.out.println((i + 1) + ". " + this.sintomasDisponibles.get(i));
            }
            System.out.print("Seleccionar un sintoma: ");
            opcion = sc.nextInt() - 1;
            if (opcion < 0 || opcion >= sintomasDisponibles.size()) break;
            this.sintomasAgregados.add(this.sintomasDisponibles.get(opcion));
            this.sintomasDisponibles.remove(opcion);
        }
    }

    public List<String> getSintomasAgregados() {
        return sintomasAgregados;
    }
}
