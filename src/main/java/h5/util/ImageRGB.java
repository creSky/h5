/**
 * 2018年11月22日
 * 
 */
package h5.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

/**
 * <p>
 * Title: ImageRGB.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zjd
 * @date 2018年11月22日
 * @remark Code is far away from bugs with the god animal protecting
 */
//处理灰度值
public class ImageRGB {
	public static void main(String[] args) {
		long time = new Date().getTime();
		makeImageColorToBlackWhite("C:\\Users\\Administrator\\Desktop\\1.jpg");
		System.out.println(new Date().getTime() - time + "ms");
		}

	/**
	 * 将彩色图片变为灰色的图片
	 * 
	 * @param imagePath
	 */
	public static void makeImageColorToBlackWhite(String imagePath) {
		int[][] result = getImageGRB(imagePath);
		int[] rgb = new int[3];
		BufferedImage bi = new BufferedImage(result.length, result[0].length, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				rgb[0] = (result[i][j] & 0xff0000) >> 16;
				rgb[1] = (result[i][j] & 0xff00) >> 8;
				rgb[2] = (result[i][j] & 0xff);
				int color = (int) (rgb[0] * 0.3 + rgb[1] * 0.59 + rgb[2] * 0.11);
				// color = color > 128 ? 255 : 0;
				bi.setRGB(i, j, (color << 16) | (color << 8) | color);
			}
		}
		try {
			ImageIO.write(bi, "JPEG", new File("C:\\Users\\Administrator\\Desktop\\2.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取图片的像素点
	 * 
	 * @param filePath
	 * @return
	 */
	public static int[][] getImageGRB(String filePath) {
		File file = new File(filePath);
		int[][] result = null;
		if (!file.exists()) {
			System.out.println("图片不存在");
			return result;
		}
		try {
			BufferedImage bufImg = ImageIO.read(file);
			int height = bufImg.getHeight();
			int width = bufImg.getWidth();
			result = new int[width][height];
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					result[i][j] = bufImg.getRGB(i, j) & 0xFFFFFF;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	


}
