import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database 
{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/ngo";
	
	static final String USER = "root";
	static final String PASS = "";
	static Statement stmt = null;
	static Connection conn = null;
	
	public static Connection connection() throws SQLException, ClassNotFoundException
	{
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		return conn;
	}

}
