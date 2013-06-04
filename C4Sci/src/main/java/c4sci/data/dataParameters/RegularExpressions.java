package c4sci.data.dataParameters;

public final class RegularExpressions {
	public static final String BOOLEAN_REGEXP 	= "true|false";
	public static final String DOUBLE_REGEXP	= "[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?"; 
	public static final String FLOAT_REGEXP		= "[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?"; 
	public static final String INTEGER_REGEXP	= "(\\+|-)?\\d+";
	public static final String NO_WHITE_SPACE_REGEXP	= "[^\\s]*";
}
