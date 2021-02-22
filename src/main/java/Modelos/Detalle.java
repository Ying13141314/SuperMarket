package Modelos;

import java.io.Serializable;

public class Detalle implements Serializable {
    private int idDetalle;
    private Integer cantidadVenta;
    private Integer idProducto;
    private Integer idCompra;
    private Producto productoByIdProducto;
    private Compra compraByIdCompra;

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getCantidadVenta() {
        return cantidadVenta;
    }

    public void setCantidadVenta(Integer cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Detalle detalle = (Detalle) o;

        if (idDetalle != detalle.idDetalle) return false;
        if (cantidadVenta != null ? !cantidadVenta.equals(detalle.cantidadVenta) : detalle.cantidadVenta != null)
            return false;
        if (idProducto != null ? !idProducto.equals(detalle.idProducto) : detalle.idProducto != null) return false;
        if (idCompra != null ? !idCompra.equals(detalle.idCompra) : detalle.idCompra != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDetalle;
        result = 31 * result + (cantidadVenta != null ? cantidadVenta.hashCode() : 0);
        result = 31 * result + (idProducto != null ? idProducto.hashCode() : 0);
        result = 31 * result + (idCompra != null ? idCompra.hashCode() : 0);
        return result;
    }

    public Producto getProductoByIdProducto() {
        return productoByIdProducto;
    }

    public void setProductoByIdProducto(Producto productoByIdProducto) {
        this.productoByIdProducto = productoByIdProducto;
    }

    public Compra getCompraByIdCompra() {
        return compraByIdCompra;
    }

    public void setCompraByIdCompra(Compra compraByIdCompra) {
        this.compraByIdCompra = compraByIdCompra;
    }
}
