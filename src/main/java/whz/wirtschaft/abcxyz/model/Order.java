package whz.wirtschaft.abcxyz.model;

import javafx.beans.property.*;

import java.util.Date;

public class Order {
    private IntegerProperty orderNumber;
    private ObjectProperty<Supplier> supplier;
    private ObjectProperty<Date> date;
    private DoubleProperty sum;
    private ObjectProperty<Quality> quality;

    Order(){
        orderNumber = new SimpleIntegerProperty();
        supplier = new SimpleObjectProperty<>();
        date = new SimpleObjectProperty<>();
        sum = new SimpleDoubleProperty();
        quality = new SimpleObjectProperty<>();
    }

    public int getOrderNumber() {
        return orderNumber.get();
    }

    public IntegerProperty orderNumberProperty() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber.set(orderNumber);
    }

    public Supplier getSupplier() {
        return supplier.get();
    }

    public ObjectProperty<Supplier> supplierProperty() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier.set(supplier);
    }

    public Date getDate() {
        return date.get();
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public double getSum() {
        return sum.get();
    }

    public DoubleProperty sumProperty() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum.set(sum);
    }

    public Quality getQuality() {
        return quality.get();
    }

    public ObjectProperty<Quality> qualityProperty() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality.set(quality);
    }



}
