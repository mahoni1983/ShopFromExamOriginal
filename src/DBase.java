
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Connector to DB using Singleton pattern
 * @author User
 *
 */
public class DBase {
	String CONNECTION_STRING =

			"jdbc:mysql://localhost:3306/SHOP?useSSL=false";
	String USER = "root";
	String PASS = "root";
	private static DBase instance = null;
	private Connection connection = null;

	public static DBase getInstance() {
		if (instance == null)
			instance = new DBase();
		return instance;
	}

	private DBase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection

			(CONNECTION_STRING, USER, PASS);
		} catch (Exception e) {
			e.printStackTrace();
			connection = null;
			System.out.println("Connection to SQL server problems:" + e.getMessage());
		}
	}

	public Connection getConnection() {
		return connection;
	}

}