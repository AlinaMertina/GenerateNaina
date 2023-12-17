package buildclasse;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connectionbd{

    String nombase;
    String jdbc;
    public Connectionbd(String nom,String jdbc1){
        nombase=nom;
        jdbc=jdbc1;
    }
    public Connection mysql(String nombase) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+nombase, "root", "root");
        return conn;
    }
    public Connection oracle(String nombase)throws Exception{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
        return con;
    }
    public Connection postgres(String nombase)throws Exception{
        Class.forName("org.postgresql.Driver");
        //étape 2: créer l'objet de connexion
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+nombase,"mertina","root");
        return conn;
    }
    
    public Connection getConnex() throws Exception{
        switch (jdbc) {
            case "mysql":
                return mysql(nombase);
            case "oracle":
                return oracle(nombase);
            case "postgres":
                return postgres(nombase);
            default:
                break;
        }
        return null;
    }
}