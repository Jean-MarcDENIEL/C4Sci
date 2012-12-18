package c4sci.math.algebra;

/**
 * Useful floating operations managing uncertainty.
 * @author jeanmarc.deniel
 *
 */
public final class Floatings {

	private Floatings(){}
	
	public static final float	EPSILON_VALUE = 0.0001f;
	
	public static boolean isLess(float first_term, float second_term){
		return first_term-second_term < -EPSILON_VALUE;
	}
	
	public static boolean isEqual(float first_term, float second_term){
		return Math.abs(first_term - second_term) < EPSILON_VALUE;
	}
	
	public static boolean isGreater(float first_term, float second_term){

		return first_term - second_term > EPSILON_VALUE;
	}
	
	public static boolean isLessEqual(float first_term, float second_term){
		return isLess(first_term,  second_term) || isEqual(first_term,  second_term);
	}
	
	public static boolean isGreaterEqual(float first_term, float second_term){
		return isGreater(first_term, second_term) || isEqual(first_term, second_term);
	}
}
