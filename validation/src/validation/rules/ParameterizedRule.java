package validation.rules;

import java.util.ArrayList;

import validation.Str;

public class ParameterizedRule extends Rule {

	/**
	 * Parameterized rules are rules that have parameters like:
	 * max:40 min:30       ---> max is a rule. 40 is a parameter.
	 * digits:10           ---> digits is a rule. 10 is a parameter.
	 * in:admin,student    ---> in is a rule. admin and student are parameters.
	 */
	protected ArrayList<String> parameters = new ArrayList<>();
	
	public ParameterizedRule(String rule, String fieldName, String fieldValue, String message) throws RuleException {
		
		super("parameterized", rule, fieldName, fieldValue, message);
		// We split rule to the rule and parameters
		parseRuleAndParameters(rule);
		
		setMatcher(parseMatcher(getRule()));
	}

	public ParameterizedRule(String fieldName, String fieldValue, String rule) throws RuleException {
		this(rule, fieldName, fieldValue, null);
	}
	
	/**
	 * Separate rule name from its parameters.
	 * Here we get two strings.
	 * Then we need to convert parameters string to an ArrayList
	 * of parameters.
	 * 
	 * @param rule String
	 * @throws RuleException
	 */
	private void parseRuleAndParameters(String rule) throws RuleException {
		try {
			String[] ruleAndParams = rule.split(ruleParamsSeparator);
			this.setRule(ruleAndParams[0]);
			this.setParameters(parseParameters(ruleAndParams[1]));
			
		} catch (Exception e) {
			throw new RuleException("Rule " + rule + " is not valid");
		}
	}

	public ArrayList<String> getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList<String> parameters) {
		this.parameters = parameters;
	}
	
	public void addParameter(String param) {
		parameters.add(param);
	}
	
	public void addParameters(ArrayList<String> params) {
		parameters.addAll(params);
	}
	
	/**
	 * Make the parameters part as an arrayList
	 * @param parameters
	 * @return
	 */
	private ArrayList<String> parseParameters(String parameters) {
		
		ArrayList<String> params = new ArrayList<>();
		
		for (String string : parameters.split(paramsSeparator)) {
			params.add(string);
		}
		return params;
	}
	
	
	/**
	 * Here we generate Lambda function for a rule to match a field value.
	 * @param rule
	 * @return Lambda Function to matches a value for given rule.
	 * @throws RuleException
	 */
	protected Matcher parseMatcher(String rule) throws RuleException {
		switch (rule) {
		case "digits": {
			return matchDigits();
		}
		case "between": {
			return matchBetween();
		}
		case "max": {
			return matchMax();
		}
		case "min": {
			return matchMin();
		}
		case "digits_max": {
			return matchDigitsMax();
		}
		case "digits_min": {
			return matchDigitsMin();
		}
		case "length_max": {
			return matchDigitsMax();
		}
		case "length_min": {
			return matchDigitsMin();
		}
		case "in": {
			return matchIn();
		}
		case "notIn": {
			return matchNotIn();
		}
		case "gt": {
			return matchGT();
		}
		case "gte": {
			return matchGTE();
		}
		case "lt": {
			return matchLT();
		}
		case "lte": {
			return matchLTE();
		}
		case "format": {
			return matchDateFormat(getParameters().get(0));
		}
		default:
			throw new RuleException("Could not generate rule: " + rule);
		}
	}
	
	private Matcher matchDigits() throws RuleException {
		return matchLength("==");
	}
	
	private Matcher matchBetween() throws RuleException {
		return value->{
			try {
				long fValue = Long.parseLong(value);
				long param1 = Long.parseLong(getParameters().get(0));
				long param2 = Long.parseLong(getParameters().get(1));
				
				if(fValue >= param1 && fValue <= param2)
					return true;
				
				return false;
			} catch (Exception e) {
				return false;
			}
		};
	}
	
	private Matcher matchIn() throws RuleException {
		return value -> getParameters().contains(value);
	}
	
	private Matcher matchNotIn() throws RuleException {
		return value -> !getParameters().contains(value);
	}
	
	private Matcher matchMax() throws RuleException {
		return value->{
			// if the value is number so max is value.
			if(Str.isNumeric(value))
				return Long.parseLong(value) < Long.parseLong(getParameters().get(0)) ? true : false;
			// otherwise Max is a length of the value
			return value.length() < Integer.parseInt(getParameters().get(0)) ? true : false;
		};
	}
	
	private Matcher matchMin() throws RuleException {
		return value->{
			// if the value is number so max is value.
			if(Str.isNumeric(value))
				return Long.parseLong(value) > Long.parseLong(getParameters().get(0)) ? true : false;
			// otherwise Max is a length of the value
			return value.length() > Integer.parseInt(getParameters().get(0)) ? true : false;
		};
	}
	
	private Matcher matchDigitsMax() throws RuleException {
		return matchLength("<=");
	}
	
	private Matcher matchDigitsMin() throws RuleException {
		return matchLength(">=");
	}
	
	private Matcher matchGT() throws RuleException {
		return matchCompareValue(">");
	}
	
	private Matcher matchLT() throws RuleException {
		return matchCompareValue("<");
	}
	
	private Matcher matchGTE() throws RuleException {
		return matchCompareValue(">=");
	}
	
	private Matcher matchLTE() throws RuleException {
		return matchCompareValue("<=");
	}
	
	private Matcher matchLength(String operator) throws RuleException {
		return value->{
			if(operator.equals(">="))
				return value.length() >= Integer.parseInt(getParameters().get(0)) ? true : false;
			else if(operator.equals("<="))
				return value.length() <= Integer.parseInt(getParameters().get(0)) ? true : false;
			else if(operator.equals("=="))
				return value.length() == Integer.parseInt(getParameters().get(0)) ? true : false;
			
			return false;
		};
	}
	
	private Matcher matchCompareValue(String operator) throws RuleException {
		return value->{
			try {
				Double v = Double.parseDouble(value);
				Double param = Double.parseDouble(getParameters().get(0));
				if(operator.equals(">="))
					return v >= param ? true : false;
				else if(operator.equals("<="))
					return v <= param ? true : false;
				else if(operator.equals("<"))
					return v < param ? true : false;
				else if(operator.equals(">"))
					return v > param ? true : false;
				else if(operator.equals("=="))
						return v == param ? true : false;
				
			} catch (Exception e) {
				return false;
			}
			return false;
		};
	}
	
	private Matcher matchDateFormat(String format) {
		return value -> Str.isDate(value, format);
	}
}
