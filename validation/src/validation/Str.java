package validation;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public abstract class Str {

	/**
	 * Determines if a string is not empty
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		return string == null || string.isBlank() ? true : false;
	}
	
	public static boolean isInteger(String string) {
		try {
			int a = Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Determines if a string is Double.
	 * @param string
	 * @return
	 */
	public static boolean isDouble(String string) {
		try {
			double a = Double.parseDouble(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Determines if a string is a valid email address.
	 * @param string
	 * @return
	 */
	public static boolean isEmail(String string) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);

		if (pat.matcher(string).matches())
			return true;

		return false;
	}

	/**
	 * Determines if a string only contains letters.
	 * @param string
	 * @return
	 */
	public static boolean isAlpha(String string) {
		String alphaRegex = "^[\\pL\\pM]+$";

		Pattern pat = Pattern.compile(alphaRegex);

		if (pat.matcher(string).matches())
			return true;

		return false;
	}

	/**
	 * Determine if a string only contains letters and numbers.
	 * @param string
	 * @return
	 */
	public static boolean isAlphaNumeric(String string) {
		String alphaNumericRegex = "^[\\pL\\pM\\pN]+$";

		Pattern pat = Pattern.compile(alphaNumericRegex);

		if (pat.matcher(string).matches())
			return true;

		return false;
	}

	/**
	 * determine if a string consists of letters, numbers, dashes or underscores
	 * @param string
	 * @return
	 */
	public static boolean isAlphaDash(String string) {
		String alphaDashRegex = "^[\\pL\\pM\\pN_-]+$";

		Pattern pat = Pattern.compile(alphaDashRegex);

		if (pat.matcher(string).matches())
			return true;

		return false;
	}

	/**
	 * Determine if a string is a number.
	 * @param string
	 * @return
	 */
	public static boolean isNumeric(String string) {
		String alphaDashRegex = "^[\\pN]+$";

		Pattern pat = Pattern.compile(alphaDashRegex);

		if (pat.matcher(string).matches())
			return true;

		return false;
	}
	
	/**
	 * Determine if a string is a boolean.
	 * @param string
	 * @return
	 */
	public static boolean isBoolean(String string) {
		return string == "true" || string == "false" ? true : false;
	}
	
	/**
	 * Determine if a string is a boolean.
	 * @param string
	 * @return
	 */
	public static boolean isDate(String string) {
		String dateFormater = "dd/MM/yyyy";
		return isDate(string, dateFormater);
	}
	
	public static boolean isDate(String date, String format) {
		String dateFormater = format;
		DateFormat sdf = new SimpleDateFormat(dateFormater);
		sdf.setLenient(false);
		try {
			sdf.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}
