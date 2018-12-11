/**
 * 2018年11月23日
 * 
 */
package h5.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import h5.domain.H5BackPictureDomain;
import h5.domain.H5UserPictureDomain;
import h5.service.H5UserPictureService;
import h5.util.Cv4jUtil;
import h5.util.FaceAI;
import h5.util.Global;
import h5.util.PictureDealUtil;

/**  
* <p>Title: Controller.java</p>  
* <p>Description: </p>  
* @author zjd  
* @date 2018年11月23日  
* @remark Code is far away from bugs with the god animal protecting
*/
@Controller
@RequestMapping("/controllerException")
public class ControllerException {
	@Autowired
	private H5UserPictureService h5UserPictureService;
    @RequestMapping(value = "/error")
    public void test(){
       int b=10/0;
        System.out.println(b);
    }
    
    @RequestMapping(params = "method=uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadImg(String file, HttpServletRequest request) throws Exception {
		System.out.println(file);
		String projectFolder = request.getSession().getServletContext().getRealPath("/");

		String uploadImgFolder = new File(projectFolder).getParentFile().getAbsolutePath() + File.separator
				+ "uploadImgFolder" + File.separator;

		System.out.println("上传文件目录uploadImgFolder==========" + uploadImgFolder);

		// json字符串转对象
		// ObjectMapper objectMapper = new ObjectMapper();
		//
		// Img img = objectMapper.readValue(uploadImgFile, Img.class);

		// 取base64图片编码后字符串
		// String base64File = img.getFormdata();

		// base64解码 生成字节流
		byte[] baseCode = decodeBase64(file);

		// 保存图片 (旋转完的图片)
		Map<String, String> imgPath = saveImgFile(baseCode, 0, uploadImgFolder);

	

		return null;

	}
	/**
	 * <p>
	 * Title: decodeBase64
	 * </p>
	 * <p>
	 * Description: base64解码
	 * </p>
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private byte[] decodeBase64(String base64File) {
		base64File = base64File.replaceAll("data:image/jpeg;base64,", "");

		Base64.Decoder decoder = Base64.getDecoder();

		// BASE64Decoder decoder = new BASE64Decoder();
		// Base64解码
		byte[] imageByte = null;
		try {
			imageByte = decoder.decode(base64File);
			;
			for (int i = 0; i < imageByte.length; ++i) {
				if (imageByte[i] < 0) {// 调整异常数据
					imageByte[i] += 256;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return imageByte;
	}
	private Map<String, String> saveImgFile(byte[] baseCode, int count, String uploadImgFolder) throws Exception {
		// 项目在容器中实际发布运行的根路径
		Map<String, String> imgMap = new HashMap<String, String>();
		String realPath = uploadImgFolder;
		String fileName = null;
		String fileFolderPath = null;

		if (baseCode != null) {// 判断上传的文件是否为空
			Long time = System.currentTimeMillis();

			fileName = time.toString() + "base.npg";

			fileFolderPath = realPath + fileName;// 文件原名称

			System.out.println("上传的文件原名称:" + fileFolderPath);

			// 流存文件
			OutputStream out;
			try {
				out = new FileOutputStream(fileFolderPath);
				out.write(baseCode);
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			PictureDealUtil.spin(fileFolderPath, fileFolderPath, count * -90);
			// 获取原图尺寸
			// BufferedImage sourceImg = ImageIO.read(new FileInputStream(fileName));
			//
			// System.out.println(sourceImg.getHeight());
			// System.out.println(sourceImg.getWidth());
			//
			// // 等比例缩放
			// Double heightRatio = new Double((double) sourceImg.getHeight() / (double)
			// img.getPicHeight());
			// Double weightRatio = new Double((double) sourceImg.getWidth() / (double)
			// img.getPicWidth());
			//
			// Double newX = new Double(img.getX() * weightRatio);
			// Double newY = new Double(img.getY() * heightRatio);
			// Double newWidth = new Double(img.getWidth() * weightRatio);
			// Double newHeight = new Double(img.getHeight() * heightRatio);
			//
			// // 自定义的文件名称
			// OperateImage o = new OperateImage(newX.intValue(), newY.intValue(),
			// newWidth.intValue(),
			// newHeight.intValue());
			//
			// relEndFile = time.toString() + "end.jpg";
			// endFileName = realPath + relEndFile;
			//
			// o.setSrcpath(fileName);
			//
			// o.setSubpath(endFileName);
			//
			// o.cut();
		} else {
			System.out.println("base 64  为空 ....");
		}

		imgMap.put("urlSourcePath", Global.getConfig("master.realUrl") + fileName);
		imgMap.put("absoluteSourcePath", fileFolderPath);
		imgMap.put("fileName", fileName);
		return imgMap;

	}

}
