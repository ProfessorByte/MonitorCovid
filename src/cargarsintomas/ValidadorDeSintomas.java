package cargarsintomas;

import monitor.Sintoma;
import monitor.Sintomas;

public class ValidadorDeSintomas {
    private String nombreSintoma;

    public ValidadorDeSintomas(String nombreSintoma) {
        this.nombreSintoma = normalizar(nombreSintoma);
    }

    public boolean validar(Sintomas sintomas) {
        for (Sintoma s : sintomas) {
            if (levenshteinDistance(nombreSintoma, s.toString()) <= 3) {
                return false;
            }
        }
        return true;
    }

    public String getNombreSintoma() {
        return nombreSintoma;
    }

    private String normalizar(String nombreSintoma) {
        String normalizacionEspacios = nombreSintoma.replaceAll(" +", " ").trim().toLowerCase();
        char[] nombreSintomaChars = normalizacionEspacios.toCharArray();
        nombreSintomaChars[0] = Character.toUpperCase(nombreSintomaChars[0]);
        return new String(nombreSintomaChars);
    }

    private int levenshteinDistance(String str1, String str2) {
        return levenshteinDistance(str1.toCharArray(), str2.toCharArray());
    }

    private int minimum(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    private int levenshteinDistance(char[] str1, char[] str2) {
        int[][] distance = new int[str1.length + 1][str2.length + 1];

        for (int i = 0; i <= str1.length; i++) {
            distance[i][0] = i;
        }
        for (int j = 0; j <= str2.length; j++) {
            distance[0][j] = j;
        }
        for (int i = 1; i <= str1.length; i++) {
            for (int j = 1; j <= str2.length; j++) {
                distance[i][j] = minimum(distance[i - 1][j] + 1, distance[i][j - 1] + 1, distance[i - 1][j - 1] + ((str1[i - 1] == str2[j - 1]) ? 0 : 1));
            }
        }
        return distance[str1.length][str2.length];
    }
}
