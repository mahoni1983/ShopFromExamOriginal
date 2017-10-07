
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * for working with user table in sql DB
 * 
 * @author User
 *
 */
public class UserSQL {
	/**
	 * to add a new user to DB using sql query
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public int addUser(User user) throws SQLException {

		try {
			String query = "insert into user(name, email, phone) values(?, ?, ?);";
			PreparedStatement ps = DBase.getInstance().getConnection().prepareStatement(query, 1);
			ps.setString(1, user.name);
			ps.setString(2, user.email);
			ps.setString(3, user.phone);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next())
				user.user_id = rs.getInt(1);
			return user.user_id;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		}

	}

	/**
	 * checks if user with the email already exists
	 * 
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public boolean isEmail(String email) throws SQLException {
		try {
			String query = "select * from user where email = ?;";
			PreparedStatement ps = DBase.getInstance().getConnection().prepareStatement(query);
			ps.setString(1, email);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs.next())
				return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("UserSQL isEmail error");
		}

		return false;
	}

	/**
	 * to get list of all users from user table in DB
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<User> getUsers() throws SQLException {
		ArrayList<User> userList = new ArrayList<User>();
		try {
			String query = "select * from user order by name;";
			PreparedStatement ps = DBase.getInstance().getConnection().prepareStatement(query);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while (rs.next()) {
				String name = rs.getString("name");
				int user_id = rs.getInt("user_id");
				User user = new User();
				user.user_id = user_id;
				user.name = name;
				user.email = rs.getString("email");
				user.phone = rs.getString("phone");
				userList.add(user);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("UserSQL getUsers error");
		}
		return userList;
	}

	/**
	 * checks if a user exists by id
	 * 
	 * @param user_id
	 * @return
	 * @throws SQLException
	 */
	public boolean isUserID(int user_id) throws SQLException {
		try {
			String query = "select * from  user where user_id=?;";
			PreparedStatement ps = DBase.getInstance().getConnection().prepareStatement(query);
			ps.setInt(1, user_id);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs.next())
				return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("UserSQL isUser_id error");
		}
		return false;
	}
}
