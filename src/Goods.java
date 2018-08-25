import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.sql.*;

public class Goods 
{
	static InputReader sc = new InputReader(System.in);
	static Database db;
	static Connection conn;
	static Statement stmt;
	public void donate(User user, NGO ngo) throws ClassNotFoundException, SQLException {
		conn = db.connection();
		stmt = conn.createStatement();
		System.out.println("What type of goods do you want to donate?");
		String goods = sc.nextLine();
		System.out.println("How do you plan to donate the goods? (S for Shipping/ P for Pickup)");
		if(sc.nextLine().equals("S"))
		{
			System.out.println("Okay! Here's the address of the NGO.\n"+ ngo.getName() + ";\n " + ngo.getAddress() + ";\n " + ngo.getArea() + ", " + ngo.getCity() + ", " + ngo.getState() + ".\nContact no: " + ngo.getContact());
			System.out.println("You will receive a confirmation after the goods are delivered to the NGO\n\t\t\t\tOn the side of NGO");
			System.out.println("\t\t\t\tPlease enter your login credentials to continue");
			System.out.println("\t\t\t\tEmail");
			while(true)
			{
				if(sc.nextLine().equals(ngo.getEmailId()))
				{
					System.out.println("\t\t\t\tPassword");
					String pass  = sc.nextLine();
					String query = "SELECT * FROM ngo WHERE email = '"+ngo.getEmailId()+"'";
					ResultSet rs = stmt.executeQuery(query);
					rs.next();
					if(pass.equals(rs.getString("password")))
					{
						while(true)
						{
							System.out.println("\t\t\t\t"+user.getEmailId() + " has shipped " + goods + " to your NGO. Please reply Yes if you have already received the donation.");
							if(sc.nextLine().equals("Yes"))
							{
								query = "INSERT INTO `Donation`(`user`, `ngo`, `typeofdonation`, `donation`, `Time`) VALUES ('"+user.getEmailId()+"', '"+ngo.getEmailId()+"', 'Goods', '"+goods+"', CURRENT_TIMESTAMP);";
								stmt.executeUpdate(query);
								System.out.println("Donation received");
								return;
							}
						}
					}
					else
						System.out.println("Incorrect credentials. Please try again\n\t\t\t\tEmail");
				}
				else
					System.out.println("Incorrect credentials. Please try again\n\t\t\t\tEmail");
			}
		}
		else
		{
			System.out.println("Your personal details will be shared with the NGO for pickup.\n\t\t\t\tOn the side of NGO");
			System.out.println("\t\t\t\tPlease enter your login credentials to continue");
			System.out.println("\t\t\t\tEmail");
			while(true)
			{
				if(sc.nextLine().equals(ngo.getEmailId()))
				{
					System.out.println("\t\t\t\tPassword");
					String pass  = sc.nextLine();
					String query = "SELECT * FROM ngo WHERE email = '"+ngo.getEmailId()+"'";
					ResultSet rs = stmt.executeQuery(query);
					rs.next();
					if(pass.equals(rs.getString("password")))
					{
						while(true)
						{
							System.out.println("\t\t\t\t"+user.getEmailId() + " wants you to pick up " + goods + " from his/her home. The details are shared below");
							user.displayInfo();
							System.out.println("Have you picked up the goods");
							if(sc.nextLine().equals("Yes"))
							{
								query = "INSERT INTO `Donation`(`user`, `ngo`, `typeofdonation`, `donation`, `Time`) VALUES ('"+user.getEmailId()+"', '"+ngo.getEmailId()+"', 'Goods', '"+goods+"', CURRENT_TIMESTAMP);";
								stmt.executeUpdate(query);
								System.out.println("Donation received");
								return;
							}
						}
					}
					else
						System.out.println("Incorrect credentials. Please try again\n\t\t\t\tEmail");
				}
				else
					System.out.println("Incorrect credentials. Please try again\n\t\t\t\tEmail");
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
