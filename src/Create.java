import java.sql.*;

public class Create
{
  public static void main( String args[] )
  {
    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:databasemoc.db");
      System.out.println("Opened database successfully");

      stmt = c.createStatement();
      String sql = "CREATE TABLE MOCDATA " +
                   "(ID INT PRIMARY KEY     NOT NULL," +
                   " TIME           INT    NOT NULL, " + 
                   " ALTITUDE            INT     NOT NULL, " + 
                   " TEMP        INT      NOT NULL,";
      stmt.executeUpdate(sql);
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Table created successfully");
  }
}
