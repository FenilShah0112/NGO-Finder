import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class Register {
	Database db = new Database();
	static Connection conn = null;
	static Statement stmt = null;
	
	static InputReader sc = new InputReader(System.in);
	
	public void registerNgo() throws ClassNotFoundException, SQLException 
	{
		conn = db.connection();
		stmt = conn.createStatement();
		String name, type, email, password, address, area, city, state; 
		long contact;
		System.out.println("Enter Name");
		name = sc.nextLine();
		System.out.println("Enter Type of NGO");
		type = sc.nextLine();
		System.out.println("Enter Email-ID");
		email = sc.nextLine();
		System.out.println("Enter password");
		password = sc.nextLine();
		System.out.println("Enter address");
		address = sc.nextLine();
		System.out.println("Enter area");
		area = sc.nextLine();
		System.out.println("Enter city");
		city = sc.nextLine();
		System.out.println("Enter state");
		state = sc.nextLine();
		System.out.println("Enter Contact number");
		contact = sc.nextLong();
		String query = "INSERT INTO `ngo` (`name`, `type`, `email`, `password`, `Address`, "
				+ "`Area`, `City`, `State`, `contact`, `Block`) VALUES ('"+name+"', '"+type+"', '"+email+"', "
						+ "'"+password+"', '"+address+"', '"+area+"', '"+city+"', '"+state+"', '"+contact+"', '0');";
		stmt.executeUpdate(query);
		System.out.println("\nAccount successfully created.\nPlease login to continue");
		stmt.close();
		conn.close();
	}

	public void registerUser() throws ClassNotFoundException, SQLException 
	{
		conn = db.connection();
		stmt = conn.createStatement();
		String name, email, password, address;
		System.out.println("Enter Name");
		name = sc.nextLine();
		System.out.println("Enter Email-ID");
		email = sc.nextLine();
		System.out.println("Enter password");
		password = sc.nextLine();
		System.out.println("Enter address");
		address = sc.nextLine();
		String query = "INSERT INTO `User` (`name`, `email`, `password`, `address`, `Block`) VALUES ('"+name+"', '"+email+"', '"+password+"', '"+address+"', '0');";
		stmt.executeUpdate(query);
		System.out.println("\nAccount successfully created.\nPlease login to continue");
		stmt.close();
		conn.close();
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
