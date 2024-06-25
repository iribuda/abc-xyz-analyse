package whz.wirtschaft.abcxyz.model;

import javafx.beans.property.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Supplier {
    private IntegerProperty id;
    private StringProperty name;
    private DoubleProperty totalAmount;
    private DoubleProperty percent;
    private DoubleProperty kumPercent;
    private StringProperty abc;
    private StringProperty xyz;
    private int allPoints;
    private double totalScore;
    private int orderQuantity;


    Supplier(int id, String name){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.totalAmount = new SimpleDoubleProperty();
        this.percent = new SimpleDoubleProperty();
        this.kumPercent = new SimpleDoubleProperty();
        this.abc = new SimpleStringProperty();
        this.xyz = new SimpleStringProperty();
    }

    public Supplier(){
        id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        this.totalAmount = new SimpleDoubleProperty();
        this.percent = new SimpleDoubleProperty();
        this.kumPercent = new SimpleDoubleProperty();
        this.abc = new SimpleStringProperty();
        this.xyz = new SimpleStringProperty();
    }

    public void reset(){
        this.totalAmount = new SimpleDoubleProperty();
        this.percent = new SimpleDoubleProperty();
        this.kumPercent = new SimpleDoubleProperty();
        this.abc = new SimpleStringProperty();
        this.xyz = new SimpleStringProperty();
        this.allPoints = 0;
        this.totalScore = 0;
        this.orderQuantity = 0;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getTotalAmount() {
        return totalAmount.get();
    }

    public DoubleProperty totalAmountProperty() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount.set(totalAmount);
    }

    public double getPercent() {
        return percent.get();
    }

    public String getPercentString(){
        return String.format("%.2f%%", getPercent());
    }

    public DoubleProperty percentProperty() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent.set(percent);
    }

    public double getKumPercent() {
        return kumPercent.get();
    }

    public DoubleProperty kumPercentProperty() {
        return kumPercent;
    }

    public String getKumPercentString(){
        return String.format("%.2f%%", getKumPercent());
    }

    public void setKumPercent(double kumPercent) {
        this.kumPercent.set(kumPercent);
        if (kumPercent > 95)
            setAbc("C");
        else if (kumPercent > 75)
            setAbc("B");
        else
            setAbc("A");
    }

    public String getAbc() {
        return abc.get();
    }

    public StringProperty abcProperty() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc.set(abc);
    }

    public String getXyz() {
        return xyz.get();
    }

    public StringProperty xyzProperty() {
        return xyz;
    }

    public void setXyz(String xyz) {
        this.xyz.set(xyz);
    }

    public int getAllPoints() {
        return allPoints;
    }

    public void setAllPoints(int allPoints) {
        this.allPoints = allPoints;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void calculateTotalScore() {
        this.totalScore = (double) allPoints /orderQuantity;
        if (totalScore > 2.5)
            setXyz("X");
        else if (totalScore > 1.5)
            setXyz("Y");
        else
            setXyz("Z");
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Supplier other = (Supplier) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }

        return true;
    }
}
