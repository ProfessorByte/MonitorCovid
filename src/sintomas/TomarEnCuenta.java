package sintomas;

import monitor.Sintoma;

public class TomarEnCuenta extends Sintoma {

    public TomarEnCuenta(String n) {
        super(n);
    }

    @Override
    public int peso() {
        return 1;
    }
}
