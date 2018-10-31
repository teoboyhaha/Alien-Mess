package Sprites;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage subImage;
	
	public SpriteSheet(BufferedImage image) {
		this.subImage = image;
	}
	
	public BufferedImage grabSubImage(int col, int row, int width, int height) {
		return subImage.getSubimage((col*16)-16, (row*16)-16, width, height);
	}
}
