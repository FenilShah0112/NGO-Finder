import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.sql.*;


public class Money 
{
	static InputReader sc = new InputReader(System.in);
	static Database db;
	static Connection conn;
	static Statement stmt;

	public void donate(User user, NGO ngo) throws ClassNotFoundException, SQLException {
		conn = db.connection();
		stmt = conn.createStatement();
		System.out.println("Please enter the amount you want to donate");
		int amount = sc.nextInt();
		boolean success;
		System.out.println("Please select a mode of payment");
		System.out.println("1) Credit/Debit card");
		System.out.println("2) Internet Banking");
		System.out.println("3) Cash");
		switch(sc.nextInt())
		{
		case 1:
			long card;
			int cvv;
			String name, date;
			System.out.println("Card number");
			card = sc.nextLong();
			System.out.println("CVV");
			cvv = sc.nextInt();
			System.out.println("Name on the card");
			name = sc.nextLine();
			System.out.println("Expiry Date");
			date = sc.nextLine();
			success = verify();
			if(success)
			{
				System.out.println("Payment has been processed");
				String query = "INSERT INTO `Donation`(`user`, `ngo`, `typeofdonation`, `donation`, `Time`) VALUES ('"+user.getEmailId()+"', '"+ngo.getEmailId()+"', 'Monetary', '"+amount+"', CURRENT_TIMESTAMP)";
				stmt.executeUpdate(query);
				return;
			}
			else
				System.out.println("Problem occured during processing your request. Please try again later");
			return;
			
		case 2:
			long account;
			String ifsc, password;
			System.out.println("Please enter your account number");
			account = sc.nextLong();
			System.out.println("Please enter your IFSC code");
			ifsc = sc.nextLine();
			System.out.println("Please enter your password");
			password = sc.nextLine();
			success = verify();
			if(success)
			{
				System.out.println("Payment has been processed");
				String query = "INSERT INTO `Donation`(`user`, `ngo`, `typeofdonation`, `donation`, `Time`) VALUES ('"+user.getEmailId()+"', '" 
										+ ngo.getEmailId()+"', 'Monetary', '"+amount+"', CURRENT_TIMESTAMP)";
				stmt.executeUpdate(query);
				return;
			}
			else
				System.out.println("Problem occured during processing your request. Please try again later");
			return;
			
		case 3:
			System.out.println("Please visit the NGO office to make a donation.\nThe office is located at: \n"
						+ ngo.getName() + ", " + ngo.getAddress() + ", " + ngo.getArea() + ", " + ngo.getCity() + ", " + ngo.getState());
		}
	}
	private boolean verify() {
		Random r = new Random();
		int Low = 0;
		int High = 3;
		int Result = r.nextInt(High-Low) + Low;
		if(Result != 0)
			return true;
		return false;
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
