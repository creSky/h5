/**
 * 2018年11月16日
 * 
 */
package h5;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.json.JSONException;
import org.springframework.http.HttpRequest;

import h5.util.FaceAI;
import h5.util.Global;
import h5.util.QrCodeUtil;

/**
 * <p>
 * Title: test.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zjd
 * @date 2018年11月16日
 * @remark Code is far away from bugs with the god animal protecting
 */
public class test4 {

	/**
	 * <p>
	 * Title: main
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param args
	 * @throws IOException
	 * @throws URISyntaxException 
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws IOException, JSONException, URISyntaxException {
//		String fileName = "1542899012847base.png";
//		// AI图像识别
//		Map<String, String> aa = new HashMap<>();
//		Map<String, String> cutmgPath = FaceAI.faceAI(Global.getConfig("master.realUrl"), fileName,
//				"C:\\Users\\Administrator\\Desktop\\", aa);
		
		String aa="test";
		System.out.println(aa.hashCode());
	}
}
