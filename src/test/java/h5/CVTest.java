/**
 *    Auth:riozenc
 *    Date:2018年11月22日 上午10:03:24
 *    Title:test.CVTest.java
 **/
package h5;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cv4j.core.datamodel.CV4JImage;
import com.cv4j.core.datamodel.ImageProcessor;
import com.cv4j.core.filters.MeansBinaryFilter;
import com.cv4j.core.filters.SepiaToneFilter;
import com.cv4j.core.filters.StrokeAreaFilter;
import com.cv4j.core.pixels.Resize;

public class CVTest {
	public static void main(String[] args) throws IOException {
		File file = new File("C:\\Users\\riozenc\\Desktop\\test.jpg");
		BufferedImage image = ImageIO.read(file);

		CV4JImage cv4jImage = new CV4JImage(image);

//		MeansBinaryFilter meansBinaryFilter = new MeansBinaryFilter();
//		meansBinaryFilter.filter(cv4jImage.getProcessor());

		SepiaToneFilter sepiaToneFilter = new SepiaToneFilter();
		sepiaToneFilter.filter(cv4jImage.getProcessor());

//		//铅笔画
//		StrokeAreaFilter strokeAreaFilter = new StrokeAreaFilter();
//		strokeAreaFilter.filter(cv4jImage.getProcessor());

//		Resize resize = new Resize(0.75f);
//
//		ImageProcessor imageProcessor = resize.resize(cv4jImage.getProcessor(), Resize.NEAREST_INTEPOLATE);
//
//		if (imageProcessor != null) {
//			CV4JImage resultCV4JImage = new CV4JImage(imageProcessor.getWidth(), imageProcessor.getHeight(),
//					imageProcessor.getPixels());
//			BufferedImage bufferedImage= resultCV4JImage.getProcessor().getImage().toBitmap();
//			
//			cv4jImage.savePic(resultCV4JImage.getProcessor().getImage().toBitmap(), "C:\\Users\\riozenc\\Desktop\\czy.jpg");
//		}

		cv4jImage.savePic(cv4jImage.getProcessor().getImage().toBitmap(), "C:\\Users\\riozenc\\Desktop\\czy.jpg");
		// ImageIO.write(bitmap, "png", out);
	}
}
