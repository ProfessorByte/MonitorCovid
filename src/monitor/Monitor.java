package monitor;

import cargarregistros.CargarRegistros;
import cargarsintomas.CargarSintomas;
import diagnosticos.DiagnosticoPorFases;

public class Monitor {
    private Fase fase;
    private Sintomas sintomas;
    private Registros registros;
    private FuncionDiagnostico funcion;
    private int resultadoDiagnostico;
    private CargarRegistros cargarRegistros;

    public Monitor() {
        CargarSintomas cargarSintomas = new CargarSintomas();
        sintomas = cargarSintomas.getSintomas();
        registros = new Registros();
        fase = (new DatosFase()).leerDatosFase();
        funcion = new DiagnosticoPorFases(sintomas, fase);
        cargarRegistros = new CargarRegistros(sintomas.getSintomasFase(fase));
    }

    public void monitorear() {
        registros = cargarRegistros.getRegistros();
        resultadoDiagnostico = funcion.diagnostico(registros);
        mostrarRecomendacion(resultadoDiagnostico);
    }

    private void mostrarRecomendacion(int resultadoDiagnostico){
        if (resultadoDiagnostico == 1) {
            System.out.println("No debe olvidar registrar sus sintomas diariamente y por precausion vaya a visitar a un medico");
        } else if (resultadoDiagnostico == 2) {
            System.out.println("USTED DEBE IR AL MEDICO DE FORMA URGENTE Y TOMARSE UNA PRUEBA PCR");
        }
    }


    public int getResultado() {
        return resultadoDiagnostico;
    }

}
