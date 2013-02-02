package gui;

import java.awt.Component;

import javax.swing.JComboBox;

public class InstanceList extends JComboBox{
	public String[] list;

	public InstanceList(String[] ls){
		list=ls;
		for(int i=0;i<list.length;i++){
			this.addItem(list[i]);
		}
		this.setSelectedItem(this.getItemAt(0));
	}

}
