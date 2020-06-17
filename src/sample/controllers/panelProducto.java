package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DAO.productoDAO;
import sample.DAO.tipoProductoDAO;
import sample.modelos.Conexion;
import sample.modelos.producto;
import sample.modelos.tipoProducto;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class panelProducto implements Initializable {

    @FXML
    TableView<sample.modelos.producto> tblProd;
    @FXML
    TableColumn idProducto, producto, descripcion, precio, costo, idTipoProducto;
    @FXML
    Button btnAgregar, btnActualizar, btnEliminar, btnRegresar;
    @FXML
    TextField txtNombre, txtPrecio, txtCosto, txtDescripcion;
    @FXML ComboBox cmbTipo;
    @FXML
    Label lblMOD;
    int modificar;
    sample.DAO.productoDAO productoDAO = new productoDAO(Conexion.getConnection());
    tipoProductoDAO tipoProductoDAO =  new tipoProductoDAO(Conexion.getConnection());
    List<tipoProducto> infoTipoProd = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initTableProducto();
        lblMOD.setVisible(false);

        infoTipoProd = tipoProductoDAO.findAllTipoProducto();
        for (int i = 0; i < infoTipoProd.size(); i++) {
            cmbTipo.getItems().add(infoTipoProd.get(i).getTipo());
        }

        btnRegresar.setOnAction(event -> {
            paneGerente(event);
        });

        tblProd.setOnMouseClicked(event -> {
            try{

                producto p = tblProd.getSelectionModel().getSelectedItem();
                txtNombre.setText(String.valueOf(p.getProducto()));
                txtPrecio.setText(String.valueOf(p.getPrecio()));
                txtDescripcion.setText(p.getDescripcion());
                txtCosto.setText(String.valueOf(p.getCosto()));
                modificar = p.getIdProducto();
                lblMOD.setVisible(true);

            }catch (NullPointerException n){
                Alert alert5 = new Alert(Alert.AlertType.INFORMATION);
                alert5.setTitle("Error");
                alert5.setContentText("Selecciona algo");
                alert5.show();
            }
        });

        btnAgregar.setOnAction(event -> {
            addProd();
        });

        btnEliminar.setOnAction(event -> {
            eraseProd();
        });

        btnActualizar.setOnAction(event -> {
            updateProd();
        });


    }


    public void initTableProducto(){
        idProducto.setCellValueFactory(new PropertyValueFactory<producto, Integer>("idProducto"));
        producto.setCellValueFactory(new PropertyValueFactory<producto, String>("producto"));
        descripcion.setCellValueFactory(new PropertyValueFactory<producto, String>("descripcion"));
        precio.setCellValueFactory(new PropertyValueFactory<producto, Double>("precio"));
        costo.setCellValueFactory(new PropertyValueFactory<producto, Double>("costo"));
        idTipoProducto.setCellValueFactory(new PropertyValueFactory<producto, Double>("idTipoProducto"));
        tblProd.setItems(productoDAO.findAllProductos());

    }

    private void updateProd(){
        if (productoDAO.updateProd(Double.parseDouble(txtPrecio.getText()),Double.parseDouble(txtCosto.getText()),modificar)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modificar Producto");
            alert.setContentText("Producto modificado");
            alert.show();
            tblProd.setItems(productoDAO.findAllProductos());
        }
        modificar=0;
        txtNombre.setText("");
        txtPrecio.setText("");
        txtDescripcion.setText("");
        txtCosto.setText("");
        lblMOD.setVisible(false);

    }

    private void eraseProd() {
        if (tblProd.getSelectionModel().getSelectedIndex() >= 0){
            producto p = tblProd.getSelectionModel().getSelectedItem();
            if(productoDAO.deleteProd(p.getIdProducto())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Borrando Producto");
                alert.setContentText("Producto Eliminado");
                alert.show();
            }
        }
        lblMOD.setVisible(false);
        txtNombre.setText("");
        txtPrecio.setText("");
        txtDescripcion.setText("");
        txtCosto.setText("");
        tblProd.setItems(productoDAO.findAllProductos());
    }

    private void addProd() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nuevo Producto");
        alert.setContentText("¿Deseas agregar este nuevo producto?");
        ButtonType buttonTypeYES = new ButtonType("YES");
        ButtonType buttonTypeNO = new ButtonType("NO");
        alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
        Optional<ButtonType> response2 = alert.showAndWait();
        if (response2.get() == buttonTypeYES) {
            String prod = "";
            int tipo = 0;
            prod = cmbTipo.getSelectionModel().getSelectedItem().toString();
            tipo = tipoProd(prod);
            if(productoDAO.insertProductos(txtNombre.getText(),txtDescripcion.getText(),
                   Double.valueOf( txtPrecio.getText()),Double.valueOf(txtCosto.getText()), tipo)){
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("¡¡¡¡ ACTUALIZACION !!!!");
                alert2.setContentText("Se ha agregado con exito el nuevo producto");
                alert2.show();
            }
        }
        txtNombre.setText("");
        txtPrecio.setText("");
        txtDescripcion.setText("");
        txtCosto.setText("");
        tblProd.setItems(productoDAO.findAllProductos());
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

    public void paneGerente(ActionEvent event){
        try {
            Stage homeStage=new Stage();
            homeStage.setTitle("Panel Gerente");
            Parent root= null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/paneles/panelGerente.fxml"));
            PanelGerente panelGerente = new PanelGerente();
            loader.setController(panelGerente);
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
}
