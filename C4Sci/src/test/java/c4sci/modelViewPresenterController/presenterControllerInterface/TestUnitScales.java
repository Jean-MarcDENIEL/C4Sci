package c4sci.modelViewPresenterController.presenterControllerInterface;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.NoSuchScaleExistsException;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;

public class TestUnitScales {

	@SuppressWarnings("unused")
	@Test
	public void test() {

		UnitScales _unit = new UnitScales("m", new InternationalizableTerm("meter"));
		_unit.addScale("mm",new InternationalizableTerm("millimeter"),0.001f);
		_unit.addScale("km", new InternationalizableTerm("kilometer"),1000.0f);
		_unit.addScale("dm", new InternationalizableTerm("decimeter"),0.1f);
		_unit.addScale("dam", new InternationalizableTerm("decameter"), 10f);
		_unit.addScale("falseScale", new InternationalizableTerm("false scale"), 0.0f);
		
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
		
		try{
			float _scale_bad = _unit.getScalingFactor("lk");
			fail("should have thrown");
		}
		catch(NoSuchScaleExistsException _e){
			assertTrue(true);
		}
		
		try{
			float _scale_null = _unit.getScalingFactor(null);
			fail("should have thrown");
		}
		catch(NoSuchScaleExistsException _e){
			assertTrue(true);
		}
	}
	
	@Test
	public void testGetScalesAndAbbreviations(){
		UnitScales _unit = new UnitScales("m", new InternationalizableTerm("meter"));
		_unit.addScale("mm",new InternationalizableTerm("millimeter"),0.001f);
		_unit.addScale("km", new InternationalizableTerm("kilometer"),1000.0f);
		_unit.addScale("dm", new InternationalizableTerm("decimeter"),0.1f);
		_unit.addScale("dam", new InternationalizableTerm("decameter"), 10f);
		String[] _abbrev_tab = _unit.getSortedAbbreviations();
		
		for (String _abbrev : _abbrev_tab){
			try {
				float _scale = _unit.getScalingFactor(_abbrev);
				String _abbrev_2 = _unit.getAbbreviation(_scale);
				assertTrue(_abbrev_2.compareTo(_abbrev)==0);
			} catch (NoSuchScaleExistsException _e) {
				fail("should not throw here : "+_e.getMessage());
			}
		}
		String _to_mm = _unit.chooseBestFittedScale(0.001f);
		String _to_cm = _unit.chooseBestFittedScale(0.035f);
		String _to_km = _unit.chooseBestFittedScale(990.0f);
		String _to_dam = _unit.chooseBestFittedScale(45f);
		String _to_m = _unit.chooseBestFittedScale(3.1f);
		String _to_0 = _unit.chooseBestFittedScale(0.0f);
		assertTrue("instead of mm :"+_to_mm, 	"mm".compareTo(_to_mm)==0);
		assertTrue("instead of dm :"+_to_cm, 	"dm".compareTo(_to_cm)==0);
		assertTrue("instead of km :"+_to_km, 	"km".compareTo(_to_km)==0);
		assertTrue("instead of dam:"+_to_dam, 	"dam".compareTo(_to_dam)==0);
		assertTrue("instead of m : "+_to_m, 	"m".compareTo(_to_m)==0);
		assertTrue("instead of mm :"+_to_0,		"mm".compareTo(_to_0)==0);
	}
	
	@Test
	public void TestCreateMeterScale(){
		UnitScales _unit= UnitScales.createMeterUnitSCales();
		String _to_mm = _unit.chooseBestFittedScale(0.001f);
		String _to_cm = _unit.chooseBestFittedScale(0.01f);
		String _to_dm = _unit.chooseBestFittedScale(0.1f);
		String _to_km = _unit.chooseBestFittedScale(1000f);
		String _to_dam = _unit.chooseBestFittedScale(10f);
		String _to_m = _unit.chooseBestFittedScale(1f);
		String _to_0 = _unit.chooseBestFittedScale(0.0f);
		
		assertTrue("instead of mm :"+_to_mm, 	"mm".compareTo(_to_mm)==0);
		assertTrue("instead of cm :"+_to_cm, 	"cm".compareTo(_to_cm)==0);
		assertTrue("instead of dm :"+_to_dm, 	"dm".compareTo(_to_dm)==0);
		assertTrue("instead of km :"+_to_km, 	"km".compareTo(_to_km)==0);
		assertTrue("instead of dam:"+_to_dam, 	"dam".compareTo(_to_dam)==0);
		assertTrue("instead of m : "+_to_m, 	"m".compareTo(_to_m)==0);
		assertTrue("instead of mm :"+_to_0,		"mm".compareTo(_to_0)==0);
	}


}
