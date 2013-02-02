//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Depot.java
//  @ Date : 29/06/2012
//  @ Author : 
//
//



package cookItBO.actives;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import cookItBO.common.Place;
import cookItBO.demand.Command;
import cookItBO.recipes.Product;


public class Depot extends StockPlace {
	public final Place placeType=Place.DEPOT;
	private int capacity;
	private final int DISPLAY_SIZE=15;
	public Depot(int capa){
		capacity=capa;
		commandList=new ArrayList<Command>();
		productList=new ArrayList<Product>();
	}
	
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void paintItem(Graphics g) {
		int x=this.getXPositionOnFrame()-DISPLAY_SIZE/2;
		int y=this.getYPositionOnFrame()-DISPLAY_SIZE/2;
		g.setColor(Color.blue);
		g.fillOval(x, y, DISPLAY_SIZE, DISPLAY_SIZE);
	}
}
