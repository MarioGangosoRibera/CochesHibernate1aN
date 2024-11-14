package com.example.cocheshibernate1an.Controller;

import com.example.cocheshibernate1an.DAO.CocheDAOImpl;
import com.example.cocheshibernate1an.Model.Coche;
import com.example.cocheshibernate1an.util.Alerta;
import com.example.cocheshibernate1an.util.HibernateUtil;
import com.example.cocheshibernate1an.util.R;
import com.example.cocheshibernate1an.util.Validar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private TableColumn<?, ?> columnMarca;

    @FXML
    private TableColumn<?, ?> columnMatricula;

    @FXML
    private TableColumn<?, ?> columnModelo;

    @FXML
    private TableColumn<?, ?> columnTipo;

    @FXML
    private TableView<Coche> tableView;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtModelo;

    SessionFactory sessionFactory;

    Session session;

    Coche cocheSeleccionado;

    static CocheDAOImpl cocheDao = new CocheDAOImpl();

    private final ArrayList<String> listaTipos=new ArrayList<>(Arrays.asList("Gasolina", "Diesel", "Híbrido","Electrico"));

    private ObservableList<Coche> listaCoches;


    @FXML
    void onClicCrear(ActionEvent event) {
        String matricula = txtMatricula.getText();
        if (!Validar.validarMatriculaEuropea_Exp(matricula)){
            Alerta.mostrarAlerta("Modelo de matricula no valido");
        }else {
            String marca=txtMarca.getText();
            String modelo=txtModelo.getText();
            String tipo=cbTipo.getValue();
            Coche coche=new Coche(matricula,marca,modelo,tipo);
            if (cocheDao.crearCoche(session, coche)){
                Alerta.mostrarAlerta("Coche creado correctamente");
                listaCoches.add(coche);
            }else {
                Alerta.mostrarAlerta("Error al crear el coche");
            }
        }
    }

    @FXML
    void onClicEliminar(ActionEvent event) {
        if (cocheDao.eliminarCoche(session,cocheSeleccionado)){
            Alerta.mostrarAlerta("Coche eliminado correctamente");
            listaCoches.remove(cocheSeleccionado);
        }else {
            Alerta.mostrarAlerta("Error al eliminar el coche");
        }
    }

    @FXML
    void onClicLimpiar(ActionEvent event) {
        txtMatricula.clear();
        txtMarca.clear();
        txtModelo.clear();
        cbTipo.setValue(null);
        cocheSeleccionado=null;
    }

    @FXML
    void onClicModificar(ActionEvent event) {
        int index = listaCoches.indexOf(cocheSeleccionado);
        if (!Validar.validarMatriculaEuropea_Exp(txtMatricula.getText())){
            Alerta.mostrarAlerta("Modelo de matricula no valido");
        }else {
            cocheSeleccionado.setMatricula(txtMatricula.getText());
            cocheSeleccionado.setMarca(txtMarca.getText());
            cocheSeleccionado.setModelo(txtModelo.getText());
            cocheSeleccionado.setTipo(cbTipo.getValue());

            if (cocheDao.actualizarCoche(session, cocheSeleccionado)){
                Alerta.mostrarAlerta("Coche modificadio correctamente");
                listaCoches.set(index, cocheSeleccionado);
            }else {
                Alerta.mostrarAlerta("Error al modificar el coche");
            }
        }
    }

    @FXML
    void onClicMultas(ActionEvent event) {
        if (cocheSeleccionado!=null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(R.getUI("multa-view.fxml"));
                Parent contenidoFXML = fxmlLoader.load();
                MultaViewController controller = fxmlLoader.getController();
                controller.cogerCoche(cocheSeleccionado);

                Stage nuevoState = new Stage();
                Scene nuevaEscena = new Scene(contenidoFXML);
                nuevoState.setScene(nuevaEscena);
                nuevoState.show();
            } catch (IOException e) {
                System.out.println("Error al cargar la ventana");
            }
        }else {
            Alerta.mostrarAlerta("Debes seleccionar un coche para ver sus multas");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = HibernateUtil.getSession();

        cbTipo.getItems().addAll(listaTipos);

        columnMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        listaCoches = FXCollections.observableArrayList(cocheDao.listarCoches(session));
        tableView.setItems(listaCoches);
    }

    public void clicFilaTableView(javafx.scene.input.MouseEvent mouseEvent) {
        if(tableView.getSelectionModel().getSelectedItem()!=null){
            cocheSeleccionado=tableView.getSelectionModel().getSelectedItem();
            txtMatricula.setText(cocheSeleccionado.getMatricula());
            txtMarca.setText(cocheSeleccionado.getMarca());
            txtModelo.setText(cocheSeleccionado.getModelo());
            cbTipo.setValue(cocheSeleccionado.getTipo());
        }
    }
}

