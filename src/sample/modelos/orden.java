package sample.modelos;

public class orden {

    int idOrden;
    int cantidad;
    String fecha;
    double total;
    producto idProducto;
    empleado noEmpleado;
    mesa idMesa;

    public orden(){}

    public orden(int idOrden, int cantidad, String fecha, double total, producto idProducto, empleado noEmpleado, mesa idMesa) {
        this.idOrden = idOrden;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.total = total;
        this.idProducto = idProducto;
        this.noEmpleado = noEmpleado;
        this.idMesa = idMesa;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(producto idProducto) {
        this.idProducto = idProducto;
    }

    public empleado getNoEmpleado() {
        return noEmpleado;
    }

    public void setNoEmpleado(empleado noEmpleado) {
        this.noEmpleado = noEmpleado;
    }

    public mesa getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(mesa idMesa) {
        this.idMesa = idMesa;
    }

}
