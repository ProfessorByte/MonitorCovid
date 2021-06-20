package sintomas;

import monitor.Sintoma;

public class Determinante extends Sintoma {

    public Determinante(String n) {
        super(n);
    }

    @Override
    public int peso() {
        return 100;
    }
}
