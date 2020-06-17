package sample.modelos;

public class producto {
    int idProducto;
    String producto;
    String descripcion;
    double precio;
    double costo;
    tipoProducto idTipoProducto;

    public producto(){}

    public producto(int idProducto, String producto, String descripcion, double precio, double costo, tipoProducto idTipoProducto) {
        this.idProducto = idProducto;
        this.producto = producto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.costo = costo;
        this.idTipoProducto = idTipoProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public tipoProducto getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(tipoProducto idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    @Override
    public String toString() {
        return producto;
    }
}

