package gui;

import instanceGenerator.InstanceGenerator;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import optim.heuristic.DeliveryBuilder;
import optim.localsearch.Local2Opt;
import optim.localsearch.LocalDeliverySwap;
import optim.metaheuristic.SimulatedAnnealing;
import optim.solution.DeliverySolution;
import cookItBO.CookItBO;
import cookItBO.common.Date;


public class CustomizePanel extends JPanel{

	public GraphPanel graphPanel;
	//Panel components
	public JPanel contentPane;
	public JTextField name=new JTextField("name you instance here");
	public JLabel[] textList;
	
	public ValueBox[] assignedValues;
	public JButton createInstanceButton=new JButton("Create Instance");
	public JButton initInstanceButton=new JButton("init Instance");
	public JButton runInstanceButton=new JButton("Run Instance");
	
	public JLabel text;
	public static final double innerMarginRatio=0.05;

	private double rX,rY,offsetX,offsetY;
	public boolean onlyOpt;
	DeliverySolution solution;

	public CustomizePanel(GraphPanel gp){
		graphPanel=gp;
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(500, 500));
		
		
		//titre du panel
		text=new JLabel();
		this.text.setText("Generation d'instance");
		this.add(text);
		SpringLayout layout=new SpringLayout();
		this.setLayout(layout);
		layout.putConstraint(SpringLayout.NORTH, text,
                5,
                SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, text,
                -5,
                SpringLayout.EAST, this);
		
		//Descriptifs des champs
		textList=new JLabel[]{
				new JLabel("nClients"),
				new JLabel("nLivreurs"),
				new JLabel("nRecettes"),
				new JLabel("nScooters"),
				new JLabel("nBikes"),
				new JLabel("nVoitures"),
				new JLabel("nDepots"),
				new JLabel("resolutionTime")
				};
		
		JLabel biggerLabel=textList[0];
		for(int i=1;i<textList.length;i++){
			if(biggerLabel.getText().length()<textList[i].getText().length()){
				biggerLabel=textList[i];
			}
		}
		
		assignedValues=new ValueBox[]{
				new ValueBox(textList[0].getText(), 7),
				new ValueBox(textList[1].getText(), 3),
				new ValueBox(textList[2].getText(), 3),
				new ValueBox(textList[3].getText(), 2),
				new ValueBox(textList[4].getText(), 0),
				new ValueBox(textList[5].getText(), 1),
				new ValueBox(textList[6].getText(), 1),
				new ValueBox(textList[7].getText(), 3)
		};
		
		                            
		//Adjust constraints for the text field so it's at
		//(<label's right edge> + 5, 5).
		JLabel label,oldLabel=null;
		ValueBox value,oldValue=null;
		label=textList[0];
		value=assignedValues[0];
		this.add(label);
		this.add(value);
		
		//stuck at the top
		layout.putConstraint(SpringLayout.NORTH, label,
                5,
                SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, value,
				5,
                SpringLayout.NORTH, this);
		
		
		for(int i=0;i<textList.length;i++){
			oldValue=value;
			oldLabel=label;
			
			//Stuck to the left side
			layout.putConstraint(SpringLayout.WEST, label,
	                5,
	                SpringLayout.WEST, this);
			//Stuck to each other
			layout.putConstraint(SpringLayout.WEST, value,
	                5,
	                SpringLayout.EAST, biggerLabel);
			
			if(i<textList.length-1){
				label=textList[i+1];
				value=assignedValues[i+1];
				
				this.add(label);
				this.add(value);
	
				//Under the previous ones
				layout.putConstraint(SpringLayout.NORTH, value,
	                    0,
	                    SpringLayout.SOUTH, oldValue);
				layout.putConstraint(SpringLayout.NORTH, label,
	                    0,
	                    SpringLayout.SOUTH, oldValue);
		
			}
		}
		
		this.add(createInstanceButton);
		layout.putConstraint(SpringLayout.NORTH, createInstanceButton,
                10,
                SpringLayout.SOUTH, label);
		this.add(initInstanceButton);
		layout.putConstraint(SpringLayout.NORTH, initInstanceButton,
                10,
                SpringLayout.SOUTH, createInstanceButton);
		this.add(runInstanceButton);
		layout.putConstraint(SpringLayout.NORTH, runInstanceButton,
                10,
                SpringLayout.SOUTH, initInstanceButton);
		
		createInstanceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InstanceGenerator g=new InstanceGenerator(
						assignedValues[0].getValue(), 
						assignedValues[1].getValue(),
						assignedValues[6].getValue(), 
						assignedValues[2].getValue(), 
						assignedValues[3].getValue(), 
						assignedValues[4].getValue(), 
						assignedValues[5].getValue());
				graphPanel.setData(g.run());
				graphPanel.repaint();
			}
		});
		
		initInstanceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DeliveryBuilder heur;
				heur = new DeliveryBuilder(graphPanel.getData(), new Date(0,0,0));
				solution=heur.getSolution();
				solution.instantiateCookItBo();
				
				graphPanel.repaint();

			}
		});
		runInstanceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimulatedAnnealing meta = new SimulatedAnnealing(solution);
				meta.addLocalMove(new Local2Opt(1.0));
				if (solution.getNbRoutes()>=2){
					meta.addLocalMove(new LocalDeliverySwap(1.0));
					meta.addLocalMove(new LocalDeliverySwap(1.0));
				}
				meta.startAnnealingProcess(assignedValues[7].getValue());
				solution.instantiateCookItBo();
				graphPanel.repaint();
			}
		});
		
		text.setVisible(true);
	}
	



	public void paintComponent(Graphics g){
		//Mission m;
		//int n=problem.data.nbNodes;
		g.setColor(Color.white);
		g.fillRect(0,0,this.getWidth(),this.getHeight());


	}




}
