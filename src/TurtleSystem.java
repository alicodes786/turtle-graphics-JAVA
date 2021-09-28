import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import uk.ac.leedsbeckett.oop.TurtleGraphics;

public class TurtleSystem extends TurtleGraphics {
    
	
	private final JFileChooser selector;
	private String[] fullCommand;
	protected String command,parameter;
	int param;
	private boolean save = false, penDown = false;
	Graphics diagram;
	JMenuBar menuBar;
	JMenu menuInfo, menuFile;
	JMenuItem menuItemAbout,menuItemLoad,menuItemSave;
	JLabel label1;
	
	
	
	public TurtleSystem() {
		JFrame MainFrame = new JFrame();                
        MainFrame.add(this);                                   
        MainFrame.pack();                                           
        MainFrame.setVisible(true);  
        setDirection();
        this.selector = new JFileChooser();
        menuBar = new JMenuBar();
        MainFrame.setJMenuBar(menuBar);
        
        
        menuInfo = new JMenu("Info");
        menuBar.add(menuInfo);
        
        menuFile = new JMenu("File");
        menuBar.add(menuFile);
        
        menuItemAbout = new JMenuItem("About"); 
        menuInfo.add(menuItemAbout);
        
        menuItemLoad = new JMenuItem("Load");
        menuItemSave = new JMenuItem("Save");
        menuFile.add(menuItemLoad);
        menuFile.add(menuItemSave);
        
        
        menuBarOption();
	}
	
    public void setDirection() 
    {
    	turnLeft();
    }

    
    public void loadFile() 
    {
    	selector.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    	selector.setDialogTitle("Open a File");
    	int state = selector.showOpenDialog(null);
    	if(state != JFileChooser.APPROVE_OPTION) 
    	{
    		displayMessage("Try Again");
    	}
    	else {
	    
    		File inputFile = selector.getSelectedFile();
    		try {
    			BufferedImage img = ImageIO.read(inputFile);
    			setBufferedImage(img);
    		}
    		catch(IOException e){
    			
    		}
    	}
    		
    }

    public void saveFile() 
    {   
    	selector.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    	selector.setDialogTitle("Save a File");
    	int state = selector.showSaveDialog(null);
    	if(state != JFileChooser.APPROVE_OPTION) 
    	{
    		displayMessage("Try Again");
    	}
    	else {
    		try {
    		    File outputFile = selector.getSelectedFile();
    		    ImageIO.write(getBufferedImage(), "png", outputFile);
    		    save = true;
    		} catch (IOException e) {
    		    
    		}	
    	}
    }
    
 
   public void clearNew() {
    	if(getBackground_Col() != Color.black)
    	setBackground_Col(Color.black);
    	
    }
    
   
   
   public void circle(Graphics g) {
	   g = getGraphicsContext();
	   g.setColor(Color.blue);
	   g.drawOval(350,50,300,300);
   }
   
   
   public void backward(int parameter) {
	   turnRight(180);
	   forward(parameter);
	   turnRight(180);
     }
   
   public void about(Graphics d) {
		  d = getGraphicsContext();
	      d.setColor(Color.green);
		  d.drawOval(200,100,200,200);
			    
		  d.setColor(Color.blue);
		  d.drawOval(222,120,160,160);
			    
	      d.setColor(Color.red); 
		  d.drawOval(244,140,120,120);
			   
			   
		  d.setColor(Color.white);
		  d.drawString("Version 3.13", 270, 210);   
	}
   
