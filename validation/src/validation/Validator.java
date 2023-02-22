package validation;

import java.util.ArrayList;

import validation.rules.Rule;
import validation.rules.RuleException;
import validation.rules.RuleMessage;
import validation.rules.RuleMessagesBag;
import validation.rules.RuleParser;

public class Validator {

	private boolean stopOnFirstFailure = false;
	private RuleParser ruleParser;
	private ArrayList<Rule> failedRules;
	private RuleMessagesBag messagesBag = new RuleMessagesBag();
	
	public Validator() {
		ruleParser = new RuleParser();
		failedRules = new ArrayList<>();
	}
	
	/**
	 * Constructor
	 * @param stopOnFirstFailure When true is passed, only the first failure message will
	 * be generated.
	 */
	public Validator(boolean stopOnFirstFailure) {
		this();
		this.stopOnFirstFailure = stopOnFirstFailure;
	}
	
	public void addRules(String fieldName, String fieldValue, String rules) throws RuleException {
		ruleParser.generateRules(fieldName, fieldValue, rules);
	}
	
	public boolean pass() throws RuleException {
		
		for (Rule rule : ruleParser.getRulesUnderValidation()) {
			if(!rule.pass()) {
				failedRules.add(rule);
				// We do not check all other rules when first failure occurs.
				if(stopOnFirstFailure) {
					break;
				}
			}
		}
		mergeMessages();
		
		return failedRules.size() > 0 ? false : true;
	}
	
	private void mergeMessages() {

		if(failedRules.size() > 0) {
			for (Rule rule : failedRules)
				if(messagesBag.contains(rule.getFieldName(), rule.getRule()))
					rule.setMessage(messagesBag.get(rule.getFieldName(), rule.getRule()).getMessage());
		}
	}

	public ArrayList<Rule> getRulesUnderValidation(){
		return ruleParser.getRulesUnderValidation();
	}
	
	public ArrayList<Rule> getFailedRules(){
		return failedRules;
	}
	
	public void addRuleMessage(String fieldName, String ruleName, String message) {
		/**
		 * If the rule and message already exist for the given field,
		 * we will only change the message.
		 */
		RuleMessage m = messagesBag.get(fieldName, ruleName);
		if(m != null) {
			m.setMessage(message);
			return;
		}
		
		/**
		 * Otherwise we will add the new RuleMessage to the RuleMessagesBag
		 */
		messagesBag.add(new RuleMessage(fieldName, ruleName, message));
	}
	
	public RuleMessagesBag getMessages() {
		return messagesBag;
	}
	
	public ArrayList<String> getErrorMessages() {
		ArrayList<String> errorMessages = new ArrayList<>();
		for (Rule rule : getFailedRules()) {
			errorMessages.add(rule.getMessage());
		}
		
		return errorMessages;
	}

	/**
	 * If the Validator validate all fields and no error messages are generated we
	 * will return true, otherwise false returns
	 * 
	 * @return boolean
	 */
//	public boolean passed() {
//		return errorMessages.size() > 0 ? false : true;
//	}
//
//	private boolean min(String fieldName, String value) {
//		if (value == null || value == "") {
//			errorMessages.add(String.format(messages.get("min"), fieldName));
//			return false;
//		}
//
//		String[] s = value.split(":");
//		if (s.length < 2) {
//			errorMessages.add(String.format(messages.get("min"), fieldName));
//			return false;
//		}
//
//		return true;
//	}

	/**
	 * Validate the size of an attribute is between a set of values.
	 *
	 * @param string     $attribute
	 * @param mixed      $value
	 * @param array<int, int|string> $parameters
	 * @return bool
	 */
//	public boolean validateBetween(String attribute, String value, String[] parameters) {
//		if (parameters.length < 2) {
//			return false;
//		}
//		return parameters[0].compareTo(value) == -1 && value.compareTo(parameters[1]) == -1;
//	}
//
//	private String[] parseValidationRules(String validationRules) {
//
//		return validationRules.split(ruleSeparator);
//	}
}
