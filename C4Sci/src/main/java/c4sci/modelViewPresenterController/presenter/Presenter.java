package c4sci.modelViewPresenterController.presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.MvpcLayer;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.jobs.JobProcessor;
import c4sci.modelViewPresenterController.jobs.JobProcessorFactory;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementReactiveModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.BooleanDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.FloatDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.IntegerDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.LabelDataElement;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.BooleanValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FloatValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.IntegerValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.LabelChange;

public class Presenter implements MvpcLayer<ComponentChange, StepChange>{

	private Map<DataIdentity, StepElement> componentElementMap;
	private Map<StepElement, DataIdentity> elementComponentMap;
	
	public Presenter(){
		componentElementMap = new ConcurrentHashMap<DataIdentity, StepElement>();
		elementComponentMap	= new ConcurrentHashMap<StepElement, DataIdentity>();
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
	
	/**
	 * Reaction to a boolean value change.<br>
	 * Default behavior is passing it to the "feedback side". It can be overridden to achieve special behavior.
	 * @param cmd_chg the {@link Command} indicating the value has been changed.
	 * @return The corresponding {@link StepChange} list.
	 */
	public List<StepChange> reactionToBooleanValueChange(ComponentChange cmd_chg){
		String _str_val = Boolean.toString(((BooleanValueChange)cmd_chg).getChange());
		return oneElementReactiveNotificationChange(cmd_chg, _str_val);
	}
	/**
	 * Reaction to a float value change.<br>
	 * Default behavior is passing it to the "feedback side". It can be overridden to achieve special behavior.
	 * @param cmd_chg the {@link Command} indicating the value has been changed.
	 * @return The corresponding {@link StepChange} list.
	 */
	public List<StepChange> reactionToFloatValueChange(ComponentChange cmd_chg){
		String _float_str = Float.toString(((FloatValueChange)cmd_chg).getChange());
		return oneElementReactiveNotificationChange(cmd_chg, _float_str);
	}
	/**
	 * Reaction to a boolean value change.<br>
	 * Default behavior is passing it to the "feedback side". It can be overridden to achieve special behavior.
	 * @param comp_chg the {@link Command} indicating the value has been changed.
	 * @return The corresponding {@link StepChange} list.
	 */
	public List<StepChange> reactionToIntegerValueChange(ComponentChange comp_chg){
		String _int_str = Integer.toString(((IntegerValueChange)comp_chg).getChange());
		return oneElementReactiveNotificationChange(comp_chg, _int_str);
	}
	
	
	private List<StepChange> oneElementReactiveNotificationChange(ComponentChange comp_chg, String value_str){
		List<StepChange> _res = new ArrayList<StepChange>();
		StepElement _correspElement = componentElementMap.get(comp_chg.getComponentIdentity());
		if (_correspElement != null){
			_res.add(new ElementReactiveModificationStepChange(null, _correspElement, value_str));
		}
		return _res;
	}
	
	public List<ComponentChange> feedbackToElementFeedbackModificationStepChange(StepChange step_chg){
		DataIdentity _corresp_id = elementComponentMap.get(step_chg.getStepElement());
		List<ComponentChange> _feedback_comp_chg = null;
		if (_corresp_id != null){
			_feedback_comp_chg = new ArrayList<ComponentChange>();	
			computeDefaultFeedback(_corresp_id, step_chg.getStepElement(), _feedback_comp_chg);
		}
		return _feedback_comp_chg;
	}
	
	private void computeDefaultFeedback(DataIdentity comp_id, StepElement step_elt, List<ComponentChange> res_list){
		if (step_elt.containsProperValue()){
			String _elt_value = step_elt.getProperValue();
			if (step_elt instanceof BooleanDataElement){
				res_list.add(new BooleanValueChange(comp_id, Boolean.parseBoolean(_elt_value), null));
			}
			if (step_elt instanceof FloatDataElement){
				res_list.add(new FloatValueChange(comp_id, Float.parseFloat(_elt_value), null));
			}
			if (step_elt instanceof IntegerDataElement){
				res_list.add(new IntegerValueChange(comp_id, Integer.parseInt(_elt_value), null));
			}
			if (step_elt instanceof LabelDataElement){
				res_list.add(new LabelChange(comp_id, _elt_value, null));
			}
		}

		Iterator<StepElement> _it = step_elt.getSubElementsIterator();
		while (_it.hasNext()){
			StepElement _sub_elt = _it.next();
			DataIdentity _sub_id = elementComponentMap.get(_sub_elt);
			if (_sub_id != null){
				computeDefaultFeedback(_sub_id, _sub_elt, res_list);
			}
		}
		// ICI
	}
	
}
