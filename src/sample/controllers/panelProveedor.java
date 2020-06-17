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
import sample.DAO.proveedorDAO;
import sample.modelos.Conexion;
import sample.modelos.insumo;
import sample.modelos.producto;
import sample.modelos.proveedor;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class panelProveedor implements Initializable {

    @FXML
    TableView<proveedor> tblProveedor;
    @FXML
    TableColumn idProveedor, nombreP, direccion, telefono;
    @FXML
    TextField txtNombre, txtDireccion, txtTelefono;
    @FXML
    Button btnAgregar, btnActualizar, btnEliminar, btnRegresar;
    proveedorDAO proveedorDAO = new proveedorDAO(Conexion.getConnection());
    int modificar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initTableProveedor();

        btnRegresar.setOnAction(event -> {
            paneGerente(event);
        });

        tblProveedor.setOnMouseClicked(event -> {
            try{

                proveedor pr = tblProveedor.getSelectionModel().getSelectedItem();
                txtNombre.setText(pr.getNombreP());
                txtDireccion.setText(pr.getDireccion());
                txtTelefono.setText(pr.getTelefono());
                modificar = pr.getIdProveedor();

            }catch (NullPointerException n){
                Alert alert5 = new Alert(Alert.AlertType.INFORMATION);
                alert5.setTitle("Error");
                alert5.setContentText("Selecciona algo");
                alert5.show();
            }
        });

        btnAgregar.setOnAction(event -> {
            addProveedor();
        });

        btnEliminar.setOnAction(event -> {
            eraseProveedor();
        });

        btnActualizar.setOnAction(event -> {
            updateProveedor();
        });

    }

    public void initTableProveedor(){
        idProveedor.setCellValueFactory(new PropertyValueFactory<producto, Integer>("idProveedor"));
        nombreP.setCellValueFactory(new PropertyValueFactory<producto, String>("nombreP"));
        direccion.setCellValueFactory(new PropertyValueFactory<producto, String>("direccion"));
        telefono.setCellValueFactory(new PropertyValueFactory<producto, Double>("telefono"));
        tblProveedor.setItems(proveedorDAO.findAllProveedor());
    }

    private void updateProveedor(){
        if (proveedorDAO.updateProveedor(txtNombre.getText(), txtDireccion.getText(), txtTelefono.getText(), modificar)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modificar Proveedor");
            alert.setContentText("Proveedor modificado");
            alert.show();
            tblProveedor.setItems(proveedorDAO.findAllProveedor());
        }
        modificar=0;
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
    }

    private void eraseProveedor() {
        if (tblProveedor.getSelectionModel().getSelectedIndex() >= 0){
            proveedor pr = tblProveedor.getSelectionModel().getSelectedItem();
            if(proveedorDAO.deleteProveedor(pr.getIdProveedor())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Borrando Proveedor");
                alert.setContentText("Proveedor Eliminado");
                alert.show();
            }
        }
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        tblProveedor.setItems(proveedorDAO.findAllProveedor());
    }

    private void addProveedor() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nuevo Proveedor");
        alert.setContentText("¿Deseas agregar este nuevo proveedor?");
        ButtonType buttonTypeYES = new ButtonType("YES");
        ButtonType buttonTypeNO = new ButtonType("NO");
        alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
        Optional<ButtonType> response2 = alert.showAndWait();
        if (response2.get() == buttonTypeYES) {
            if(proveedorDAO.insertProveedor(txtNombre.getText(),txtDireccion.getText(), txtTelefono.getText())){
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("¡¡¡¡ ACTUALIZACION !!!!");
                alert2.setContentText("Se ha agregado con exito el nuevo proveedor");
                alert2.show();
            }
        }
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        tblProveedor.setItems(proveedorDAO.findAllProveedor());
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
