
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * menu and user interface
 * 
 * @author User
 *
 */
public class Menu {
	/**
	 * show main menu
	 */
	Scanner scanner = new Scanner(System.in);

	private void show() {
		System.out.println();
		System.out.println("=== MENIU ===");
		System.out.println("1. Prideti nauja preke.");
		System.out.println("2. Perziureti prekes ir ju bendra kaina.");
		System.out.println("3. Uzregistruoti nauja pirkeja.");
		System.out.println("4. Perziureti pirkeju sarasa.");
		System.out.println("5. Parduoti preke pirkejui.");
		System.out.println("0. Iseiti.");
	}

	/**
	 * to get information from user what he wants
	 * 
	 * @return
	 */
	private int getMenuItem() {

		int item = -1;

		do {
			System.out.println("Please choose an option (0-5)");
			item = scanner.nextInt();

		} while (item < 0 || item > 5);
		System.out.println("Your choice is: " + item);
		return item;
	}

	/**
	 * to run appropriate method according to chosen menu item
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception {
		show();
		int menuItem = getMenuItem();
		switch (menuItem) {
		case 1:
			doAddWare();
			break;
		case 2:
			doShowWares();
			break;
		case 3:
			doAddUser();
			break;
		case 4:
			doShowUsers();
			break;
		case 5:
			doSell();
			break;
		case 0:
			break;
		}
		// todo 2.4.3.
	}

	/**
	 * to add a new ware
	 * 
	 * @throws Exception
	 */
	private void doAddWare() throws Exception {
		System.out.println("1. Prideti nauja preke.");
		WareSQL wareSQL = new WareSQL();
		Ware newWare = new Ware();
		System.out.println("Please input ware title");
		scanner.nextLine();
		newWare.title = scanner.nextLine();
		if (!wareSQL.isTitle(newWare.title)) {
			System.out.println("Please input ware price");
			newWare.price = Double.parseDouble(scanner.nextLine());
			System.out.println("Please input ware total");
			newWare.total = scanner.nextInt();
			newWare.ware_id = wareSQL.addWare(newWare);
			System.out.println("The new ware has been added:");
			System.out.println(newWare);
		} else
			System.out.println("The ware hasn't been added. A ware with this title is already exists");
	}

	/**
	 * to show all wares from DB
	 * 
	 * @throws Exception
	 */
	private void doShowWares() throws Exception {
		System.out.println("2. Perziureti prekes likucius.");
		WareSQL wareSQL = new WareSQL();
		ArrayList<Ware> wareList = wareSQL.getExistWares();
		if (wareList.size() == 0)
			System.out.println("List is empty");
		else {
			System.out.println("Nr\t\tPavadinimas\t\tKaina\t\tKiekis\t\tBendra Kaina");
			double totalSum = 0;
			for (Ware ware : wareList) {
				System.out.println(ware.toStringForArray());
				totalSum += (ware.price * ware.total);
			}
			System.out.println("Total price of all wares: " + totalSum);
		}
		// todo 4.3.2
	}

	/**
	 * to add a new user to DB
	 * 
	 * @throws Exception
	 */
	private void doAddUser() throws Exception {
		System.out.println("3. Uzregistruoti nauja pirkeja.");
		UserSQL userSQL = new UserSQL();
		User newUser = new User();
		scanner.nextLine();
		System.out.println("Please input user name");
		newUser.name = scanner.nextLine();
		System.out.println("Please input user email");
		newUser.email = scanner.nextLine();
		if (!userSQL.isEmail(newUser.email)) {

			System.out.println("Please input user phone");
			newUser.phone = scanner.nextLine();
			newUser.user_id = userSQL.addUser(newUser);
			System.out.println("The new user has been added:");
			System.out.println(newUser);
		} else
			System.out.println("The user hasn't been added. A user with this name is already exists");
	}

	/**
	 * to show all users from DB
	 * 
	 * @throws Exception
	 */
	private void doShowUsers() throws Exception {
		System.out.println("4. Perziureti pirkeju sarasa.");
		UserSQL userSQL = new UserSQL();
		ArrayList<User> userList = userSQL.getUsers();
		if (userList.size() == 0)
			System.out.println("List is empty");
		else {
			System.out.println("Nr\t\tVardas\t\tEl. paðtas\t\tTelefonas");
			double totalSum = 0;
			for (User user : userList) {
				System.out.println(user.toStringForArray());
			}
		}
	}

	/**
	 * make a sell
	 * 
	 * @throws SQLException
	 */
	private void doSell() throws SQLException {
		System.out.println("5. Parduoti preke pirkejui.");
		System.out.println("Write ware_id");
		int ware_id = scanner.nextInt();
		System.out.println("Write user_id");
		int user_id = scanner.nextInt();
		System.out.println("Write number");
		int number = scanner.nextInt();
		SellSQL sellSQL = new SellSQL();
		String sellResult = sellSQL.sell(ware_id, user_id, number);
		System.out.println(this.getErrorText(sellResult));
	}

	/**
	 * to output sell result
	 * 
	 * @param error
	 * @return
	 */
	private String getErrorText(String error) {
		switch (error) {
		case "ware not found":
			return "Preke nerasta";
		case "user not found":
			return "Pirkejas nerastas";
		case "not enough":
			return "Nepakankamas prekiu likutis";
		case "ok":
			return "Pardavimas sekmingas";
		default:
			return "Nepazistama klaida";
		}
	}
}
