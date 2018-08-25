import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.InputMismatchException;

public class Donation 
{
	static InputReader sc = new InputReader(System.in);
	public void makeadonation(User user, NGO ngo) throws ClassNotFoundException, SQLException {
		System.out.println("Select a type of donation");
		System.out.println("1) Money");
		System.out.println("2) Goods");
		System.out.println("3) Volunteer work");
		switch(sc.nextInt())
		{
		case 1:
			Money money = new Money();
			money.donate(user, ngo);
			break;
			
		case 2:
			Goods goods = new Goods();
			goods.donate(user, ngo);
			break;
			
		case 3:
			Volunteer volunteer = new Volunteer();
			volunteer.donate(user, ngo);
			break;
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
