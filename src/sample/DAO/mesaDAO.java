package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modelos.mesa;
import sample.modelos.orden;
import sample.modelos.tipoProducto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mesaDAO {
    Connection conn;

    private static ObservableList<mesa> data = FXCollections.observableArrayList();

    public mesaDAO(Connection conn) {
        this.conn = conn;
    }

    public static void addTransaction(mesa mesa) {
        data.add(mesa);
    }

    public mesa fetchMesa(int idMesa) {
        ResultSet rs = null;
        mesa m = null;
        try {
            String query = "SELECT * FROM mesa where idMesa = " + "'" + idMesa + "'";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.first()){
                m = new mesa(
                        rs.getInt("idMesa"),
                        rs.getString("descripcion")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return m;
    }
}
