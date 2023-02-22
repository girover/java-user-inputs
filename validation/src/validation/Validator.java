package validation;

import java.util.ArrayList;

import validation.rules.ExplicitRule;
import validation.rules.Messages;
import validation.rules.ParameterizedRule;
import validation.rules.Rule;
import validation.rules.RuleException;
import validation.rules.RuleMessage;
import validation.rules.RuleMessagesBag;
import validation.rules.RuleParser;

public class Validator {

	private boolean stopOnFirstFailure = false;
	private RuleParser ruleParser;
	private ArrayList<Rule> failedRules;
	private RuleMessagesBag messagesBag;
	
	public Validator() {
		ruleParser = new RuleParser();
		failedRules = new ArrayList<>();
		messagesBag  = new RuleMessagesBag();
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
	
	/**
	 * Add rules for the given field to validate the given value.
	 * @param fieldName
	 * @param fieldValue
	 * @param rules
	 * @throws RuleException
	 */
	public void addRules(String fieldName, String fieldValue, String rules) throws RuleException {
		ruleParser.generateRules(fieldName, fieldValue, rules);
	}
	
	/**
	 * Determine if all specified rules passes the user inputs
	 * @return boolean
	 * @throws RuleException
	 */
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
		// Retrieving error messages.
		mergeMessages();
		
		return failedRules.size() > 0 ? false : true;
	}
	
	/**
	 * Here we retrieve error messages from the RuleMessagesBag, and inject them
	 * to the failed rules objects.
	 * If there are error messages of particular rules specified by the user,
	 * then we replace the original messages with the user's.
	 */
	private void mergeMessages() {

		if(failedRules.size() > 0) {
			for (Rule rule : failedRules)
				if(messagesBag.contains(rule.getFieldName(), rule.getRule()))
					rule.setMessage(messagesBag.get(rule.getFieldName(), rule.getRule()).getMessage());
				else if(rule.getMessage() == null || rule.getMessage().isBlank()) {
					if(rule instanceof ExplicitRule) {
						String message = Messages.getExplicitRuleMessage(rule.getRule());
						message = String.format(message, rule.getFieldName());
						rule.setMessage(message);
					}else if(rule instanceof ParameterizedRule) {
						String message = Messages.getParameterizedRuleMessage(rule.getRule());
						message = String.format(message, getFiledNameAndParametersAsArray((ParameterizedRule)rule));
						rule.setMessage(message);
					}
				}
		}
	}

	private Object[] getFiledNameAndParametersAsArray(ParameterizedRule rule) {
		
		ArrayList<String> params = new ArrayList<>();
		params.add(rule.getFieldName());
		
		if (Rule.getRulesOfMoreThanTwoParameters().contains(rule.getRule()))
			params.add(rule.getParameters().toString());
		else
			params.addAll(rule.getParameters());
		
		return params.toArray();
	}

	/**
	 * Retrieve all validation Rules that user have added to the validator.
	 * @return
	 */
	public ArrayList<Rule> getRulesUnderValidation(){
		return ruleParser.getRulesUnderValidation();
	}
	
	/**
	 * Retrieve all failed Rules.
	 * @return
	 */
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
	
	/**
	 * We get all error messages for failed rules.
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getErrorMessages() {
		
		ArrayList<String> errorMessages = new ArrayList<>();
		for (Rule rule : getFailedRules()) {
			errorMessages.add(rule.getMessage());
		}
		
		return errorMessages;
	}
}
