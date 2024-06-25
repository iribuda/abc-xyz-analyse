package whz.wirtschaft.abcxyz;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import whz.wirtschaft.abcxyz.dao.Service;
import whz.wirtschaft.abcxyz.model.Order;
import whz.wirtschaft.abcxyz.model.Supplier;

import java.time.LocalDate;
import java.util.*;

public class SecondController {
    @FXML
    private TableView<Supplier> supplierTableView;
    @FXML
    private TableColumn<Supplier, String> supplierColumn;
    @FXML
    private TableColumn<Supplier, Double> totalAmountColumn;
    @FXML
    private TableColumn<Supplier, String > percentColumn;
    @FXML
    private TableColumn<Supplier, String> kumPercentColumn;
    @FXML
    private TableColumn<Supplier, String> abcColumn;
    @FXML
    private TableColumn<Supplier, String> xyzColumn;
    @FXML
    private TableView<Order> orderTableView;
    @FXML
    private TableColumn<Order, Integer> bestellungColumn;
    @FXML
    private TableColumn<Order, String> lieferantColumn;
    @FXML
    private TableColumn<Order, Date> dateColumn;
    @FXML
    private TableColumn<Order, Double> betragColumn;
    @FXML
    private TableColumn<Order, String> qualityColumn;

    private Service service;
    private LocalDate start;
    private LocalDate end;
    private Stage stage;

    public void initialize(){
        bestellungColumn.setCellValueFactory(cellData -> cellData.getValue().orderNumberProperty().asObject());
        lieferantColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplier().nameProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        betragColumn.setCellValueFactory(cellData -> cellData.getValue().sumProperty().asObject());
        qualityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuality().getName()));

        supplierColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        totalAmountColumn.setCellValueFactory(cellData -> cellData.getValue().totalAmountProperty().asObject());
        percentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPercentString()));
        kumPercentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKumPercentString()));
        abcColumn.setCellValueFactory(cellData -> cellData.getValue().abcProperty());
        xyzColumn.setCellValueFactory(cellData -> cellData.getValue().xyzProperty());
    }

    private void initTables(){
        ObservableList<Order> orders = FXCollections.observableList(service.getAllOrdersBetweenDates(start, end));
        orderTableView.setItems(orders);

        ObservableList<Supplier> suppliers = FXCollections.observableList(service.getAllSuppliersBetweenDates(start, end));
        supplierTableView.setItems(suppliers);
    }
    public void setDialogStage(Stage stage) {
        this.stage = stage;
    }

    public void setStartDate(LocalDate start) {
        this.start = start;
    }

    public void setEndDate(LocalDate end) {
        this.end = end;
        initTables();
    }

    public void setDAO(Service service) {
        this.service = service;
    }
}
