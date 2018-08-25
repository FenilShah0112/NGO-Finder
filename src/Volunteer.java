import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.sql.*;

public class Volunteer 
{
	static InputReader sc = new InputReader(System.in);
	static Database db;
	static Connection conn;
	static Statement stmt;
	public void donate(User user, NGO ngo) throws ClassNotFoundException, SQLException 
	{
		conn = db.connection();
		stmt = conn.createStatement();
		String area, doj, timeperiod;
		System.out.println("Are you willing to work in a remote area? (Yes/No)");
		area = sc.nextLine();
		System.out.println("When can you start working? (DD/MM)");
		doj = sc.nextLine();
		System.out.println("How long are you willing to work for the NGO? (In months)");
		timeperiod = sc.nextLine();
		System.out.println("Waiting for response from the NGO");
		System.out.println("Your personal info will be shared with the NGO.\nYou will be notified about their response\n\n\n\t\t\t\tOn the NGO's page");
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
					System.out.println("\t\t\t\tWelcome "+ ngo.getName());
					System.out.println("\t\t\t\tA user has applied to work as a Volunter in your NGO. The details are as stated below");
					user.displayInfo();
					if(area.equals("Yes"))
						System.out.println("\t\t\t\t" + user.getName() + " is willing to work in remote areas. He/She can start working from "+ doj + " and is willing to work for " + timeperiod + " months.");
					else
						System.out.println("\t\t\t\t" + user.getName() + " is not willing to work in remote areas. He/She can start working from "+ doj + " and is willing to work for " + timeperiod + " months.");
					System.out.println("\t\t\t\tDo you accept this application? (Yes/No)");
					String reply = sc.nextLine();
					if(reply.equals("Yes"))
					{
						System.out.println("You have been approved by the NGO. You can start working from " + doj);
						query = "INSERT INTO `Donation`(`user`, `ngo`, `typeofdonation`, `donation`, `Time`) VALUES ('"+user.getEmailId()+"', '"+ngo.getEmailId()+"', 'Volunteer', '"+timeperiod+" months', CURRENT_TIMESTAMP)";
						stmt.executeUpdate(query);
					}
					else
						System.out.println("Sorry your application has been rejected as there are currently no need of any new volunteers by the NGO");
					return;
				}
				else
					System.out.println("Incorrect credentials. Please try again\n\t\t\t\tEmail");
			}
			else
				System.out.println("Incorrect credentials. Please try again\n\t\t\t\tEmail");
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
