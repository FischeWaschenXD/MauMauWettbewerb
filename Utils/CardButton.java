package Utils;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CardButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6311872923253094492L;
	
	private BufferedImage image;
	
	private Cards card;
	
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
}
