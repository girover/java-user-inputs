package validation.rules;

public class Between extends ParameterizedRule{

	public Between(String fieldName, String fieldValue, String rule) throws RuleException {
		super(fieldName, fieldValue, rule);
	}

//	public Between(String fieldName, String fieldValue, int param1, int param2, String message){
//		super("between", fieldName, fieldValue, message);
//		addParameter(param1);
//		addParameter(param2);
//	}
//	public Between(String fieldName, String fieldValue,  int param1, int param2){
//		this(fieldName, fieldValue, param1, param2, "The field " 
//				+ fieldName+" must be between "+param1
//				+" and "+param2+".");
//	}
//	public Between(String fieldName, String fieldValue, double param1, double param2, String message){
//		super("between", fieldName, fieldValue, message);
//		addParameter(param1);
//		addParameter(param2);
//	}
//	public Between(String fieldName, String fieldValue,  double param1, double param2){
//		this(fieldName, fieldValue, param1, param2, "The field " 
//				+ fieldName+" must be between "+param1
//				+" and "+param2+".");
//	}
//	
//	public Between(String fieldName, String fieldValue, String param1, String param2, String message){
//		super("between", fieldName, fieldValue, message);
//	}
//
//	public void matches() throws RuleException {
//
//		if(parameters.get(0) instanceof Integer)
//			applyForInteger();
//		
//		if(parameters.get(0) instanceof Double)
//			applyForDouble();
//	}
//	
//	private void applyForInteger() {
//		int param1 = (Integer) parameters.get(0);
//		int param2 = (Integer) parameters.get(1);
//		int value  = Integer.parseInt(getFieldValue());
//
//		if (value >= param1 && value <= param2) 
//			passed = true;
//		else
//			passed = false;
//	}
//	
//	private void applyForDouble() {
//		double param1 = (Double) parameters.get(0);
//		double param2 = (Double) parameters.get(1);
//		double value  = Double.parseDouble(getFieldValue());
//		
//		if (value >= param1 && value <= param2) 
//			passed = true;
//		else
//			passed = false;
//	}
}
