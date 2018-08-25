import java.util.*;
import java.io.*;
import java.sql.*;

public class User 
{
	static InputReader sc = new InputReader(System.in);
	static Database db = new Database();
	static Connection conn = null;
	static Statement stmt = null;
	private String name;
	private String emailId;
	private String password;
	private String address;

	public void editInfo(String email) throws SQLException, ClassNotFoundException {
		conn = db.connection();
		stmt = conn.createStatement();
		String query;
		System.out.println("\n1) Name");
		System.out.println("2) Password");
		System.out.println("3) Address");
		System.out.println("4) Quit");
		switch(sc.nextInt()) {
			case 1:
				System.out.println("Enter new name");
				setName(sc.nextLine());
				query = "UPDATE `user` SET `name` = '"+this.name+"' WHERE email = '"+email+"'";
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
					query = "UPDATE `user` SET `password` = '"+this.password+"' WHERE email = '"+email+"'";
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
				query = "UPDATE `user` SET `address` = '"+this.address+"' WHERE email = '"+email+"'";
				stmt.executeUpdate(query);
				System.out.println("Address changed successfully!!");
				break;
				
			case 4:
				return;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getAddress() {
		return address;
	}
	
	public void displayInfo() {
		System.out.println("\n\t\t\t\tName\t:" + name);
		System.out.println("\t\t\t\tEmail-ID\t:" + emailId);
		System.out.println("\t\t\t\tAddress\t:" + address);
	}
	
	public void removeAccount(String email) throws ClassNotFoundException, SQLException 
	{
		conn = db.connection();
		stmt = conn.createStatement();
		String query = "DELETE FROM user WHERE email = '" + email + "'";
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



