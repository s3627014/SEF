import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnect(){
  try {

    host = ; //emailed Halil for these deets. More to come when he replies -Tim
    userName = ;
    password = ;
    Connection con = DriverManager.getConnection(host, userName, password);
}

  catch ( SQLException err ) {
    System.out.println( err.getMessage( ) );
}


}
