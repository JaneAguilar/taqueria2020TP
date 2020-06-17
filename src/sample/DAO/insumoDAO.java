package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modelos.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class insumoDAO {

    Connection conn;

    private static ObservableList<insumo> data = FXCollections.observableArrayList();

    public insumoDAO(Connection conn) { this.conn = conn; }

    public static void addTransaction(insumo insumo){
        data.add(insumo);
    }

    public insumo fetchInsumo(int idInsumo) {
        ResultSet rs = null;
        insumo i = null;
        try {
            String query = "SELECT * FROM insumo where idInsumo = " + "'" + idInsumo + "'";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.first()){
                proveedorDAO proveedorDAO = new proveedorDAO(Conexion.getConnection());
                i = new insumo(
                        rs.getInt("idInsumo"),
                        rs.getString("insumo"),
                        rs.getDouble("costo"),
                        proveedorDAO.fetchProveedor(rs.getInt("idProveedor"))
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return i;
    }

    public ObservableList<insumo> findAllInsumos() {
        ObservableList<insumo> insumo = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM insumo";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            insumo i = null;
            while (rs.next()) {
                proveedorDAO proveedorDAO = new proveedorDAO(Conexion.getConnection());
                i = new insumo(
                        rs.getInt("idInsumo"),
                        rs.getString("insumo"),
                        rs.getDouble("costo"),
                        proveedorDAO.fetchProveedor(rs.getInt("idProveedor"))
                );
                insumo.add(i);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return insumo;
    }

    public List<insumo> findAllTipoProducto() {
        List<insumo> infoIns = new ArrayList<insumo>();
        try {
            String query = "SELECT * FROM insumo";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            insumo in = null;
            while(rs.next()) {
                proveedorDAO proveedorDAO = new proveedorDAO(Conexion.getConnection());
                in = new insumo(
                        rs.getInt("idInsumo"),
                        rs.getString("insumo"),
                        rs.getDouble("costo"),
                        proveedorDAO.fetchProveedor(rs.getInt("idProveedor"))
                );
                infoIns.add(in);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return infoIns;
    }

    public Boolean updateInsumo(String insumo, double costo, int idProveedor ,int idInsumo) {
        try {
            String query = "update insumo "
                    + " set insumo = ? "
                    + " , costo = ? "
                    + " , idProveedor = ? "
                    +" where idInsumo = ?";
            System.out.println(query + "updating....");
            PreparedStatement st =  conn.prepareStatement(query);

            st.setString(1, insumo);
            st.setDouble(2, costo);
            st.setInt(3, idProveedor);
            st.setInt(4,idInsumo);
            st.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Boolean deleteInsumo(int idInsumo) {
        try {
            String query = "delete from insumo where idInsumo = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, idInsumo);
            st.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Boolean insertInsumo(String insumo, Double costo, int idProveedor) {
        try {
            String query = "insert into insumo (insumo, costo, idProveedor)"
                    + " values (?,?,?)";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(  1,insumo);
            st.setDouble(2,costo);
            st.setInt(3,idProveedor);
            st.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}
