package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Consol {
	private JFrame consol;
	private JTextArea consolText;
	private JTextField consolInput;
	private JButton consolButton;
	
	public Consol() {
		consol = new JFrame("Consol");
		
		consol.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		consol.setMinimumSize(new Dimension(400, 250));
		consol.setPreferredSize(new Dimension(400, 250));
		consol.setLocationRelativeTo(null);
		consol.setResizable(false);
		
		consol.setLayout(new BorderLayout());
		consolText = new JTextArea();
		consolText.setEditable(false);
		consolText.setLineWrap(true);
		consolText.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(consolText);
		
		consolButton = new JButton("Senden");
		consol.setVisible(true);
		consolInput = new JTextField();
		consolInput.setPreferredSize(new Dimension(200, 27));
		
		JPanel bottomBar = new JPanel();
		
		bottomBar.add(consolInput);
		bottomBar.add(consolButton);
		
		consol.add(scroll, BorderLayout.CENTER);
		consol.add(bottomBar, BorderLayout.SOUTH);
		
		consol.setVisible(true);
	}
}
