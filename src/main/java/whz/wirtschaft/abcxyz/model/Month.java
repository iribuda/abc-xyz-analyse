package whz.wirtschaft.abcxyz.model;

public enum Month {
    ALL("Das ganze Jahr"),
    JAN("Januar"),
    FEB("Februar"),
    MAR("März"),
    APR("April"),
    MAY("Mai"),
    JUN("Juni"),
    JUL("Juli"),
    AUG("August"),
    SEP("September"),
    OCT("Oktober"),
    NOV("November"),
    DEC("Dezember");

    private String name;

    Month(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
