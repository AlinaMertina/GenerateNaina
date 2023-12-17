package buildclasse;


import java.sql.*;
import java.util.HashMap;
import buildclasse.Connectionbd;

public class Description {
     HashMap<String, String> dictionnaire = new HashMap<>();
     String nombase;
     String jdbc;
    // String[][][] dictionnaire = new String[0][15][2];
    public Description(String nom,String j){
        nombase=nom;
        jdbc=j;
        dictionnaire.put("INTEGER", "Integer");
        dictionnaire.put("BIGINT", "Long");
        dictionnaire.put("SMALLINT", "Short");
        dictionnaire.put("NUMERIC", "java.math.BigDecimal");
        dictionnaire.put("REAL", "Float");
        dictionnaire.put("DOUBLE PRECISION", "Double");
        dictionnaire.put("CHAR", "String");
        dictionnaire.put("VARCHAR", "String");
        dictionnaire.put("TEXT", "String");
        dictionnaire.put("BOOLEAN", "Boolean");
        dictionnaire.put("DATE", "java.util.Date");
        dictionnaire.put("TIME", "java.util.Time");
        dictionnaire.put("TIMESTAMP", "java.sql.Timestamp");
        dictionnaire.put("INTERVAL", "String"); // Ou une classe Java personnalisée
        dictionnaire.put("UUID", "java.util.UUID");
        dictionnaire.put("TIMESTAMP WITHOUT TIME ZONE", "java.util.Date"); // Ou une classe Java personnalisée

    }
    public String getTypejava(String nomvariable){
        if(dictionnaire.get(nomvariable.toUpperCase())==null){
            return "String";
        }
        return dictionnaire.get(nomvariable.toUpperCase());
    }
    public java.util.List<String[]> getDescriptionTable(String nomtable){
        java.util.List<String[]>  resuList= new java.util.ArrayList<String[]>();
        try {
            Connection connection = new Connectionbd(nombase,jdbc).getConnex();
            connection.setSchema("public");
            Statement stmt = connection.createStatement();
            String query = "SELECT column_name, data_type, character_maximum_length FROM information_schema.columns WHERE table_schema = 'public' AND table_name = '"+nomtable+"'";
            // System.out.println(query);
            java.sql.ResultSet columns = stmt.executeQuery(query);
            // if(columns==null){
            //     System.out.println("nulll");
            // }]
            
            while (columns.next()) {
                // System.out.println("hhhhuhu"+ columns.getString("data_type"));
                String[] value = new String[2];
                value[0]= getTypejava(columns.getString("data_type")) ;
                value[1]= columns.getString("column_name");
                resuList.add(value);
            }
           
            return resuList;
        } catch (Exception e) {
            System.out.println(e);
        }
        return resuList;
    }
}
