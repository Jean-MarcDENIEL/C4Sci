package c4sci.modelViewPresenterController.viewer;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.math.geometry.plane.PlaneVector;
import c4sci.math.geometry.space.SpaceVector;
import c4sci.modelViewPresenterController.presenter.components.NoChildComponent;
import c4sci.modelViewPresenterController.viewer.swingImplementation.SwingViewer;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFamily.StandardComponentSet;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.lifeCycleChanges.CreateStandardComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.lifeCycleChanges.SuppressComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.BackgroundColorChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.LabelChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.PositionChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.SizeChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.VisibilityChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.sessionChanges.BeginSessionChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.sessionChanges.EndSessionChange;

public class TestSwingViewer {

	@Test
	public void test() {

		SwingViewer	_viewer = new SwingViewer();

		final int onesecond = 1000;

		waitAndText(onesecond,"changes ...");

		NoChildComponent 	_comp_1_panel 	= new NoChildComponent();


		//_viewer.feedbackToBeginSessionChange(new BeginSessionChange(null, null));
		System.out.println("Create Pannel");
		_viewer.feedbackToCreateStandardComponentChange(new CreateStandardComponentChange(_comp_1_panel, StandardComponentSet.PANEL, null));

		waitAndText(onesecond, "Panel background -> green");
		_viewer.feedbackToBackgroundColorChange(new BackgroundColorChange(_comp_1_panel, new SpaceVector(0.0f, 1.0f, 0.1f), null));
		_viewer.feedbackToVisibilityChange(new VisibilityChange(_comp_1_panel, true, null));

		waitAndText(onesecond, "Button");
		NoChildComponent	_comp_1_val1	= new NoChildComponent(); 
		_comp_1_val1.setSize(new PlaneVector(0.15f, 0.25f));
		_comp_1_val1.setUpperLeftOrigin(new PlaneVector(0.05f, 0.25f));
		_comp_1_val1.setParentComponent(_comp_1_panel);
		_viewer.feedbackToBeginSessionChange(new BeginSessionChange(null, null));
		_viewer.feedbackToCreateStandardComponentChange(new CreateStandardComponentChange(_comp_1_val1, StandardComponentSet.BUTTON, null));
		_viewer.feedbackToSizeChange(new SizeChange(_comp_1_val1, new PlaneVector(0.15f, 0.25f), null));
		_viewer.feedbackToPositionChange(new PositionChange(_comp_1_val1, new PlaneVector(0.05f,0.25f), null));
		_viewer.feedbackToVisibilityChange(new VisibilityChange(_comp_1_val1, true, null));
		_viewer.feedbackToLabelChange(new LabelChange(_comp_1_val1, new InternationalizableTerm("label"), null));
		_viewer.feedbackToBackgroundColorChange(new BackgroundColorChange(_comp_1_val1, new SpaceVector(1.0f,0.5f,0.8f), null));
		_viewer.feedbackToEndSessionChange(new EndSessionChange(null, null));
		
		waitAndText(onesecond, "suppress button");
		_viewer.feedbackToSuppressComponentChange(new SuppressComponentChange(_comp_1_val1, null));
		
		waitAndText(onesecond, "fin");
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
