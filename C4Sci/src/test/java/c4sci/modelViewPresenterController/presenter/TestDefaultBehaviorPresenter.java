package c4sci.modelViewPresenterController.presenter;

import java.util.List;

import org.junit.Test;

import c4sci.data.dataParameters.GenericDataParameter;
import c4sci.data.dataParameters.singleValueModifiables.FloatModifiable;
import c4sci.data.dataParameters.singleValueModifiables.NoWhiteSpaceStringModifiable;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.jobs.JobConsumerThread;
import c4sci.modelViewPresenterController.jobs.JobProcessorFactory;
import c4sci.modelViewPresenterController.jobs.RequestResultInterface;
import c4sci.modelViewPresenterController.jobs.schedulers.SequentialJobScheduler;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementAddedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.StepForwardStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.EditableDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.ScalableDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.FloatDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.LabelDataElement;
import c4sci.modelViewPresenterController.viewer.Viewer;
import c4sci.modelViewPresenterController.viewer.swingImplementation.SwingViewer;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

public class TestDefaultBehaviorPresenter {

	@Test
	public void test() {
		final int onesecond = 1000;
		Presenter _presenter = new DefaultBehaviorPresenter();
		Viewer _viewer = new SwingViewer();
		
		waitAndText(onesecond, "begin threads");

		JobProcessorFactory<StepChange, ComponentChange> _feedback_presenter_factory = _presenter.getFeedbackJobProcessorFactory();

		JobProcessorFactory<ComponentChange, ComponentChange> _feedback_viewer_factory = _viewer.getFeedbackJobProcessorFactory();
		
		_viewer.getFeedbackInterface().setRequestQueueScheduler(new SequentialJobScheduler<ComponentChange>());
		_viewer.getFeedbackInterface().setResultQueueScheduler(new SequentialJobScheduler<ComponentChange>());
		_viewer.getReactiveInterface().setRequestQueueScheduler(new SequentialJobScheduler<ComponentChange>());
		_viewer.getReactiveInterface().setResultQueueScheduler(new SequentialJobScheduler<ComponentChange>());
		
		_presenter.getFeedbackInterface().setRequestQueueScheduler(new SequentialJobScheduler<StepChange>());
		_presenter.getFeedbackInterface().setResultQueueScheduler(new SequentialJobScheduler<StepChange>());
		_presenter.getReactiveInterface().setRequestQueueScheduler(new SequentialJobScheduler<ComponentChange>());
		_presenter.getReactiveInterface().setResultQueueScheduler(new SequentialJobScheduler<ComponentChange>());

		JobConsumerThread<StepChange, ComponentChange> _feedback_presenter_thread = 
				new JobConsumerThread<StepChange, ComponentChange>(_presenter.getFeedbackInterface(), _viewer.getFeedbackInterface()){
			@Override
			public StepChange pullJobToProcess() {return pullResultJobToProcess();}
			@Override
			public void pushProcessedJob(List<ComponentChange> job_res) {pushJobResultAsResult(job_res);}
		};
		_feedback_presenter_thread.associateProcessors(_feedback_presenter_factory);

		JobConsumerThread<ComponentChange, ComponentChange> _feedback_viewer_thread = 
				new JobConsumerThread<ComponentChange, ComponentChange>(_viewer.getFeedbackInterface(), null) {

			@Override
			public ComponentChange pullJobToProcess() {
				return pullResultJobToProcess();
			}

			@Override
			public void pushProcessedJob(List<ComponentChange> job_res) {
				pushJobResultAsRequest(job_res);

			}
		};
		_feedback_viewer_thread.associateProcessors(_feedback_viewer_factory);

		_feedback_presenter_thread.start();
		_feedback_viewer_thread.start();
		
		RequestResultInterface<StepChange> _req_interface = _presenter.getFeedbackInterface();
		
		waitAndText(onesecond, "Panel");
		GenericDataParameter<NoWhiteSpaceStringModifiable> _panel_elt_1 = 
				new GenericDataParameter<NoWhiteSpaceStringModifiable>(
						new NoWhiteSpaceStringModifiable(), 
						"panel", 
						new InternationalizableTerm("Step 1 panel"), 
						new InternationalizableTerm("Step 1 panel"));
		StepElement _panel_1_step_elt = new LabelDataElement(_panel_elt_1);
		_req_interface.pushResult(new StepForwardStepChange(null, _panel_1_step_elt));
		
		waitAndText(onesecond, "Float 1");
		GenericDataParameter<FloatModifiable>	_data_elt_1 = new GenericDataParameter<FloatModifiable>(new FloatModifiable(), "data_1", new InternationalizableTerm("Data 1"), new InternationalizableTerm("Data 1 : float"));
		_data_elt_1.accesValue().setFloatValue(1000.5f);
		StepElement _step_elt_1 = new EditableDataElement(new ScalableDataElement(new FloatDataElement(_data_elt_1), UnitScales.createMeterUnitSCales()));
		//StepElement _step_elt_1 = new ComputedDataElement(new FloatDataElement(_data_elt_1));
		_req_interface.pushResult(new ElementAddedStepChange(null, _step_elt_1));
		
		//_req_interface.pushResult(new InternationalizableTermElementModificationStepChange(null, _step_elt_1, new InternationalizableTerm("float 0.05")));
		
		
		waitAndText(onesecond*4, "end : press a key");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_feedback_presenter_thread.setToDieUnused();
		_feedback_viewer_thread.setToDieUnused();
	}

	public static void waitAndText(int milli_, String txt_){
		try {
			Thread.sleep(milli_);
		} catch (InterruptedException _e) {
			// TODO Auto-generated catch block
			_e.printStackTrace();
		}
		System.out.println(txt_);
	}
}
