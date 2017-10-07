
/**
 * user class to create objects like user in MySQL
 * @author User
 *
 */
public class User 
{
	public int user_id;
	public String name;
	public String email;
	public String phone;
	
	public String toString() {
		String string = "user_id: " + user_id + " name: " + name + " email: " + email + " phone: " + phone;
		return string;

	}

	public String toStringForArray() {
		String string = "" + user_id + "\t\t" + name + "\t\t" + email + "\t\t" + phone;
		return string;

	}
}
