package validation.rules;

import java.util.HashMap;

public class Messages {

	private static HashMap<String, String> getExplicitRulesMessages(){
		
		HashMap<String, String> messages = new HashMap<>();
		
		// Explicit Rules
		messages.put("required", "The %s field is required.");
		messages.put("notEmpty", "The %s field can not be empty.");
		messages.put("email", "The %s must be a valid email address.");
		messages.put("alpha", "The %s must only contain letters.");
		messages.put("alphaDash", "The %s must only contain letters, numbers, dashes and underscores.");
		messages.put("alphaNumeric", "The %s must only contain letters and numbers.");
		messages.put("uppercase", "The %s must be uppercase.");
		messages.put("lowercase", "The %s must be lowercase.");
		messages.put("numeric", "The %s must be a number.");
		messages.put("boolean", "The %s field must be true or false.");
		messages.put("date", "The %s is not a valid date.");
		messages.put("same", "The %s and %s must match."); // email same:confirmation
		
		return messages;
	}

	private static HashMap<String, String> getParameterizedRulesMessages() {

		HashMap<String, String> messages = new HashMap<>();
		
		// Parameterized Rules
		messages.put("digits", "The %s must be %s digits."); // CPR digits:8
		messages.put("between", "The %s must be between %s and %s."); // email between:5,50
		messages.put("in", "The %s must be in %s."); // role in:admin,user
		messages.put("notIn", "The %s cannot be in %s."); // role in:admin,user
		messages.put("notIn", "The %s cannot be in %s."); // role in:admin,user
		messages.put("max", "The %s must not be greater than %s."); // age max:100
		messages.put("min", "The %s must be at least %s."); // age max:100
		messages.put("digits_max", "The %s must not have more than %s digits"); // age max:100
		messages.put("digits_min", "The %s must have at least %s digits."); // age max:100
		messages.put("length_max", "The length of %s must not be longer than %s."); // age max:100
		messages.put("length_min", "The length of %s must not be shorter than %s."); // age max:100
		messages.put("format", "The date format of %s must be of %s."); // age max:100
		messages.put("gt", "The %s must be greater than %s."); // age max:100
		messages.put("lt", "The %s must be less than %s."); // age max:100
		messages.put("gte", "The %s must be greater than or equal to %s."); // age max:100
		messages.put("lte", "The %s must be less than or equal to %s."); // age max:100
		
		return messages;
	}
	
	public static String getExplicitRuleMessage(String rule) {
		return getExplicitRulesMessages().get(rule);
	}
	
	public static String getParameterizedRuleMessage(String rule) {
		return getParameterizedRulesMessages().get(rule);
	}
}
