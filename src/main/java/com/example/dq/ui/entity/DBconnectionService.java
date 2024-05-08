import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class DB_ConnectionService {

    public boolean testConnection(ConnectionRequest connectionRequest) {
        String url = "jdbc:postgresql://" + connectionRequest.getHostname() + ":" + connectionRequest.getPort() + "/ " + connectionRequest.getDatabaseName();
        try {
            Connection connection = DriverManager.getConnection(url, connectionRequest.getUsername(), connectionRequest.getPassword());
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}