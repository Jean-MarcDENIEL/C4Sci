package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.boundedStepElements;

import c4sci.math.algebra.Floatings;


public class FloatComparator extends BoundsComparator{

	@Override
	public boolean isGreaterOrEqual(String val_1, String val_2) {
		try{
			float _fl_1 = Float.valueOf(val_1);
			float _fl_2 = Float.valueOf(val_2);
			return Floatings.isGreaterEqual(_fl_1, _fl_2);
		}
		catch(NumberFormatException _e){
			return false;
		}
	}

	@Override
	public boolean isLesserOrEqual(String val_1, String val_2) {
		try{
			float _fl_1 = Float.valueOf(val_1);
			float _fl_2 = Float.valueOf(val_2);
			return Floatings.isLessEqual(_fl_1, _fl_2);
		}
		catch(NumberFormatException _e){
			return false;
		}
	}
}

