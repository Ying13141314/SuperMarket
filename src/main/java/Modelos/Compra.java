package Modelos;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;

public class Compra implements Serializable {
    private int idCompra;
    private Date fecha;
    private Integer idEmpleado;
    private Empleado empleadoByIdEmpleado;
    private Collection<Detalle> detallesByIdCompra;

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Compra() {
    }

    public Compra(Date fecha, Integer idEmpleado) {
        this.fecha = fecha;
        this.idEmpleado = idEmpleado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Compra compra = (Compra) o;

        if (idCompra != compra.idCompra) return false;
        if (fecha != null ? !fecha.equals(compra.fecha) : compra.fecha != null) return false;
        if (idEmpleado != null ? !idEmpleado.equals(compra.idEmpleado) : compra.idEmpleado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCompra;
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (idEmpleado != null ? idEmpleado.hashCode() : 0);
        return result;
    }

    public Empleado getEmpleadoByIdEmpleado() {
        return empleadoByIdEmpleado;
    }

    public void setEmpleadoByIdEmpleado(Empleado empleadoByIdEmpleado) {
        this.empleadoByIdEmpleado = empleadoByIdEmpleado;
    }

    public Collection<Detalle> getDetallesByIdCompra() {
        return detallesByIdCompra;
    }

    public void setDetallesByIdCompra(Collection<Detalle> detallesByIdCompra) {
        this.detallesByIdCompra = detallesByIdCompra;
    }
}
