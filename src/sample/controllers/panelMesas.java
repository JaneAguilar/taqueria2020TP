package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class panelMesas implements Initializable {

    @FXML Button btnMesa1, btnMesa2,btnMesa3,btnMesa4,btnMesa5,btnMesa6;
    String user="";
    int m1;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnMesa1.setOnAction(event -> {
            m1 = 1;
            panelOrdenar(event);
        });

        btnMesa2.setOnAction(event -> {
            m1 = 2;
            panelOrdenar(event);
        });

        btnMesa3.setOnAction(event -> {
            m1 = 3;
            panelOrdenar(event);
        });

        btnMesa4.setOnAction(event -> {
            m1 = 4;
            panelOrdenar(event);
        });

        btnMesa5.setOnAction(event -> {
            m1 = 5;
            panelOrdenar(event);
        });

        btnMesa6.setOnAction(event -> {
            m1 = 6;
            panelOrdenar(event);
        });

    }

    public void obtDato(String var){
        user = var;
    }


    public void panelOrdenar(ActionEvent event){
        try {
            Stage homeStage=new Stage();
            homeStage.setTitle("PanelOrdenar");
            Parent root= null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/paneles/panelOrdenar.fxml"));
            panelOrdenar panelOrdenar = new panelOrdenar();
            panelOrdenar.obtDato(user);
            panelOrdenar.obtNum(m1);
            loader.setController(panelOrdenar);
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
