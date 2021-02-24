package Servidor;

import Utils.Mail;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    /**
     * Punto de entrada del servidor multihilo, por cada cliente que se conecta se genera un hilo que es capaz de gestionarlo.
     * aqui también se inicia la configuración del email y de hibernate.
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {

        ServerSocket servidor = new ServerSocket(6000);
        System.out.println("Servidor iniciado...");
        HibernateUtil.buildSessionFactory();
        Mail.configurar();

        while (true) {
            Socket cliente = servidor.accept();//esperando cliente
            HiloServidor hilo = new HiloServidor(cliente);
            hilo.start(); //Se atiende al cliente

        }// Fin de while

    }// Fin de main
}
