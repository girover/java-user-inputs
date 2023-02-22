package validation.rules;

import java.util.ArrayList;
import java.util.Arrays;

public class RuleParser {
	
	private String fieldName;
	private String fieldValue;
	private String rulesAsString;
	
	private ArrayList<Rule> rulesUnderValidation = new ArrayList<>();
	
	public String getRuleSeparator() {
		return Rule.ruleSeparator;
	}

	public String getParamsSeparator() {
		return Rule.paramsSeparator;
	}
	
	public String getRuleParamsSeparator() {
		return Rule.ruleParamsSeparator;
	}

	public ArrayList<Rule> getRules() {
		return rulesUnderValidation;
	}
	
	public void generateRules(String fieldName, String fieldValue, String rules) throws RuleException {
		
		this.fieldName      = fieldName;
		this.fieldValue 	= fieldValue;
		this.rulesAsString 	= rules;

		parse();
	}
	
	private void parse() throws RuleException {
		String[] pasedRules = rulesAsString.split(getRuleSeparator());

		for (String rule : pasedRules) {
			if(rule.contains(getRuleParamsSeparator()))
				rulesUnderValidation.add(parseParameterizedRule(rule));
			else
				rulesUnderValidation.add(parseExplicitRule(rule));
		}
	}
	
	private ExplicitRule parseExplicitRule(String rule) throws RuleException {
		
		if(!Rule.getValidExplicitRules().contains(rule))
			throw new RuleException("Rule " + rule + " is not valid explicit rule.");
		
		return new ExplicitRule(fieldName, fieldValue, rule);
	}
	
	private ParameterizedRule parseParameterizedRule(String rule) throws RuleException {
		// We separate rule from its parameters. example [digits:5] [between:20,100]
		String[] ruleWithParams = rule.split(getRuleParamsSeparator());
		
		if(!Rule.getValidParameterizedRules().contains(ruleWithParams[0]))
			throw new RuleException("Rule " + ruleWithParams[0] + " is not valid parameterized rule.");
		
		ParameterizedRule paramRule = new ParameterizedRule(fieldName, fieldValue, rule);

		
		return paramRule;
	}

	public ArrayList<Rule> getRulesUnderValidation(){
		return rulesUnderValidation;
	}
}
