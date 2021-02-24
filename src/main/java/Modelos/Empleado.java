package Modelos;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;

public class Empleado implements Serializable {
    private int idEmpleado;
    private Timestamp ultimaSesion;
    private Date fechaContratacion;
    private String login;
    private Collection<Compra> comprasByIdEmpleado;

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Timestamp getUltimaSesion() {
        return ultimaSesion;
    }

    public void setUltimaSesion(Timestamp ultimaSesion) {
        this.ultimaSesion = ultimaSesion;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Empleado empleado = (Empleado) o;

        if (idEmpleado != empleado.idEmpleado) return false;
        if (ultimaSesion != null ? !ultimaSesion.equals(empleado.ultimaSesion) : empleado.ultimaSesion != null)
            return false;
        if (fechaContratacion != null ? !fechaContratacion.equals(empleado.fechaContratacion) : empleado.fechaContratacion != null)
            return false;
        if (login != null ? !login.equals(empleado.login) : empleado.login != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEmpleado;
        result = 31 * result + (ultimaSesion != null ? ultimaSesion.hashCode() : 0);
        result = 31 * result + (fechaContratacion != null ? fechaContratacion.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        return result;
    }

    public Collection<Compra> getComprasByIdEmpleado() {
        return comprasByIdEmpleado;
    }

    public void setComprasByIdEmpleado(Collection<Compra> comprasByIdEmpleado) {
        this.comprasByIdEmpleado = comprasByIdEmpleado;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "idEmpleado=" + idEmpleado +
                ", ultimaSesion=" + ultimaSesion +
                ", fechaContratacion=" + fechaContratacion +
                ", login='" + login + '\'' +
                '}';
    }

    public void actualizarFechaSession() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        calendar.add(Calendar.HOUR, 1);

        this.ultimaSesion = new Timestamp(calendar.getTimeInMillis());
    }
}
