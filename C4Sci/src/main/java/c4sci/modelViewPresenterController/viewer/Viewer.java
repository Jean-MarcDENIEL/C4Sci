package c4sci.modelViewPresenterController.viewer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.MvpcLayer;
import c4sci.modelViewPresenterController.jobs.JobProcessor;
import c4sci.modelViewPresenterController.jobs.JobProcessorFactory;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFactory;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.creationChanges.CreateSpecialComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.creationChanges.CreateStandardComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.SpecialFeatureChange;
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

public abstract class Viewer implements MvpcLayer<ComponentChange, ComponentChange>{

	private ComponentFactory	componentFactory;
	Map<DataIdentity, Component>	componentIdentificationMap;
	
	public Viewer(){
		componentFactory = null;
		componentIdentificationMap = new ConcurrentHashMap<DataIdentity, Component>();
	}
	
	public ComponentFactory getComponentFactory() {
		return componentFactory;
	}

	public void setComponentFactory(ComponentFactory component_factory) {
		this.componentFactory = component_factory;
	}

	public JobProcessorFactory<ComponentChange, ComponentChange> getReactiveJobProcessorFactory() {
		return new JobProcessorFactory<ComponentChange, ComponentChange>();
	}

	public JobProcessorFactory<ComponentChange, ComponentChange> getFeedbackJobProcessorFactory() {
		JobProcessorFactory<ComponentChange, ComponentChange> _res = new JobProcessorFactory<ComponentChange, ComponentChange>();
		
		_res.addChangePerformingAbility(CreateSpecialComponentChange.class, new JobProcessor<ComponentChange, ComponentChange>() {	
			@Override
			public List<ComponentChange> processJob(ComponentChange processing_cmd) {
				feedbackToCreateSpecialComponentChange((CreateSpecialComponentChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(CreateStandardComponentChange.class, new JobProcessor<ComponentChange, ComponentChange>(){
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToCreateStandardComponentChange((CreateStandardComponentChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(ActivityChange.class, new JobProcessor<ComponentChange, ComponentChange>(){
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToActivityChange((ActivityChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(VisibilityChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToVisibilityChange((VisibilityChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(BooleanValueChange.class, new JobProcessor<ComponentChange, ComponentChange>(){
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToBooleanValueChange((BooleanValueChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(FloatValueChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToFloatValueChange((FloatValueChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(IntegerValueChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToIntegerValueChange((IntegerValueChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(FocusOrderChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToFocusOrderChange((FocusOrderChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(SpecialFeatureChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToSpecialFeatureChange((SpecialFeatureChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(TransparencyChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToTransparenceyChange((TransparencyChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(FontSizeChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToFontSizeChange((FontSizeChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(PositionChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToPositionChange((PositionChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(SizeChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToSizeChange((SizeChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(BackgroundColorChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToBackgroundColorChange((BackgroundColorChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(ForegroundColorChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToForegroundColorChange((ForegroundColorChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(FontColorChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToFontColorChange((FontColorChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(NameChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToNameChange((NameChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(DescriptionChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToDescriptionChange((DescriptionChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(LabelChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToLabelChange((LabelChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(FontTypeChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToFontTypeChange((FontTypeChange)processing_cmd);
				return null;
			}
		});
		
		_res.addChangePerformingAbility(FontStyleChange.class, new JobProcessor<ComponentChange, ComponentChange>() {
			@Override
			public List<ComponentChange> processJob(
					ComponentChange processing_cmd) {
				feedbackToFontStyleChange((FontStyleChange)processing_cmd);
				return null;
			}
		});
		
		return _res;
	}
	
	abstract void feedbackToCreateSpecialComponentChange(CreateSpecialComponentChange comp_chg);
	
	abstract void feedbackToCreateStandardComponentChange(CreateStandardComponentChange comp_chg);
	
	abstract void feedbackToActivityChange(ActivityChange comp_chg);
	
	abstract void feedbackToVisibilityChange(VisibilityChange comp_chg);
	
	abstract void feedbackToBooleanValueChange(BooleanValueChange comp_chg);
	
	abstract void feedbackToFloatValueChange(FloatValueChange comp_chg);
	
	abstract void feedbackToIntegerValueChange(IntegerValueChange comp_chg);
	
	abstract void feedbackToFocusOrderChange(FocusOrderChange comp_chg);
	
	abstract void feedbackToSpecialFeatureChange(SpecialFeatureChange comp_chg);
	
	abstract void feedbackToTransparenceyChange(TransparencyChange comp_chg);
	
	abstract void feedbackToFontSizeChange(FontSizeChange comp_chg);
	
	abstract void feedbackToPositionChange(PositionChange comp_chg);
	
	abstract void feedbackToSizeChange(SizeChange comp_chg);
	
	abstract void feedbackToBackgroundColorChange(BackgroundColorChange comp_chg);
	
	abstract void feedbackToForegroundColorChange(ForegroundColorChange comp_chg);
	
	abstract void feedbackToFontColorChange(FontColorChange comp_chg);
	
	abstract void feedbackToNameChange(NameChange comp_chg);
	
	abstract void feedbackToDescriptionChange(DescriptionChange comp_chg);
	
	abstract void feedbackToLabelChange(LabelChange comp_chg);
	
	abstract void feedbackToFontTypeChange(FontTypeChange comp_chg);
	
	abstract void feedbackToFontStyleChange(FontStyleChange comp_chg);

}
 