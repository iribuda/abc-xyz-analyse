package whz.wirtschaft.abcxyz.model;

import java.time.LocalDate;
import java.util.Date;

public enum Month {
    ALL("Das ganze Jahr"),
    JAN("Januar", 1),
    FEB("Februar", 2),
    MAR("März", 3),
    APR("April", 4),
    MAY("Mai", 5),
    JUN("Juni", 6),
    JUL("Juli", 7),
    AUG("August", 8),
    SEP("September", 9),
    OCT("Oktober", 10),
    NOV("November", 11),
    DEC("Dezember", 12);

    private String name;
    private int order;

    Month(String name){
        this.name = name;
    }

    Month(String name, int order){
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
