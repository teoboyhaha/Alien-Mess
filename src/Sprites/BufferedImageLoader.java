package Sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	private BufferedImage image;
	
	public BufferedImage loadImage(String path) {
		try {
			System.out.println("Trying to load " + path + "...");
			image = ImageIO.read(getClass().getResource(path));
			System.out.println(" succeeded!");
		} catch(IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			System.err.println(" failed!");
		}
		return image;
	}
	
}