   public void menuBarOption() {
    menuItemAbout.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		about(getGraphicsContext());
	}
  });
    menuItemLoad.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		if(save==false && penDown == true)
    		displayMessage("Save First");
    		else
    			loadFile();
    	}
    });
    menuItemSave.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		saveFile();
    	}
    });
    
}
   
	public void processCommand(String command)      
    {  
		fullCommand = command.split(" ");
		if(fullCommand.length == 2) {
			parameter = fullCommand[1];
		
		switch(fullCommand[0]) 
		{
		
		case "forward":
			
			try 
			{   
				if(Integer.parseInt(parameter) <= 500 && Integer.parseInt(parameter) > 0 && getxPos() !=0) {
				     param = Integer.parseInt(parameter);	
				     this.forward(param);
				     displayMessage("Goes forward " + param + " px");
				     
				     if(getyPos() < 0) {
				    	 setyPos(0);
				    	 displayMessage("Parameter taking out of bound (y-axis)");
				     }
				     else if(getyPos() > 400) {
				    	 setyPos(300);
				    	 displayMessage("Parameter taking out of bound (y-axis)");
				     }
				     
				}
				else
					displayMessage("Parameter out of bound");
			  }
			 
		    catch(NumberFormatException e){
		    	displayMessage("Invalid parameter");
		    }
			
		break;
		
		case "turnleft":
			try
			{   if(Integer.parseInt(parameter) <= 360) {
				  param = Integer.parseInt(parameter);
				  displayMessage("Turns Left " + param + " degrees");
			}
			   else
				   displayMessage("Parameter out of bound");
			}
			catch(NumberFormatException e)
			{
				displayMessage("Invalid parameter");
			}
			this.turnLeft(param);
		break;
		
		case "turnright": 
			
			try 
			{    
				 if(Integer.parseInt(parameter) <= 360) {
					  param = Integer.parseInt(parameter);
					  displayMessage("Turns Right " + param + " degrees");
				 }
				 else
				   displayMessage("Parameter out of bound");
			}
		    catch(NumberFormatException e){
		    	displayMessage("Invalid parameter");
		    }
			this.turnRight(param);
			
		
		break;
		
		case "backward":
			try 
			{
				if(Integer.parseInt(parameter) <= 500 && Integer.parseInt(parameter) > 0 && getxPos() != 0) {
					param = Integer.parseInt(parameter);
					displayMessage("Goes Backward " + param + " px");
				}
				else
					displayMessage("Parameter out of bound or A Non-Numeric Parameter provided");
			}
			catch(NumberFormatException e){
				displayMessage("Invalid parameter");
			}
		   this.backward(param);
		   break;
		   
		default: displayMessage("No parameters"); 
		}
	}
		else if(fullCommand.length == 1) {
			switch(fullCommand[0]) {
			case "Pendown":penDown(); penDown = true; displayMessage("Pen is In Use Now");
			break;
			case "Penup":penUp(); displayMessage("Pen in Not in Use Now");
			break;
			case "Green":setPenColour(Color.green); displayMessage("Pen Color Changed to Green Now");
			break;
			case "Red":setPenColour(Color.red); displayMessage("Pen Color Changed to Red Now");
			break;
			case "Black":setPenColour(Color.black); displayMessage("Pen Color Changed to Black Now");
			break;
			case "Blue":setPenColour(Color.blue); displayMessage("Pen Color Changed to Blue Now");
			break;
			case "White":setPenColour(Color.white); displayMessage("Pen Color Changed to White Now");
			break;
			case "New":clearNew();clear(); displayMessage("New Command Invoked");
			break;
			case "Reset":reset();setDirection();clearInterface(); displayMessage("Reset Command Invoked");
			break;
			case "Save":saveFile(); displayMessage("Save Command Invoked");
			break;
			case "Load": displayMessage("Load Command Invoked");
				try {
					if(save == false && penDown == true) {
						displayMessage("Save First");
					}
					else 
						loadFile();
				}
				catch(Exception e) {
					
				}
			break;
			case "turnright":turnRight(); displayMessage("Turns Right 90 degrees");
			break;
			case "turnleft":turnLeft(); displayMessage("Turns Left 90 Degrees");
			break;
			case "xpos":System.out.println(getxPos());
			break;
			case "ypos":System.out.println(getyPos());
			break;
			case "about":about(getGraphicsContext()); displayMessage("Version 3.13");
			break;
			case "aboutT":about();
			break;
			case "circle":circle(getGraphicsContext());
			break;
			default:displayMessage("Invalid Command Or No parameter provided");
			}
		}
		else {
			displayMessage("Command out of bounds");
		}
	
   }	
	
}
