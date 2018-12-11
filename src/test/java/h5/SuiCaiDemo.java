package h5;
 
 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
 
import javax.imageio.ImageIO;
 
public class SuiCaiDemo {
	
	public static BufferedImage readImage(String imageName) {
		File imageFile = new File(imageName);
		BufferedImage bufferedImage=null;
		  try {
			bufferedImage = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		  return bufferedImage;
		
	}
	public static void writeImage(BufferedImage bi, String imageName) {
		  File skinImageOut = new File(imageName);
		  try {
		   ImageIO.write(bi, "png", skinImageOut);
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
	}
	public static int rgb2gray(int rgb) {
		int r = (rgb & 0xff0000) >> 16;  
        int g = (rgb & 0xff00) >> 8;  
        int b = (rgb & 0xff);  
        int gray = (int)(r * 0.3 + g * 0.59 + b * 0.11);
		return gray;
	}
	public static void main(String[] args) {
		//参数设置
		int radius =4;//半径
		int tank=10;//油漆桶
		String savePath="C:\\Users\\Administrator\\Desktop\\1.jpg";
		String openPath="C:\\Users\\Administrator\\Desktop\\2.jpg";
		BufferedImage colorImage=readImage(openPath);//原图
		int width=colorImage.getWidth();
		int height=colorImage.getHeight();
		//输出图
		BufferedImage outRes = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int y = 1; y < height - 1; ++y){
			for (int x = 1; x < width - 1; ++x){
				ColorTank ctank=new ColorTank(tank);
				for (int tx = x-radius; tx < x+radius; tx++) {
					for (int ty = y-radius; ty < y+radius; ty++) {
						if (tx>0 && tx<width && ty>0 && ty<height) {
							ctank.addToTank(colorImage.getRGB(tx, ty));
						}
					}	
				}//内矩阵循环
				outRes.setRGB(x, y,ctank.getColor());
//				System.out.println("x:"+x+"y:"+y);
			}
		}
		writeImage(outRes, savePath+System.currentTimeMillis()+".jpg");
		System.out.println("****success****");
	}
 
}
