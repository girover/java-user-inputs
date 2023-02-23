package validation.rules;

import java.util.ArrayList;
import java.util.Arrays;

import validation.Str;

public abstract class Rule {

	protected String type;       // {explicit, parameterized}
	protected String rule;       // required
	protected String fieldName;  // title
	protected String fieldValue; // title-1
	protected String message;    // field is required
	protected boolean passed = false;  // check if rule passed
	
	protected Matcher matcher;
	
	protected static String ruleSeparator = "\\|"; 
	protected static String ruleParamsSeparator = ":"; 
	protected static String paramsSeparator = ","; 
	
	private static ArrayList<String> validExplicitRules = new ArrayList<>(Arrays.asList(
			"required",
			"notEmpty",
			"alpha",
			"alphaNumeric",
			"alphaDash",
			"numeric",
			"email",
			"date"
	));
	
	private static ArrayList<String> validParameterizedRules = new ArrayList<>(Arrays.asList(
			"digits",
			"between",
			"in",
			"notIn",
			"size",
			"max",
			"min",
			"digits_max",
			"digits_min",
			"length_max",
			"length_min",
			"gt",
			"gte",
			"lt",
			"lte",
			"mime",
			"format",
			"regex"
	));
	
	protected static ArrayList<String> rulesOfOneParameter = new ArrayList<>(Arrays.asList(
			"digits",
			"max",
			"min",
			"digits_max",
			"digits_min",
			"length_max",
			"length_min",
			"gt",
			"gte",
			"lt",
			"lte",
			"format",
			"regex"
	));
	
	protected static ArrayList<String> rulesOfTwoParameters = new ArrayList<>(Arrays.asList(
			"between"
	));
	
	protected static ArrayList<String> rulesOfMoreThanTwoParameters = new ArrayList<>(Arrays.asList(
			"in",
			"notIn",
			"mime"
	));
	
	public Rule(String fieldName, String fieldValue, String rule, String type) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.rule = rule;
		this.type = type;
	}
	
	public static ArrayList<String> getValidExplicitRules() {
		return validExplicitRules;
	}
	
	public static ArrayList<String> getValidParameterizedRules() {
		return validParameterizedRules;
	}
	
	public static ArrayList<String> getRulesOfOneParameter() {
		return rulesOfOneParameter;
	}
	
	public static ArrayList<String> getRulesOfTwoParameters() {
		return rulesOfTwoParameters;
	}
	
	public static ArrayList<String> getRulesOfMoreThanTwoParameters() {
		return rulesOfMoreThanTwoParameters;
	}
	
	/**
	 * All rule classes must implement this method
	 * to check if the provided value passes the given rule.
	 * This is because the rule implementation varies from through rules.
	 */
//	public abstract void matches() throws RuleException;
	

	/**
	 * Set a matcher for this rule
	 * This matcher is responsible for matching the value for a given rule
	 * @param matcher
	 */
	public void setMatcher(Matcher matcher) {
		this.matcher = matcher;
	}
	
	public Matcher getMatcher() {
		return matcher;
	}
	
	public boolean pass() throws RuleException {
		passed = matcher.matches(getFieldValue());
		return isPassed();
	}
	
	public boolean isPassed() {
		return passed;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String FieldValue) {
		this.fieldValue = FieldValue;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean valid() {
		return false;
	}
	
	public boolean isExplicit() {
		return getType().equals("explicit") ? true : false;
	}
	
	public boolean isParameterized() {
		return !isExplicit();
	}
	
	protected int convertParameterToInteger(String param) throws RuleException {
		try {
			int p = Integer.parseInt(param);
			return p;
		} catch (Exception e) {
			throw new RuleException(param +" cannot be converted to integer.");
		}
	}
	
	protected double convertParameterToDouble(String param) throws RuleException {
		try {
			double p = Double.parseDouble(param);
			return p;
		} catch (Exception e) {
			throw new RuleException(param +" cannot be converted to double.");
		}
	}
	
	protected boolean isIntegerValue() {
		return Str.isInteger(getFieldValue());
	}
	
	protected boolean isDoubleValue() {
		return Str.isDouble(getFieldValue());
	}
}
