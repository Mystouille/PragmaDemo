package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import optim.solution.DeliveryNode;
import optim.solution.DeliveryRouting;
import optim.solution.DeliverySolution;

import cookItBO.CookItBO;


public class GraphPanel extends JPanel{
	private Image image;
	public static final int SOL_SIZE=4;
	private int solutionFocused=-1;
	private JLabel text;
	private int c1=0;
	private int c2=1;
	private CookItBO data;
	private int displayTime=CookItBO.getMinTime();
	public static final int WIDTH=500;
	public static final int HEIGHT=500;
	public static BufferedImage lol;
	

	public GraphPanel(Image img){
		this.image=img;
		data=null;
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(500, 500));
		text=new JLabel();
		this.add(text);
		text.setVisible(true);
		lol=null;
		try {
			lol = ImageIO.read(new File("img/lol.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setData(CookItBO d){
		data=d;
	}
	public CookItBO getData(){
		return data;
	}
	
	public void paintComponent(Graphics g){
		boolean a=g.drawImage(image, 0, 0, null);
		if(data!=null){
			data.paintItem(g,displayTime);
		}
	}
	
	private void paintNode(Graphics g, DeliveryNode n){
		
	}


	private void paintSol(Graphics g,int x, int y){
		g.fillOval(x-SOL_SIZE/2, y-SOL_SIZE/2, SOL_SIZE, SOL_SIZE);
	}

	private void highLightSol(Graphics g,int x, int y){
		g.drawOval(x-(SOL_SIZE/2)-2, y-(SOL_SIZE/2)-2, SOL_SIZE+4, SOL_SIZE+4);
	}

	public void refreshZoom() {


	}

	public void displaySolution(int s) {
		solutionFocused=s;
		String costs=new String();

		this.text.setText("Top one: "+costs.toString());

	}

	public void mergeSame() {
		
	}

	public int getMinTime() {
		return CookItBO.getMinTime();
	}

	public int getMaxTime() {
		return CookItBO.getMaxTime();
	}

	public void setTime(int value) {
		this.displayTime=value;
		this.repaint();
		
	}




}
