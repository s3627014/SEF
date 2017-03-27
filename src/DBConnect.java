import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnect(){
  try {

    host = ;
    userName = ;
    password = ;
    Connection con = DriverManager.getConnection(host, userName, password);
}

  catch ( SQLException err ) {
    System.out.println( err.getMessage( ) );
}


}
