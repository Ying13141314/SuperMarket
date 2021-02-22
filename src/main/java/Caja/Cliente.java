package Caja;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    String Host = "localhost";
    int Puerto = 6000;//puerto remoto
    Socket cliente;

    /**
     * Scanner para recoger información que escribe el usuario.
     */
    private static Scanner miScanner;

    /**
     * Variable que guarda el id del empleado.
     */
    private int idEmpleado;

    private int cantidad;

    private InputStream entrada;

    private OutputStream salida;

    private int opcionProducto;

    public Cliente() throws IOException {
        miScanner = new Scanner(System.in);
        cliente= new Socket(Host, Puerto);
        entrada = cliente.getInputStream(); // CREO FLUJO DE ENTRADA DEL CLIENTE
        salida = cliente.getOutputStream(); // CREO FLUJO DE SALIDA AL CLIENTE
    }



    public void principal() throws Exception {

        System.out.println("PROGRAMA CLIENTE INICIADO....");


        // CREO FLUJO DE SALIDA AL SERVIDOR
        DataOutputStream flujoSalida = new DataOutputStream(salida);

        // ENVIO el login al servidor
        System.out.println("¡Hola empleado, loguease por favor!");
        idEmpleado=validacionNumero();
        flujoSalida.writeUTF("Login;"+idEmpleado);

        // CREO FLUJO DE ENTRADA AL SERVIDOR
        ObjectInputStream flujoEntrada = new ObjectInputStream(entrada);

        if (flujoEntrada.readObject() != null){
            menu();
        }else{
            System.out.println("Id incorrecto,por favor, introduce un id correcto\n");
            principal();
        }


    }// fin de main


    private void menu() throws IOException {
        int opcion;
        boolean continuar = true;

        System.out.println("Hola buenos días puedes realizar siguiente ");
        while (continuar) {
            System.out.println("1.Cobrar compra");
            System.out.println("2.Obtener la caja del día");
            System.out.println("3.Salir");
            System.out.println("Elige una de las opciones");
            opcion = validacionNumero();

            switch (opcion) {
                case 1:
                    cobrarCompra();
                    continuar = false;
                    break;
                case 2:
                    obtenerCajaDia();
                    continuar = false;
                    break;
                case 3:
                    continuar = false;
                    break;
                default:
                    System.out.println("Solo puedes introducir 1 , 2 y 3");
                    continuar = true;
            }
        }

    }

    private void obtenerCajaDia() {
    }

    private void cobrarCompra() throws IOException {
        boolean continuar = true;

        System.out.println("ARTÍCULOS DE LOS BUENOS:");
        while (continuar) {
            System.out.println("1.Disco duro");
            System.out.println("2.USB");
            System.out.println("3.Monitor");
            System.out.println("4.Ratón");
            System.out.println("Seleccione el artículo que desea:");

            opcionProducto = validacionNumero();

            switch (opcionProducto) {
                case 1:
                    comprarDisco();
                    continuar = false;
                    break;
                case 2:
                    comprarUSB();
                    continuar = false;
                    break;
                case 3:
                    comprarMonitor();
                    continuar = false;
                    break;
                case 4:
                    comprarRaton();
                    continuar = false;
                    break;
                default:
                    System.out.println("Solo puedes introducir 1 , 2 , 3 y 4");
                    continuar = true;
            }
        }
    }

    private void comprarRaton() throws IOException {
        System.out.println("¿Cuántas unidades?");
        cantidad = validacionNumero();

        // CREO FLUJO DE SALIDA AL SERVIDOR
        DataOutputStream flujoSalida = new DataOutputStream(salida);

        // ENVIO el login al servidor
        flujoSalida.writeUTF("Cobro;"+opcionProducto+";"+cantidad);

        // CREO FLUJO DE ENTRADA AL SERVIDOR
        ObjectInputStream flujoEntrada = new ObjectInputStream(entrada);

    }

    private void comprarMonitor() {
        System.out.println("¿Cuántas unidades?");
        cantidad = validacionNumero();
    }

    private void comprarUSB() {
        System.out.println("¿Cuántas unidades?");
        cantidad = validacionNumero();
    }

    private void comprarDisco() {
        System.out.println("¿Cuántas unidades?");
        cantidad = validacionNumero();
    }

    /**
     * Método que valida los números introducido por el usuario sea número y no string.
     * @return
     */
    private static int validacionNumero() {
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

}
