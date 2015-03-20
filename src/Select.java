import java.sql.*;

public class Select
{
  public static void main( String args[] )
  {
    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:databasemoc");
      c.setAutoCommit(false);
      System.out.println("Opened database successfully");

      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM MOCDATA;" );
      while ( rs.next() ) {
         int altitude = rs.getInt("ALTITUDE");
         int time  = rs.getInt("TIME");
         int tempo  = rs.getInt("TEMPO");
		 System.out.println( "ALTITUDE = " + altitude );
         System.out.println( "TIME = " + time );
         System.out.println( "TEMPO = " + tempo );
         System.out.println();
      }
      rs.close();
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Operation done successfully");
  }
}
