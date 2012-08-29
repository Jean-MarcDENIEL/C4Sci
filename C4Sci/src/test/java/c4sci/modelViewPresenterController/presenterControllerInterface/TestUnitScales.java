package c4sci.modelViewPresenterController.presenterControllerInterface;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.presenterControllerInterface.UnitScales.NoSuchScaleExistsException;

public class TestUnitScales {

	@Test
	public void test() {

		UnitScales _unit = new UnitScales("m", new InternationalizableTerm("meter"));
		_unit.addScale("mm",new InternationalizableTerm("millimeter"),0.001f);
		_unit.addScale("km", new InternationalizableTerm("kilometer"),1000.0f);
		_unit.addScale("dm", new InternationalizableTerm("decimeter"),0.1f);
		_unit.addScale("dam", new InternationalizableTerm("decameter"), 10f);
		
		String[] _abbrev_tab = _unit.getSortedAbbreviations();
		assertTrue(_abbrev_tab.length == 5);
		
		float _prev_scaling = 0f;
		for (String _abbrev : _abbrev_tab){
			try {
				float _scale=_unit.getScalingFactor(_abbrev) ;
				assertTrue(_scale > _prev_scaling);
				_prev_scaling = _scale;
			} catch (NoSuchScaleExistsException _e) {
				fail("should not fail on "+_abbrev);
			}
		}
	}


}
