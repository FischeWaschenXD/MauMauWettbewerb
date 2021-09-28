package Utils;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import GUI.Game;

public class CardButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6311872923253094492L;
	
	private BufferedImage image;
	
	private Cards card;
	
	public CardButton(int pos, Game game) {
		addActionListener(pos, game);
	}
	
	public void display(Cards card, boolean big) {
		image = Card.getImage(card, big);
		setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		this.card = card;
		setIcon(new ImageIcon(image));
	}
	
	public void display(Cards card) {
		display(card, false);
	}
	
	public Cards getCard() {
		return card;
	}
	
	public void addActionListener(int pos, Game game) {
		super.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				game.kardPlaced = pos + 1;
			}
		});
	}
}
