package whz.wirtschaft.abcxyz.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import whz.wirtschaft.abcxyz.model.Order;
import whz.wirtschaft.abcxyz.model.Quality;
import whz.wirtschaft.abcxyz.model.Supplier;

import java.sql.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceImpl implements Service {

    private ObservableList<Order> allOrders = FXCollections.observableArrayList();
    private static final Map<String, Supplier> supplierMap = new HashMap<>();

    public ServiceImpl(){
        getAllSuppliers();
        supplierMap.forEach((s, supplier) -> supplier.reset());
    }

    private void getAllSuppliers(){
        Connection conn = DBConnection.getConnection();
        Statement statement = null;
        ResultSet rs = null;
        String sql = "select * from supplier";
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()){
                Supplier s = new Supplier();
                s.setId(rs.getInt("supplierId"));
                s.setName(rs.getString("supplierName"));
                supplierMap.put(s.getName(), s);
            }
        } catch (SQLException e) {
            throw new DBException("Error by connecting while getting all the suppliers: " + e.getMessage());
        }finally {
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }
    }

    @Override
    public ObservableList<Order> getAllOrders() {
        if (allOrders.isEmpty())
            allOrders = getAllFromDB();
        return allOrders;
    }

    private ObservableList<Order> getAllFromDB() {
        Connection conn = DBConnection.getConnection();
        Statement statement = null;
        ResultSet rs = null;
        String sql = "select b.*, s.supplierName from bestellung b\n" +
                "inner join supplier s ON b.supplierId = s.supplierId\n";
        ObservableList<Order> orders = FXCollections.observableArrayList();
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()){
                Order o = extractOrder(rs);
                orders.add(o);
                allOrders.add(o);
            }
        } catch (SQLException e) {
            throw new DBException("Error by connecting while getting all the orders: " + e.getMessage());
        }finally {
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrdersBetweenDates(LocalDate start, LocalDate end) {
        return allOrders.stream()
                .filter(order -> order.getDate().after(Date.valueOf(start)) && order.getDate().before(Date.valueOf(end)))
                .collect(Collectors.toList());
    }

    @Override
    public ObservableList<Supplier> getAllSuppliersBetweenDates(LocalDate start, LocalDate end) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select sum(b.amount) as total, s.supplierName \n" +
                "from bestellung b\n" +
                "join supplier s ON b.supplierId = s.supplierId\n" +
                "where b.orderDate BETWEEN ? AND ?\n" +
                "GROUP BY s.supplierName";
        List<Supplier> suppliers = new ArrayList<>();
        try {
            statement = conn.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(start));
            statement.setDate(2, Date.valueOf(end));
            rs = statement.executeQuery();
            while (rs.next()){
                Supplier s = supplierMap.get(rs.getString("supplierName"));
                s.setTotalAmount(rs.getDouble("total"));
                suppliers.add(s);
            }
            calculateABC(suppliers);
            List<Order> orders = getAllOrdersBetweenDates(start, end);
            for(Order o: orders){
                Supplier s = o.getSupplier();
                s.setAllPoints(s.getAllPoints() + o.getQuality().getScore());
                s.setOrderQuantity(s.getOrderQuantity()+1);
            }
            suppliers.forEach(Supplier::calculateTotalScore);
        } catch (SQLException e) {
            throw new DBException("Error by connecting while getting all the suppliers: " + e.getMessage());
        }finally {
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement(statement);
            DBConnection.disconnect();
        }
        return FXCollections.observableList(suppliers);
    }

    private void calculateABC(List<Supplier> suppliers){
        double total = suppliers.stream().mapToDouble(Supplier::getTotalAmount).sum();
        double kumPercent = 0;
        suppliers.forEach(s -> s.setPercent(s.getTotalAmount()/total * 100));
        suppliers.sort(Comparator.comparing(Supplier::getPercent).reversed());
        for (Supplier s: suppliers){
            kumPercent += s.getPercent();
            s.setKumPercent(kumPercent);
        }
    }

    private Order extractOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderNumber(rs.getInt("orderId"));
        order.setDate(rs.getDate("orderDate"));
        order.setSum(rs.getDouble("amount"));
        for (Quality q: Quality.values()){
            if (rs.getString("quality").equals(q.getName()))
                order.setQuality(q);
        }
        order.setSupplier(supplierMap.get(rs.getString("supplierName")));
        return order;
    }
}
