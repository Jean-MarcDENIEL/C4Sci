package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.boundedStepElements;


public class IntegerComparator extends BoundsComparator{

	@Override
	public boolean isGreaterOrEqual(String val_1, String val_2) {
		try{
			int _int_1 = Integer.valueOf(val_1);
			int _int_2 = Integer.valueOf(val_2);
			return _int_1 >= _int_2;
		}
		catch(NumberFormatException _e){
			return false;
		}
	}

	@Override
	public boolean isLesserOrEqual(String val_1, String val_2) {
		try{
			int _int_1 = Integer.valueOf(val_1);
			int _int_2 = Integer.valueOf(val_2);
			return _int_1 <= _int_2;
		}
		catch(NumberFormatException _e){
			return false;
		}
	}
}
