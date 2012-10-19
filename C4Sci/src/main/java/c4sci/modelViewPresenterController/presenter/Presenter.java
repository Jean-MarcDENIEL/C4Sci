package c4sci.modelViewPresenterController.presenter;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.modelViewPresenterController.MvpcLayer;
import c4sci.modelViewPresenterController.jobs.JobProcessor;
import c4sci.modelViewPresenterController.jobs.JobProcessorFactory;
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
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.BooleanValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FloatValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.IntegerValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.StringValueChange;

public abstract class Presenter extends MvpcLayer<ComponentChange, StepChange>{

	private Map<Component, StepElement> componentElementMap;
	private Map<StepElement, Component> elementComponentMap;


	public Presenter(){
		componentElementMap = new ConcurrentHashMap<Component, StepElement>();
		elementComponentMap	= new ConcurrentHashMap<StepElement, Component>();
	}

	/**
	 * 
	 * @throws NoSuchElementException in the case there is no correspondence
	 */
	public Component getCorrespondingComponent(StepElement step_elt){
		Component _res= elementComponentMap.get(step_elt);
		if (_res == null){
			throw new NoSuchElementException();
		}
		return _res;
	}

	public void setCorrespondence(Component comp_, StepElement step_elt){
		componentElementMap.put(comp_, step_elt);
		elementComponentMap.put(step_elt, comp_);
	}

	public void suppressCorrespondence(Component comp_, StepElement step_elt){
		componentElementMap.remove(comp_);
		elementComponentMap.remove(step_elt);
	}

	/**
	 * 
	 * @throws NoSuchElementException in the case there is no correspondence
	 */
	public StepElement getCorrespondingElement(Component comp_){
		StepElement _res = componentElementMap.get(comp_);
		if (_res == null){
			throw new NoSuchElementException();
		}
		return _res;
	}
	
	class ReactiveBooleanValueChangeJobProcessor extends JobProcessor<ComponentChange, StepChange> {
		@Override
		public List<StepChange> processJob(ComponentChange processing_cmd) {
			if (BooleanValueChange.class.isInstance(processing_cmd)){
				return reactionToBooleanValueChange((BooleanValueChange)processing_cmd);
			}
			return null;
		}
	};
	
	class ReactiveFloatValueChangeJobProcessor extends JobProcessor<ComponentChange, StepChange> {
		@Override
		public List<StepChange> processJob(ComponentChange processing_cmd) {
			if (FloatValueChange.class.isInstance(processing_cmd)){
				return reactionToFloatValueChange((FloatValueChange)processing_cmd);
			}
			return null;
		}
	};
	
	class ReactiveIntegerValueChangeJobProcessor extends JobProcessor<ComponentChange, StepChange> {
		@Override
		public List<StepChange> processJob(ComponentChange processing_cmd) {
			if(IntegerValueChange.class.isInstance(processing_cmd)){
				return reactionToIntegerValueChange((IntegerValueChange)processing_cmd);
			}
			return null;
		}
	};
	
	class ReactiveStringValueChangeJobProcessor extends JobProcessor<ComponentChange, StepChange> {

		@Override
		public List<StepChange> processJob(ComponentChange processing_cmd) {
			if (StringValueChange.class.isInstance(processing_cmd)){
				return reactionToStringValueChange((StringValueChange)processing_cmd);
			}
			return null;
		}
	};
	 


	public JobProcessorFactory<ComponentChange, StepChange> getReactiveJobProcessorFactory() {
		JobProcessorFactory<ComponentChange, StepChange> 	_reactive_job_processor_factory = new JobProcessorFactory<ComponentChange, StepChange>();

		_reactive_job_processor_factory.addChangePerformingAbility(BooleanValueChange.class, new ReactiveBooleanValueChangeJobProcessor());
		_reactive_job_processor_factory.addChangePerformingAbility(FloatValueChange.class, new ReactiveFloatValueChangeJobProcessor());
		_reactive_job_processor_factory.addChangePerformingAbility(IntegerValueChange.class, new ReactiveIntegerValueChangeJobProcessor());
		_reactive_job_processor_factory.addChangePerformingAbility(StringValueChange.class, new ReactiveStringValueChangeJobProcessor());

		return _reactive_job_processor_factory;
	}

	public class FeedbackElementActivatedJobProcessor extends JobProcessor<StepChange, ComponentChange> {
		@Override
		public List<ComponentChange> processJob(StepChange processing_cmd) {
			if (ElementActivatedStepChange.class.isInstance(processing_cmd)){
				return feedbackToElementActivatedStepChange((ElementActivatedStepChange)processing_cmd);
			}
			return null;
		}
	};
	
