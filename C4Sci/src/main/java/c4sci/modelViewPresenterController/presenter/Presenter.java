package c4sci.modelViewPresenterController.presenter;

import java.util.Map;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.MvpcLayer;
import c4sci.modelViewPresenterController.jobs.consumption.JobConsumerThread;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

public class Presenter implements MvpcLayer<ComponentChange, StepChange>{

	private Map<DataIdentity, StepElement> componentElementMap;
	
	public void uploadReactiveThreadStrategies(
			JobConsumerThread<ComponentChange, StepChange> consumer_thread) {
		// TODO Auto-generated method stub
		
	}

	public void uploadFeedbackThreadStrategies(
			JobConsumerThread<StepChange, ComponentChange> consumer_thread) {
		// TODO Auto-generated method stub
		
	}

	/*protected List<StepChange>processBooleanChange(ComponentChange req_cmd){
		List<StepChange> _res = new ArrayList<StepChange>();
		
		BooleanChange _bool_chg = (BooleanChange)req_cmd;

		StepElement corresp_elt = componentElementMap.get(req_cmd.getComponentIdentity());
		if (corresp_elt != null){
			ElementReactiveModificationStepChange _elt_chg = new ElementReactiveModificationStepChange(null);



			_res.add(_elt_chg);
		}
		return _res;
	}*/
	
	
}
