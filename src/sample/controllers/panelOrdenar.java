package sample.controllers;

import com.sun.deploy.association.Action;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DAO.empleadoDAO;
import sample.DAO.ordenDAO;
import sample.DAO.productoDAO;
import sample.DAO.tipoProductoDAO;
import sample.modelos.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class panelOrdenar implements Initializable {
    @FXML Button btnAgregar, btnEliminar,btnCancelar,btnCerrar,btnBuscar;
    @FXML Label lblNombre,lblMesa,lblFecha;
    @FXML TextField txtcantidad;
    @FXML ComboBox cmbTipoProducto;
    @FXML TableView<producto> tblProducto;
    @FXML TableColumn idProducto, producto, descripcion, precio, costo, idTipoProducto;
    @FXML TableColumn idOrden, cantidad, fecha, total, idProducto2, noEmpleado, idMesa;
    @FXML TableView<ordenTemp> tblOrden;
    String user ="";
    int mesa = 0;
    double totalOrden;
    sample.DAO.productoDAO productoDAO = new productoDAO(Conexion.getConnection());
    producto producto1 = new producto();
    sample.DAO.tipoProductoDAO tipoProductoDAO = new tipoProductoDAO(Conexion.getConnection());
    empleadoDAO empleadoDAO = new empleadoDAO(Conexion.getConnection());
    ordenDAO ordenDAO = new ordenDAO(Conexion.getConnection());
    List<tipoProducto> infoTipoProd = new ArrayList<>();
    public static final String ticket = "results/ticket/ticket.pdf";
    List<String> infoTemp = new ArrayList<>();
    List<Integer> infoCantidad = new ArrayList<>();

    String fecha_, prod;
    int id_Orden, cant ,id_Producto2, no_Empleado, id_Mesa;
    double total_;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblMesa.setText(String.valueOf(mesa));
        lblNombre.setText("Mesero: " + user);
        lblFecha.setText("Fecha: "+ fechaActual());
        infoTipoProd = tipoProductoDAO.findAllTipoProducto();
        for (int i = 0; i < infoTipoProd.size(); i++) {
            cmbTipoProducto.getItems().add(infoTipoProd.get(i).getTipo());
        }

        //Comprobamos el numero de orden
        if(ordenDAO.verificarOrden()!=0){
            id_Orden = ordenDAO.verificarOrden();
            id_Orden++;
        }else{
            id_Orden++;
        }

        btnBuscar.setOnAction(event -> {
            initTableProducto();
        });

        btnAgregar.setOnAction(event -> {
            initTableOrden();
        });

        btnEliminar.setOnAction(event -> {
            ObservableList<ordenTemp> ordenes, Orden;
            ordenes = tblOrden.getItems();
            Orden = tblOrden.getSelectionModel().getSelectedItems();
            Orden.forEach(ordenes::remove);
        });

        btnCancelar.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cancelando Orden");
            alert.setHeaderText("Orden: "+id_Orden);
            alert.setContentText("CANCELANDO ORDEN");
            alert.showAndWait();
            cleanOrden();
            paneMesas(event);
        });

        btnCerrar.setOnAction(event -> {
            cerrarOrden(event);
        });

        tblProducto.setOnMouseClicked(event -> {
            try{

                producto1 = tblProducto.getSelectionModel().getSelectedItem();
                infoTemp.add(producto1.getProducto());
                fecha_ = fechaActual();
                cant = obtCantidad();
                infoCantidad.add(cant);
                id_Producto2 = producto1.getIdProducto();
                no_Empleado = empleadoDAO.noEmpleado(user);
                id_Mesa = mesa;
                prod = producto1.getProducto();
                total_ = calMonto(producto1, cant);

            }catch (NullPointerException n){
                Alert alert5 = new Alert(Alert.AlertType.INFORMATION);
                alert5.setTitle("Error");
                alert5.setContentText("Selecciona algo");
                alert5.show();
            }
        });

    }


    public void cleanOrden(){
        mesa = 0;
        fecha_ = "";
        cant = 0;
        id_Producto2 = 0;
        no_Empleado = 0;
        total_ = 0;
    }

    public void initTableProducto(){
        String prod = "";
        int tipo = 0;
        prod = cmbTipoProducto.getSelectionModel().getSelectedItem().toString();
        tipo = tipoProd(prod);
        idProducto.setCellValueFactory(new PropertyValueFactory<producto, Integer>("idProducto"));
        producto.setCellValueFactory(new PropertyValueFactory<producto, String>("producto"));
        descripcion.setCellValueFactory(new PropertyValueFactory<producto, String>("descripcion"));
        precio.setCellValueFactory(new PropertyValueFactory<producto, Double>("precio"));
        costo.setCellValueFactory(new PropertyValueFactory<producto, Double>("costo"));
        idTipoProducto.setCellValueFactory(new PropertyValueFactory<producto, Double>("idTipoProducto"));
        tblProducto.setItems(productoDAO.findAllProducto(tipo));

    }

    public void initTableOrden(){
        idOrden.setCellValueFactory(new PropertyValueFactory<>("idOrden"));
        cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        idProducto2.setCellValueFactory(new PropertyValueFactory<>("idProducto2"));
        noEmpleado.setCellValueFactory(new PropertyValueFactory<>("noEmpleado"));
        idMesa.setCellValueFactory(new PropertyValueFactory<>("idMesa"));
        ordenTemp temp = new ordenTemp();
        temp.setIdOrden(id_Orden);
        temp.setCantidad(cant);
        temp.setFecha(fechaActual());
        temp.setTotal(total_);
        temp.setIdProducto2(id_Producto2);
        temp.setNoEmpleado(no_Empleado);
        temp.setIdMesa(mesa);
        tblOrden.getItems().add(temp);
    }

    public int tipoProd(String prod){
        int id=0;
        switch (prod){
            case "tacos":
                id=1;
                break;
            case "tortas":
                id=2;
                break;
            case "Quesadillas":
                id=3;
                break;
            case "Ensaladas":
                id=4;
                break;
            case "Bebidas":
                id=5;
                break;
            case "paquetes":
                id=6;
                break;
        }
        return id;
    }

    public double calMonto(producto prd, int cant){
        totalOrden=(cant*prd.getPrecio());
        return totalOrden;
    }

    public int obtCantidad(){
        int cantidad=0;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cantidad");
        dialog.setHeaderText("Cantidad A la Orden");
        dialog.setContentText("Cantidad:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            cantidad = Integer.parseInt(result.get());
        }

        return cantidad;
    }

    public void obtDato(String var){
        user = var;
    }

    public void obtNum(int var){
        mesa = var;
    }

    public String fechaActual(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
        String fecha = format.format(date);
        return fecha;
    }

    public void paneMesas(ActionEvent event){
        try {
            Stage homeStage=new Stage();
            homeStage.setTitle("PanelMesas");
            Parent root= null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/paneles/panelMesas.fxml"));
            panelMesas panelMesas = new panelMesas();
            panelMesas.obtDato(user);
            loader.setController(panelMesas);
            root=loader.load();
            Scene scene=new Scene(root);
            homeStage.setScene(scene);
            homeStage.setMaximized(false);
            homeStage.show();
            ((Stage)(((Button) event.getSource()).getScene().getWindow())).hide();
        }catch (IOException e ){
            e.printStackTrace();
        }
    }

    public void cerrarOrden(ActionEvent event){
        try{
            double pago=0;
            double apagar =0;
            double cambio=0;
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Caja");
            dialog.setHeaderText("Cantidad A Pagar: "+totalOrden());
            apagar = totalOrden();
            dialog.setContentText("Ingrese Cantidad:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                pago = Double.parseDouble(result.get());
            }

            if (pago< apagar){
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Pago Rechazado: ");
                alert2.setContentText("Lo siento, no le alcanza");
                alert2.show();
            }else if (pago>apagar){
                pago = pago - total_;
                cambio = pago;
                ObservableList<ordenTemp> allOrden;
                allOrden = tblOrden.getItems();
                for (int i = 0; i < allOrden.size(); i++) {
                    if (allOrden!=null){
                        ordenDAO.regristrarVenta(allOrden.get(i).getIdOrden(),
                                allOrden.get(i).getCantidad(),fechaActual(),
                                allOrden.get(i).getTotal(),allOrden.get(i).getIdProducto2(),
                                allOrden.get(i).getNoEmpleado(), allOrden.get(i).getIdMesa());
                    }
                }
                generarTicket();
                cleanOrden();
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setTitle("Gracias Por la Visita: ");
                alert3.setContentText("Su cambio: "+cambio);
                alert3.show();
                paneMesas(event);
            }else if (pago==apagar){
                ObservableList<ordenTemp> allOrden;
                allOrden = tblOrden.getItems();
                for (int i = 0; i < allOrden.size(); i++) {
                    if (allOrden!=null){
                        ordenDAO.regristrarVenta(allOrden.get(i).getIdOrden(),
                                allOrden.get(i).getCantidad(),fechaActual(),
                                allOrden.get(i).getTotal(),allOrden.get(i).getIdProducto2(),
                                allOrden.get(i).getNoEmpleado(), allOrden.get(i).getIdMesa());
                    }
                }
                generarTicket();
                cleanOrden();
                Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
                alert4.setTitle("Gracias Por la Visita: ");
                alert4.show();
                paneMesas(event);
            }
        }catch (NumberFormatException e){

        }
    }

    public double totalOrden(){
        double monto=0;
        ObservableList<ordenTemp> allOrden;
        allOrden = tblOrden.getItems();
        for (int i = 0; i < allOrden.size(); i++) {
            if (allOrden!=null){
                monto = monto + allOrden.get(i).getTotal();
            }
        }
        return monto;
    }

    public void generarTicket(){
        try{
            File file = new File(ticket);
            file.getParentFile().mkdirs();
            new Ticket().createPdf(ticket, mesa, fechaActual(), user, id_Orden, totalOrden(), infoTemp, infoCantidad);
            Desktop.getDesktop().open(file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}