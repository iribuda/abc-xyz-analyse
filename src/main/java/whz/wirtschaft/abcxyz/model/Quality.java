package whz.wirtschaft.abcxyz.model;

public enum Quality {
    POOR("Mangelhaft", 1),
    SUFFICIENT("Ausreichend", 2),
    SATISFACTORY("Befriedigend", 3),
    GOOD ("Gut", 4);
    private String name;
    private int score;

    Quality(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
