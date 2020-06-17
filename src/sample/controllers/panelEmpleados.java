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
import sample.DAO.empleadoDAO;
import sample.DAO.puestoDAO;
import sample.modelos.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class panelEmpleados implements Initializable {

    @FXML Button btnAgregar, btnActualizar, btnEliminar, btnRegresar;
    @FXML  TableView<empleado> tblEmpleados;
    @FXML TableColumn noEmpleado, nombreE, apellidoP, apellidoM, usuario, contrasena, idPuesto;
    @FXML ComboBox cmbPuesto;
    @FXML TextField txtNombre, txtAPaterno, txtAMaterno, txtUsuario, txtContrasena;
    int modificar;
    empleadoDAO empleadoDAO = new empleadoDAO(Conexion.getConnection());
    puestoDAO puestoDAO = new puestoDAO(Conexion.getConnection());
    List<puesto> infoPuesto = new ArrayList<>();




    @Override
    public void initialize(URL location, ResourceBundle resources) {


        infoPuesto = puestoDAO.findAllPuesto();
        for (int i = 0; i < infoPuesto.size(); i++) {
            cmbPuesto.getItems().add(infoPuesto.get(i).getPuesto());
        }

        initTableEmpleados();

        tblEmpleados.setOnMouseClicked(event -> {
            try{

                    empleado emp = tblEmpleados.getSelectionModel().getSelectedItem();
                    txtNombre.setText(emp.getNombreE());
                    txtAPaterno.setText(emp.getApellidoP());
                    txtAMaterno.setText(emp.getApellidoM());
                    txtUsuario.setText(emp.getUsuario());
                    txtContrasena.setText(emp.getContrasena());
                    modificar = emp.getNoEmpleado();

            }catch (NullPointerException n){
                Alert alert5 = new Alert(Alert.AlertType.INFORMATION);
                alert5.setTitle("Error");
                alert5.setContentText("Selecciona algo");
                alert5.show();
            }
        });

        btnAgregar.setOnAction(event -> {
            addEmpleado();
        });

        btnEliminar.setOnAction(event -> {
            eraseEmpleado();
        });

        btnActualizar.setOnAction(event -> {
            updateEmpleado();
        });

        btnRegresar.setOnAction(event -> {
            paneGerente(event);
        });
    }

    public void initTableEmpleados(){
        noEmpleado.setCellValueFactory(new PropertyValueFactory<>("noEmpleado"));
        nombreE.setCellValueFactory(new PropertyValueFactory<>("nombreE"));
        apellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidoP"));
        apellidoM.setCellValueFactory(new PropertyValueFactory<>("apellidoM"));
        usuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        noEmpleado.setCellValueFactory(new PropertyValueFactory<>("noEmpleado"));
        contrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
        idPuesto.setCellValueFactory(new PropertyValueFactory<>("idPuesto"));
        tblEmpleados.setItems(empleadoDAO.findAllEmpleado());
    }

    private void updateEmpleado(){
        if (empleadoDAO.updateEmpleado(txtUsuario.getText(),txtContrasena.getText(), modificar)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modificar Empleado");
            alert.setContentText("Empleado modificado");
            alert.show();
            tblEmpleados.setItems(empleadoDAO.findAllEmpleado());
        }
        modificar=0;
        txtNombre.setText("");
        txtAPaterno.setText("");
        txtAMaterno.setText("");
        txtUsuario.setText("");
        txtContrasena.setText("");
    }

    private void eraseEmpleado() {
        if (tblEmpleados.getSelectionModel().getSelectedIndex() >= 0){
            empleado emp = tblEmpleados.getSelectionModel().getSelectedItem();
            if(empleadoDAO.deleteEmpleado(emp.getNoEmpleado())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Borrando Empleado");
                alert.setContentText("Empleado Eliminado");
                alert.show();
            }
        }
        txtNombre.setText("");
        txtAPaterno.setText("");
        txtAMaterno.setText("");
        txtUsuario.setText("");
        txtContrasena.setText("");
        tblEmpleados.setItems(empleadoDAO.findAllEmpleado());
    }

    private void addEmpleado() {
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
            prod = cmbPuesto.getSelectionModel().getSelectedItem().toString();
            tipo = tipoPuesto(prod);
            if(empleadoDAO.insertEmpleado(
                    txtNombre.getText(),
                    txtAPaterno.getText(),
                    txtAMaterno.getText(),
                    txtUsuario.getText(),
                    txtContrasena.getText(),
                    tipo
            )){
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("¡¡¡¡ ACTUALIZACION !!!!");
                alert2.setContentText("Se ha agregado con exito el nuevo empleado");
                alert2.show();
            }
        }
        txtNombre.setText("");
        txtAPaterno.setText("");
        txtAMaterno.setText("");
        txtUsuario.setText("");
        txtContrasena.setText("");
        tblEmpleados.setItems(empleadoDAO.findAllEmpleado());
    }

    public int tipoPuesto(String puesto){
        int id=0;
        switch (puesto){
            case "GERENTE":
                id=1;
                break;
            case "MESERO":
                id=3;
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
