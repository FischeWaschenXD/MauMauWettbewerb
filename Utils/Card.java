package Utils;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;

public class Card {
	private static BufferedImage[] imageSmall =  new BufferedImage[33];
	private static BufferedImage[] imageBig =  new BufferedImage[33];
	
	private static FileImageInputStream is;
	private static ImageReader imageReader;
	private static ImageReadParam readParameters;
	
	private static Dimension screenSize;
	private static Dimension bigCards;
	private static Dimension smallCards;
	
	static {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		bigCards = calcBigCardSize();
		smallCards = calcSmallCardSize();
		
		try {
			is = new FileImageInputStream(new File(Card.class.getResource("PlayingCard.jpg").toURI()));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		imageReader = ImageIO.getImageReaders(is).next();
		imageReader.setInput(is, false, true);
		readParameters = imageReader.getDefaultReadParam();
		for(Cards card : Cards.values()) {
			try {
				imageSmall[card.pos] = resizeImage(loadFrame(card), (int) smallCards.getWidth(), (int) smallCards.getHeight());
				imageBig[card.pos] = resizeImage(loadFrame(card), (int) bigCards.getWidth(), (int) bigCards.getHeight());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static BufferedImage loadFrame(int x, int y, int w, int h) {
	    readParameters.setSourceRegion(new Rectangle(x, y, w, h));
	    try {
	        return imageReader.read(0, readParameters);
	    } catch (IOException ex) {
	        return null;
	    }
	}
	
	private static BufferedImage loadFrame(Cards card) {
		return loadFrame(card.x, card.y, card.w, card.h);
	}
	
	private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
	    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = resizedImage.createGraphics();
	    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
	    graphics2D.dispose();
	    return resizedImage;
	}
	
	private static Dimension calcBigCardSize() {
		if(screenSize.getHeight() / 400 * 180 * 2 + 40 > screenSize.width / 5 * 4) {
			return new Dimension((int) ((screenSize.getWidth() / 5 * 4 - 40) / 2), (int) (((screenSize.getWidth() / 5 * 4 - 40) / 2) * 1.4));
		}
		return new Dimension((int) (screenSize.getHeight() / 400 * 180), (int) (screenSize.getHeight() / 5 * 3));
	}
	private static Dimension calcSmallCardSize() {
		return new Dimension((int) (screenSize.getHeight() / 400 * 75), (int) (screenSize.getHeight() / 4));
	}
	
	public static BufferedImage getImage(Cards card, boolean big) {
		return (big) ? imageBig[card.pos] : imageSmall[card.pos];
	}
	
	public static BufferedImage getImage(Cards card) {
		return getImage(card, false);
	}
}
