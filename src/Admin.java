import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class Admin 
{
	static InputReader sc = new InputReader(System.in);
	static Database db = new Database();
	static Connection conn = null;
	static Statement stmt = null;
	
	private String name;
	private String emailId;
	private String password;
	private String address;
	private long contact;

	public int editInfo(String email) throws ClassNotFoundException, SQLException {
		conn = db.connection();
		stmt = conn.createStatement();
		String query;
		System.out.println("\n1) Name");
		System.out.println("2) Password");
		System.out.println("3) Address");
		System.out.println("4) Contact");
		System.out.println("5) Quit");
		
		while(true) {
			switch(sc.nextInt()) {
				case 1:
					System.out.println("Enter new name");
					setName(sc.nextLine());
					query = "UPDATE `admin` SET `name` = '"+this.name+"' WHERE email = '"+email+"'";
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
						query = "UPDATE `admin` SET `password` = '"+this.password+"' WHERE email = '"+email+"'";
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
					setName(sc.nextLine());
					query = "UPDATE `admin` SET `address` = '"+this.address+"' WHERE email = '"+email+"'";
					stmt.executeUpdate(query);
					setAddress(sc.nextLine());
					System.out.println("Address changed successfully!!");
					break;
					
				case 4:
					System.out.println("Enter new contact");
					setName(sc.nextLine());
					query = "UPDATE `admin` SET `contact` = '"+this.contact+"' WHERE email = '"+email+"'";
					stmt.executeUpdate(query);
					setContact(sc.nextLong());
					System.out.println("Contact changed successfully!!");
					break;
					
				case 5:
					return 0;
			}
		}
	}
	
	public String verify(String advertise) throws ClassNotFoundException, SQLException 
	{
		Login lg = new Login();
		Admin admin = new Admin();
		admin = lg.admin_login();
		String email = admin.getEmailId();
		String temp = null;
		if(email != null)
		{
			System.out.println("Post this ad?? (Yes/No)");
			if(sc.nextLine().equals("Yes"))
				return email;
			else
			{
				System.out.println("\nAdmin rejected your advertisement");
			}
		}
		else
			temp = verify(advertise);
		return temp;
		
	}

	public void displayInfo() {
		System.out.println("\nName\t:" + name);
		System.out.println("Email-ID\t:" + emailId);
		System.out.println("Address\t:" + address);
		System.out.println("Contact\t:" + contact);
	}
	
	public void blockNgo(String emailId) throws ClassNotFoundException, SQLException 
	{
		conn = db.connection();
		stmt = conn.createStatement();
		String query = "UPDATE `ngo` SET `Block` = 1 WHERE email = '"+emailId+"'";
		stmt.executeUpdate(query);
		System.out.println("\nNGO account successfully blocked");
	}
	
	public void blockUser(String emailId) throws SQLException, ClassNotFoundException 
	{
		conn = db.connection();
		stmt = conn.createStatement();
		String query = "UPDATE `user` SET `Block` = 1 WHERE email = '"+emailId+"'";
		stmt.executeUpdate(query);
		System.out.println("\nUser account successfully blocked");
	}
	
	public void unblockNgo(String emailId) throws ClassNotFoundException, SQLException 
	{
		conn = db.connection();
		stmt = conn.createStatement();
		String query = "UPDATE `ngo` SET `Block` = 0 WHERE email = '"+emailId+"'";
		stmt.executeUpdate(query);
		System.out.println("\nNGO account successfully unblocked");
	}
	
	public void unblockUser(String emailId) throws SQLException, ClassNotFoundException 
	{
		conn = db.connection();
		stmt = conn.createStatement();
		String query = "UPDATE `user` SET `Block` = 0 WHERE email = '"+emailId+"'";
		stmt.executeUpdate(query);
		System.out.println("\nUser account successfully unblocked");
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	public void setPassword(String password) {
		this.password = password;
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
