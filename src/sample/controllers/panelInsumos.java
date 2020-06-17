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
import sample.DAO.insumoDAO;
import sample.DAO.proveedorDAO;
import sample.modelos.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class panelInsumos implements Initializable {


    @FXML TableView<insumo> tblIns;
    @FXML TableColumn idInsumo, insumo, costo, idProveedor;
    @FXML Button btnAgregar, btnActualizar, btnEliminar, btnRegresar;
    @FXML ComboBox cmbTipo;
    @FXML TextField txtInsumo, txtCosto;
    insumoDAO insumoDAO = new insumoDAO(Conexion.getConnection());
    proveedorDAO proveedorDAO = new proveedorDAO(Conexion.getConnection());
    List<proveedor> infoProveedor = new ArrayList<>();
    int modificar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initTableInsumo();

        infoProveedor = proveedorDAO.findAllTipoProveedor();
        for (int i = 0; i < infoProveedor.size(); i++) {
            cmbTipo.getItems().add(infoProveedor.get(i).getNombreP());

        }

        tblIns.setOnMouseClicked(event -> {
            try{
                insumo ins = tblIns.getSelectionModel().getSelectedItem();
                txtInsumo.setText(ins.getInsumo());
                txtCosto.setText(String.valueOf(ins.getCosto()));
                modificar = ins.getIdInsumo();
            }catch (NullPointerException n){
                Alert alert5 = new Alert(Alert.AlertType.INFORMATION);
                alert5.setTitle("Error");
                alert5.setContentText("Selecciona algo");
                alert5.show();
            }
        });

        btnAgregar.setOnAction(event -> {
            addIns();
        });
        btnActualizar.setOnAction(event -> {
            updateIns();
        });
        btnEliminar.setOnAction(event -> {
            eraseIns();
        });
        btnRegresar.setOnAction(event -> {
            paneGerente(event);
        });
    }


    public void initTableInsumo(){
        idInsumo.setCellValueFactory(new PropertyValueFactory<producto, Integer>("idInsumo"));
        insumo.setCellValueFactory(new PropertyValueFactory<producto, String>("insumo"));
        costo.setCellValueFactory(new PropertyValueFactory<producto, String>("costo"));
        idProveedor.setCellValueFactory(new PropertyValueFactory<producto, Double>("idProveedor"));
        tblIns.setItems(insumoDAO.findAllInsumos());
    }

    public int tipoProveedor(String proveedor){
        int id=0;
        switch (proveedor){
            case "FRUTAS Y VERDURAS FANNY":
                id=1;
                break;
            case "TORTILLERIA ACOSTA":
                id=2;
                break;
            case "COCA-COLA":
                id=3;
                break;
            case "PEPSI":
                id=4;
                break;
            case "PANADERIA LOEZA":
                id=5;
                break;
            case "TORTILLERIA LA ESPIGA":
                id=6;
                break;
        }
        return id;
    }

    private void updateIns(){
        int prove = 0;
        prove = tipoProveedor(cmbTipo.getSelectionModel().getSelectedItem().toString());
        if (insumoDAO.updateInsumo(txtInsumo.getText(), Double.parseDouble(txtCosto.getText()), prove,modificar)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modificando Insumo");
            alert.setContentText("Insumo modificado");
            alert.show();
            tblIns.setItems(insumoDAO.findAllInsumos());
        }
        modificar=0;
        txtInsumo.setText("");
        txtCosto.setText("");
    }

    private void eraseIns() {
        if (tblIns.getSelectionModel().getSelectedIndex() >= 0){
             insumo ins = tblIns.getSelectionModel().getSelectedItem();
            if(insumoDAO.deleteInsumo(ins.getIdInsumo())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Borrando Insumo");
                alert.setContentText("Insumo Eliminado");
                alert.show();
            }
        }
        txtInsumo.setText("");
        txtCosto.setText("");
        tblIns.setItems(insumoDAO.findAllInsumos());
    }

    private void addIns() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nuevo Insumo");
        alert.setContentText("¿Deseas agregar este nuevo insumo?");
        ButtonType buttonTypeYES = new ButtonType("YES");
        ButtonType buttonTypeNO = new ButtonType("NO");
        alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
        Optional<ButtonType> response2 = alert.showAndWait();
        if (response2.get() == buttonTypeYES) {
            String proveedor = "";
            int tipo = 0;
            proveedor = cmbTipo.getSelectionModel().getSelectedItem().toString();
            tipo = tipoProveedor(proveedor);
            if(insumoDAO.insertInsumo(txtInsumo.getText(), Double.valueOf(txtCosto.getText()), tipo)){
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("¡¡¡¡ ACTUALIZACION !!!!");
                alert2.setContentText("Se ha agregado con exito el nuevo insumo");
                alert2.show();
            }
        }
        txtInsumo.setText("");
        txtCosto.setText("");
        tblIns.setItems(insumoDAO.findAllInsumos());
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