	public class FeedbackElementAddedJobProcessor extends JobProcessor<StepChange, ComponentChange> {
		@Override
		public List<ComponentChange> processJob(StepChange processing_cmd) {
			if (ElementAddedStepChange.class.isInstance(processing_cmd)){
				return feedbackToElementAddedStepChange((ElementAddedStepChange)processing_cmd);
			}
			return null;
		}
	};
	
	public class FeedbackElementsuppressedJobProcessor extends JobProcessor<StepChange, ComponentChange> {
		@Override
		public List<ComponentChange> processJob(StepChange processing_cmd) {
			if (ElementSuppressedStepChange.class.isInstance(processing_cmd)){
				return feedbackToElementSuppressedStepChange((ElementSuppressedStepChange)processing_cmd);
			}
			return null;
		}
	};
	
	public class FeedbackStepBackwardJobProcessor extends JobProcessor<StepChange, ComponentChange> {
		@Override
		public List<ComponentChange> processJob(StepChange processing_cmd) {
			if (StepBackwardStepChange.class.isInstance(processing_cmd)){
				return feedbackToStepBackwardStepChange((StepBackwardStepChange)processing_cmd);
			}
			return null;
		}
	};
	
	public class FeedbackStepForwardJobProcessor extends JobProcessor<StepChange, ComponentChange> {
		@Override
		public List<ComponentChange> processJob(StepChange processing_cmd) {
			if (StepForwardStepChange.class.isInstance(processing_cmd)){
				return feedbackToStepForwardStepChange((StepForwardStepChange)processing_cmd);
			}
			return null;
		}
	};
	
	public class FeedbackElementInactivatedJobProcessor extends JobProcessor<StepChange, ComponentChange> {
		@Override
		public List<ComponentChange> processJob(StepChange processing_cmd) {
			if (ElementInactivatedStepChange.class.isInstance(processing_cmd)){
				return feedbackToElementInactivatedStepChange((ElementInactivatedStepChange)processing_cmd);
			}
			return null;
		}
	};
	
	public class FeedbackBooleanModificationJobProcessor extends JobProcessor<StepChange, ComponentChange> {
		@Override
		public List<ComponentChange> processJob(StepChange processing_cmd) {
			if (BooleanElementModificationStepChange.class.isInstance(processing_cmd)){
				return feedbackToBooleanElementModificationStepChange((BooleanElementModificationStepChange)processing_cmd);
			}
			return null;
		}
	};
	
	public JobProcessorFactory<StepChange, ComponentChange> getFeedbackJobProcessorFactory() {
		JobProcessorFactory<StepChange, ComponentChange> _feedback_job_processor_factory = new JobProcessorFactory<StepChange, ComponentChange>();

		_feedback_job_processor_factory.addChangePerformingAbility(ElementActivatedStepChange.class, new FeedbackElementActivatedJobProcessor());
		_feedback_job_processor_factory.addChangePerformingAbility(ElementAddedStepChange.class, new FeedbackElementAddedJobProcessor());		
		_feedback_job_processor_factory.addChangePerformingAbility(ElementSuppressedStepChange.class, new FeedbackElementsuppressedJobProcessor());
		_feedback_job_processor_factory.addChangePerformingAbility(StepBackwardStepChange.class, new FeedbackStepBackwardJobProcessor());
		_feedback_job_processor_factory.addChangePerformingAbility(StepForwardStepChange.class, new FeedbackStepForwardJobProcessor());
		_feedback_job_processor_factory.addChangePerformingAbility(ElementInactivatedStepChange.class, new FeedbackElementInactivatedJobProcessor());
		_feedback_job_processor_factory.addChangePerformingAbility(BooleanElementModificationStepChange.class, new FeedbackBooleanModificationJobProcessor());
		
		_feedback_job_processor_factory.addChangePerformingAbility(FloatElementModificationStepChange.class, new JobProcessor<StepChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(StepChange processing_cmd) {
				if (FloatElementModificationStepChange.class.isInstance(processing_cmd)){
					return feedbackToFloatElementModificationStepChange((FloatElementModificationStepChange)processing_cmd);
				}
				return null;
			}
		});
		
