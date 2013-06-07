package c4sci.modelViewPresenterController.presenterControllerInterface;

import java.util.Iterator;

import org.junit.Test;

import static org.junit.Assert.*;

import c4sci.data.HierarchicalData;
import c4sci.data.dataParameters.GenericDataParameter;
import c4sci.data.dataParameters.singleValueModifiables.BooleanModifiable;
import c4sci.data.dataParameters.singleValueModifiables.FloatModifiable;
import c4sci.data.dataParameters.singleValueModifiables.IntegerModifiable;
import c4sci.data.dataParameters.singleValueModifiables.NoWhiteSpaceStringModifiable;
import c4sci.data.exceptions.CannotInstantiateDataException;
import c4sci.data.exceptions.CannotInstantiateParameterException;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.math.algebra.Floatings;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.BoundedStepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.CompoundStepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.ComputedDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.EditableDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.ScalableDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.TreatmentStepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.boundedStepElements.BoundsComparator;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.boundedStepElements.FloatComparator;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.boundedStepElements.IntegerComparator;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.BooleanDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.FloatDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.IntegerDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.LabelDataElement;

public class TestStepElements {

	@Test
	public void testDataParamElements() throws CannotInstantiateParameterException, CannotInstantiateDataException {
		
		class TestData extends HierarchicalData{
			public GenericDataParameter<BooleanModifiable>		boolParam;
			public GenericDataParameter<FloatModifiable>		lowerFloatBound;
			public GenericDataParameter<FloatModifiable>		upperFloatBound;
			public GenericDataParameter<FloatModifiable>		boundedFloat;
			public GenericDataParameter<IntegerModifiable>		lowerIntBound;
			public GenericDataParameter<IntegerModifiable>		upperIntBound;
			public GenericDataParameter<IntegerModifiable>		boundedInteger;
			public GenericDataParameter<NoWhiteSpaceStringModifiable>		stringParam;
			
			public TestData(String data_token,
					InternationalizableTerm data_name,
					InternationalizableTerm data_description) throws CannotInstantiateParameterException, CannotInstantiateDataException {
				super(data_token, data_name, data_description);
				
				boolParam = new GenericDataParameter<BooleanModifiable>(new BooleanModifiable(), "bool", new InternationalizableTerm("boolean param"), new InternationalizableTerm("boolean param descr"));
				addDataParameter(boolParam);

				lowerFloatBound = new GenericDataParameter<FloatModifiable>(new FloatModifiable(), "lowerFloatBound", new InternationalizableTerm("lower float bound"), new InternationalizableTerm("lower float bound descr"));
				upperFloatBound = new GenericDataParameter<FloatModifiable>(new FloatModifiable(), "upperFloatBound", new InternationalizableTerm("upper float bound"), new InternationalizableTerm("upper bound bound descr"));
				boundedFloat	= new GenericDataParameter<FloatModifiable>(new FloatModifiable(), "boundedFloat", new InternationalizableTerm("bounded float"), new InternationalizableTerm("bounded float descr"));
				
				
				addDataParameter(lowerFloatBound);
				addDataParameter(upperFloatBound);
				addDataParameter(boundedFloat);

				lowerIntBound 	= new GenericDataParameter<IntegerModifiable>(new IntegerModifiable(), "lowerIntBound", new InternationalizableTerm("lower Integer bound"), new InternationalizableTerm("lower Integer bound descr"));
				upperIntBound 	= new GenericDataParameter<IntegerModifiable>(new IntegerModifiable(), "upperIntBound", new InternationalizableTerm("upper Integer bound"), new InternationalizableTerm("upper Integer bound descr"));
				boundedInteger 	= new GenericDataParameter<IntegerModifiable>(new IntegerModifiable(), "boundedInteger", new InternationalizableTerm("bounded Integer"), new InternationalizableTerm("bounded Integer descr"));
				
				addDataParameter(lowerIntBound);
				addDataParameter(upperIntBound);
				addDataParameter(boundedInteger);
				
				stringParam = new GenericDataParameter<NoWhiteSpaceStringModifiable>(new NoWhiteSpaceStringModifiable(), "string", new InternationalizableTerm("string param"), new InternationalizableTerm("string param descr"));
				
				addDataParameter(stringParam);	
			}
		}
		
		TestData _data = new TestData("test", new InternationalizableTerm("test data"), new InternationalizableTerm("test data description"));

		BooleanDataElement _bool = new BooleanDataElement(_data.boolParam);
		
		FloatDataElement _lower_fl = new FloatDataElement(_data.lowerFloatBound);
		FloatDataElement _upper_fl = new FloatDataElement(_data.upperFloatBound);
		FloatDataElement _value_fl = new FloatDataElement(_data.boundedFloat);
		
		BoundedStepElement<FloatDataElement> _bounded_fl = new BoundedStepElement<FloatDataElement>(_lower_fl, _upper_fl, _value_fl, new FloatComparator());
		
		IntegerDataElement	_lower_int = new IntegerDataElement(_data.lowerIntBound);
		IntegerDataElement	_upper_int = new IntegerDataElement(_data.upperIntBound);
		IntegerDataElement	_value_int = new IntegerDataElement(_data.boundedInteger);
		
		BoundedStepElement<IntegerDataElement> _bounded_int = new BoundedStepElement<IntegerDataElement>(_lower_int, _upper_int, _value_int, new IntegerComparator());
		
		BoundedStepElement<BoundedStepElement<FloatDataElement>> _bounded_2 = new BoundedStepElement<BoundedStepElement<FloatDataElement>>(_bounded_fl, _bounded_fl, _bounded_fl, new BoundsComparator() {
			
			@Override
			public boolean isLesserOrEqual(String val_1, String val_2) {
				return true;
			}
			
			@Override
			public boolean isGreaterOrEqual(String val_1, String val_2) {
				return true;
			}
		});
		
		LabelDataElement _label = new LabelDataElement(_data.stringParam);
		
		assertTrue(_label.isEditable());
		assertTrue(_lower_fl.isEditable());
		_data.lowerFloatBound.accesValue().setFloatValue(1.0f);
		assertTrue(_lower_fl.isInternallyCoherent());
		_lower_fl.ensureCoherentInternalState();
		assertTrue(Floatings.isEqual(_data.lowerFloatBound.accesValue().getFloatValue(), 0.0f));
		assertTrue(_lower_fl.getUnits() == null);
		
		assertTrue(_lower_int.isEditable());
		assertTrue(_lower_int.isInternallyCoherent());
		_data.lowerIntBound.accesValue().setIntegerValue(2);
		_lower_int.ensureCoherentInternalState();
		assertTrue(_data.lowerIntBound.accesValue().getIntegerValue() == 0);
		assertTrue(_lower_int.getUnits() == null);
		
		assertTrue(_label.isEditable());
		assertTrue(_label.isInternallyCoherent());
		_label.ensureCoherentInternalState();
		assertTrue(_label.getUnits()==null);
		
		assertTrue(_bool.isEditable());
		assertTrue(_bool.isInternallyCoherent());
		assertTrue(_bool.getUnits()==null);
		
		try {
			_label.getSingleBinding().getBoundData().setValue("string testing");
			assertTrue(true);
		} catch (DataValueParsingException _e) {
			fail("should not throw here");
		}
		
		try {
			_bool.getSingleBinding().getBoundData().setValue("true");
			assertTrue(_data.boolParam.accesValue().getBooleanValue());
			_bool.getSingleBinding().getBoundData().setValue("54de");
			assertFalse(_data.boolParam.accesValue().getBooleanValue());
		} catch (DataValueParsingException _e) {
			fail();
		}
		
		
		CompoundStepElement _compound = new CompoundStepElement();
		_compound.setSubElement(1, _bounded_int);
		_compound.setSubElement(2, _label);
		
		_bool.ensureCoherentInternalState();
		
		assertTrue(_bool.isInternallyCoherent());
		assertTrue(_bool.isEditable());
		assertTrue(_bool.getUnits() == null);
		assertTrue(_bool.isOverallCoherent());
		
		StepElement.createResourceDependentRelationship(_bounded_fl, _bounded_int);
		StepElement.createResourceDependentRelationship(_bounded_int, _label);
		
		
		
		_data.lowerIntBound.accesValue().setIntegerValue(1);
		_data.upperIntBound.accesValue().setIntegerValue(5);
		_data.boundedInteger.accesValue().setIntegerValue(7);
		assertFalse(_bounded_int.isInternallyCoherent());
		_data.boundedInteger.accesValue().setIntegerValue(-2);
		assertFalse(_bounded_int.isInternallyCoherent());
		assertFalse(_label.isOverallCoherent());
		assertFalse(_compound.isOverallCoherent());

		
		_bounded_int.ensureCoherentInternalState();
		assertTrue(_bounded_int.isInternallyCoherent());
		assertTrue(""+_data.boundedInteger.accesValue().getIntegerValue()+" instead of " + _data.lowerIntBound.accesValue().getIntegerValue(),_data.boundedInteger.accesValue().getIntegerValue() == _data.lowerIntBound.accesValue().getIntegerValue());
		assertTrue(_compound.isOverallCoherent());
	
		_data.lowerFloatBound.accesValue().setFloatValue(2.0f);
		_data.upperFloatBound.accesValue().setFloatValue(4.0f);
		_data.boundedFloat.accesValue().setFloatValue(5.0f);
		assertFalse(_bounded_fl.isOverallCoherent());
		assertFalse(_label.isOverallCoherent());
		assertFalse(_compound.isOverallCoherent());
		assertFalse(_bounded_2.isInternallyCoherent());
		
		assertTrue(_compound.getSubElement(1)==_bounded_int);
		assertTrue(_compound.isEditable());
		assertTrue(_compound.getBindings().size() == 2);
		
		_compound.setSubElement(3, new ComputedDataElement(_bounded_int));
		assertFalse(_compound.isEditable());
		_data.boundedInteger.accesValue().setIntegerValue(8);
		assertFalse(_bounded_int.isInternallyCoherent());
		assertFalse(_compound.isInternallyCoherent());
		_bounded_int.ensureCoherentInternalState();
		assertTrue(_compound.isInternallyCoherent());
		
		assertFalse(_compound.getSubElement(3).isEditable());
		StepElement _step_3 = _compound.getSubElement(3).getBindings().iterator().next().getBoundElement();
		assertTrue(""+_step_3.getClass(), _step_3 == _bounded_int.getSingleBinding().getBoundElement());
		
		_data.boundedInteger.accesValue().setIntegerValue(100);
		assertFalse(_compound.isInternallyCoherent());
		_compound.ensureCoherentInternalState();
		assertTrue(_compound.isInternallyCoherent());

		_bounded_fl.ensureCoherentInternalState();
		assertTrue(Floatings.isEqual(_data.boundedFloat.accesValue().getFloatValue(), _data.lowerFloatBound.accesValue().getFloatValue()));
		assertTrue(_label.isOverallCoherent());
		assertTrue("2.0".compareTo(_bounded_fl.getSingleBinding().getBoundData().getValue())==0);
		assertTrue(_bounded_2.isInternallyCoherent());
		assertTrue(_bounded_fl.isEditable());
		assertTrue(_bounded_fl.getBindings().size() == 1);
		
		int nb_dep = 0;
		for (Iterator<StepElement> _it=_bounded_fl.getDependentsIterator();_it.hasNext();){
			assertTrue(_it.next().isInternallyCoherent());
			nb_dep++;
		}
		assertTrue(nb_dep == 1);
		
		int nb_res = 0;
		for(Iterator<StepElement> _it = _label.getResourcesIterator();_it.hasNext();){
			assertTrue(_it.next().isInternallyCoherent());
			nb_res++;
		}
		assertTrue(nb_res == 1);
		
		_bool.setImportance(2.0f);
		assertTrue(Floatings.isEqual(_bool.getImportance(), 1.0f));
		_bool.setImportance(-0.2f);
		assertTrue(Floatings.isEqual(_bool.getImportance(), 0.0f));
		_bool.setImportance(0.42f);
		assertTrue(Floatings.isEqual(_bool.getImportance(), 0.42f));
		
		assertTrue(_bounded_int.getSingleBinding().getBoundElement() == _value_int);
		assertTrue(_bool.getSingleBinding().getBoundElement() == _bool);
		
		ScalableDataElement _scalable = new ScalableDataElement(_bounded_int, UnitScales.createMeterUnitSCales());
		_data.boundedInteger.accesValue().setIntegerValue(1000);
		String _km = _scalable.getUnits().chooseBestFittedScale(Float.valueOf(_scalable.getSingleBinding().getBoundData().getValue()));
		assertTrue(_km, _km.compareTo("km")==0);
		assertFalse(_scalable.isInternallyCoherent());
		assertTrue(_scalable.isEditable());
		_scalable.ensureCoherentInternalState();
		_km = _scalable.getUnits().chooseBestFittedScale(Float.valueOf(_scalable.getSingleBinding().getBoundData().getValue()));
		assertTrue(_km, _km.compareTo("m")==0);
		
		EditableDataElement _editable = new EditableDataElement(_bounded_int);
		assertTrue(_editable.isEditable());
		_data.boundedInteger.accesValue().setIntegerValue(12);
		assertFalse(_editable.isInternallyCoherent());
		_editable.ensureCoherentInternalState();
		assertTrue(_editable.isInternallyCoherent());
		assertTrue(_editable.getSingleBinding().getBoundData() == _data.boundedInteger);
		
		TreatmentStepElement _treat = new TreatmentStepElement();
		assertFalse(_treat.isEditable());
		assertTrue(_treat.isInternallyCoherent());
		assertTrue(_treat.getUnits() == null);
		_treat.ensureCoherentInternalState();
		assertFalse(_treat.getSubElementsIterator().hasNext());
		assertTrue(_treat.getBindings().size() == 0);
	}
}