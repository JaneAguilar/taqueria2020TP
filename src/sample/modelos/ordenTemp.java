package sample.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DAO.tipoProductoDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ordenTemp {
    int idOrden;
    int cantidad;
    String fecha;
    double total;
    int idProducto2;
    int noEmpleado;
    int idMesa;

    public ordenTemp() {
    }

    public ordenTemp(int idOrden, int cantidad, String fecha, double total, int idProducto2, int noEmpleado, int idMesa) {
        this.idOrden = idOrden;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.total = total;
        this.idProducto2 = idProducto2;
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

    public int getIdProducto2() {
        return idProducto2;
    }

    public void setIdProducto2(int idProducto2) {
        this.idProducto2 = idProducto2;
    }

    public int getNoEmpleado() {
        return noEmpleado;
    }

    public void setNoEmpleado(int noEmpleado) {
        this.noEmpleado = noEmpleado;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }
}
