package gui.control;

import gui.CenterPanel;
import gui.CookItFrame;
import gui.TimeSlider;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickListener implements MouseListener{

	CookItFrame parent;
	Thread thread ;

	public ClickListener(CookItFrame fr){
		parent=fr;
	}

	@Override
	public synchronized void mouseClicked(MouseEvent arg0) {
		synchronized(this.parent){
			if(!parent.running&&arg0.getButton()==1){
				thread =new Thread(parent);
				thread.start();
			}
			if(parent.running){
				parent.running=false;
			}
		}

		if(arg0.getButton()==3){	
			parent.centerPanel.customizePanel.onlyOpt=!parent.centerPanel.customizePanel.onlyOpt;
			parent.repaint();
		}
	}



	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
