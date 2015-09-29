package fuzhu.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import javax.swing.JTextField;

public class MyFrame {

	public JFrame frame;
	private JTextField textField;

	/**
	 * Create the application.
	 */
	public MyFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("About");
			}
		});
		mnHelp.add(mntmAbout);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("7");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputproc(7);
			}
		});
		btnNewButton.setBounds(10, 41, 93, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("8");
		btnNewButton_1.setBounds(113, 41, 93, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("9");
		btnNewButton_2.setBounds(216, 41, 93, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("+");
		btnNewButton_3.setBounds(319, 41, 93, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("5");
		btnNewButton_4.setBounds(10, 74, 93, 23);
		frame.getContentPane().add(btnNewButton_4);
		
		JButton button = new JButton("6");
		button.setBounds(113, 74, 93, 23);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("7");
		button_1.setBounds(216, 74, 93, 23);
		frame.getContentPane().add(button_1);
		
		JButton button_2 = new JButton("-");
		button_2.setBounds(319, 74, 93, 23);
		frame.getContentPane().add(button_2);
		
		JButton button_3 = new JButton("1");
		button_3.setBounds(10, 107, 93, 23);
		frame.getContentPane().add(button_3);
		
		JButton button_4 = new JButton("2");
		button_4.setBounds(113, 107, 93, 23);
		frame.getContentPane().add(button_4);
		
		JButton button_5 = new JButton("3");
		button_5.setBounds(216, 107, 93, 23);
		frame.getContentPane().add(button_5);
		
		JButton button_6 = new JButton("*");
		button_6.setBounds(319, 107, 93, 23);
		frame.getContentPane().add(button_6);
		
		JButton button_7 = new JButton("0");
		button_7.setBounds(10, 140, 196, 23);
		frame.getContentPane().add(button_7);
		
		JButton button_8 = new JButton(".");
		button_8.setBounds(216, 140, 93, 23);
		frame.getContentPane().add(button_8);
		
		JButton button_9 = new JButton("/");
		button_9.setBounds(319, 140, 93, 23);
		frame.getContentPane().add(button_9);
		
		JButton button_10 = new JButton("=");
		button_10.setBounds(319, 173, 93, 23);
		frame.getContentPane().add(button_10);
		
		textField = new JTextField();
		textField.setBounds(10, 10, 402, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
	}
	
	private void inputproc(int num){
		String newnum = String.valueOf(num);
		String outstr = textField.getText();
		outstr += newnum;
		
		textField.setText(outstr);
		
		
	}
}
