package com.example.cocheshibernate1an.Controller;

import com.example.cocheshibernate1an.DAO.MultaDAO;
import com.example.cocheshibernate1an.DAO.MultaDAOImpl;
import com.example.cocheshibernate1an.Model.Coche;
import com.example.cocheshibernate1an.Model.Multa;
import com.example.cocheshibernate1an.util.Alerta;
import com.example.cocheshibernate1an.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MultaViewController implements Initializable {

    @FXML
    private TableColumn<?, ?> columnFecha;

    @FXML
    private TableColumn<?, ?> columnIdMulta;

    @FXML
    private TableColumn<?, ?> columnPrecio;

    @FXML
    private TableView<Multa> tableView;

    @FXML
    private DatePicker txtFecha;

    @FXML
    private TextField txtIdMulta;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtPrecio;

    MultaDAO multaDAO = new MultaDAOImpl();

    SessionFactory sessionFactory;

    Session session;

    private ObservableList<Multa> listaMultas = FXCollections.observableArrayList();

    Coche cocheActual;
    Multa multaActual;

    @FXML
    void onClicActualizar(ActionEvent event) {
        int index=listaMultas.indexOf(multaActual);
        multaActual.setId_multa(Integer.parseInt(txtMatricula.getText()));
        multaActual.setPrecio(Double.parseDouble(txtPrecio.getText()));
        multaActual.setFecha(txtFecha.getValue());
        if (multaDAO.actualizarMulta(session, multaActual)){
            Alerta.mostrarAlerta("Multa actualizada correctamente");
            listaMultas.set(index, multaActual);
            tableView.refresh();
        }else {
            Alerta.mostrarAlerta("Error al modificar multa");
        }
    }

    @FXML
    void onClicBorrar(ActionEvent event) {
        if (multaDAO.eliminarMulta(session, multaActual)){
            Alerta.mostrarAlerta("Multa eliminada correctamente");
            listaMultas.remove(multaActual);
        }else {
            Alerta.mostrarAlerta("Error al eliminar la multa");
        }
    }

    @FXML
    void onClicInsertar(ActionEvent event) {
        String matricula = cocheActual.getMatricula();
        txtMatricula.setText(matricula);
        double precio=Double.parseDouble(txtPrecio.getText());
        LocalDate fecha=txtFecha.getValue();
        Multa multa=new Multa(precio, fecha, cocheActual);
        if (multaDAO.crearMulta(session, multa)){
            Alerta.mostrarAlerta("Multa creada correctamente");
        }else {
            Alerta.mostrarAlerta("No se pudo crear la multa");
        }
    }

    @FXML
    void onClicLimpiar(ActionEvent event) {
        txtIdMulta.setEditable(true);
        txtIdMulta.clear();
        txtPrecio.clear();
        txtFecha.setValue(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = HibernateUtil.getSession();

        columnIdMulta.setCellValueFactory(new PropertyValueFactory<>("id_multa"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
    }

    public void cogerCoche(Coche coche){
        this.cocheActual=coche;
        listaMultas = FXCollections.observableArrayList(multaDAO.listarMultaCoche(session,cocheActual.getMatricula()));
        tableView.setItems(listaMultas);
        txtMatricula.setEditable(false);
        txtMatricula.setText(cocheActual.getMatricula());
    }

    public void clicFilaTableView(javafx.scene.input.MouseEvent mouseEvent) {
        if(tableView.getSelectionModel().getSelectedItem()!=null){
            multaActual=tableView.getSelectionModel().getSelectedItem();
            txtMatricula.setText(multaActual.getCoche().getMatricula());
            txtIdMulta.setText(String.valueOf(multaActual.getId_multa()));
            txtPrecio.setText(String.valueOf(multaActual.getPrecio()));
            txtFecha.setValue(multaActual.getFecha());
            txtIdMulta.setEditable(false);
        }
    }
}

