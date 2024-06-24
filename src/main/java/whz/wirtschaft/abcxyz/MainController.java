package whz.wirtschaft.abcxyz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import whz.wirtschaft.abcxyz.model.Month;
import whz.wirtschaft.abcxyz.model.Order;
import whz.wirtschaft.abcxyz.model.Quality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainController {
    @FXML
    private TableView<Order> tableView;
    @FXML
    private TableColumn<Order, Integer> bestellungColumn;
    @FXML
    private TableColumn<Order, String> lieferantColumn;
    @FXML
    private TableColumn<Order, Date> dateColumn;
    @FXML
    private TableColumn<Order, Double> betragColumn;
    @FXML
    private TableColumn<Order, Quality> qualityColumn;
    @FXML
    private ChoiceBox<Integer> yearChoiceBox;
    @FXML
    private ChoiceBox<String> monthChoiceBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Label welcomeText;

    private final List<Integer> years = new ArrayList<>(Arrays.asList(2022, 2023, 2024));

    public void initialize(){
        bestellungColumn.setCellValueFactory(cellData -> cellData.getValue().orderNumberProperty().asObject());
        lieferantColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplier().nameProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        betragColumn.setCellValueFactory(cellData -> cellData.getValue().sumProperty().asObject());
        qualityColumn.setCellValueFactory(cellData -> cellData.getValue().qualityProperty());
        List<String> months = new ArrayList<>();
        for(Month m: Month.values())
            months.add(m.getName());
        monthChoiceBox.getItems().setAll(months);
        monthChoiceBox.setValue("Juni");
        yearChoiceBox.setValue(2024);
        yearChoiceBox.getItems().setAll(years);
    }

    @FXML
    protected void handleAnalyse(ActionEvent actionEvent) {
    }
}