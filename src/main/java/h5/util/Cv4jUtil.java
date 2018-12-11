/**
 * 2018年11月22日
 * 
 */
package h5.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cv4j.core.datamodel.CV4JImage;
import com.cv4j.core.filters.SepiaToneFilter;

/**
 * <p>
 * Title: Cv4jUtil.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zjd
 * @date 2018年11月22日
 * @remark Code is far away from bugs with the god animal protecting
 */
public class Cv4jUtil {
	public static void handle(String inPath, String outPath) throws IOException {
		File file = new File(inPath);
		BufferedImage image = ImageIO.read(file);
		CV4JImage cv4jImage = new CV4JImage(image);
		SepiaToneFilter sepiaToneFilter = new SepiaToneFilter();
		sepiaToneFilter.filter(cv4jImage.getProcessor());
		cv4jImage.savePic(cv4jImage.getProcessor().getImage().toBitmap(), outPath);
	}
}
