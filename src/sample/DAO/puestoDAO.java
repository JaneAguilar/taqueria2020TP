package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modelos.Conexion;
import sample.modelos.empleado;
import sample.modelos.puesto;
import sample.modelos.tipoProducto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class puestoDAO {

    Connection conn;

    private static ObservableList<puesto> data = FXCollections.observableArrayList();

    public puestoDAO(Connection conn) {
        this.conn = conn;
    }

    public static void addTransaction(puesto puesto) {
        data.add(puesto);
    }


    public puesto fetchPuesto(int idPuesto) {
        ResultSet rs = null;
        puesto p = null;
        try {
            String query = "SELECT * FROM puesto where idPuesto = " + "'" + idPuesto + "'";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);

            if(rs.first()){

                p = new puesto(
                        rs.getInt("idPuesto"),
                        rs.getString("puesto"),
                        rs.getString("descripcion"),
                        rs.getDouble("sueldo")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return p;
    }

    public List<puesto> findAllPuesto() {
        List<puesto> infoPuesto = new ArrayList<puesto>();
        try {
            String query = "SELECT * FROM puesto";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            puesto ps = null;
            while(rs.next()) {
                tipoProductoDAO tipoProductoDAO = new tipoProductoDAO(Conexion.getConnection());
                insumoDAO insumoDAO = new insumoDAO(Conexion.getConnection());
                ps = new puesto(
                        rs.getInt("idPuesto"),
                        rs.getString("puesto"),
                        rs.getString("descripcion"),
                        rs.getDouble("sueldo")
                );
                infoPuesto.add(ps);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return infoPuesto;
    }
}
