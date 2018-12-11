/**
 *    Auth:riozenc
 *    Date:2018年11月20日 下午8:02:28
 *    Title:test.Test.java
 **/
package h5;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Test1 {
	public static final void overlapImage(String bigPath, String smallPath, String outFile) {
		try {
			BufferedImage big = ImageIO.read(new File(bigPath));
			BufferedImage small = ImageIO.read(new File(smallPath));
			Graphics2D g = big.createGraphics();
			int x = (big.getWidth() - small.getWidth()) / 2;
			int y = (big.getHeight() - small.getHeight()) / 2;
			g.drawImage(small, 750/2-75, 1334/10+30, small.getWidth(), small.getHeight(), null);
			g.dispose();
			ImageIO.write(big, outFile.split("\\.")[1], new File(outFile));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static final void overlapImage2(String bigPath, String smallPath, String outFile) {
		try {
			BufferedImage big = ImageIO.read(new File(bigPath));
			BufferedImage small = ImageIO.read(new File(smallPath));
			Graphics2D g = big.createGraphics();
			int x = (big.getWidth() - small.getWidth()) / 2;
			int y = (big.getHeight() - small.getHeight()) / 2;
			g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
			g.dispose();
			ImageIO.write(big, outFile.split("\\.")[1], new File(outFile));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		String beijing = "C:\\Users\\riozenc\\Desktop\\0.png";
		String bigPath = "C:\\Users\\riozenc\\Desktop\\2.png";
		String smallPath = "C:\\Users\\riozenc\\Desktop\\1.jpg";
		String outFile = "C:\\Users\\riozenc\\Desktop\\3.png";
		String newFile = "C:\\Users\\riozenc\\Desktop\\4.png";
		
		overlapImage(beijing, smallPath, outFile);
		overlapImage2(outFile, bigPath, newFile);
	}
}
