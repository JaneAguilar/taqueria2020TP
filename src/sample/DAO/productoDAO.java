package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import sample.modelos.DBConnect;
import sample.modelos.empleado;
import sample.modelos.producto;
import sample.modelos.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class productoDAO {

    Connection conn;

    private static ObservableList<producto> data = FXCollections.observableArrayList();
    private static ObservableList data1 = FXCollections.observableArrayList();

    public productoDAO(Connection conn) { this.conn = conn; }

    public static void addTransaction(producto producto){
        data.add(producto);
    }

    public producto fetchProducto(int idProducto) {
        ResultSet rs = null;
        producto pr = null;
        try {
            String query = "SELECT * FROM producto where idProducto = " + "'" + idProducto + "'";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.first()){
                tipoProductoDAO tipoProductoDAO = new tipoProductoDAO(Conexion.getConnection());
                pr = new producto(
                        rs.getInt("idProducto"),
                        rs.getString("producto"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getDouble("costo"),
                        tipoProductoDAO.fetchTipoProducto(rs.getInt("idTipoProducto"))
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return pr;
    }

    public List<producto> findAll() {
        List<producto> infoProd = new ArrayList<producto>();
        try {
            String query = "SELECT * FROM producto";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            producto p = null;
            while(rs.next()) {
                tipoProductoDAO tipoProductoDAO = new tipoProductoDAO(Conexion.getConnection());
                insumoDAO insumoDAO = new insumoDAO(Conexion.getConnection());
                p = new producto(
                      rs.getInt("idProducto"),
                      rs.getString("producto"),
                      rs.getString("descripcion"),
                      rs.getDouble("precio"),
                      rs.getDouble("costo"),
                      tipoProductoDAO.fetchTipoProducto(rs.getInt("idTipoProducto"))
                );
                infoProd.add(p);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return infoProd;
    }

    public ObservableList<producto> findAllProducto(int tipoProd) {
        ObservableList<producto> productos = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM producto where idTipoProducto = " + "'" + tipoProd + "'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            producto p = null;
            while (rs.next()) {
                tipoProductoDAO tipoProductoDAO = new tipoProductoDAO(Conexion.getConnection());
                p = new producto(
                        rs.getInt("idProducto"),
                        rs.getString("producto"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getDouble("costo"),
                        tipoProductoDAO.fetchTipoProducto(rs.getInt("idTipoProducto"))
                );
                productos.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return productos;
    }

    public ObservableList<producto> findAllProductos() {
        ObservableList<producto> productos = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM producto";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            producto p = null;
            while (rs.next()) {
                tipoProductoDAO tipoProductoDAO = new tipoProductoDAO(Conexion.getConnection());
                p = new producto(
                        rs.getInt("idProducto"),
                        rs.getString("producto"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getDouble("costo"),
                        tipoProductoDAO.fetchTipoProducto(rs.getInt("idTipoProducto"))
                );
                productos.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return productos;
    }

    public Boolean updateProd(double precio, double costo,int idProdcuto) {
        try {
            String query = "update producto "
                          + " set precio = ? "
                          + " , costo = ? "
                          +" where idProducto = ?";
            System.out.println(query + "updating....");
            PreparedStatement st =  conn.prepareStatement(query);

            st.setDouble(1, precio);
            st.setDouble(2, costo);
            st.setInt(3, idProdcuto);
            st.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Boolean deleteProd(int idProducto) {
        try {
            String query = "delete from producto where idProducto = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, idProducto);
            st.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Boolean insertProductos(String producto, String descripcion, Double precio, Double costo, int idTipoProducto) {
        try {
            String query = "insert into producto (producto, descripcion, precio, costo, idTipoProducto)"
                    + " values (?,?,?,?,?)";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(  1,producto);
            st.setString(2,descripcion);
            st.setDouble(3,precio);
            st.setDouble(4,costo);
            st.setInt(5,idTipoProducto);
            st.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }


    public ObservableList buildData(){
        Connection c ;
        data1 = FXCollections.observableArrayList();
        try{
            c = DBConnect.connect();
            String SQL = "select extract(year from o.fecha), "
                    + "sum((precio - costo) * cantidad) from producto, orden o"
                    + " group by 1";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                //adding data on piechart data
                data1.add(new PieChart.Data(rs.getString(1),rs.getDouble(2)));
            }
        }catch(Exception e){
            System.out.println("Error on DB connection");
        }
        return data1;
    }



}
