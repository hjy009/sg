package fuzhu.idMaker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class NumberFrame extends JFrame{
	
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	JPanel contentPane=new JPanel();
	JTextArea area=new JTextArea();
	public NumberFrame() {
		    try {
		      jbInit();
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		    }
	 }
	 
	 
	 private void jbInit() throws Exception{
		 contentPane = (JPanel)this.getContentPane();
		 this.setResizable(true);
		 this.setForeground(Color.black);
		 this.setSize(new Dimension(200, 300));
		 this.setTitle("身份证号码");
		 contentPane.add(area,BorderLayout.CENTER);
	 }
	 
	 public void display(String info){		 
		   area.setText("生成号码如下\n"+info);
	 }
}
