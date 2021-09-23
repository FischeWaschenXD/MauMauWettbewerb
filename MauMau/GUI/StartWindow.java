package GUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import Utils.StartCallBack;

public class StartWindow {
	private JFrame frame;
	private JComboBox<String> comboBox;
	private boolean[] kiEnabled;
	private String[] names;
	private String mcName;
	private StartCallBack callBack;
	
	public StartWindow(StartCallBack callBack) {
		this.callBack = callBack;
		
		kiEnabled = new boolean[3];
		Arrays.fill(kiEnabled, true);
		names = new String[3];
		Arrays.fill(names, "");
		
		frame = new JFrame("Test");
		frame.setSize(500, 250);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		frame.setVisible(true);
	}

	private void init() {
		
		JPanel layout = new JPanel();
		layout.setLayout(new BoxLayout(layout, BoxLayout.PAGE_AXIS));
		
		JPanel players = new JPanel();
		players.setLayout(new GridLayout(4, 1));
		
		JPanel box = new JPanel();
		String[] arr = {"1 Gegenspieler", "2 Gegenspieler", "3 Gegenspieler"};
		comboBox = new JComboBox<String>(arr);
		comboBox.addActionListener(new ActionListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayersSelected((String) ((JComboBox<String>) e.getSource()).getSelectedItem(), players);
			}
		});
		box.add(comboBox);
		layout.add(box);
		PlayersSelected("1", players);
		layout.add(players);
		
		JButton weiter = new JButton("Bestätigen");
		weiter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!checkFieldFull()) return;
				callBack.starten(Integer.parseInt(comboBox.getSelectedItem().toString().substring(0, 1)) + 1, mcName, names, kiEnabled);
				frame.setVisible(false);
			}
		});
		layout.add(weiter);
		
		frame.add(layout);
	}
	
	private void PlayersSelected(String s, JPanel players) {
		players.removeAll();
		frame.setVisible(true);
		JPanel pan = new JPanel();
		JLabel lab = new JLabel("Ich:");
		pan.add(lab);
		JTextField txtField = new JTextField(mcName, 32);
		txtField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				event(e);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				event(e);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				event(e);
			}
			
			private void event(DocumentEvent e) {
				try {
					mcName = e.getDocument().getText(0, e.getDocument().getLength()).strip();
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		});
		pan.add(txtField);
		players.add(pan);
		for(int i = 1; i <= Integer.parseInt(s.substring(0, 1)); i++) {
			JPanel panel = new JPanel();
			JLabel label = new JLabel("Spieler " + i + ":");
			panel.add(label);
			JTextField textField = new JTextField(names[i - 1], 32);
			textField.getDocument().putProperty("obj", textField);
			textField.setToolTipText("" + i);
			textField.getDocument().addDocumentListener(new DocumentListener() {
				
				@Override
				public void removeUpdate(DocumentEvent e) {
					event(e);
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					event(e);
				}
				
				@Override
				public void changedUpdate(DocumentEvent e) {
					event(e);
				}
				
				private void event(DocumentEvent e) {
					Object obj = e.getDocument().getProperty("obj");
					if(obj != null && obj instanceof JTextField) {
						names[Integer.parseInt(((JTextField) obj).getToolTipText()) - 1] = ((JTextField) obj).getText().strip();
					}
				}
			});
			panel.add(textField);
			JCheckBox checkBox = new JCheckBox("KI", kiEnabled[i - 1]);
			checkBox.setToolTipText("" + i);
			checkBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					kiEnabled[Integer.parseInt(((JCheckBox) e.getSource()).getToolTipText()) - 1] = ((JCheckBox) e.getSource()).isSelected();
				}
			});
			panel.add(checkBox);
			players.add(panel);
		}
		frame.setVisible(true);
	}
	
	private boolean checkFieldFull() {
		
		
		if(mcName == null || mcName.length() < 3) return false;
		for(int i = 0; i < Integer.parseInt(comboBox.getSelectedItem().toString().substring(0, 1)); i++) {
			
			String s = names[i];
			if(s == null || s.length() < 3) return false;
		}
		return true;
	}
}
