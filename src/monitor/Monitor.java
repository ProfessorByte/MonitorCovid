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
            System.out.println("Dia " + fase.getDia() + ": No debe olvidar registrar sus sintomas diariamente y por precausion vaya a visitar a un medico");
        } else if (resultadoDiagnostico == 2) {
            System.out.println("Dia " + fase.getDia() + ": USTED DEBE IR AL MEDICO DE FORMA URGENTE Y TOMARSE UNA PRUEBA PCR");
        } else if (resultadoDiagnostico == 3) {
            System.out.println("Dia " + fase.getDia() + ": SU SALUD ESTA EN RIESGO, EXISTE UNA ALTA PROBABILIDAD DE QUE TENGA COVID-19, DEBE IR AL MEDICO Y HACERSE LA PRUEBA PCR INMEDIATAMENTE");
        }
    }


    public int getResultado() {
        return resultadoDiagnostico;
    }

}
