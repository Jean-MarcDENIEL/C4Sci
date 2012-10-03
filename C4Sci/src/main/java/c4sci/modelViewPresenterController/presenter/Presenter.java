package c4sci.modelViewPresenterController.presenter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.MvpcLayer;
import c4sci.modelViewPresenterController.jobs.JobConsumerThread;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

public class Presenter implements MvpcLayer<ComponentChange, StepChange>{

	private Map<DataIdentity, StepElement> componentElementMap;
	private Map<StepElement, DataIdentity> elementComponentMap;
	
	public Presenter(){
		componentElementMap = new ConcurrentHashMap<DataIdentity, StepElement>();
		elementComponentMap	= new ConcurrentHashMap<StepElement, DataIdentity>();
	}
	
	public void uploadReactiveThreadStrategies(JobConsumerThread<ComponentChange, StepChange> consumer_thread) {
		// TODO Auto-generated method stub
		
	}

	public void uploadFeedbackThreadStrategies(JobConsumerThread<StepChange, ComponentChange> consumer_thread) {
		// TODO Auto-generated method stub
		
	}

	//protected List<StepChange> process
	
	
}
