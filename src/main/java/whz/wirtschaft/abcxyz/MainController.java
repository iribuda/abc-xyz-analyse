package whz.wirtschaft.abcxyz;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import whz.wirtschaft.abcxyz.dao.Service;
import whz.wirtschaft.abcxyz.dao.ServiceImpl;
import whz.wirtschaft.abcxyz.model.Month;
import whz.wirtschaft.abcxyz.model.Order;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

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
    private TableColumn<Order, String> qualityColumn;
    @FXML
    private ChoiceBox<Integer> yearChoiceBox;
    @FXML
    private ChoiceBox<String> monthChoiceBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;

    private Service service;
    private LocalDate start;
    private LocalDate end;
    private Map<String, Month> monthMap = new HashMap<>();

    private final List<Integer> years = new ArrayList<>(Arrays.asList(2022, 2023, 2024));

    public void initialize(){
        service = new ServiceImpl();
        bestellungColumn.setCellValueFactory(cellData -> cellData.getValue().orderNumberProperty().asObject());
        lieferantColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplier().nameProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        betragColumn.setCellValueFactory(cellData -> cellData.getValue().sumProperty().asObject());
        qualityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuality().getName()));
        List<String> months = new ArrayList<>();
        for(Month m: Month.values()){
            months.add(m.getName());
            monthMap.put(m.getName(), m);
        }
        monthChoiceBox.getItems().setAll(months);
        monthChoiceBox.setValue("Juni");
        yearChoiceBox.setValue(2024);
        yearChoiceBox.getItems().setAll(years);
        ObservableList<Order> orders = service.getAllOrders();
        tableView.setItems(orders);
        start = LocalDate.of(2024, 6, 1);
        startDatePicker.setValue(start);
        end = LocalDate.now();
        endDatePicker.setValue(end);
        monthChoiceBox.setOnAction(event -> {
            if (monthChoiceBox.getValue().equals("Das ganze Jahr")){
                start = LocalDate.of(yearChoiceBox.getValue(), 1, 1);
                end = LocalDate.of(yearChoiceBox.getValue(), 12, 31);
            }
            else{
                int month = monthMap.get(monthChoiceBox.getValue()).getOrder();
                start = LocalDate.of(yearChoiceBox.getValue(), month, 1);
                end = start.with(TemporalAdjusters.lastDayOfMonth());
            }
            startDatePicker.setValue(start);
            endDatePicker.setValue(end);
        });
        yearChoiceBox.setOnAction(event -> {
            start = start.withYear(yearChoiceBox.getValue());
            end = end.withYear(yearChoiceBox.getValue());
            startDatePicker.setValue(start);
            endDatePicker.setValue(end);
        });
    }

    @FXML
    protected void handleAnalyse(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("second.fxml"));
            VBox page = (VBox) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Analyse");
            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.initOwner(mainPageController.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            SecondController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDAO(service);
            controller.setStartDate(start);
            controller.setEndDate(end);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}