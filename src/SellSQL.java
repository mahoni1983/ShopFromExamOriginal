
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * class for making sell with work with DB
 * 
 * @author User
 *
 */
public class SellSQL {
	private void addSell(int ware_id, int user_id, int number) throws SQLException {

		try {
			String query = "insert into sell(ware_id, user_id, number, sell_date) values(?, ?, ?, ?);";
			Calendar calendar = Calendar.getInstance();
			java.util.Date now = calendar.getTime();
			java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
			PreparedStatement ps = DBase.getInstance().getConnection().prepareStatement(query, 1);
			ps.setInt(1, ware_id);
			ps.setInt(2, user_id);
			ps.setInt(3, number);
			ps.setTimestamp(4, currentTimestamp);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * to update ware table in DB
	 * 
	 * @param ware_id
	 * @param number
	 * @throws SQLException
	 */
	private void subWareTotal(int ware_id, int number) throws SQLException {

		try {
			String query = "update ware set total = total - ? where ware_id=?;";
			PreparedStatement ps = DBase.getInstance().getConnection().prepareStatement(query, 1);
			ps.setInt(1, number);
			ps.setInt(2, ware_id);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * to make a transaction and get a message
	 * 
	 * @param ware_id
	 * @param user_id
	 * @param number
	 * @return
	 * @throws SQLException
	 */
	public String sell(int ware_id, int user_id, int number) throws SQLException {
		try {
			WareSQL wareSQL = new WareSQL();
			UserSQL userSQL = new UserSQL();
			if (!wareSQL.isWareID(ware_id))
				return "ware not found";
			if (!userSQL.isUserID(user_id))
				return "user not found";
			if (!wareSQL.isEnough(ware_id, number))
				return "not enough";
			this.addSell(ware_id, user_id, number);
			this.subWareTotal(ware_id, number);
		} catch (Exception e) {
			e.printStackTrace();
			return "unknown error";
		}
		return "ok";
	}
}
