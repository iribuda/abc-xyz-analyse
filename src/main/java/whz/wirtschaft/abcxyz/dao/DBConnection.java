package whz.wirtschaft.abcxyz.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnection {
    private static Connection conn = null;
    private static final String connString = "jdbc:sqlserver://localhost;database=abc_xyz;" +
            "encrypt=true;trustServerCertificate=true;authenticationScheme=NTLM;domain=myDomain;";

    public static Connection getConnection(){
        if (conn == null || connectionIsClosed()){
            try {
                Properties properties = loadProperties();
                String user = properties.getProperty("user");
                String password = properties.getProperty("password");
                conn = DriverManager.getConnection(connString, user, password);
            }catch (SQLException e){
                throw new DBException(e.getMessage());
            }
        }
        return conn;
    }

    private static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("src/main/resources/whz/wirtschaft/abcxyz/db.properties")){
            Properties properties = new Properties();
            properties.load(fs);
            return properties;
        }catch (IOException e){
            throw new DBException(e.getMessage());
        }
    }

    public static void disconnect(){
        try{
            if (conn != null && !conn.isClosed()){
                conn.close();
            }
        }catch (Exception e){
            throw new DBException(e.getMessage());
        }
    }

    private static boolean connectionIsClosed() {
        boolean connectionIsClosed=true;
        try {
            if (conn != null)
                connectionIsClosed = conn.isClosed();
        } catch (SQLException e) {
            throw new DBException("Error by checking whether the connection is closed");
        }
        return connectionIsClosed;
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }
}
