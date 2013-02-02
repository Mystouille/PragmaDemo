package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import javax.imageio.ImageIO;




public class CenterPanel extends JPanel {
	private Dimension size;
	private Container pane;
	public GraphPanel graphPanel;
	public CustomizePanel customizePanel;
	
	public CenterPanel(){
		super();

		BufferedImage myImage=null;
		try {
			myImage = ImageIO.read(new File("img/paris500x500.bmp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		graphPanel=new GraphPanel(myImage);
		customizePanel=new CustomizePanel(graphPanel);
		this.setBackground(Color.gray);
		this.setLayout(new BorderLayout(3,3));
		this.add(customizePanel,BorderLayout.WEST);
		this.add(graphPanel,BorderLayout.EAST);
		
	}
	

	

	
}
