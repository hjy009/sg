package fuzhu.idMaker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Random;

public class MainFrame extends JFrame{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 JPanel contentPane=new JPanel();
	 JLabel Lcity=new JLabel();
	 JLabel Lquantity=new JLabel();
	 JButton make=new JButton();
     JButton about=new JButton();
     JButton exit=new JButton();
	 JComboBox Ccity=new JComboBox();
	  JComboBox Cquantity=new JComboBox();
	  
		 public MainFrame() {
			    try {
			      jbInit();
			    }
			    catch (Exception e) {                 
			      e.printStackTrace();
			    }
		 }
	 
	 
	 
	 
	 private void jbInit() throws Exception {
		 contentPane = (JPanel)this.getContentPane();
		 contentPane.setLayout(null);
		 this.setResizable(false);
		 this.setForeground(Color.black);
		 this.setSize(new Dimension(300, 200));
		 this.setTitle("Java版身份证号码生成器");
		 Lcity.setText("数量");
		 Lquantity.setText("城市");
		 make.setText("生成");
		 about.setText("关于");
		 exit.setText("退出");
		 Ccity.addItem("北京");
		 Ccity.addItem("上海");
		 Ccity.addItem("天津");
		 Ccity.addItem("重庆");
		 Cquantity.addItem("1");
		 Cquantity.addItem("5");
		 Cquantity.addItem("10");
		 Lcity.setBounds(new Rectangle(50, 25, 30, 15));
		 Lquantity.setBounds(new Rectangle(50, 60, 30, 15));
		 Cquantity.setBounds(new Rectangle(85,25,50,20));
		 Ccity.setBounds(new Rectangle(85,60,50,20));
		 make.setBounds(new Rectangle(50,100,60,30));
		 about.setBounds(new Rectangle(115,100,60,30));
		 exit.setBounds(new Rectangle(180,100,60,30));
		 exit.addActionListener(exitListener);
		 about.addActionListener(aboutListener);
		 make.addActionListener(makeListener);
		 contentPane.add(Lcity, null);
		 contentPane.add(Lquantity, null);
		 contentPane.add(Ccity, null);
		 contentPane.add(Cquantity, null);
		 contentPane.add(make, null);
		 contentPane.add(about, null);
		 contentPane.add(exit, null);		 
	 }     
	 
	 
	 private   String  make(){
		 String statenumber="";
		 String result="";
		 switch(Ccity.getSelectedIndex()){
		 case 0:
			 statenumber="110000";
			 break;
		 case 1:
			 statenumber="310000";
			 break;
		 case 2:
			 statenumber="120000";
			 break;
		 case 3:
			 statenumber="50000";
			 break;
		 default:
		     break; 
	       }
		 
		 switch(Cquantity.getSelectedIndex()){
		 case 0:
			 result=statenumber+"19"+randoms();
			 break;
		 case 1:
			 for(int i=1;i<=5;i++){
				 result=result+statenumber+"19"+randoms()+"\n";
			 }
			 break;
		 case 2:
			 for(int i=1;i<=10;i++){
				 result=result+statenumber+"19"+randoms()+"\n";
			 }
			 break;
		 default:
			 break;
		 }	 
		 return result;
	 }
	 
	 private String randoms(){
		Random a=new Random();
		 String randoms="";
		 for(int i=3;i<=12;i++){
			 randoms=randoms+a.nextInt(i);
		 }
		 return randoms;
	 }
	 
	 
	 
	 ActionListener exitListener = new  ActionListener(){
	      public void actionPerformed(ActionEvent event) {
	              System.exit(0);
	           }};
	           
	          
	ActionListener aboutListener = new  ActionListener(){
	     	      public void actionPerformed(ActionEvent event) {
	     	    	 JOptionPane.showMessageDialog(null, "版本:0.1\n北京枫火石油 www.hopepetro.com\n作者:刘昭", 
	     	  			  "Java版身份证号码生成器",
	     	                JOptionPane.INFORMATION_MESSAGE); 
	     	           }};

	  ActionListener makeListener = new  ActionListener(){
	    public void actionPerformed(ActionEvent event) {
	    	NumberFrame frame=new NumberFrame();	
	    	frame.display(make());
	    	boolean packFrame = false;
		    if (packFrame) {
			      frame.pack();
			    }
			    else {
			      frame.validate();
			      make.setEnabled(false);
			    }
			    //Center the window
			    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			    Dimension frameSize = frame.getSize();
			    if (frameSize.height > screenSize.height) {
			      frameSize.height = screenSize.height;
			    }
			    if (frameSize.width > screenSize.width) {
			      frameSize.width = screenSize.width;
			    }
			    frame.setLocation( (screenSize.width - frameSize.width) / 2,
			                      (screenSize.height - frameSize.height) / 2);
			    frame.setVisible(true);
			  }   
        };
}


