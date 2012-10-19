package c4sci.modelViewPresenterController.viewer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.MvpcLayer;
import c4sci.modelViewPresenterController.jobs.JobProcessor;
import c4sci.modelViewPresenterController.jobs.JobProcessorFactory;
import c4sci.modelViewPresenterController.viewerPresenterInterface.CannotCreateSuchComponentException;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.SpecialFeatureChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.lifeCycleChanges.CreateSpecialComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.lifeCycleChanges.CreateStandardComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.lifeCycleChanges.SuppressComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.ActivityChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.BackgroundColorChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.BooleanValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.DescriptionChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FloatValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FocusOrderChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FontColorChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FontSizeChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FontStyleChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FontTypeChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.ForegroundColorChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.IntegerValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.LabelChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.NameChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.PositionChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.SizeChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.TransparencyChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.VisibilityChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.sessionChanges.BeginSessionChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.sessionChanges.EndSessionChange;

public abstract class Viewer extends MvpcLayer<ComponentChange, ComponentChange>{


	private Map<DataIdentity, ComponentSupport>	componentIdentificationMap;


	public Viewer(){
		componentIdentificationMap = new ConcurrentHashMap<DataIdentity, ComponentSupport>();
	}


	/**
	 * 
	 * @param comp_id the identity of the component the method retrieves. 
	 * @return The component which {@link DataIdentity} equals the passed argument or <i>null</i> if there's no one.
	 */
	public ComponentSupport getComponent(DataIdentity comp_id){
		return componentIdentificationMap.get(comp_id);
	}
	/**
	 * Records the component so that it can be retrieved through the {@link #getComponent(DataIdentity)} method.
	 * @param comp_to_store
	 */
	public void storeComponent(ComponentSupport comp_to_store){
		componentIdentificationMap.put(comp_to_store.getSupportedComponent().getIdentity(), comp_to_store);
	}

	public JobProcessorFactory<ComponentChange, ComponentChange> getReactiveJobProcessorFactory() {
		return new JobProcessorFactory<ComponentChange, ComponentChange>();
	}


