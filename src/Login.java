import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class Login
{	
	Database db = new Database();
	static Connection conn = null;
	static Statement stmt = null;
	
	static InputReader sc = new InputReader(System.in);
	
	static String email, pass;

	public User user_login() throws SQLException, ClassNotFoundException 
	{
		conn = db.connection();
		stmt = conn.createStatement();
		User user1 = new User();
		System.out.println("Enter Email-ID");
		email = sc.nextLine();
		System.out.println("Enter password");
		pass = sc.nextLine();
		String query = "SELECT * FROM user WHERE email = '"+email+"'";
		ResultSet rs = stmt.executeQuery(query);
	
		while(rs.next())
		{
			String name = rs.getString("name");
			String password = rs.getString("password");
			String address = rs.getString("address");
			int block = rs.getInt("Block");
			
			if(pass.equals(password) && block == 0)
			{
				System.out.println("\nLogin Successful!!  Welcome " + name);
				user1.setName(name);
				user1.setEmailId(email);
				user1.setAddress(address);
				user1.setPassword(password);
			}
			else if(block == 1)
			{
				System.out.println("Your account has been blocked. Please contact the Admin");
			}
			else
			{
				System.out.println("\nIncorrect credentials");
			}
		}
		stmt.close();
		conn.close();
		return user1;
	}

	public  NGO ngo_login() throws SQLException, ClassNotFoundException
	{
		conn = db.connection();
		stmt = conn.createStatement();
		NGO ngo = new NGO();
		System.out.println("Enter Email-ID");
		email = sc.nextLine();
		System.out.println("Enter password");
		pass = sc.nextLine();
		String query = "SELECT * FROM ngo WHERE email = '"+ email +"'";
		ResultSet rs = stmt.executeQuery(query);
	
		while(rs.next())
		{
			String name = rs.getString("name");
			String password = rs.getString("password");
			String type = rs.getString("type");
			String address = rs.getString("Address");
			String area = rs.getString("Area");
			String city = rs.getString("City");
			String state = rs.getString("State");
			long contact = rs.getInt("contact");
			int block = rs.getInt("Block");
			
			if(pass.equals(password) && block == 0)
			{
				System.out.println("\nLogin Successful!!  Welcome " + name);
				ngo.setName(name);
				ngo.setType(type);
				ngo.setEmailId(email);
				ngo.setAddress(address);
				ngo.setArea(area);
				ngo.setCity(city);
				ngo.setState(state);
				ngo.setContact(contact);
			}
			else if(block == 1)
			{
				System.out.println("Your account has been blocked. Please contact the Admin");
			}
			else
			{
				System.out.println("\nIncorrect credentials");
			}
		}
		stmt.close();
		conn.close();
		return ngo;
	}
	
	public Admin admin_login() throws SQLException, ClassNotFoundException
	{
		conn = db.connection();
		stmt = conn.createStatement();
		Admin admin = new Admin();
		System.out.println("Enter Email-ID");
		email = sc.nextLine();
		System.out.println("Enter password");
		pass = sc.nextLine();
		String query = "SELECT * FROM admin WHERE email = '"+ email +"'";
		ResultSet rs = stmt.executeQuery(query);
	
		while(rs.next())
		{
			String name = rs.getString("name");
			String password = rs.getString("password");
			String address = rs.getString("address");
			long contact = rs.getInt("contact");
			
			if(pass.equals(password))
			{
				System.out.println("\nLogin Successful!!  Welcome " + name);
				admin.setName(name);
				admin.setEmailId(email);
				admin.setPassword(password);
				admin.setAddress(address);
				admin.setContact(contact);
			}
			else
				System.out.println("\nIncorrect credentials");
		}
		stmt.close();
		conn.close();
		return admin;
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
