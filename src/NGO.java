import java.util.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NGO {
	
	static InputReader sc = new InputReader(System.in);
	Database db = new Database();
	static Connection conn = null;
	static Statement stmt = null;
	static boolean flag = false;
	
	
	private String name;
	private String type;
	private String emailId;
	private String password;
	private String address;
	private String area;
	private String city;
	private String state;
	private long contact;
	
	public void editInfo(String email) throws ClassNotFoundException, SQLException {
		conn = db.connection();
		stmt = conn.createStatement();
		String query;
		System.out.println("\n1)  Name");
		System.out.println("2)  Password");
		System.out.println("3)  Address");
		System.out.println("4)  Area");
		System.out.println("5)  City");
		System.out.println("6)  State");
		System.out.println("7)  Contact");
		System.out.println("8)  Type");
		System.out.println("9)  Quit");
		
		switch(sc.nextInt()) {
			case 1:
				System.out.println("Enter new name");
				setName(sc.nextLine());
				query = "UPDATE `ngo` SET `name` = '"+this.name+"' WHERE email = '"+email+"'";
				stmt.executeUpdate(query);
				System.out.println("Name changed successfully!!");
				break;
				
			case 2:
				String temp1, temp2;
				System.out.println("Enter new password");
				temp1 = sc.nextLine();
				System.out.println("Re-Enter your password");
				temp2 = sc.nextLine();
				if(temp1.equals(temp2))
				{
					setPassword(temp1);
					query = "UPDATE `ngo` SET `password` = '"+this.password+"' WHERE email = '"+email+"'";
					stmt.executeUpdate(query);
					System.out.println("Password changed successfully!!");
				}
				else
				{
					System.out.println("Passwords do not match");
				}
				break;
				
			case 3:
				System.out.println("Enter new address");
				setAddress(sc.nextLine());
				query = "UPDATE `ngo` SET `Address` = '"+this.address+"' WHERE email = '"+email+"'";
				stmt.executeUpdate(query);
				System.out.println("Address changed successfully!!");
				break;
				
			case 4:
				System.out.println("Enter new area");
				setArea(sc.nextLine());
				query = "UPDATE `ngo` SET `Area` = '"+this.area+"' WHERE email = '"+email+"'";
				stmt.executeUpdate(query);
				System.out.println("Area changed successfully!!");
				break;
				
			case 5:
				System.out.println("Enter new city");
				setCity(sc.nextLine());
				query = "UPDATE `ngo` SET `City` = '"+this.city+"' WHERE email = '"+email+"'";
				stmt.executeUpdate(query);
				System.out.println("City changed successfully!!");
				break;
				
			case 6:
				System.out.println("Enter new state");
				setState(sc.nextLine());
				query = "UPDATE `ngo` SET `State` = '"+this.state+"' WHERE email = '"+email+"'";
				stmt.executeUpdate(query);
				System.out.println("State changed successfully!!");
				break;
				
			case 7:
				System.out.println("Enter new contact");
				setContact(sc.nextLong());
				query = "UPDATE `ngo` SET `contact` = '"+this.contact+"' WHERE email = '"+email+"'";
				stmt.executeUpdate(query);
				System.out.println("Contact changed successfully!!");
				break;
				
			case 8:
				System.out.println("Enter new type");
				setType(sc.nextLine());
				query = "UPDATE `ngo` SET `type` = '"+this.type+"' WHERE email = '"+email+"'";
				stmt.executeUpdate(query);
				System.out.println("Type changed successfully!!");
				break;
				
			case 9:
				return;
		}
	}
	
	public void advertise(String advertise) throws ClassNotFoundException, SQLException {
		Admin admin = new Admin();
		System.out.println("Admin verification required");
		String adminid = admin.verify(advertise);
		if(adminid != null)
		{
			conn = db.connection();
			stmt = conn.createStatement();
			String query = "INSERT INTO `Advertisement` (`NGOID`, `AdminID`, `Advertisement`, `Date`) VALUES ('"+this.emailId+"', '"+adminid+"', '"+advertise+"', CURRENT_TIMESTAMP);";
			stmt.executeUpdate(query);
			System.out.println("\nAd posted Successfully!");
		}
	}
	
	public void editad() throws SQLException, ClassNotFoundException {
		conn = db.connection();
		stmt = conn.createStatement();
		listad();
		if(flag)
			return;
		else
		{
			String ngo,adminId,ad;
			String date, date1 = null;	
			ResultSet rs;
			System.out.println("Select the ad you want to edit");
			int edit = sc.nextInt();
			System.out.println("Write your updated ad");
			String advertise = sc.nextLine();
			Admin admin = new Admin();
			System.out.println("Admin verification required");
			String adminid = admin.verify(advertise);
			if(adminid != null)
			{
				String query = "SELECT * FROM Advertisement WHERE NGOID = '"+this.emailId+"'";
				rs = stmt.executeQuery(query);
				while(rs.next())
				{
					date1 = rs.getString("Date");
					if(edit == 1)
						break;
					else
						edit--;
				}
				String update = "UPDATE `Advertisement` SET `Advertisement`= '"+advertise+"' WHERE Date= '"+date1+"' AND NGOID = '"+this.emailId+"'";
				stmt.executeUpdate(update);
				System.out.println("\nAd updated Successfully!");
			}
		}
		return;
	}

	public void deletead() throws SQLException, ClassNotFoundException {
		conn = db.connection();
		stmt = conn.createStatement();
		listad();
		if(flag)
			return;
		else
		{
			String query = "SELECT * FROM Advertisement WHERE NGOID = '"+this.emailId+"'";
			String ngo,adminId,ad = null;
			String date = null;	
			System.out.println("Select the ad you want to delete");
			int edit = sc.nextInt();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				ad = rs.getString("Advertisement");
				date = rs.getString("Date");
				if(edit == 1)
					break;
				else
					edit--;
			}
			System.out.println("Are you sure you want to delete this ad? (Yes/No)");
			System.out.println(ad);
			if(sc.nextLine().equals("Yes"))
			{
				String delete = "DELETE FROM `Advertisement` WHERE Advertisement = '"+ad+"' AND Date = '"+date+"' AND NGOID = '"+this.emailId+"'";
				stmt.executeUpdate(delete);
				System.out.println("Ad successfully deleted");
			}
		}
	}
	
	public void listad() throws ClassNotFoundException, SQLException {
		conn = db.connection();
		stmt = conn.createStatement();
		String query = "SELECT * FROM Advertisement WHERE NGOID = '"+this.emailId+"'";
		String ngo,adminId,ad = null;
		String date = null;	
		ResultSet rs = stmt.executeQuery(query);
		int i = 1;
		if(rs.next() == false)
		{
			flag = true;
			System.out.println("You have not posted any ad!!!");
			return;
		}
		else
		{
			ngo = rs.getString("NGOID");
			adminId = rs.getString("AdminID");
			ad = rs.getString("Advertisement");
			date = rs.getString("Date");
			System.out.println("Ads you have posted\n");
			System.out.println(i + ") Advertisement: " + ad );
			i++;
		}
		while(rs.next())
		{
			ngo = rs.getString("NGOID");
			adminId = rs.getString("AdminID");
			ad = rs.getString("Advertisement");
			date = rs.getString("Date");
			System.out.println(i + ") Advertisement: " + ad );
			i++;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getEmailId() {
		return emailId;
	}
	
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public long getContact() {
		return contact;
	}
	
	public void setContact(long contact) {
		this.contact = contact;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void displayInfo() {
		System.out.println("\nName\t:" + name);
		System.out.println("Email-ID\t:" + emailId);
		System.out.println("Address\t:" + address +", " + area + ", " + city + ", " + state);
		System.out.println("Contact\t:" + contact);
		System.out.println("Type\t:" + type);
	}
	
	public void removeAccount(String email) throws ClassNotFoundException, SQLException 
	{
		conn = db.connection();
		stmt = conn.createStatement();
		String query = "DELETE FROM ngo WHERE email = '" + email + "'";
		stmt.executeUpdate(query);
		System.out.println("\nAccount successfully deleted!");
		return;
	}
	
	static class InputReader {
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

