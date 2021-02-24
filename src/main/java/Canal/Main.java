package Canal;

import Caja.Cliente;

import java.io.IOException;

public class Main {

    /**
     * Punto de entrada del cliente.
     * @param args0
     */
    public static void main(String[] args0) {
        try {
            Cliente miCaja = new Cliente();
            miCaja.principal();
        } catch (IOException e) {
            System.out.println("Problema al conectarse con el servidor. Cerrando caja...");
        } catch (ClassNotFoundException e ) {
            e.printStackTrace();
        }

    }
}
