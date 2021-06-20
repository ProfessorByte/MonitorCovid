package sintomas;

import monitor.Sintoma;

public class Importante extends Sintoma {

    public Importante(String n) {
        super(n);
    }

    @Override
    public int peso() {
        return 10;
    }

}
