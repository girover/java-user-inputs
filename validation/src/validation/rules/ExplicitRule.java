package validation.rules;

import validation.Str;

public class ExplicitRule extends Rule {

	public ExplicitRule(String rule, String fieldName, String fieldValue, String message) throws RuleException {
		super("explcit", rule, fieldName, fieldValue, message);
		setMatcher(parseMatcher(rule));
	}
	
	public ExplicitRule(String fieldName, String fieldValue, String rule) throws RuleException {
		this(rule, fieldName, fieldValue, null);
	}
	
	
	/**
	 * Here we generate Lambda function for a rule to check if the field value
	 * passes the given rule.
	 * @param rule
	 * @return Lambda Function to matches a value for given rule.
	 * @throws RuleException
	 */
	protected Matcher parseMatcher(String rule) throws RuleException {
		switch (rule) {
		case "required": {
			return string -> string != null;
		}
		case "notEmpty": {
			return string -> !Str.isEmpty(string);
		}
		case "alpha": {
			return string -> Str.isAlpha(string);
		}
		case "alphaNumeric": {
			return string -> Str.isAlphaNumeric(string);
		}
		case "alphaDash": {
			return string -> Str.isAlphaDash(string);
		}
		case "email": {
			return string -> Str.isEmail(string);
		}
		case "numeric": {
			return string -> Str.isNumeric(string);
		}
		case "date": {
			return string -> Str.isDate(string);
		}
		default:
			throw new RuleException("Could not generate rule: " + rule);
		}
	}
}