		_feedback_job_processor_factory.addChangePerformingAbility(IntegerElementModificationStepChange.class, new JobProcessor<StepChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(StepChange processing_cmd) {
				if (IntegerElementModificationStepChange.class.isInstance(processing_cmd)){
					return feedbackToIntegerElementModificationStepChange((IntegerElementModificationStepChange)processing_cmd);
				}
				return null;
			}
		});
		
		_feedback_job_processor_factory.addChangePerformingAbility(InternationalizableTermElementModificationStepChange.class, new JobProcessor<StepChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(StepChange processing_cmd) {
				if (InternationalizableTermElementModificationStepChange.class.isInstance(processing_cmd)){
					return feedbackToInternationalizableTermElementModificationStepChange((InternationalizableTermElementModificationStepChange)processing_cmd);
				}
				return null;
			}
		});
		
		_feedback_job_processor_factory.addChangePerformingAbility(ThreeDimensionalElementModificationStepChange.class, new JobProcessor<StepChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(StepChange processing_cmd) {
				if (ThreeDimensionalElementModificationStepChange.class.isInstance(processing_cmd)){
					return feedbackToThreeDimensionalElementModificationStepChange((ThreeDimensionalElementModificationStepChange)processing_cmd);
				}
				return null;
			}
		});
		
		_feedback_job_processor_factory.addChangePerformingAbility(TwoDimensionalElementModificationStepChange.class, new JobProcessor<StepChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(StepChange processing_cmd) {
				if (TwoDimensionalElementModificationStepChange.class.isInstance(processing_cmd)){
					return feedbackToTwoDimensionElementModificationStepChange((TwoDimensionalElementModificationStepChange)processing_cmd);
				}
				return null;
			}
		});

		return _feedback_job_processor_factory;
	}

	/********************* FEEDBACK METHODS ***************************************************/
	public abstract List<ComponentChange> feedbackToElementActivatedStepChange(ElementActivatedStepChange step_chg);
	public abstract List<ComponentChange> feedbackToElementAddedStepChange(ElementAddedStepChange step_chg);
	public abstract List<ComponentChange> feedbackToElementSuppressedStepChange(ElementSuppressedStepChange step_chg);
	public abstract List<ComponentChange> feedbackToStepBackwardStepChange(StepBackwardStepChange step_chg);
	public abstract List<ComponentChange> feedbackToStepForwardStepChange(StepForwardStepChange step_chg);
	public abstract List<ComponentChange> feedbackToElementInactivatedStepChange(ElementInactivatedStepChange step_chg);
	public abstract List<ComponentChange> feedbackToBooleanElementModificationStepChange(BooleanElementModificationStepChange step_chg);
	public abstract List<ComponentChange> feedbackToFloatElementModificationStepChange(FloatElementModificationStepChange step_chg);
	public abstract List<ComponentChange> feedbackToIntegerElementModificationStepChange(IntegerElementModificationStepChange step_chg);
	public abstract List<ComponentChange> feedbackToInternationalizableTermElementModificationStepChange(InternationalizableTermElementModificationStepChange step_chg);
	public abstract List<ComponentChange> feedbackToThreeDimensionalElementModificationStepChange(ThreeDimensionalElementModificationStepChange step_chg);
	public abstract List<ComponentChange> feedbackToTwoDimensionElementModificationStepChange(TwoDimensionalElementModificationStepChange step_chg);

	/********************* REACTION METHODS **************************************************/
	public abstract List<StepChange> reactionToBooleanValueChange(BooleanValueChange comp_chg);
	public abstract List<StepChange> reactionToFloatValueChange(FloatValueChange comp_chg);
	public abstract List<StepChange> reactionToIntegerValueChange(IntegerValueChange comp_chg);
	public abstract List<StepChange> reactionToStringValueChange(StringValueChange comp_chg);

	/********************* FACTORY METHODS ***************************************************/
	/**
	 * 
	 * Computes the {@link Command} sequence corresponding to the creation of the {@link StepElement}.<br>
	 * This method only works on the current {@link StepElement} level.<br>
	 * 
	 * @param corresp_component the {@link Component} corresponding to the {@link StepElement} argument.
	 * @param step_elt The {@link StepElement} to analyze.
	 * @param previous_change The {@link Command} that is previous all {@link Command commands} of the result
	 * @return a list of {@link ComponentChange}
	 */
	//public abstract List<ComponentChange> computeCreationSequence(Component corresp_component, StepElement step_elt, ComponentChange previous_change);
	/**
	 * Computes the {@link Command} sequence corresponding to the suppression of the {@link StepElement}.<br>
	 * This method only works on the current {@link StepElement} level.<br>
	 * 
	 */
	//public abstract List<ComponentChange> computeSuppressionSequence(Component corresp_component, StepElement step_elt, ComponentChange previous_change);

}
