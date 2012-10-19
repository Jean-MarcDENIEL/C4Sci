package c4sci.modelViewPresenterController.presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import c4sci.data.Modifiable;
import c4sci.math.geometry.plane.PlaneVector;
import c4sci.math.geometry.space.SpaceVector;
import c4sci.modelViewPresenterController.jobs.CannotPerformSuchChangeException;
import c4sci.modelViewPresenterController.presenter.components.CompoundComponent;
import c4sci.modelViewPresenterController.presenter.components.NoChildComponent;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.NoSuchScaleExistsException;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementActivatedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementAddedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementInactivatedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementSuppressedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.StepBackwardStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.StepForwardStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges.BooleanElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges.FloatElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges.IntegerElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges.InternationalizableTermElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges.ThreeDimensionalElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges.TwoDimensionalElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.SingleDataStepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.TreatmentStepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.BooleanDataElement;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFamily.StandardComponentSet;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.lifeCycleChanges.CreateComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.lifeCycleChanges.CreateStandardComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.ActivityChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.BackgroundColorChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.BooleanValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FloatValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.ForegroundColorChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.IntegerValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.PositionChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.SizeChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.StringValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.TransparencyChange;
/**
 * This class implements default behavior on Commands : simply transferring from one interface to the other the effect of a {@link Command}.
 * @author jeanmarc.deniel
 *
 */
public class DefaultBehaviorPresenter extends Presenter {

	public DefaultBehaviorPresenter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ComponentChange> feedbackToElementActivatedStepChange(ElementActivatedStepChange step_chg) {
		try{
			return oneComponentFeedbackNotificationChange(new ActivityChange(getCorrespondingComponent(step_chg.getStepElement()), true, null));
		}
		catch(NoSuchElementException _e){
			return null;
		}
	}

	@Override
	public List<ComponentChange> feedbackToElementAddedStepChange(ElementAddedStepChange step_chg) {
		List<ComponentChange> _res= new ArrayList<ComponentChange>();

		// DEBUG
		System.out.println("Element added");

		appendComponentHierarchyAdded(step_chg.getStepElement(), _res, null, null);

		return _res;

	}

	@Override
	public List<ComponentChange> feedbackToElementSuppressedStepChange(
			ElementSuppressedStepChange step_chg) {
		List<ComponentChange> _res = new ArrayList<ComponentChange>();

		appendComponentHierarchySuppressed(step_chg.getStepElement(), _res, null);

		return _res;
	}

