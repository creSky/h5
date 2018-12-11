/**
 * 2018年11月16日
 * 
 */
package h5.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

/**
 * <p>
 * Title: QrCodeUtil.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zjd
 * @date 2018年11月16日
 * @remark Code is far away from bugs with the god animal protecting
 */
public class QrCodeUtil {
	//生成二维码
	public static String getQrCode() throws IOException {
		String qrCodePath = h5.util.Global.getConfig("master.qrCodePath") + "qrCode.jpg";
		String qrCodeUrl = h5.util.Global.getConfig("master.qrCodeUrl");
		ByteArrayOutputStream out = QRCode.from(qrCodeUrl).to(ImageType.PNG).stream();
		File file = new File(qrCodePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		byte[] data = out.toByteArray();
		OutputStream oute = new FileOutputStream(file);
		oute.write(data);
		oute.flush();
		oute.close();
		return qrCodePath;
	}
}
