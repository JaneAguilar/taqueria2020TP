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
import sample.DAO.ordenDAO;
import sample.modelos.Conexion;
import sample.modelos.orden;
import sample.modelos.ordenTemp;
import sample.modelos.producto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class panelOrden implements Initializable {

    @FXML TableColumn idOrden, cantidad, fecha, total, idProducto, noEmpleado, idMesa;
    @FXML TableView<orden> tblOrden;
    @FXML TextField txtCantidad;
    @FXML Button btnActualizar, btnRegresar, btnGanancias, btnEliminar;
    int modificar;
    ordenDAO ordenDAO = new ordenDAO(Conexion.getConnection());

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initTableOrden();

        tblOrden.setOnMouseClicked(event -> {


            try{
                orden ord = tblOrden.getSelectionModel().getSelectedItem();
                txtCantidad.setText(String.valueOf(ord.getCantidad()));
                modificar = ord.getIdOrden();
            }catch (NullPointerException n){
                Alert alert5 = new Alert(Alert.AlertType.INFORMATION);
                alert5.setTitle("Error");
                alert5.setContentText("Selecciona algo");
                alert5.show();
            }

        });

        btnActualizar.setOnAction(event -> {
            updateOrden();
        });

        btnRegresar.setOnAction(event -> {
            paneGerente(event);
        });

        btnGanancias.setOnAction(event -> {
            paneGanancias(event);
        });

        btnEliminar.setOnAction(event -> {
            eraseOrden();
        });
    }

    public void initTableOrden(){
        idOrden.setCellValueFactory(new PropertyValueFactory<>("idOrden"));
        cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        idProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        noEmpleado.setCellValueFactory(new PropertyValueFactory<>("noEmpleado"));
        idMesa.setCellValueFactory(new PropertyValueFactory<>("idMesa"));
        tblOrden.setItems(ordenDAO.findAllOrden());
    }

    private void updateOrden(){
        if (ordenDAO.updateOrden(Integer.parseInt(txtCantidad.getText()), modificar)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modificar Orden");
            alert.setContentText("Orden modificada");
            alert.show();
            tblOrden.setItems(ordenDAO.findAllOrden());
        }
        modificar=0;
        txtCantidad.setText("");
    }

    private void eraseOrden() {
        if (tblOrden.getSelectionModel().getSelectedIndex() >= 0){
            orden ord = tblOrden.getSelectionModel().getSelectedItem();
            if(ordenDAO.deleteOrden(ord.getIdOrden())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Borrando Orden");
                alert.setContentText("Orden Eliminada");
                alert.show();
            }
        }
        tblOrden.setItems(ordenDAO.findAllOrden());
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

    public void paneGanancias(ActionEvent event){
        try {
            Stage homeStage=new Stage();
            homeStage.setTitle("Panel Ganancias");
            Parent root= null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/paneles/panelGanacias.fxml"));
            panelGanancias panelGanancias = new panelGanancias();
            loader.setController(panelGanancias);
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
