package whz.wirtschaft.abcxyz.dao;

import javafx.collections.ObservableList;
import whz.wirtschaft.abcxyz.model.Order;
import whz.wirtschaft.abcxyz.model.Supplier;

import java.time.LocalDate;
import java.util.List;

public interface Service {

    ObservableList<Order> getAllOrders();

    List<Order> getAllOrdersBetweenDates(LocalDate start, LocalDate end);

    ObservableList<Supplier> getAllSuppliersBetweenDates(LocalDate start, LocalDate end);
}
