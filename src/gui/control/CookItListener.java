package gui.control;

import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import gui.CookItFrame;
import gui.InstanceList;
import gui.TimeSlider;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CookItListener implements ChangeListener, ActionListener, ItemListener{
	CookItFrame parent;
	
	public CookItListener(CookItFrame fr){
		this.parent=fr;
	}
	@Override
	public void stateChanged(ChangeEvent arg0) {
		if(arg0.getSource() instanceof TimeSlider){
			TimeSlider source=((TimeSlider)(arg0.getSource()));
			parent.repaint();
		}

	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() instanceof JButton){
			parent.init();
		}
	}
	
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if(arg0.getSource() instanceof Checkbox){
			parent.liste.setEnabled(((Checkbox)(arg0.getSource())).getState());
			if(parent.liste.isEnabled()){
			}
			else{
			}
		}
	}


}
