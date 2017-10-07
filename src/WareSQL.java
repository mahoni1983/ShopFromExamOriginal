
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * to work with ware sql queries
 * 
 * @author User
 *
 */
public class WareSQL {
	public int addWare(Ware ware) throws SQLException {
		try {
			String query = "insert into ware(title, price, total) values(?, ?, ?);";
			PreparedStatement ps = DBase.getInstance().getConnection().prepareStatement(query, 1);
			ps.setString(1, ware.title);
			ps.setString(2, Double.toString(ware.price));
			ps.setInt(3, ware.total);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next())
				ware.ware_id = rs.getInt(1);
			return ware.ware_id;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}
/**
 * check if ware with a title already exists
 * @param title
 * @return
 * @throws SQLException
 */
	public boolean isTitle(String title) throws SQLException {
		try {
			String query = "select * from ware where title = ?;";
			PreparedStatement ps = DBase.getInstance().getConnection().prepareStatement(query);
			ps.setString(1, title);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs.next())
				return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("WareSQL isTitle error");
		}

		return false;
	}
/**
 * read out all wares from DB
 * @return
 * @throws SQLException
 */
	public ArrayList<Ware> getExistWares() throws SQLException {
		ArrayList<Ware> wareList = new ArrayList<Ware>();
		try {
			String query = "select * from ware order by title;";
			PreparedStatement ps = DBase.getInstance().getConnection().prepareStatement(query);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while (rs.next()) {
				String title = rs.getString("title");
				int ware_id = rs.getInt("ware_id");
				Ware ware = new Ware();
				ware.ware_id = ware_id;
				ware.title = title;
				ware.price = rs.getDouble("price");
				ware.total = rs.getInt("total");
				wareList.add(ware);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("WareSQL getWares error");
		}
		return wareList;
	}
/**
 * checks if there is a ware by id
 * @param ware_id
 * @return
 * @throws SQLException
 */
	public boolean isWareID(int ware_id) throws SQLException {
		try {
			String query = "select * from ware where ware_id=?;";
			PreparedStatement ps = DBase.getInstance().getConnection().prepareStatement(query);
			ps.setInt(1, ware_id);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs.next())
				return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("WareSQL isWare_id error");
		}

		return false;
	}
/**
 * checks if ware quantity enough to make a sell
 * @param ware_id
 * @param number
 * @return
 * @throws SQLException
 */
	public boolean isEnough(int ware_id, int number) throws SQLException {
		try {
			String query = "select total from ware where ware_id=?;";
			PreparedStatement ps = DBase.getInstance().getConnection().prepareStatement(query);
			ps.setInt(1, ware_id);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs.next())
			
				return (rs.getInt("total") > number);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("WareSQL isWare_id error");
		}

		return false;
	}
}
