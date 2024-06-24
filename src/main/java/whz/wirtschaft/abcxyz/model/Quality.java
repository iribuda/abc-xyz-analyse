package whz.wirtschaft.abcxyz.model;

public enum Quality {
    POOR("Mangelhaft"),
    SUFFICIENT("Ausreichend"),
    SATISFACTORY("Befriedigend"),
    GOOD ("Gut");
    private String name;

    Quality(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
