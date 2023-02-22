package validation.rules;

@FunctionalInterface
public interface Matcher {

	public boolean matches(String fieldValue);
}
