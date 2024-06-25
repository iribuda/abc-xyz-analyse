package whz.wirtschaft.abcxyz.dao;

public class DBException extends RuntimeException{
    public DBException(String msg){
        super("Database related exception: " + msg);
    }
}
