package gui;

import gui.control.ClickListener;
import gui.control.CookItListener;
import gui.control.TimeSliderListener;

import instanceGenerator.InstanceGenerator;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cookItBO.CookItBO;



public class CookItFrame extends JFrame implements Runnable{
	private Container pane;
	public CenterPanel centerPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel topPanel;
	private BottomPanel bottomPanel;
	private JLabel label;

	private CookItBO instance;
	
	public InstanceList liste;
	public boolean running=false;

	public JButton saveInstanceButton=new JButton("Save Instance");
	
	private TimeSlider timeSlider;
	private ClickListener clickListener;
	
	public int nbDysplayedSol;
	private TimeSliderListener timeListener;

	private boolean ready;

	String currentFile;
	String folder;
	String[] files;
	
	public CookItFrame(String folder){
		super();
		this.folder=folder;
		//List of the files in the folder
		files=new File(folder).list();
		liste=new InstanceList(files);
		timeListener=new TimeSliderListener();

		
		saveInstanceButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		int k=0;
		for(String s:files){
			if(s.contains(".tour")){
				k++;
			}
		}
		String[] files2=new String[files.length-k];
		k=0;
		for(String s:files){
			if(!s.contains(".tour")){
				files2[k]=s;
				k++;
			}
		}
		files=files2;
		init();
	}
	
	public void init(){
		
		centerPanel=new CenterPanel();
		leftPanel=new JPanel();
		//d.addObserver(this);
		rightPanel=new JPanel();

		bottomPanel=new BottomPanel(0);
		pane = getContentPane();
		timeSlider=new TimeSlider(centerPanel.graphPanel,bottomPanel);
		clickListener=new ClickListener(this);


		pane.removeAll();
		pane.setLayout(new BorderLayout(5, 5));
		pane.setBackground(Color.gray);



		if(topPanel==null){
			topPanel=new JPanel();
			topPanel.add(saveInstanceButton);
			topPanel.add(liste);
			topPanel.add(new JLabel("% speed"));
			topPanel.add(new JLabel("indiv"));
			topPanel.add(new JLabel("cibles"));
		}
		
		
		rightPanel.add(timeSlider);
		

		timeSlider.addChangeListener(timeListener);
		centerPanel.customizePanel.addMouseListener(clickListener);

		pane.add(centerPanel,BorderLayout.CENTER);
		pane.add(leftPanel,BorderLayout.WEST);
		pane.add(rightPanel,BorderLayout.EAST);
		pane.add(topPanel,BorderLayout.NORTH);
		pane.add(bottomPanel,BorderLayout.SOUTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.pack();
		this.repaint();
	}
	


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
}
