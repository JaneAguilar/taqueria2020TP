package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modelos.Conexion;
import sample.modelos.insumo;
import sample.modelos.producto;
import sample.modelos.tipoProducto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class tipoProductoDAO {
    Connection conn;

    private static ObservableList<tipoProducto> data = FXCollections.observableArrayList();

    public tipoProductoDAO(Connection conn) { this.conn = conn; }

    public static void addTransaction(tipoProducto tipoProducto){
        data.add(tipoProducto);
    }

    public tipoProducto fetchTipoProducto(int idTipoProducto) {
        ResultSet rs = null;
        tipoProducto tp = null;
        try {
            String query = "SELECT * FROM tipoproducto where idTipoProducto = " + "'" + idTipoProducto + "'";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.first()){
                tp = new tipoProducto(
                        rs.getInt("idTipoProducto"),
                        rs.getString("tipo")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return tp;
    }

    public List<tipoProducto> findAllTipoProducto() {
        List<tipoProducto> infoProdTipoProd = new ArrayList<tipoProducto>();
        try {
            String query = "SELECT * FROM tipoProducto";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            tipoProducto tp = null;
            while(rs.next()) {
                tipoProductoDAO tipoProductoDAO = new tipoProductoDAO(Conexion.getConnection());
                insumoDAO insumoDAO = new insumoDAO(Conexion.getConnection());
                tp = new tipoProducto(
                        rs.getInt("idTipoProducto"),
                        rs.getString("tipo")
                );
                infoProdTipoProd.add(tp);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return infoProdTipoProd;
    }


}
