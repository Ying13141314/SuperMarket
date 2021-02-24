package Caja;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import Modelos.Producto;
import Utils.Utils;

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

    /**
     * Varia inputStream en el estado porque lo necesitamos en varios partes del programa.
     */
    private final InputStream entrada;

    /**
     * Varia OutputStream en el estado porque lo necesitamos en varios partes del programa.
     */
    private final OutputStream salida;

    /**
     * Constructor de la clase Cliente.
     * @throws IOException
     */
    public Cliente() throws IOException {

        //Iniciamos
        miScanner = new Scanner(System.in);
        cliente= new Socket(Host, Puerto);
        entrada = cliente.getInputStream(); // CREO FLUJO DE ENTRADA DEL CLIENTE
        salida = cliente.getOutputStream(); // CREO FLUJO DE SALIDA AL CLIENTE
    }


    /**
     * Método principal que realiza el login del empleado
     * @throws Exception
     */
    public void principal() throws Exception {

        System.out.println("PROGRAMA CLIENTE INICIADO....");

        // CREO FLUJO DE SALIDA AL SERVIDOR
        DataOutputStream flujoSalida = new DataOutputStream(salida);

        // ENVIO el login al servidor
        System.out.println("¡Hola empleado, loguease por favor!");
        idEmpleado = Utils.validacionNumero(miScanner);
        flujoSalida.writeUTF("Login;"+idEmpleado);

        // CREO FLUJO DE ENTRADA AL SERVIDOR
        ObjectInputStream flujoEntrada = new ObjectInputStream(entrada);

        //Si lo que nos pasa por servidor es nulo significa que ha introducido un id incorrecto,
        // si nos devuelve el objeto empleado me muestra lo siguiente menu.
        if (flujoEntrada.readObject() != null){
            menu();
        }else{
            System.out.println("Id incorrecto,por favor, introduce un id correcto\n");
            principal();
        }


    }// fin de main

    /**
     * Método menu que muestra un menu de las cosas que se puede realizar después de haber logueado.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void menu() throws IOException, ClassNotFoundException {
        int opcion;
        boolean continuar = true;

        System.out.println("Hola buenos días puedes realizar siguiente ");
        while (continuar) {
            System.out.println("1.Cobrar compra");
            System.out.println("2.Obtener la caja del día");
            System.out.println("3.Salir");
            System.out.println("Elige una de las opciones");
            opcion = Utils.validacionNumero(miScanner);

            switch (opcion) {
                case 1 -> {
                    cobrarCompra();
                    continuar = false;
                }
                case 2 -> {
                    obtenerCajaDia();
                    continuar = false;
                }
                case 3 -> continuar = false;
                default -> {
                    System.out.println("Solo puedes introducir 1 , 2 y 3");
                    continuar = true;
                }
            }
        }

    }

    /**
     * Método que obtiene los dineros obtenido de cada dia.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void obtenerCajaDia() throws IOException, ClassNotFoundException {
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println();
        System.out.println("La caja del " + formatter.format(date));

        new DataOutputStream(salida).writeUTF("Caja");

        String mensaje = new DataInputStream(entrada).readUTF();

        //Si lo que nos llegan ko significa que hoy aún no se ha realizado ningún cobro.
        if (mensaje.equals("ko")) {
            System.out.println("No has realizado ningún cobro hoy aún");
        } else {
            System.out.println("Ha sido lo siguiente: " + mensaje + " €.");
        }

        System.out.println();

        menu();
    }

    /**
     * Método que realiza el cobro del producto.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void cobrarCompra() throws IOException, ClassNotFoundException {
        new DataOutputStream(salida).writeUTF("Productos");

        ArrayList<Producto> miProductos = (ArrayList<Producto>) new ObjectInputStream(entrada).readObject();

        //Mostrar el menu de los productos que estoy vendiendo.
        System.out.println("ARTÍCULOS DE LOS BUENOS:");

        for (int i = 0; i < miProductos.size(); i++) {
            Producto miProductito = miProductos.get(i);
            System.out.println((i + 1) + ". " + miProductito.getNombreProducto());
        }

        System.out.println("Seleccione el artículo que desea:");

        int opcionProducto = Utils.validacionNumero(miScanner,4);
        int idProducto = miProductos.get(opcionProducto - 1).getIdProducto();

        System.out.println("¿Cuántas unidades?");

        int cantidad = Utils.validacionNumero(miScanner);

        String mensaje = "Cobro;" + idProducto + ";" + cantidad;

        new DataOutputStream(salida).writeUTF(mensaje);

        String recibir = new DataInputStream(entrada).readUTF();

        System.out.println(recibir);

        menu();
    }

}
