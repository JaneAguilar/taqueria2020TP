package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modelos.Conexion;
import sample.modelos.empleado;
import sample.modelos.orden;
import sample.modelos.producto;

import java.sql.*;

public class ordenDAO {

    Connection conn;

    private static ObservableList<orden> data = FXCollections.observableArrayList();

    public ordenDAO(Connection conn) {
        this.conn = conn;
    }

    public static void addTransaction(orden orden) {
        data.add(orden);
    }

    public Boolean regristrarVenta(int idOrden, int cantidad, String fecha, double total, int idProducto, int noEmpleado, int idMesa) {
        try {
            String query = "insert into orden(idOrden, cantidad, fecha, total, idProducto, noEmpleado, idMesa) " +
                    "values (?,?,?,?,?,?,?);";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setInt(1,idOrden);
            st.setInt(  2,cantidad);
            st.setString(3,fecha);
            st.setDouble(4,total);
            st.setInt(5,idProducto);
            st.setInt(6,noEmpleado);
            st.setInt(7,idMesa);
            st.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public int verificarOrden() {
        ResultSet rs = null;
        int valor = 0;
        try {
            String query = "select idOrden valor from orden order by idOrden desc limit 1";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.first()){
                valor = rs.getInt("valor");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return valor;
    }

    public ObservableList<orden> findAllOrden() {
        ObservableList<orden> ordenes = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM orden";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            orden o = null;
            while (rs.next()) {
                productoDAO productoDAO = new productoDAO(Conexion.getConnection());
                empleadoDAO empleadoDAO = new empleadoDAO(Conexion.getConnection());
                mesaDAO mesaDAO = new mesaDAO(Conexion.getConnection());
                o = new orden(
                        rs.getInt("idOrden"),
                        rs.getInt("cantidad"),
                        rs.getString("fecha"),
                        rs.getDouble("total"),
                        productoDAO.fetchProducto(rs.getInt("idProducto")),
                        empleadoDAO.fetchEmpleado(rs.getInt("noEmpleado")),
                        mesaDAO.fetchMesa(rs.getInt("idMesa"))
                );
                ordenes.add(o);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return ordenes;
    }

    public Boolean updateOrden(int cantidad, int idOrden) {
        try {
            String query = "update orden "
                    + " set cantidad = ? "
                    +" where idOrden = ?";
            System.out.println(query + "updating....");
            PreparedStatement st =  conn.prepareStatement(query);

            st.setInt(1, cantidad);
            st.setInt(2, idOrden);
            st.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Boolean deleteOrden(int idOrden) {
        try {
            String query = "delete from orden where idOrden = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, idOrden);
            st.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


}
