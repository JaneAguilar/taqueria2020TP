package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modelos.Conexion;
import sample.modelos.empleado;
import sample.modelos.insumo;
import sample.modelos.mesa;

import java.sql.*;

public class empleadoDAO {

    Connection conn;

    private static ObservableList<empleado> data = FXCollections.observableArrayList();

    public empleadoDAO(Connection conn) {
        this.conn = conn;
    }

    public static void addTransaction(empleado empleado) {
        data.add(empleado);
    }


    public empleado fetchEmpleado(int noEmpleado) {
        ResultSet rs = null;
        empleado emp = null;
        try {
            String query = "SELECT * FROM empleado where noEmpleado = " + "'" + noEmpleado + "'";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.first()){
                puestoDAO puestoDAO = new puestoDAO(Conexion.getConnection());
                emp = new empleado(
                        rs.getInt("noEmpleado"),
                        rs.getString("nombreE"),
                        rs.getString("apellidoP"),
                        rs.getString("apellidoM"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        puestoDAO.fetchPuesto(rs.getInt("idPuesto"))
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return emp;
    }

    public int tipoUsuario(String usuario, String contrasena){
        ResultSet rs = null;
        int valor = 0;
        try {

            String query = "select idPuesto from empleado where usuario = '" + usuario + "' and contrasena ='" + contrasena + "'";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);

            if(rs.first()){
                valor = rs.getInt("idPuesto");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return valor;
    }

    public int noEmpleado(String usuario){
        ResultSet rs = null;
        int valor = 0;
        try {

            String query = "select noEmpleado from empleado where usuario = '" + usuario + "'";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);

            if(rs.first()){
                valor = rs.getInt("noEmpleado");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return valor;
    }

    public ObservableList<empleado> findAllEmpleado() {
        ObservableList<empleado> empleado = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM empleado";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            empleado emp = null;
            while (rs.next()) {
                puestoDAO puestoDAO = new puestoDAO(Conexion.getConnection());
                emp = new empleado(
                        rs.getInt("noEmpleado"),
                        rs.getString("nombreE"),
                        rs.getString("apellidoP"),
                        rs.getString("apellidoM"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        puestoDAO.fetchPuesto(rs.getInt("idPuesto"))
                );
                empleado.add(emp);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return empleado;
    }

    public Boolean updateEmpleado(String usuario, String contrasena, int noEmpleado) {
        try {
            String query = "update empleado "
                    + " set usuario = ? "
                    + " , contrasena = ? "
                    +" where noEmpleado = ?";
            System.out.println(query + "updating....");
            PreparedStatement st =  conn.prepareStatement(query);

            st.setString(1, usuario);
            st.setString(2, contrasena);
            st.setInt(3, noEmpleado);
            st.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Boolean deleteEmpleado(int noEmpleado) {
        try {
            String query = "delete from empleado where noEmpleado = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, noEmpleado);
            st.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Boolean insertEmpleado(String nombreE, String apellidoP, String apellidoM, String usuario, String contrasena, int idPuesto) {
        try {
            String query = "insert into empleado (nombreE, apellidoP, apellidoM, usuario, contrasena, idPuesto)"
                    + " values (?,?,?,?,?,?)";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(  1,nombreE);
            st.setString(2,apellidoP);
            st.setString(3,apellidoM);
            st.setString(4,usuario);
            st.setString(5,contrasena);
            st.setInt(6, idPuesto);
            st.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

}
