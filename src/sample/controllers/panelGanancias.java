package sample.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import sample.DAO.productoDAO;
import sample.modelos.Conexion;

import java.net.URL;
import java.util.ResourceBundle;

public class panelGanancias implements Initializable {

    @FXML PieChart pieChart;
    productoDAO productoDAO = new productoDAO(Conexion.getConnection());


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList data;
        data = productoDAO.buildData();
        pieChart.getData().addAll(data);

    }
}
