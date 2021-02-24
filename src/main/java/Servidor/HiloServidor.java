package Servidor;

import Modelos.Compra;
import Modelos.Detalle;
import Modelos.Empleado;
import Modelos.Producto;
import Utils.Mail;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class HiloServidor extends Thread {


    private final Session session;
    private final Socket cliente;
    private final InputStream entrada;
    private final OutputStream salida;

    private Empleado empleado;

    /**
     * Constructor del hilo servidor
     * @param cliente
     * @throws IOException
     */
    public HiloServidor(Socket cliente) throws IOException {
        HibernateUtil.openSession();
        session = HibernateUtil.getSession();
        this.cliente = cliente;
        entrada = cliente.getInputStream(); // CREO FLUJO DE ENTRADA DEL CLIENTE
        salida = cliente.getOutputStream(); // CREO FLUJO DE SALIDA AL CLIENTE
    }

    /**
     * Método principal de la clase donde lee la entrada del cliente y según ésta se ejecuta el método correspondiente.
     * Al ser hilo este método se ejecuta cuando le decimos start en el servidor.
     */
    @Override
    public void run() {
        try {
            while (true) {
                //Creamos un nuevo dataInputStream
                DataInputStream flujoEntrada = new DataInputStream(entrada);

                //Spliteamos la información que nos ha llegado por el flujo de entrada.
                String[] primeraParte = flujoEntrada.readUTF().split(";");

                if (primeraParte[0].contains("Login")) {
                    runLogin(primeraParte);
                } else if (primeraParte[0].contains("Productos")) {
                    runProductos();
                } else if (primeraParte[0].contains("Caja")) {
                    runCaja();
                } else if (primeraParte[0].contains("Cobro")) {
                    runCobro(primeraParte);
                }
            }
        } catch (SocketException e) {
            cerrar();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo que cierra los socket y stream.
     */
    private void cerrar() {
        try {
            salida.close();
            entrada.close();
            cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que realiza el login , que guardamos el resultado en el objeto empleado.
     * Guardará null si el id no ha sido encontrado.
     * si existe el empleado actualizará la fecha de última sessión.
     * @param entrada
     * @throws IOException
     */
    private void runLogin(String[] entrada) throws IOException {
        ObjectOutputStream flujoSalida = new ObjectOutputStream(salida);

        Query<Empleado> query = session.createQuery("select e from Empleado e where e.id = :id", Empleado.class);
        query.setParameter("id", Integer.parseInt(entrada[1]));
        empleado = query.uniqueResult();
        if (empleado != null) {
            empleado.actualizarFechaSession();
            session.getTransaction().begin();
            session.update(empleado);
            session.getTransaction().commit();
        }

        flujoSalida.writeObject(empleado);
    }

    /**
     * Método que enviar la lista de los productos al cliente.
     * @throws IOException
     */
    private void runProductos() throws IOException {
        Query<Producto> query = session.createQuery("select p from Producto p", Producto.class);
        List<Producto> productos = query.list();

        new ObjectOutputStream(salida).writeObject(productos);
    }

    /**
     * A partir de los datos del cliente se genera un registro en compra, otro en detalle y se actualiza la cantidad de producto.
     * Si el producto llega una cantidad negativa , se enviará un email.
     * @param entrada
     * @throws IOException
     */
    private void runCobro(String[] entrada) throws IOException {
        int productoId = Integer.parseInt(entrada[1]);
        int cantidad = Integer.parseInt(entrada[2]);

        // Restamos la cantidad al producto en el bbdd.
        Query<Producto> query = session.createQuery("select p from Producto p where p.id = :id", Producto.class);
        query.setParameter("id",productoId);
        Producto producto = query.uniqueResult();

        producto.restarCantidad(cantidad);

        session.getTransaction().begin();
        session.update(producto);
        session.getTransaction().commit();

        // Generamos la compra
        Compra compra = new Compra(new Timestamp(Calendar.getInstance().getTimeInMillis()), empleado.getIdEmpleado());

        session.getTransaction().begin();
        session.save(compra);
        session.getTransaction().commit();
        session.evict(compra);

        Query<Compra> compraQuery = session.createQuery("select c from Compra c order by c.id desc");
        compraQuery.setMaxResults(1);
        Compra compraNueva = compraQuery.uniqueResult();

        // Generamos los detalles
        Detalle detalle = new Detalle(cantidad,productoId,compraNueva.getIdCompra());

        session.getTransaction().begin();
        session.save(detalle);
        session.getTransaction().commit();
        session.evict(detalle);

        // Enviamos el email si hace falta
        String cuerpo = "El empleado: "+empleado.getLogin() + " a las "+compra.getFecha()
                +" ha vendido toda la existencia de "+ producto.getNombreProducto()
                +" a un precio de proveedor de "+producto.getPrecioProveedor()+" €";
        if (producto.getCantidadStock() <= 0) Mail.enviarEmail("Fin de existencia",cuerpo);

        new DataOutputStream(salida).writeUTF("Compra realizada!!");
    }

    /**
     * Envia la caja del día al cliente , esto es , la diferencia entre el precio del producto y el precio del proveedor
     * multiplicado por la cantidad de productos vendido.
     * @throws IOException
     */
    private void runCaja() throws IOException {

        Query query = session.createQuery("SELECT SUM((P.precioVenta - P.precioProveedor) * D.cantidadVenta) " +
                "AS TOTAL FROM Compra C " +
                "LEFT JOIN Detalle D ON D.idCompra = C.id " +
                "LEFT JOIN Producto P ON  D.idProducto = P.id " +
                "WHERE Date(C.fecha) = current_date() AND C.idEmpleado = :idEmpleado");
        query.setParameter("idEmpleado",empleado.getIdEmpleado());

        List resultado = query.list();

        System.out.println(resultado.get(0));

        if (resultado.get(0) == null) {
            new DataOutputStream(salida).writeUTF("ko");
        } else{
            new DataOutputStream(salida).writeUTF(resultado.get(0).toString());
        }

    }

}
