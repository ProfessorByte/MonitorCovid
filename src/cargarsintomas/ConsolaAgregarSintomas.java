package cargarsintomas;

import java.util.List;
import java.util.Scanner;

public class ConsolaAgregarSintomas {
    List<String> categorias;
    CargarSintomas cs;
    Scanner sc;

    public ConsolaAgregarSintomas() {
        cs = new CargarSintomas();
        categorias = cs.listarCategoriasSintomas();
        String nuevoSintoma;
        while (true) {
            sc = new Scanner(System.in);
            System.out.print("Insertar nuevo sintoma: ");
            nuevoSintoma = sc.nextLine();
            if (nuevoSintoma.equals("")) break;
            ValidadorDeSintomas validador = new ValidadorDeSintomas(nuevoSintoma);
            if (validador.validar(cs.getSintomasConsola())) {
                for (int i = 0; i < categorias.size(); i++) {
                    System.out.println((i + 1) + ". " + categorias.get(i));
                }
                System.out.print("Seleccionar categoria: ");
                int opcion = sc.nextInt();
                cs.insertarSintoma(validador.getNombreSintoma(), categorias.get(opcion - 1));
            } else {
                System.out.println("Sintoma existente");
            }
        }

    }
}
