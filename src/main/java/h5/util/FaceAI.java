/**
 *    Auth:riozenc
 *    Date:2018年11月20日 下午6:09:51
 *    Title:test.FaceAI.java
 **/
package h5.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.aip.face.AipFace;

import h5.domain.FaceAIDomain;

public class FaceAI {
	// 设置APPID/AK/SK
	public static final String APP_ID = "14878464";
	public static final String API_KEY = "jTS521GONtpvwUYuhh2B0Gsp";
	public static final String SECRET_KEY = "5IUhgfT2m2psT7kTELnwMp1fNmq5xnzA";

	public static Map<String, String> faceAI(String url, String fileName, String folderPath, Map<String, String> imgMap)
			throws URISyntaxException, JSONException, IOException {
		// 初始化一个AipFace
		AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

		// 左下角贴图处坐标
		double ratio = Double.parseDouble(Global.getConfig("master.ratio"));

		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("face_field", "age");

		System.out.println(url + fileName);
		JSONObject res = client.detect(url + fileName, "URL", params);
		System.out.println(res.toString(2));
		int errorCode = res.getInt("error_code");
		if (errorCode > 0) {
			imgMap.put("errorCode", errorCode + "");
			return imgMap;
		}

		FaceAIDomain faceAIDomain = new FaceAIDomain();
		faceAIDomain.setX(res.getJSONObject("result").getJSONArray("face_list").getJSONObject(0)
				.getJSONObject("location").getInt("left"));
		faceAIDomain.setY(res.getJSONObject("result").getJSONArray("face_list").getJSONObject(0)
				.getJSONObject("location").getInt("top"));
		faceAIDomain.setWidth(res.getJSONObject("result").getJSONArray("face_list").getJSONObject(0)
				.getJSONObject("location").getInt("width"));
		faceAIDomain.setHeight(res.getJSONObject("result").getJSONArray("face_list").getJSONObject(0)
				.getJSONObject("location").getInt("height"));

		/*
		 * double faceAIWidth=faceAIDomain.getWidth(); double
		 * faceAIHeight=faceAIDomain.getHeight(); double
		 * faceRatio=faceAIWidth/faceAIHeight;
		 */

		/*
		 * //宽/高>原来数值 保留高度 反之保留宽度 if(faceRatio>ratio) { int middleWidth=(int)
		 * (faceAIHeight*ratio); int
		 * middleCenter=faceAIDomain.getX()+faceAIDomain.getWidth()/2;
		 * faceAIDomain.setX(middleCenter-middleWidth/2);
		 * faceAIDomain.setWidth(middleWidth); }else { int middleHeight=(int)
		 * (faceAIWidth/ratio); int
		 * middleCenter=faceAIDomain.getY()+faceAIDomain.getHeight()/2;
		 * faceAIDomain.setY(middleCenter-middleHeight/2);
		 * faceAIDomain.setHeight(middleCenter); }
		 */

		File file = new File(folderPath + fileName);

		File outFile = new File(folderPath + fileName.replace("base", "cut"));
		InputStream in = new FileInputStream(file);
		OutputStream out = new FileOutputStream(outFile);

		int x = faceAIDomain.getX() - (faceAIDomain.getWidth() / 2);
		int y = faceAIDomain.getY() - (faceAIDomain.getHeight() / 2);
		
		int realX=x<0?0:x;
		int realY=y<0?0:y;
		
		int realWidth=(faceAIDomain.getX()+(faceAIDomain.getWidth() / 2)-realX)*2;
		int realHeight=(faceAIDomain.getY()+(faceAIDomain.getHeight() / 2)-realY)*2;

		cutJPG(in, out, realX, realY, realWidth, realHeight);
		out.flush();
		out.close();

		imgMap.put("absoluteCutPath", folderPath + fileName.replace("base", "cut"));
		return imgMap;

	}

	public static void cutJPG(InputStream input, OutputStream out, int x, int y, int width, int height)
			throws IOException {
		ImageInputStream imageStream = null;
		try {
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("png");
			ImageReader reader = readers.next();
			imageStream = ImageIO.createImageInputStream(input);
			reader.setInput(imageStream, true);
			ImageReadParam param = reader.getDefaultReadParam();

			System.out.println(reader.getWidth(0));
			System.out.println(reader.getHeight(0));
			Rectangle rect = new Rectangle(x, y - 20, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, "png", out);
		} finally {
			imageStream.close();
		}
	}

}
