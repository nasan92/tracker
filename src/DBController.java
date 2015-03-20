import java.sql.Connection; 
import java.sql.Date; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 

class DBController { 
     
    private static final DBController dbcontroller = new DBController(); 
    private static Connection connection; 
    private static final String DB_PATH = System.getProperty("user.home") + "/" + "databasemoc.db"; 

    static { 
        try { 
            Class.forName("org.sqlite.JDBC"); 
        } catch (ClassNotFoundException e) { 
            System.err.println("Fehler beim Laden des JDBC-Treibers"); 
            e.printStackTrace(); 
        } 
    } 
     
    private DBController(){ 
    } 
     
    public static DBController getInstance(){ 
        return dbcontroller; 
    } 
     
    private void initDBConnection() { 
        try { 
            if (connection != null) 
                return; 
            System.out.println("Creating Connection to Database..."); 
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH); 
            if (!connection.isClosed()) 
                System.out.println("...Connection established"); 
        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        } 

        Runtime.getRuntime().addShutdownHook(new Thread() { 
            public void run() { 
                try { 
                    if (!connection.isClosed() && connection != null) { 
                        connection.close(); 
                        if (connection.isClosed()) 
                            System.out.println("Connection to Database closed"); 
                    } 
                } catch (SQLException e) { 
                    e.printStackTrace(); 
                } 
            } 
        }); 
    } 

    private void handleDB() { 
        try { 
            Statement stmt = connection.createStatement(); 
           // stmt.executeUpdate("DROP TABLE IF EXISTS mocdata;"); 
           // stmt.executeUpdate("CREATE TABLE mocdata (altitude, tempo, time);"); 
            stmt.execute("INSERT INTO mocdata (altitude, tempo, time) VALUES (233, 254, 777)"); 
             
            PreparedStatement ps = connection 
                    .prepareStatement("INSERT INTO mocdata VALUES (?, ?, ?);"); 

            ps.setInt(1, 444); 
            ps.setInt(2, 656); 
            ps.setInt(3, 343);  
            ps.addBatch(); 
/*
            ps.setString(1, "Anton Antonius"); 
            ps.setString(2, "Anton's Alarm"); 
            ps.setDate(3, Date.valueOf("2009-10-01")); 
            ps.setInt(4, 123); 
            ps.setDouble(5, 98.76); 
            ps.addBatch(); 
*/
            connection.setAutoCommit(false); 
            ps.executeBatch(); 
            connection.setAutoCommit(true); 

            ResultSet rs = stmt.executeQuery("SELECT * FROM mocdata;"); 
            while (rs.next()) { 
                System.out.println("altitude = " + rs.getInt("altitude")); 
                System.out.println("tempo = " + rs.getInt("tempo")); 
                System.out.println("time = " + rs.getInt("time")); 
            } 
            rs.close(); 
            connection.close(); 
        } catch (SQLException e) { 
            System.err.println("Couldn't handle DB-Query"); 
            e.printStackTrace(); 
        } 
    } 

    public static void main(String[] args) { 
        DBController dbc = DBController.getInstance(); 
        dbc.initDBConnection(); 
        dbc.handleDB(); 
    } 
}