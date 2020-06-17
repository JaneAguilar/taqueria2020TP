package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modelos.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class proveedorDAO {
    Connection conn;

    private static ObservableList<proveedor> data = FXCollections.observableArrayList();

    public proveedorDAO(Connection conn) { this.conn = conn; }

    public static void addTransaction(proveedor proveedor){
        data.add(proveedor);
    }

    public proveedor fetchProveedor(int idProveedor) {
        ResultSet rs = null;
        proveedor pr = null;
        try {
            String query = "SELECT * FROM proveedor where idProveedor = " + "'" + idProveedor + "'";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.first()){
                pr = new proveedor(
                        rs.getInt("idProveedor"),
                        rs.getString("nombreP"),
                        rs.getString("direccion"),
                        rs.getString("telefono")
                        );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return pr;
    }

    public List<proveedor> findAllTipoProveedor() {
        List<proveedor> infoProveedor = new ArrayList<proveedor>();
        try {
            String query = "SELECT * FROM proveedor";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            proveedor prv = null;
            while(rs.next()) {
                tipoProductoDAO tipoProductoDAO = new tipoProductoDAO(Conexion.getConnection());
                insumoDAO insumoDAO = new insumoDAO(Conexion.getConnection());
                prv = new proveedor(
                        rs.getInt("idProveedor"),
                        rs.getString("nombreP"),
                        rs.getString("direccion"),
                        rs.getString("telefono")
                );
                infoProveedor.add(prv);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return infoProveedor;
    }

    public ObservableList<proveedor> findAllProveedor() {
        ObservableList<proveedor> proveedor = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM proveedor";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            proveedor pr = null;
            while (rs.next()) {
                pr = new proveedor(
                        rs.getInt("idProveedor"),
                        rs.getString("nombreP"),
                        rs.getString("direccion"),
                        rs.getString("telefono")
                );
                proveedor.add(pr);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return proveedor;
    }

    public Boolean updateProveedor(String nombreP, String direccion, String telefono, int idProveedor) {
        try {
            String query = "update proveedor "
                    + " set nombreP = ? "
                    + " , direccion = ? "
                    + " , telefono = ? "
                    +" where idProveedor = ?";
            System.out.println(query + "updating....");
            PreparedStatement st =  conn.prepareStatement(query);

            st.setString(1, nombreP);
            st.setString(2, direccion);
            st.setString(3, telefono);
            st.setInt(4, idProveedor);
            st.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Boolean deleteProveedor(int idProveedor) {
        try {
            String query = "delete from proveedor where idProveedor = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, idProveedor);
            st.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Boolean insertProveedor(String nombreP, String direccion, String telefono) {
        try {
            String query = "insert into proveedor (nombreP, direccion, telefono)"
                    + " values (?,?,?)";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(  1,nombreP);
            st.setString(2,direccion);
            st.setString(3,telefono);
            st.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}
