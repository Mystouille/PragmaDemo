package gui;

import javax.swing.JTextField;

public class ValueBox extends JTextField{
	String name;
	
	public ValueBox(String name,int ini){
		super(ini+"",2);
		this.name=name;
	}
	
	public int getValue(){
		return Integer.parseInt(this.getText());
	}

}
