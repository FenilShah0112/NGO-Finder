import java.util.*;
import java.io.*;
import java.sql.*;

public class Dashboard {
	private static boolean isUser = false;
	private static boolean isNgo = false;
	private static boolean isAdmin = false;
	private static boolean run = true;

	static Database db = new Database();
	static Connection conn = null;
	static Statement stmt = null;
	
	static boolean donation = false;
	
	static User user = new User();
	static Admin admin = new Admin();
	static NGO ngo = new NGO();
	
	static InputReader sc = new InputReader(System.in);
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		while(run) {
			if(isUser) {				
				System.out.println("---------------------------------------------------------");
				System.out.println("1)  Donate");
				System.out.println("2)  View Personal Details");
				System.out.println("3)  Edit Personal Details");
				System.out.println("4)  Log out");
				System.out.println("5)  Remove Account");
				System.out.println("6)  Search");
				
				switch(sc.nextInt()) {
					case 1:
						Donation donate = new Donation();
						donation = true;
						System.out.println("Search an NGO as per your preferences");
						search();
						donate.makeadonation(user, ngo);
						break;
						
					case 2:
						user.displayInfo();
						break;
						
					case 3:
						user.editInfo(user.getEmailId());
						break;
						
					case 4:
						user = null;
						isUser = false;
						break;
						
					case 5:
						user.removeAccount(user.getEmailId());
						break;
						
					case 6:
						search();
						break;
				}
			}
			else if(isNgo) {
				System.out.println("---------------------------------------------------------");
				System.out.println("\n1) Advertise");
				System.out.println("2) View Personal Details");
				System.out.println("3) Edit Personal Details");
				System.out.println("4) Log Out");
				System.out.println("5) Remove Account");
				
				switch(sc.nextInt()) 
				{
					case 1:
						System.out.println("\n1) Post ad");
						System.out.println("2) Edit ad");
						System.out.println("3) Delete ad");
						System.out.println("4) List ad");
						switch(sc.nextInt())
						{
						case 1:
							System.out.println("Enter your ad");
							ngo.advertise(sc.nextLine());
							break;
							
						case 2:
							ngo.editad();
							break;
							
						case 3:
							ngo.deletead();
							break;
							
						case 4:
							ngo.listad();
							break;
						}
						break;
						
					case 2:
						ngo.displayInfo();
						break;
						
					case 3:
						ngo.editInfo(ngo.getEmailId());
						break;
						
					case 4:
						ngo = null;
						isNgo = false;
						break;
						
					case 5:
						ngo.removeAccount(ngo.getEmailId());
						break;
				}
			}
			else if(isAdmin) {
				System.out.println("---------------------------------------------------------");
				System.out.println("\n1)  Block User");
				System.out.println("2)  Block NGO");
				System.out.println("3)  Unblock User");
				System.out.println("4)  Unblock NGO");
				System.out.println("5)  View Personal Details");
				System.out.println("6)  Edit Personal Details");
				System.out.println("7)  Log Out");
				
				switch(sc.nextInt()) {
					case 1:
						System.out.println("Enter the emailId of User");
						admin.blockUser(sc.nextLine());
						break;
						
					case 2:
						System.out.println("Enter the emailId of NGO");
						admin.blockNgo(sc.nextLine());
						break;
						
					case 3:
						System.out.println("Enter the emailId of User");
						admin.unblockUser(sc.nextLine());
						break;
						
					case 4:
						System.out.println("Enter the emailId of NGO");
						admin.unblockNgo(sc.nextLine());
						break;
					
					case 5:
						admin.displayInfo();
						break;
						
					case 6:
						admin.editInfo(admin.getEmailId());
						break;
						
					case 7:
						isAdmin = false;
						break;
				}
			}	
			else {
				System.out.println("***************************************************XXXXXXX"
								 + "***************************************************");
				System.out.println("1)  Login");
				System.out.println("2)  Registration");
				System.out.println("3)  Search");
				System.out.println("9)  Quit");
				switch(sc.nextInt()) {
					case 1:
						System.out.println("---------------------------------------------------------");
						System.out.println("1)  User");
						System.out.println("2)  NGO");
						System.out.println("3)  Admin");
						System.out.println("4)  Quit");
						int party = sc.nextInt();
						Login lg = new Login();
						String name;
						
						switch(party) 
						{
							case 1:
								user = lg.user_login();
								name = user.getName();
								if(name != null)
									isUser = true;
								break;
						
							case 2:
								ngo = lg.ngo_login();
								name = ngo.getName();
								if(name != null)
									isNgo = true;
								break;
							
							case 3:
								admin = lg.admin_login();
								name = admin.getName();
								if(name != null)
									isAdmin = true;
								break;
						}
						break;
						
					case 2:
						System.out.println("---------------------------------------------------------");
						Register register = new Register();
						System.out.println("\n1)  User");
						System.out.println("2)  NGO");
						System.out.println("3)  Quit");						
						switch(sc.nextInt()) {
							case 1:
								register.registerUser();
								break;
							
							case 2:
								register.registerNgo();
								break;
						}
						break;
					
					case 3:
						search();
						break;
					
					case 9:
						run = false;
						break;
				}
			}
		}
	}
	
	public static void search() throws ClassNotFoundException, SQLException {
		conn = db.connection();
		stmt = conn.createStatement();
		ResultSet rs = null, rs1 = null;
		String query = null, name, type, area, city, state, email, password, address;
		int i = 1;
		System.out.println("---------------------------------------------------------");
		System.out.println("\n1)  Search by Name");
		System.out.println("2)  Search by Area");
		System.out.println("3)  Search by City");
		System.out.println("4)  Search by State");
		System.out.println("5)  Search by Type");
		System.out.println("6)  Search by Name and Area");
		System.out.println("7)  Search by Name and City");
		System.out.println("8)  Search by Name and Type");
		System.out.println("9)  Search by Type and Area");
		System.out.println("10) Search by Type and City");
		System.out.println("11) Search by Type and State");
		System.out.println("12) Quit");
		
		switch(sc.nextInt()) {
		case 1:
			System.out.println("Enter the name of the NGO");
			name = sc.nextLine();
			query = "SELECT * FROM ngo WHERE name = '"+name+"' AND Block = '0'";
			rs = stmt.executeQuery(query);
			break;
			
		case 2:
			System.out.println("Enter the area of the NGO");
			area = sc.nextLine();
			query = "SELECT * FROM ngo WHERE Area = '"+area+"' AND Block = '0'";
			rs = stmt.executeQuery(query);
			break;
		
		case 3:
			System.out.println("Enter the city of the NGO");
			city = sc.nextLine();
			query = "SELECT * FROM ngo WHERE City = '"+city+"' AND Block = '0'";
			rs = stmt.executeQuery(query);
			break;
			
		case 4:
			System.out.println("Enter the state of the NGO");
			state = sc.nextLine();
			query = "SELECT * FROM ngo WHERE State = '"+state+"' AND Block = '0'";
			rs = stmt.executeQuery(query);
			break;
			
		case 5:
			System.out.println("Enter the type of NGO");
			type = sc.nextLine();
			query = "SELECT * FROM ngo WHERE type = '"+type+"' AND Block = '0'";
			rs = stmt.executeQuery(query);
			break;
			
		case 6:
			System.out.println("Enter the name of NGO");
			name = sc.nextLine();
			System.out.println("Enter the area of NGO");
			area = sc.nextLine();
			query = "SELECT * FROM ngo WHERE name = '"+name+"' AND type = '"+area+"' AND Block = '0'";
			rs = stmt.executeQuery(query);
			break;
			
		case 7:
			System.out.println("Enter the name of NGO");
			name = sc.nextLine();
			System.out.println("Enter the city of NGO");
			city = sc.nextLine();
			query = "SELECT * FROM ngo WHERE name = '"+name+"' AND City = '"+city+"' AND Block = '0'";
			rs = stmt.executeQuery(query);
			break;
			
		case 8:
			System.out.println("Enter the name of NGO");
			name = sc.nextLine();
			System.out.println("Enter the type of NGO");
			type = sc.nextLine();
			query = "SELECT * FROM ngo WHERE name = '"+name+"' AND type = '"+type+"' AND Block = '0'";
			rs = stmt.executeQuery(query);
			break;
			
		case 9:
			System.out.println("Enter the type of NGO");
			type = sc.nextLine();
			System.out.println("Enter the area of NGO");
			area = sc.nextLine();
			query = "SELECT * FROM ngo WHERE Area = '"+area+"' AND type = '"+type+"' AND Block = '0'";
			rs = stmt.executeQuery(query);
			break;
			
		case 10:
			System.out.println("Enter the type of NGO");
			type = sc.nextLine();
			System.out.println("Enter the city of NGO");
			city = sc.nextLine();
			query = "SELECT * FROM ngo WHERE Area = '"+city+"' AND type = '"+type+"' AND Block = '0'";
			rs = stmt.executeQuery(query);
			break;
			
		case 11:
			System.out.println("Enter the type of NGO");
			type = sc.nextLine();
			System.out.println("Enter the state of NGO");
			state = sc.nextLine();
			query = "SELECT * FROM ngo WHERE Area = '"+state+"' AND type = '"+type+"' AND Block = '0'";
			rs = stmt.executeQuery(query);
			break;
			
		case 12:
			return;
			
	}
		if(rs.next() == false)
		{
			System.out.println("No results");
			return;
			
		}
		else
		{	
			System.out.println("\n"+i+"\tName\t:" + rs.getString("name"));
			System.out.println("\tEmail-ID\t:" + rs.getString("email"));
			System.out.println("\tAddress\t:" + rs.getString("Address") +", " + rs.getString("Area") + ", " + rs.getString("City") + ", " + rs.getString("State"));
			System.out.println("\tContact\t:" + rs.getInt("contact"));
			System.out.println("\tType\t:" + rs.getString("type"));
			i++;
		}
		while(rs.next())
		{
			System.out.println("\n"+i+"\tName\t:" + rs.getString("name"));
			System.out.println("\tEmail-ID\t:" + rs.getString("email"));
			System.out.println("\tAddress\t:" + rs.getString("Address") +", " + rs.getString("Area") + ", " + rs.getString("City") + ", " + rs.getString("State"));
			System.out.println("\tContact\t:" + rs.getInt("contact"));
			System.out.println("\tType\t:" + rs.getString("type"));
			i++;
		}
		if(donation)
		{
			System.out.println("\nSelect an NGO");
			int select = sc.nextInt();
			rs1 = stmt.executeQuery(query);
			while(rs1.next())
			{
				if(select == 1)
				{
					System.out.println("Are your sure you want to donate to " + rs1.getString("name") + "? (Yes/No)");
					if(sc.nextLine().equals("Yes"))
					{	
						ngo.setName(rs1.getString("name"));
						ngo.setEmailId(rs1.getString("email"));
						ngo.setType(rs1.getString("type"));
						ngo.setPassword(rs1.getString("password"));
						ngo.setAddress(rs1.getString("Address"));
						ngo.setArea(rs1.getString("Area"));
						ngo.setCity(rs1.getString("City"));
						ngo.setState(rs1.getString("State"));
						ngo.setContact(rs1.getInt("contact"));
						return;
					}
					else
					{
						System.out.println("Select another NGO");
						search();
						return;
					}
				}
				select--;	
			}
		}
	}
	
	static class InputReader 
	{
	    	private final InputStream stream;
	    	private final byte[] buf = new byte[8192];
	    	private int curChar, snumChars;
	    	private SpaceCharFilter filter;
	
	    	public InputReader(InputStream stream) {
	    		this.stream = stream;
	    	}
	
	    	public int snext() {
	    		if (snumChars == -1)
	    			throw new InputMismatchException();
	    		if (curChar >= snumChars) {
	    			curChar = 0;
	    			try {
	    				snumChars = stream.read(buf);
	    			} catch (IOException e) {
	    				throw new InputMismatchException();
	    			}
	    			if (snumChars <= 0)
	    				return -1;
	    		}
	    		return buf[curChar++];
	    	}
	
	    	public int nextInt() {
	    		int c = snext();
	    		while (isSpaceChar(c)) {
	    			c = snext();
	    		}
	    		int sgn = 1;
	    		if (c == '-') {
	    			sgn = -1;
	    			c = snext();
	    		}
	    		int res = 0;
	    		do {
	    			if (c < '0' || c > '9')
	    				throw new InputMismatchException();
	    			res *= 10;
	    			res += c - '0';
	    			c = snext();
	    		} while (!isSpaceChar(c));
	    		return res * sgn;
	    	}
	
	    	public long nextLong() {
	    		int c = snext();
	    		while (isSpaceChar(c)) {
	    			c = snext();
	    		}
	    		int sgn = 1;
	    		if (c == '-') {
	    			sgn = -1;
	    			c = snext();
	    		}
	    		long res = 0;
	    		do {
	    			if (c < '0' || c > '9')
	    				throw new InputMismatchException();
	    			res *= 10;
	    			res += c - '0';
	    			c = snext();
	    		} while (!isSpaceChar(c));
	    		return res * sgn;
	    	}
	
	    	public int[] nIArr(int n) {
	    		int a[] = new int[n];
	    		for (int i = 0; i < n; i++) {
	    			a[i] = nextInt();
	    		}
	    		return a;
	    	}
	
	    	public long[] nLArr(int n) {
	    		long a[] = new long[n];
	    		for (int i = 0; i < n; i++) {
	    			a[i] = nextLong();
	    		}
	    		return a;
	    	}
	
	    	public String readString() {
	    		int c = snext();
	    		while (isSpaceChar(c)) {
	    			c = snext();
	    		}
	    		StringBuilder res = new StringBuilder();
	    		do {
	    			res.appendCodePoint(c);
	    			c = snext();
	    		} while (!isSpaceChar(c));
	    		return res.toString();
	    	}
	
	    	public String nextLine() {
	    		int c = snext();
	    		while (isSpaceChar(c))
	    			c = snext();
	    		StringBuilder res = new StringBuilder();
	    		do {
	    			res.appendCodePoint(c);
	    			c = snext();
	    		} while (!isEndOfLine(c));
	    		return res.toString();
	    	}
	
	    	public boolean isSpaceChar(int c) {
	    		if (filter != null)
	    			return filter.isSpaceChar(c);
	    		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	    	}
	
	    	private boolean isEndOfLine(int c) {
	    		return c == '\n' || c == '\r' || c == -1;
	    	}
	
	    	public interface SpaceCharFilter {
	    		public boolean isSpaceChar(int ch);
	    	}
	}
}