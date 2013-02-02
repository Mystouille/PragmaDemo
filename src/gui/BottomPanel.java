package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cookItBO.common.Time;

public class BottomPanel extends JPanel{
	public JLabel timeInfo;
	
	public BottomPanel(int time){
		timeInfo=new JLabel(""+time);
		this.add(timeInfo);
	}
	
	public void setTimeInfo(int time){
		timeInfo.setText(Time.pretty(time));
		this.repaint();
	}
}
