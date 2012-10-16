package c4sci.modelViewPresenterController.presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import c4sci.modelViewPresenterController.jobs.CannotPerformSuchChangeException;
import c4sci.modelViewPresenterController.presenter.components.CompoundComponent;
import c4sci.modelViewPresenterController.presenter.components.NoChildComponent;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
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
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.ActivityChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.BooleanValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FloatValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.IntegerValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.StringValueChange;
/**
 * This class implements default behavior on Commands : simply transferring form one interface to the other the effect of a {@link Command}.
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

		appendComponentHierarchy(step_chg.getStepElement(), _res, null, null);

		return _res;

	}

	@Override
	public List<ComponentChange> feedbackToElementSuppressedStepChange(
			ElementSuppressedStepChange step_chg) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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

	private void appendComponentHierarchy(StepElement step_elt, List<ComponentChange> res_list, Component parent_comp, ComponentChange previous_change){
		Component _current_comp = getCorrespondingComponent(step_elt);
		ComponentChange _previous_change = previous_change;
		//
		// first create the component corresponding to the current step element if it doesn't exist yet.
		//
		try{
			if (step_elt.containsProperValue()){
				_current_comp = new NoChildComponent();
			}
			else{
				_current_comp = new CompoundComponent();
			}
			if (parent_comp != null){
				try {
					parent_comp.addChildComponent(_current_comp);
				} catch (CannotPerformSuchChangeException _e) {
					// should not happen !
					_e.printStackTrace();
				}
			}
			//
			// update maps
			//
			setCorrespondence(_current_comp, step_elt);
			//
			// computes the creation sequence then appends it to the result sequence
			//
			List<ComponentChange> _creation_seq = computeCreationSequence(_current_comp, step_elt, previous_change);
			res_list.addAll(_creation_seq);
			_previous_change = _creation_seq.get(_creation_seq.size());
		}
		catch(NoSuchElementException _e){}
		//
		// passes through all children elements to create their components (and send sequences) if they do not exist
		//
		for(Iterator<StepElement> _it = step_elt.getSubElementsIterator();_it.hasNext();){
			appendComponentHierarchy(_it.next(), res_list, _current_comp, _previous_change);
		}
	}

	@Override
	public List<ComponentChange> computeCreationSequence(Component corresp_component, StepElement step_elt, ComponentChange previous_change) {
		// TODO Auto-generated method stub
		return null;
	}

}

