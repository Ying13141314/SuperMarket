package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String args[]) throws IOException {

        ServerSocket servidor = new ServerSocket(6000);
        System.out.println("Servidor iniciado...");
        HibernateUtil.buildSessionFactory();

        while (true) {
            Socket cliente = servidor.accept();//esperando cliente
            HiloServidor hilo = new HiloServidor(cliente);
            hilo.start(); //Se atiende al cliente

        }// Fin de while

    }// Fin de main
}
