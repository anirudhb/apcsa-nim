import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/// Manages input, and provides safe abstractions that perform validation
public class Input {
	private Scanner sc;
	private PrintStream out;

	public Input(InputStream in, PrintStream out) {
		sc = new Scanner(in);
		this.out = out;
	}

	/** Returns -1 if the empty string was provided */
	public int getNumberRangeOrEmpty(String question, int min, int max) {
		while (true) {
			out.print(question);
			String line = sc.nextLine();
			if (line.isBlank())
				return -1;
			try {
				int i = Integer.parseInt(line.strip(), 10);
				if (i < min || i > max)
					continue;
				return i;
			} catch (NumberFormatException e) {
			}
		}
	}

	public int getNumber(String question) {
		while (true) {
			out.print(question);
			String line = sc.nextLine();
			try {
				int i = Integer.parseInt(line.strip(), 10);
				return i;
			} catch (NumberFormatException e) {
			}
		}
	}

	/// Returns true if the user types "y", false otherwise
	public boolean getBoolean(String question) {
		out.println(question + " (y for yes)");
		return sc.nextLine().strip().toLowerCase().equals("y");
	}

	public void close() {
		sc.close();
	}
}
