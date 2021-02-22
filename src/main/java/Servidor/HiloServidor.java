package Servidor;

import Modelos.Compra;
import Modelos.Empleado;
import Modelos.Producto;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Date;


import java.io.*;
import java.net.Socket;
import java.util.List;

public class HiloServidor extends Thread {

    private Socket cliente;

    private Session session;

    private InputStream entrada;

    private OutputStream salida;

    private String[] primeraParte;

    private int cantidadProductoQuedado;

    public HiloServidor(Socket cliente) throws IOException {
        this.cliente = cliente;
        HibernateUtil.openSession();
        session = HibernateUtil.getSession();
        entrada = cliente.getInputStream(); // CREO FLUJO DE ENTRADA DEL CLIENTE
        salida = cliente.getOutputStream(); // CREO FLUJO DE SALIDA AL CLIENTE
    }

    @Override
    public void run() {
        try {
            while (true) {
                DataInputStream flujoEntrada = new DataInputStream(entrada);
                primeraParte = flujoEntrada.readUTF().split(";");
                if (primeraParte[0].contains("Login")) {
                    runLogin();
                } else if (primeraParte[0].contains("Cobro")) {

                    Producto miProducto = cantidadStock(Integer.parseInt(primeraParte[1]));
                    cantidadProductoQuedado = miProducto.getCantidadStock()-Integer.parseInt(primeraParte[2]);
                    System.out.println(cantidadProductoQuedado);
                    runCobro();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void runLogin() throws IOException {

        ObjectOutputStream flujoSalida = new ObjectOutputStream(salida);

        // ENVIO UN SALUDO AL CLIENTE
        flujoSalida.writeObject(login(Integer.parseInt(primeraParte[1])));


    }

    private void runCobro() throws IOException {


        ObjectOutputStream flujoSalida = new ObjectOutputStream(salida);

        // ENVIO UN SALUDO AL CLIENTE
        flujoSalida.writeObject(cantidad(Integer.parseInt(primeraParte[1]), cantidadProductoQuedado));

    }

    private Empleado login(int id) {
        Query<Empleado> query =
                session.createQuery("select e from Empleado e where e.id = :id", Empleado.class);
        query.setParameter("id", id);

        return query.uniqueResult();
    }

    private Compra compra(Date fecha, int idEmpleado) {
        Compra miCompra = new Compra(fecha, idEmpleado);
        return miCompra;
    }

    private void fechaSession() {
    }

    private int cantidad(int id, int cantidad) {
        Query<Producto> query =
                session.createQuery("update Producto set cantidadStock= :cantidad where id= :id");
        query.setParameter("id", id);
        query.setParameter("cantidad", cantidad);

        return query.executeUpdate();
    }

    private Producto cantidadStock(int id) {
        Query<Producto> query =
                session.createQuery("select p from Producto p where p.id= :id",Producto.class);
        query.setParameter("id", id);
        return query.uniqueResult();
    }
}
