package Utils;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class CardPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4506393491906857304L;
	
	private BufferedImage image;
	
	private Cards card;
	
	public void display(Cards card, boolean big) {
		image = Card.getImage(card, big);
		setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		this.card = card;
	}
	
	public void display(Cards card) {
		display(card, false);
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
    }
	
	public Cards getCard() {
		return card;
	}
}
