package gui.control;

import gui.CookItFrame;
import gui.TimeSlider;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TimeSliderListener implements ChangeListener {

	public TimeSliderListener(){
	}
	@Override
	public void stateChanged(ChangeEvent arg0) {
		TimeSlider source=((TimeSlider)(arg0.getSource()));
		source.propagateChange();
	}

}
