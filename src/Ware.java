
/**
 * ware class to create objects like ware in MySQL
 * 
 * @author User
 *
 */
public class Ware {
	public int ware_id;
	public String title;
	public double price;
	public int total;

	public String toString() {
		String string = "ware_id: " + ware_id + " title: " + title + " price: " + price + " total: " + total;
		return string;

	}

	public String toStringForArray() {
		String string = "" + ware_id + "\t\t" + title + "\t\t" + price + "\t\t" + total + "\t\t" + price * total;
		return string;

	}
}
