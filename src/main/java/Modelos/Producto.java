package Modelos;

import java.io.Serializable;
import java.util.Collection;

public class Producto implements Serializable {
    private int idProducto;
    private String nombreProducto;
    private Integer precioVenta;
    private Integer precioProveedor;
    private Integer cantidadStock;
    private Collection<Detalle> detallesByIdProducto;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Integer precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getPrecioProveedor() {
        return precioProveedor;
    }

    public void setPrecioProveedor(Integer precioProveedor) {
        this.precioProveedor = precioProveedor;
    }

    public Integer getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(Integer cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public Producto() {
    }

    public Producto(int idProducto, Integer cantidadStock) {
        this.idProducto = idProducto;
        this.cantidadStock = cantidadStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Producto producto = (Producto) o;

        if (idProducto != producto.idProducto) return false;
        if (nombreProducto != null ? !nombreProducto.equals(producto.nombreProducto) : producto.nombreProducto != null)
            return false;
        if (precioVenta != null ? !precioVenta.equals(producto.precioVenta) : producto.precioVenta != null)
            return false;
        if (precioProveedor != null ? !precioProveedor.equals(producto.precioProveedor) : producto.precioProveedor != null)
            return false;
        if (cantidadStock != null ? !cantidadStock.equals(producto.cantidadStock) : producto.cantidadStock != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idProducto;
        result = 31 * result + (nombreProducto != null ? nombreProducto.hashCode() : 0);
        result = 31 * result + (precioVenta != null ? precioVenta.hashCode() : 0);
        result = 31 * result + (precioProveedor != null ? precioProveedor.hashCode() : 0);
        result = 31 * result + (cantidadStock != null ? cantidadStock.hashCode() : 0);
        return result;
    }

    public Collection<Detalle> getDetallesByIdProducto() {
        return detallesByIdProducto;
    }

    public void setDetallesByIdProducto(Collection<Detalle> detallesByIdProducto) {
        this.detallesByIdProducto = detallesByIdProducto;
    }
}
