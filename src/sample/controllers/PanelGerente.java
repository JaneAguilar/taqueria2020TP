package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PanelGerente implements Initializable {


    @FXML Label lblNombre;
    @FXML Button btnProveedores,btnOrdenes,btnInsumos, btnEmpleados,btnProductos;
    @FXML ImageView imgUsuario;
    String user="";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colocar_imgBotones();
        lblNombre.setText("Administrador: "+user);

        btnInsumos.setOnAction(event -> {
           panelInsumos(event);
        });

        btnProductos.setOnAction(event -> {
            panelProducto(event);
        });

        btnProveedores.setOnAction(event -> {
            panelProveedor(event);
        });

        btnEmpleados.setOnAction(event -> {
            panelEmpleados(event);
        });

        btnOrdenes.setOnAction(event -> {
            panelOrdenes(event);
        });

    }

    private void colocar_imgBotones(){

        Image imgInsumo= new Image(getClass().getResourceAsStream("/sample/img/insumos.png"));
        Image imgOrdenes= new Image(getClass().getResourceAsStream("/sample/img/ordenes.png"));
        Image imgProductos=new Image(getClass().getResourceAsStream("/sample/img/productos.png"));
        Image imgProveedores=new Image(getClass().getResourceAsStream("/sample/img/proveedores.png"));
        Image imgEmpleados=new Image(getClass().getResourceAsStream("/sample/img/empleados.png"));
        btnInsumos.setGraphic(new ImageView(imgInsumo));
        btnOrdenes.setGraphic(new ImageView(imgOrdenes));
        btnProductos.setGraphic(new ImageView(imgProductos));
        btnProveedores.setGraphic(new ImageView(imgProveedores));
        btnEmpleados.setGraphic(new ImageView(imgEmpleados));
    }

    public void obtDato(String var){
        user = var;
    }

    public void panelProducto(ActionEvent event){
        try {
            Stage homeStage=new Stage();
            homeStage.setTitle("Panel Producto");
            Parent root= null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/paneles/panelProducto.fxml"));
            panelProducto panelProducto = new panelProducto();
            loader.setController(panelProducto);
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

    public void panelInsumos(ActionEvent event){
        try {
            Stage homeStage=new Stage();
            homeStage.setTitle("Panel Insumos");
            Parent root= null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/paneles/panelInsumos.fxml"));
            panelInsumos panelInsumos = new panelInsumos();
            loader.setController(panelInsumos);
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

    public void panelProveedor(ActionEvent event){
        try {
            Stage homeStage=new Stage();
            homeStage.setTitle("Panel Proveedor");
            Parent root= null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/paneles/panelProveedor.fxml"));
            panelProveedor panelProveedor = new panelProveedor();
            loader.setController(panelProveedor);
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

    public void panelEmpleados(ActionEvent event){
        try {
            Stage homeStage=new Stage();
            homeStage.setTitle("Panel Empleados");
            Parent root= null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/paneles/panelEmpleados.fxml"));
            panelEmpleados panelEmpleados = new panelEmpleados();
            loader.setController(panelEmpleados);
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

    public void panelOrdenes(ActionEvent event){
        try {
            Stage homeStage=new Stage();
            homeStage.setTitle("Panel Ordenes");
            Parent root= null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/paneles/panelOrden.fxml"));
            panelOrden panelOrden = new panelOrden();
            loader.setController(panelOrden);
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
