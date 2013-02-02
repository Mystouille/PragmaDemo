package gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSlider;


public class TimeSlider extends JSlider {
	GraphPanel target;
	BottomPanel bottomTarget;
	
	private int minTime;
	private int maxTime;
	public TimeSlider(GraphPanel targetPanel ,BottomPanel bottomPanel){

		this.target=targetPanel;
		this.minTime=targetPanel.getMinTime();
		this.maxTime=targetPanel.getMaxTime();
		this.bottomTarget=bottomPanel;
		this.setOrientation(VERTICAL);
		this.setPreferredSize(new Dimension(50,500));
		this.setMinimum(minTime);
		this.setMaximum(maxTime);
		this.setMinorTickSpacing(60);
		this.setMajorTickSpacing(1800);
		this.setPaintTicks(true);
	}
	
	public void propagateChange(){
		bottomTarget.setTimeInfo(this.getValue());
		target.setTime(this.getValue());
	}
	
	
	
	
	public void refreshMax(int a) {
		this.setMaximum(a*100);
	}
}
