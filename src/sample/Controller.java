package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DAO.empleadoDAO;
import sample.controllers.PanelGerente;
import sample.controllers.panelMesas;
import sample.modelos.Conexion;
import sample.modelos.empleado;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //variables
    @FXML Button btnEntrar;
    @FXML TextField txtUser;
    @FXML PasswordField pwField;
    empleado empleado = new empleado();
    sample.DAO.empleadoDAO empleadoDAO = new empleadoDAO(Conexion.getConnection());
    int numMesa=0;
    String user="";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Las acciones de los botones

        btnEntrar.setOnAction(event -> {
            if (validarEmpleado().equals("Gerente")){
                //abrimos panel de gerente
                paneGerente(event);
            }else if (validarEmpleado().equals("Mesero")){
                //abrimos panel de venta para mesero
                paneMesas(event);
            }else{
                //Alerta de que no existe usuario
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Usuario y/o Password Incorrectos");
                alert.show();
            }
        });

    }


    public String validarEmpleado(){
        int resultado = 0;
        String tipoU = "";
        String usuario = txtUser.getText();
        String contra = pwField.getText();
        user = usuario;
        resultado = empleadoDAO.tipoUsuario(usuario,contra);
        if (resultado==1){
            tipoU = "Gerente";
            txtUser.setText("");
            pwField.setText("");
        }else if (resultado==3){
            tipoU = "Mesero";
        }
        return tipoU;
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

    public void paneGerente(ActionEvent event){
        try {
            Stage homeStage=new Stage();
            homeStage.setTitle("PanelGerente");
            Parent root= null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/paneles/panelGerente.fxml"));
            PanelGerente panelGerente = new PanelGerente();
            panelGerente.obtDato(user);
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
