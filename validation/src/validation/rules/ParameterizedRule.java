package validation.rules;

import java.util.ArrayList;

import validation.Regex;
import validation.Str;

public class ParameterizedRule extends Rule {

	/**
	 * Parameterized rules are rules that have parameters like:
	 * max:40 min:30       ---> max is a rule. 40 is a parameter.
	 * digits:10           ---> digits is a rule. 10 is a parameter.
	 * in:admin,student    ---> in is a rule. admin and student are parameters.
	 */
	protected ArrayList<String> parameters = new ArrayList<>();
	
	/**
	 * @param fieldName      The field under validation.
	 * @param fieldValue     The value of the field under validation.
	 * @param rule           The rule this field must pass.
	 * @throws RuleException
	 */
	public ParameterizedRule(String fieldName, String fieldValue, String rule) throws RuleException {
		super(fieldName, fieldValue, rule, "parameterized");
		
		parseRuleAndParameters(rule);
		
		setMatcher(parseMatcher(getRule()));
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
			return matchLength("==");
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
			return matchLength("<=");
		}
		case "digits_min": {
			return matchLength(">=");
		}
		case "length_max": {
			return matchLength("<=");
		}
		case "length_min": {
			return matchLength(">=");
		}
		case "in": {
			return value -> getParameters().contains(value);
		}
		case "notIn": {
			return value -> !getParameters().contains(value);
		}
		case "gt": {
			return matchCompareValue(">");
		}
		case "gte": {
			return matchCompareValue(">=");
		}
		case "lt": {
			return matchCompareValue("<");
		}
		case "lte": {
			return matchCompareValue("<=");
		}
		case "format": {
			return value -> Str.isDate(value, getParameters().get(0));
		}
		case "regex": {
			return string -> Regex.matches(string, getParameters().get(0));
		}
		default:
			throw new RuleException("Could not generate rule: " + rule);
		}
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
}
