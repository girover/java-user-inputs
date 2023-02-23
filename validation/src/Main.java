import validation.Validator;
import validation.rules.Rule;
import validation.rules.RuleException;

public class Main {
	private static String email = "";
	private static String name  = "name";
	private static String phone = "11111111";
	private static String cpr   = "623145665564";
	private static String userRole = "userr";
	private static String age = "5";
	private static String date = "12/11-2023";
	private static String tall = "12A";

	public static void main(String[] args) {

		try {
			Validator validator = new Validator();
			
			validator.addRules("cpr", cpr, "length_min:6|length_max:10|min:111111|max:555555");
//			validator.addRules("cpr", cpr, "digits:8|between:12345678,22222222|min:7|max:8");
			validator.addRules("userRole", userRole, "alpha|in:user,admin,student");
			validator.addRules("age", age, "numeric|gte:18|lte:50");
			validator.addRules("date", date, "date|format:dd/mm/yyyy");
			validator.addRules("email", email, "notEmpty|email");
			validator.addRules("tall", tall, "regex:[0-9A-Z]+");
			
			validator.addRuleMessage("date", "date", "date skal vaere en valid dato.");
			validator.addRuleMessage("email", "email", "Fuck you please provide a valid email address.");
			
			if(validator.pass())
				System.out.println("passed successfully");
			else
				for (String string : validator.getErrorMessages())
					System.out.println(string);
		} catch (RuleException e) {
			e.printStackTrace();
		}
	}

}