	@Override
	public List<ComponentChange> feedbackToStepBackwardStepChange(
			StepBackwardStepChange step_chg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComponentChange> feedbackToStepForwardStepChange(
			StepForwardStepChange step_chg) {
		List<ComponentChange> _res = new ArrayList<ComponentChange>();

		Component _panel_comp = new NoChildComponent();
		setCorrespondence(_panel_comp, step_chg.getStepElement());

		// DEBUG
		SpaceVector _back_color = new SpaceVector(1f, 1f, 0.5f);

		_res.add(new CreateStandardComponentChange(_panel_comp, StandardComponentSet.PANEL, null));
		_res.add(new BackgroundColorChange(_panel_comp, _back_color, null));

		// DEBUG
		System.out.println("step forward");

		return _res;
	}

	@Override
	public List<ComponentChange> feedbackToElementInactivatedStepChange(
			ElementInactivatedStepChange step_chg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComponentChange> feedbackToBooleanElementModificationStepChange(
			BooleanElementModificationStepChange step_chg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComponentChange> feedbackToFloatElementModificationStepChange(
			FloatElementModificationStepChange step_chg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComponentChange> feedbackToIntegerElementModificationStepChange(
			IntegerElementModificationStepChange step_chg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComponentChange> feedbackToInternationalizableTermElementModificationStepChange(
			InternationalizableTermElementModificationStepChange step_chg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComponentChange> feedbackToThreeDimensionalElementModificationStepChange(
			ThreeDimensionalElementModificationStepChange step_chg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComponentChange> feedbackToTwoDimensionElementModificationStepChange(
			TwoDimensionalElementModificationStepChange step_chg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StepChange> reactionToBooleanValueChange(
			BooleanValueChange comp_chg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StepChange> reactionToFloatValueChange(FloatValueChange comp_chg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StepChange> reactionToIntegerValueChange(
			IntegerValueChange comp_chg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StepChange> reactionToStringValueChange(
			StringValueChange comp_chg) {
		// TODO Auto-generated method stub
		return null;
	}


	private List<ComponentChange> oneComponentFeedbackNotificationChange(ComponentChange comp_chg){
		List<ComponentChange> _res = new ArrayList<ComponentChange>();
		_res.add(comp_chg);
		return _res;
	}

	private void appendComponentHierarchyAdded(StepElement step_elt, List<ComponentChange> res_list, Component parent_comp, ComponentChange previous_change){

		ComponentChange _previous_change = previous_change;

		float _size_x	= 0.5f;
		float _size_y 	= 0.5f;
		float _pos_x 	= 0.25f;
		float _pos_y 	= 0.25f;

		//
		// first create the component corresponding to the current step element if it doesn't exist yet.
		//
		Component _corresp_component = null;
		Component _parent_component = parent_comp;


		if (step_elt.containsProperValue()){
			//
			// step_elt has no sub elements
			//
			if (TreatmentStepElement.class.isInstance(step_elt)){
				_corresp_component = new NoChildComponent();

				_previous_change = appendComponentChange(new CreateStandardComponentChange(_corresp_component, StandardComponentSet.BUTTON, null), 
						res_list, _previous_change);
			}
			else{
				if (SingleDataStepElement.class.isInstance(step_elt)){
					//
					// single data
					//
					if (step_elt.getUnits() != null){
						//
						// there are units : creates an invisible container cut in two : value (left), units (right)
						//

						//
						// first create the invisible container and the ComponentChange
						//
						_corresp_component = new CompoundComponent();
						_corresp_component.setSize(new PlaneVector(_size_x, _size_y));
						_corresp_component.setUpperLeftOrigin(new PlaneVector(_pos_x, _pos_y));


						try {
							if (_parent_component != null){
								_parent_component.addChildComponent(_corresp_component);
							}
							_previous_change = appendComponentChange(new CreateStandardComponentChange(_corresp_component, StandardComponentSet.INVISIBLE_CONTAINER, null), 
									res_list, _previous_change);
							_previous_change = appendComponentChange(new PositionChange(_corresp_component, null, null), res_list, _previous_change);
							_previous_change = appendComponentChange(new SizeChange(_corresp_component, null, null), res_list, _previous_change);
							_previous_change = appendComponentChange(new TransparencyChange(_corresp_component, 1f, null), res_list, _previous_change);
							
							// DEBUG : INVISIBLE background in green
							_previous_change = appendComponentChange(new BackgroundColorChange(_corresp_component, new SpaceVector(0f, 1f,0f), null), res_list, _previous_change);
							
						} catch (CannotPerformSuchChangeException _e1) {
							// should not happen
							_e1.printStackTrace();
						}

						// 
						// then creates the units component and the ComponentChanges
						//
						_size_x /= 2.0f;

						Component _sub_comp_units = new NoChildComponent();
						_sub_comp_units.setSize(new PlaneVector(_size_x, _size_y));
						_sub_comp_units.setUpperLeftOrigin(new PlaneVector(_pos_x + _size_x, _pos_y));

						try {
							_corresp_component.addChildComponent(_sub_comp_units);
							_previous_change = appendComponentChange(new CreateStandardComponentChange(_sub_comp_units, StandardComponentSet.LABEL, null), 
									res_list, _previous_change);
							_previous_change = appendComponentChange(new PositionChange(_sub_comp_units, null, null), res_list, _previous_change);
							_previous_change = appendComponentChange(new SizeChange(_sub_comp_units, null, null), res_list, _previous_change);
							_previous_change = appendComponentChange(new TransparencyChange(_sub_comp_units, 0.5f, null), res_list, _previous_change);
							_previous_change = appendComponentChange(new ForegroundColorChange(_sub_comp_units, new SpaceVector(0f, 0f, 0f), null), res_list, _previous_change);
							
							// DEBUG : units background in violet
							_previous_change = appendComponentChange(new BackgroundColorChange(_sub_comp_units, new SpaceVector(1f, 0f, 1f), null), res_list, _previous_change);
							//
							// fill with text
							//
							try {
								_previous_change = appendComponentChange(new StringValueChange(_sub_comp_units,  "units : " +step_elt.getUnits().getAbbreviation(1f), null), res_list, _previous_change);
							} catch (NoSuchScaleExistsException _e1) {
								// TODO Auto-generated catch block
								_e1.printStackTrace();
							}


						} catch (CannotPerformSuchChangeException _e) {
							// should not happen
							_e.printStackTrace();
						}

						//
						// indicates the parent as being the invisible container
						//
						_parent_component = _corresp_component;
						_corresp_component = null;
					}

					//
					// create the good kind of component
					//
					_corresp_component = new NoChildComponent();
					Modifiable _corresp_data = step_elt.getBindings().iterator().next().getBoundData();

					if (BooleanDataElement.class.isInstance(step_elt)){

						_previous_change = appendComponentChange(new CreateStandardComponentChange(_corresp_component, StandardComponentSet.CHECKBOX, null), 
								res_list, _previous_change);

						_previous_change = appendComponentChange(new BooleanValueChange(_corresp_component, Boolean.getBoolean(_corresp_data.getValue()), null), 
								res_list, _previous_change);
					}
					else{
						StandardComponentSet _comp_type;
						if (step_elt.isEditable()){
							// editable : text field
							_comp_type = StandardComponentSet.TEXT_FIELD;							
						}
						else{
							// not editable : label
							_comp_type = StandardComponentSet.LABEL;
						}
						
						_previous_change = appendComponentChange(new CreateStandardComponentChange(_corresp_component, _comp_type, null), 
								res_list, _previous_change);

						//
						// fill the component
						//
						_previous_change = appendComponentChange(new StringValueChange(_corresp_component, _corresp_data.getValue(), null), 
								res_list, _previous_change);						
					}

					_previous_change = appendComponentChange(new ActivityChange(_corresp_component, step_elt.isEditable(), null), 
							res_list, _previous_change);
				}
				else{
					// not a compound neither a single : ???
				}
			}
		}
		else{
			//
			// step_elt is compound
			//
			_corresp_component = new CompoundComponent();

			_previous_change = appendComponentChange(new CreateStandardComponentChange(_corresp_component, StandardComponentSet.INVISIBLE_CONTAINER, null), 
					res_list, _previous_change);

		}

		//
		// size and position the component
		//
		_corresp_component.setSize(new PlaneVector(_size_x, _size_y));
		_corresp_component.setUpperLeftOrigin(new PlaneVector(_pos_x, _pos_x));
		_previous_change = appendComponentChange(new PositionChange(_corresp_component, null, null), res_list, _previous_change);
		_previous_change = appendComponentChange(new SizeChange(_corresp_component, null, null), res_list, _previous_change);
		
		// DEBUG : background in orange
		_previous_change = appendComponentChange(new BackgroundColorChange(_corresp_component, new SpaceVector(1f, 0.5f, 0f), null), res_list, _previous_change);
		
		//
		// adds to the parent Component
		//
		if (_parent_component != null){
			try {
				_parent_component.addChildComponent(_corresp_component);
			} catch (CannotPerformSuchChangeException _e) {
				// should not happen !
				_e.printStackTrace();
			}
		}
		//
		// update maps
		//
		setCorrespondence(_corresp_component, step_elt);

		//
		// passes through all children elements to create their components (and send sequences) if they do not exist
		//
		for(Iterator<StepElement> _it = step_elt.getSubElementsIterator();_it.hasNext();){
			appendComponentHierarchyAdded(_it.next(), res_list, _corresp_component, _previous_change);
		}
	}
	/**
	 * 
	 * @param comp_chg The {@link ComponentChange} to append
	 * @param comp_chg_list The {@link List} that will get a new element append
	 * @param previous_cmd The previous {@link ComponentChange} 
	 * @return the new previous command for following commands to come
	 */
	private ComponentChange appendComponentChange(ComponentChange comp_chg, List<ComponentChange> comp_chg_list, ComponentChange previous_cmd){
		comp_chg.setPreviousCommand(previous_cmd);
		comp_chg_list.add(comp_chg);
		return comp_chg;
	}

	private void appendComponentHierarchySuppressed(StepElement step_elt, List<ComponentChange> res_list, ComponentChange previous_change){
		try{
			Component 		_current_comp		= getCorrespondingComponent(step_elt);
			ComponentChange _previous_change 	= previous_change;

			//
			// first suppress the children of the step_element
			//
			for (Iterator<StepElement> _it = step_elt.getSubElementsIterator(); _it.hasNext();){
				appendComponentHierarchySuppressed(_it.next(), res_list, _previous_change);
			}

			//
			// update maps
			//
			suppressCorrespondence(_current_comp, step_elt);

			//
			// eventually remove the component from its parent
			//
			Component _parent_comp = _current_comp.getParentComponent();
			if (_parent_comp != null){
				computeSuppressionFromParentSequence(_current_comp, _parent_comp, _previous_change);
			}


			//
			// computes the suppression sequence then appends it to the result sequence
			//
			_previous_change = res_list.get(res_list.size()-1);
			List<ComponentChange> _suppr_seq = computeSuppressionSequence(_current_comp, step_elt, _previous_change);
			res_list.addAll(_suppr_seq);

		}
		catch(NoSuchElementException _e){}

	}

	private List<ComponentChange> computeCreationSequence(Component corresp_component, StepElement step_elt, ComponentChange previous_change) {
		List<ComponentChange> _res = new ArrayList<ComponentChange>();



		return _res;
	}

	private List<ComponentChange> computeSuppressionSequence(Component corresp_component, StepElement step_elt, ComponentChange previous_change) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<ComponentChange> computeSuppressionFromParentSequence(Component suppressed_comp, Component parent_comp, ComponentChange previous_change){
		// TODO
		return null;
	}

}

