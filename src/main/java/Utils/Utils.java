package Utils;

import java.util.Scanner;

public class Utils {

    /**
     * Método que valida los números introducido por el usuario sea número y no string.
     * @return número que da el usuario
     */
    public static int validacionNumero(Scanner miScanner) {
        boolean comprobar = false;
        int numero = 0;
        while (!comprobar) {
            try {
                numero = Integer.parseInt(miScanner.nextLine());
                comprobar = true;
            } catch (Exception e) {
                System.err.println("Debes introducir un número");
            }
        }

        return numero;
    }

    /**
     * Método que valida los números introducido por el usuario sea número y no string.
     * @param miScanner
     * @param numeroValido
     * @return número que da el usuario
     */
    public static int validacionNumero(Scanner miScanner, int numeroValido) {
        int numero = 0;
        boolean continuar = true;
        while (continuar) {
            numero = validacionNumero(miScanner);
            continuar = false;

            if (numero <= 0 || numero > numeroValido) {
                System.out.println("Introduzca una opción válida");
                continuar = true;
            }
        }

        return numero;
    }

}