	public JobProcessorFactory<ComponentChange, ComponentChange> getFeedbackJobProcessorFactory() {
		JobProcessorFactory<ComponentChange, ComponentChange> _res = new JobProcessorFactory<ComponentChange, ComponentChange>();

		_res.addChangePerformingAbility(CreateSpecialComponentChange.class, new JobProcessor<ComponentChange, ComponentChange>() {	
			@Override
			public List<ComponentChange> processJob(ComponentChange processing_cmd) {
				if(CreateSpecialComponentChange.class.isInstance(processing_cmd)){
					feedbackToCreateSpecialComponentChange((CreateSpecialComponentChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(CreateStandardComponentChange.class, new JobProcessor<ComponentChange, ComponentChange>(){
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (CreateStandardComponentChange.class.isInstance(processing_cmd )){
					feedbackToCreateStandardComponentChange((CreateStandardComponentChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(ActivityChange.class, new JobProcessor<ComponentChange, ComponentChange>(){
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (ActivityChange.class.isInstance(processing_cmd )){
					feedbackToActivityChange((ActivityChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(VisibilityChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (VisibilityChange.class.isInstance(processing_cmd )){
					feedbackToVisibilityChange((VisibilityChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(BooleanValueChange.class, new JobProcessor<ComponentChange, ComponentChange>(){
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (BooleanValueChange.class.isInstance(processing_cmd )){
					feedbackToBooleanValueChange((BooleanValueChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(FloatValueChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (FloatValueChange.class.isInstance(processing_cmd )){
					feedbackToFloatValueChange((FloatValueChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(IntegerValueChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (IntegerValueChange.class.isInstance(processing_cmd )){
					feedbackToIntegerValueChange((IntegerValueChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(FocusOrderChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (FocusOrderChange.class.isInstance(processing_cmd )){
					feedbackToFocusOrderChange((FocusOrderChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(SpecialFeatureChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (SpecialFeatureChange.class.isInstance(processing_cmd )){
					feedbackToSpecialFeatureChange((SpecialFeatureChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(TransparencyChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (TransparencyChange.class.isInstance(processing_cmd )){
					feedbackToTransparenceyChange((TransparencyChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(FontSizeChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (FontSizeChange.class.isInstance(processing_cmd )){
					feedbackToFontSizeChange((FontSizeChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(PositionChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (PositionChange.class.isInstance(processing_cmd )){
					feedbackToPositionChange((PositionChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(SizeChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (SizeChange.class.isInstance(processing_cmd )){
					feedbackToSizeChange((SizeChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(BackgroundColorChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (BackgroundColorChange.class.isInstance(processing_cmd )){
					feedbackToBackgroundColorChange((BackgroundColorChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(ForegroundColorChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (ForegroundColorChange.class.isInstance(processing_cmd )){
					feedbackToForegroundColorChange((ForegroundColorChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(FontColorChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (FontColorChange.class.isInstance(processing_cmd )){
					feedbackToFontColorChange((FontColorChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(NameChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (NameChange.class.isInstance(processing_cmd )){
					feedbackToNameChange((NameChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(DescriptionChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (DescriptionChange.class.isInstance(processing_cmd )){
					feedbackToDescriptionChange((DescriptionChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(LabelChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (LabelChange.class.isInstance(processing_cmd )){
					feedbackToLabelChange((LabelChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(FontTypeChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (FontTypeChange.class.isInstance(processing_cmd )){
					feedbackToFontTypeChange((FontTypeChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(FontStyleChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (FontStyleChange.class.isInstance(processing_cmd )){
					feedbackToFontStyleChange((FontStyleChange)processing_cmd);
				}
				return null;
			}
		});

		_res.addChangePerformingAbility(SuppressComponentChange.class, new JobProcessor<ComponentChange, ComponentChange>() {

			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				if (SuppressComponentChange.class.isInstance(processing_cmd )){
					feedbackToSuppressComponentChange((SuppressComponentChange)processing_cmd);
				}
				return null;
			}
		});
		
		return _res;
	}
	/**
	 * Treats {@link CreateSpecialComponentChange} coming from the "feedback" side.
	 * @param comp_chg The {@link Command} to treat.
	 */
	void feedbackToCreateSpecialComponentChange(CreateSpecialComponentChange comp_chg){
		try {
			ComponentSupport _created_comp = treatFeedbackCreateSpecialComponent(comp_chg);
			componentIdentificationMap.put(comp_chg.getComponentIdentity(), _created_comp);
		} catch (CannotCreateSuchComponentException _e) {
			treatUnableToCreateSpecialComponent(comp_chg);
		}
	}
	/**
	 * Treats a {@link CreateStandardComponentChange} coming from the "feedback" side.
	 * @param comp_chg contains all the data needed to create the result {@link Component} including {@link ComponentChange#getComponentIdentity()}.
	 */
	public void feedbackToCreateStandardComponentChange(CreateStandardComponentChange comp_chg) {
		try {
			ComponentSupport _created_comp = treatFeedbackCreateStandardComponent(comp_chg);
			componentIdentificationMap.put(comp_chg.getComponentIdentity(), _created_comp);
		} catch (CannotCreateSuchComponentException _e) {
			treatUnableToCreateStandardComponent(comp_chg);
		}
	}

	/**
	 * Contains the treatment to run in the case a {@link StandardComponentSet} {@link Component} cannot be created.
	 */
	protected abstract void treatUnableToCreateStandardComponent(CreateStandardComponentChange comp_chg);
	/**
	 * Contains the treatment to run in the case a special {@link Component} cannot be created.
	 */
	protected abstract void treatUnableToCreateSpecialComponent(CreateSpecialComponentChange comp_chg);
	/**
	 * Contains the treatment to run in the case a {@link ComponentChange} cannot be properly processed.
	 * @param comp_chg The message that cannot be properly processed
	 * @param compl_msg A complementary info message.
	 */
	protected abstract void treatUnableToProcessCommand(ComponentChange comp_chg, String compl_msg);
	/**
	 * Contains the treatment to run in the case a {@link ComponentChange} cannot be properly processed.
	 * @param comp_chg The message that cannot be properly processed
	 */
	protected abstract void treatUnableToProcessCommand(ComponentChange comp_chg);
	/**
	 * 
	 * @param comp_chg
	 * @return a {@link ComponentSupport}
	 * @throws CannotCreateSuchComponentException in case it cannot create a {@link ComponentSupport}
	 */
	protected abstract ComponentSupport treatFeedbackCreateStandardComponent(CreateStandardComponentChange comp_chg) throws CannotCreateSuchComponentException;
	
	protected abstract ComponentSupport treatFeedbackCreateSpecialComponent(CreateSpecialComponentChange comp_chg) throws CannotCreateSuchComponentException;

	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg {@link ActivityChange} {@link Command} to treat.
	 */
	protected abstract void feedbackToActivityChange(ActivityChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg {@link VisibilityChange} {@link Command} to treat.
	 */
	protected abstract void feedbackToVisibilityChange(VisibilityChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg {@link BooleanValueChange} {@link Command} to treat.
	 */
	protected abstract void feedbackToBooleanValueChange(BooleanValueChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg {@link FloatValueChange} to treat.
	 */
	protected abstract void feedbackToFloatValueChange(FloatValueChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg An {@link IntegerValueChange} to treat.
	 */
	protected abstract void feedbackToIntegerValueChange(IntegerValueChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg A {@link FocusOrderChange} to treat.
	 */
	protected abstract void feedbackToFocusOrderChange(FocusOrderChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg A {@link SpecialFeatureChange} to treat.
	 */
	protected abstract void feedbackToSpecialFeatureChange(SpecialFeatureChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg A {@link TransparencyChange} to treat.
	 */
	protected abstract void feedbackToTransparenceyChange(TransparencyChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg A {@link FontSizeChange} to treat.
	 */
	protected abstract void feedbackToFontSizeChange(FontSizeChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg a {@link PositionChange} to treat.
	 */
	protected abstract void feedbackToPositionChange(PositionChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg A {@link SizeChange} to treat.
	 */
	protected abstract void feedbackToSizeChange(SizeChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg A {@link BackgroundColorChange} to treat.
	 */
	protected abstract void feedbackToBackgroundColorChange(BackgroundColorChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg A {@link ForegroundColorChange} to treat.
	 */
	protected abstract void feedbackToForegroundColorChange(ForegroundColorChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg A {@link FontColorChange} to treat.
	 */
	protected abstract void feedbackToFontColorChange(FontColorChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg A {@link NameChange} to treat.
	 */
	protected abstract void feedbackToNameChange(NameChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg A {@link DescriptionChange} to treat.
	 */
	protected abstract void feedbackToDescriptionChange(DescriptionChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg A {@link LabelChange} to treat.
	 */
	protected abstract void feedbackToLabelChange(LabelChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg A {@link FontTypeChange} to treat.
	 */
	protected abstract void feedbackToFontTypeChange(FontTypeChange comp_chg);
	/**
	 * Treats a {@link Command} coming from the feedback side.
	 * @param comp_chg A {@link FontStyleChange} to treat.
	 */
	protected abstract void feedbackToFontStyleChange(FontStyleChange comp_chg);
	/**
	 * Indicates the beginning of several changes to process.
	 * @param comp_chg unused at the moment.
	 */
	protected abstract void feedbackToBeginSessionChange(BeginSessionChange comp_chg);
	/**
	 * Indicates the end of several changes that have been processed.
	 * @param comp_chg unused at the moment
	 */
	protected abstract void feedbackToEndSessionChange(EndSessionChange comp_chg);
	/**
	 * Treats the removal of a {@link Component} from the GUI.<br>
	 * Does not concern its sub components.
	 * @param comp_chg
	 */
	protected abstract void feedbackToSuppressComponentChange(SuppressComponentChange comp_chg);
	
}
