package c4sci.modelViewPresenterController.presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.DataIdentity;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.MvpcLayer;
import c4sci.modelViewPresenterController.jobs.JobProcessor;
import c4sci.modelViewPresenterController.jobs.JobProcessorFactory;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementActivatedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementAddedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementFeedbackModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementInactivatedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementReactiveModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementSuppressedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.StepBackwardStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.StepForwardStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.BooleanDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.FloatDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.IntegerDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.LabelDataElement;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.BooleanValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FloatValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.IntegerValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.LabelChange;

public abstract class Presenter implements MvpcLayer<ComponentChange, StepChange>{

	private Map<Component, StepElement> componentElementMap;
	private Map<StepElement, Component> elementComponentMap;

	
	public Presenter(){
		componentElementMap = new ConcurrentHashMap<Component, StepElement>();
		elementComponentMap	= new ConcurrentHashMap<StepElement, Component>();
	}


	public JobProcessorFactory<ComponentChange, StepChange> getReactiveJobProcessorFactory() {
		JobProcessorFactory<ComponentChange, StepChange> 	_reactive_job_processor_factory = new JobProcessorFactory<ComponentChange, StepChange>();
		
		_reactive_job_processor_factory.addChangePerformingAbility(BooleanValueChange.class, new JobProcessor<ComponentChange, StepChange>() {
			@Override
			public List<StepChange> processJob(ComponentChange processing_cmd) {
				return reactionToBooleanValueChange(processing_cmd);
			}
		});
		_reactive_job_processor_factory.addChangePerformingAbility(FloatValueChange.class, new JobProcessor<ComponentChange, StepChange>() {
			@Override
			public List<StepChange> processJob(ComponentChange processing_cmd) {
				return reactionToFloatValueChange(processing_cmd);
			}
		});
		_reactive_job_processor_factory.addChangePerformingAbility(IntegerValueChange.class, new JobProcessor<ComponentChange, StepChange>() {
			@Override
			public List<StepChange> processJob(ComponentChange processing_cmd) {
				return reactionToIntegerValueChange(processing_cmd);
			}
		});
		
		return _reactive_job_processor_factory;
	}

	public JobProcessorFactory<StepChange, ComponentChange> getFeedbackJobProcessorFactory() {
		JobProcessorFactory<StepChange, ComponentChange> _feedback_job_processor_factory = new JobProcessorFactory<StepChange, ComponentChange>();
		
		return _feedback_job_processor_factory;
	}
	
	/********************* FEEDBACK METHODS ***************************************************/
	public abstract List<ComponentChange> feedbackToElementActivatedStepChange(ElementActivatedStepChange step_chg);
	public abstract List<ComponentChange> feedbackToElementAddedStepChange(ElementAddedStepChange step_chg);
	public abstract List<ComponentChange> feedbackToElementSuppressedStepChange(ElementSuppressedStepChange step_chg);
	public abstract List<ComponentChange> feedbackToStepBackwardStepChange(StepBackwardStepChange step_chg);
	public abstract List<ComponentChange> feedbackToStepForwardStepChange(StepForwardStepChange step_chg);
	public abstract List<ComponentChange> feedbackToElementInactivatedStepChange(ElementInactivatedStepChange step_chg);
	public abstract List<ComponentChange> feedbackToElementFeedbackModificationStepChange(ElementFeedbackModificationStepChange step_chg);
	/**
	 * Feedback to an element having been modified :
	 * <ul>
	 * 	<li>BooleanDataElement
	 * </ul>. <br>
	 * This method contains the default behavior and can be overridden. 
	 * @param step_chg
	 * @return
	 */
	public List<ComponentChange> feedbackToElementFeedbackModificationStepChange(StepChange step_chg){
		Component _corresp_comp = elementComponentMap.get(step_chg.getStepElement());
		List<ComponentChange> _feedback_comp_chg = null;
		if (_corresp_comp != null){
			_feedback_comp_chg = new ArrayList<ComponentChange>();	
			computeDefaultFeedback(_corresp_comp, step_chg.getStepElement(), _feedback_comp_chg);
		}
		return _feedback_comp_chg;
	}
	
	/********************* REACTION METHODS **************************************************/
	
	/**
	 * Reaction to a boolean value change.<br>
	 * Default behavior is passing it to the "feedback side". It can be overridden to achieve special behavior.
	 * @param cmd_chg the {@link Command} indicating the value has been changed.
	 * @return The corresponding {@link StepChange} list.
	 */
	public List<StepChange> reactionToBooleanValueChange(ComponentChange cmd_chg){
		final String _str_val = Boolean.toString(((BooleanValueChange)cmd_chg).getChange());
		return oneElementReactiveNotificationChange(cmd_chg, _str_val);
	}
	/**
	 * Reaction to a float value change.<br>
	 * Default behavior is passing it to the "feedback side". It can be overridden to achieve special behavior.
	 * @param cmd_chg the {@link Command} indicating the value has been changed.
	 * @return The corresponding {@link StepChange} list.
	 */
	public List<StepChange> reactionToFloatValueChange(ComponentChange cmd_chg){
		final String _float_str = Float.toString(((FloatValueChange)cmd_chg).getChange());
		return oneElementReactiveNotificationChange(cmd_chg, _float_str);
	}
	/**
	 * Reaction to a boolean value change.<br>
	 * Default behavior is passing it to the "feedback side". It can be overridden to achieve special behavior.
	 * @param comp_chg the {@link Command} indicating the value has been changed.
	 * @return The corresponding {@link StepChange} list.
	 */
	public List<StepChange> reactionToIntegerValueChange(ComponentChange comp_chg){
		final String _int_str = Integer.toString(((IntegerValueChange)comp_chg).getChange());
		return oneElementReactiveNotificationChange(comp_chg, _int_str);
	}
	
	
	private List<StepChange> oneElementReactiveNotificationChange(ComponentChange comp_chg, String value_str){
		List<StepChange> _res = new ArrayList<StepChange>();
		StepElement _corresp_element = componentElementMap.get(comp_chg.getComponentIdentity());
		if (_corresp_element != null){
			_res.add(new ElementReactiveModificationStepChange(null, _corresp_element, value_str));
		}
		return _res;
	}
	
	private void computeDefaultFeedback(Component comp_elt, StepElement step_elt, List<ComponentChange> res_list){
		if (step_elt.containsProperValue()){
			String _elt_value = step_elt.getProperValue();
			if (step_elt instanceof BooleanDataElement){
				res_list.add(new BooleanValueChange(comp_elt, Boolean.parseBoolean(_elt_value), null));
			}
			if (step_elt instanceof FloatDataElement){
				res_list.add(new FloatValueChange(comp_elt, Float.parseFloat(_elt_value), null));
			}
			if (step_elt instanceof IntegerDataElement){
				res_list.add(new IntegerValueChange(comp_elt, Integer.parseInt(_elt_value), null));
			}
			if (step_elt instanceof LabelDataElement){
				res_list.add(new LabelChange(comp_elt, new InternationalizableTerm(_elt_value), null));
			}
		}
		Iterator<StepElement> _it = step_elt.getSubElementsIterator();
		while (_it.hasNext()){
			StepElement _sub_elt = _it.next();
			Component _sub_id = elementComponentMap.get(_sub_elt);
			if (_sub_id != null){
				computeDefaultFeedback(_sub_id, _sub_elt, res_list);
			}
		}
	}
}
