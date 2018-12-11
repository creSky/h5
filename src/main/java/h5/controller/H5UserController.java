/**
 * 2018年11月15日
 * 
 */
package h5.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import h5.cache.BaseCache;
import h5.domain.H5BackPictureDomain;
import h5.domain.H5UserAnswerDomain;
import h5.domain.H5UserDomain;
import h5.domain.H5UserPictureDomain;
import h5.service.H5BackPictureService;
import h5.service.H5UserAnswerService;
import h5.service.H5UserPictureService;
import h5.service.H5UserService;
import h5.util.Cv4jUtil;
import h5.util.FaceAI;
import h5.util.Global;
import h5.util.PictureDealUtil;

/**
 * <p>
 * Title: H5Controller.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zjd
 * @date 2018年11月15日
 * @remark Code is far away from bugs with the god animal protecting
 */
@ControllerAdvice
@RequestMapping("/h5UserController")
public class H5UserController {

	@Autowired
	private H5UserService h5UserService;

	@Autowired
	private H5UserPictureService h5UserPictureService;

	@Autowired
	private H5UserAnswerService h5UserAnswerService;

	@Autowired
	private H5BackPictureService h5BackPictureService;

	/**
	 * <p>
	 * Title: insertH5User
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param h5UserDomain
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=insertH5User", method = RequestMethod.POST)
	@ResponseBody
	public int insertH5User(H5UserDomain h5UserDomain, HttpServletRequest request) {

		h5UserDomain.setDate(new Date());

		h5UserService.insert(h5UserDomain);

		int id = h5UserDomain.getId();

		// System.out.println("num=============" +
		// request.getSession().getServletContext().getRealPath("/"));

		return id;

	}

	@RequestMapping(params = "method=saveAnswer", method = RequestMethod.POST)
	@ResponseBody
	public int saveAnswer(H5UserAnswerDomain formData) {
		h5UserAnswerService.insert(formData);
		return formData.getId();
	}

	/**
	 * <p>
	 * Title: uploadImg
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param uploadImgFile
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=uploadImg", method = RequestMethod.POST)
	@ResponseBody
	// public Object uploadImg(@RequestBody String uploadImgFile, HttpServletRequest
	// request) throws Exception {
	public Object uploadImg(String base64File, Integer id, Integer count, HttpServletRequest request) throws Exception {
		System.out.println("*******************id:" + id);
		System.out.println("*******************count:" + count);
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
		byte[] baseCode = decodeBase64(base64File);

		// 保存图片 (旋转完的图片)
		Map<String, String> imgPath = saveImgFile(baseCode, count, uploadImgFolder);

		// AI图像识别
		Map<String, String> cutmgPath = FaceAI.faceAI(Global.getConfig("master.realUrl"), imgPath.get("fileName"),
				uploadImgFolder, imgPath);

		// 图像识别返回
		if (cutmgPath.get("errorCode") != null && Integer.parseInt(cutmgPath.get("errorCode")) > 0) {
			return "999";
		}

		// 获取背景图头像坐标
		H5BackPictureDomain backgroundImg = getBackgroundImg(id);

		// 空白图
		// String whitePicture=uploadImgFolder+"white.png";

		// 缩放图片
		System.out.println("===============backgroundImg:" + backgroundImg);
		System.out.println("===============cutmgPath:" + cutmgPath.get("absoluteCutPath"));
		String newCutPath = reduceImg(backgroundImg, cutmgPath.get("absoluteCutPath"));

		// 滤镜
		Cv4jUtil.handle(newCutPath, newCutPath);

		// 缩放图像+空白图路径
		// String middleOutFile=cutmgPath.get("absoluteCutPath").replace("cut",
		// "middle");

		// //叠加空白图
		// PictureDealUtil.overlapImage(whitePicture, newCutPath,
		// middleOutFile,backgroundImg);
		//
		//
		String endOutFile = newCutPath.replace("cut", "end");

		String backgroundEndImg = uploadImgFolder + backgroundImg.getUrl();

		// 正式图路径
		PictureDealUtil.overlapImage(backgroundEndImg, cutmgPath.get("absoluteCutPath"), endOutFile, backgroundImg);

		cutmgPath.put("outAbsoluteFilePath",
				Global.getConfig("master.realUrl") + imgPath.get("fileName").replaceAll("base", "end"));
		// 返回新文件路径
		// imgPath.put("backgroundImg", backgroundImg);

		// 保存图片
		saveImgFile(cutmgPath, id);

		return imgPath;

	}

	// 不裁剪图片
	@RequestMapping(params = "method=upload", method = RequestMethod.POST)
	public String upload(Integer id, Integer count, HttpServletRequest request) throws Exception {
		// 获取路径
		String projectFolder = request.getSession().getServletContext().getRealPath("/");

		String uploadImgFolder = new File(projectFolder).getParentFile().getAbsolutePath() + File.separator
				+ "uploadImgFolder" + File.separator;

		Long startTime = System.currentTimeMillis();

		String fileName = startTime.toString() + "base.png";
		System.out.println(fileName);
		File file = null;
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {

			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile multipartFile = multiRequest.getFile(iter.next().toString());
				if (multipartFile != null) {
					String path = uploadImgFolder + fileName;
					// 上传
					file = new File(path);
					multipartFile.transferTo(file);
				}
			}
		}

		while (!file.exists()) {
			Thread.sleep(500);
		}

		// 保存图片 (旋转完的图片)
		Map<String, String> imgPath = saveImgFile(file.getName(), file.getAbsolutePath(), count, uploadImgFolder);

		// AI图像识别
		Map<String, String> cutmgPath = FaceAI.faceAI(Global.getConfig("master.realUrl"), imgPath.get("fileName"),
				uploadImgFolder, imgPath);

		// 图像识别返回
		if (cutmgPath.get("errorCode") != null && Integer.parseInt(cutmgPath.get("errorCode")) > 0) {
			return "404.html";
		}

		// 获取背景图头像坐标
		H5BackPictureDomain backgroundImg = getBackgroundImg(id);

		// 空白图
		// String whitePicture=uploadImgFolder+"white.png";

		// 缩放图片
		System.out.println("===============backgroundImg:" + backgroundImg);
		System.out.println("===============cutmgPath:" + cutmgPath.get("absoluteCutPath"));
		String newCutPath = reduceImg(backgroundImg, cutmgPath.get("absoluteCutPath"));

		// 滤镜
		Cv4jUtil.handle(newCutPath, newCutPath);

		//
		String endOutFile = newCutPath.replace("cut", "end");

		String backgroundEndImg = uploadImgFolder + backgroundImg.getUrl();

		// 正式图路径
		PictureDealUtil.overlapImage(backgroundEndImg, cutmgPath.get("absoluteCutPath"), endOutFile, backgroundImg);

		Random randomXG = new Random();
		int jyRandomNum = randomXG.nextInt(BaseCache.getJYSize());
		int xgRandomNum = randomXG.nextInt(BaseCache.getXGSize());

		// 寄语路径
		String jyPath = BaseCache.getJY(jyRandomNum);
		System.out.println("jyPath==========" + jyPath);
		H5BackPictureDomain jyXY = new H5BackPictureDomain();
		jyXY.setX(122);
		jyXY.setY(306);
		jyXY.setWidth(227);
		jyXY.setHeight(173);
		System.out.println(cutmgPath.get("absoluteCutPath") + "======" + endOutFile);
		PictureDealUtil.overlapImage(endOutFile, jyPath, endOutFile, jyXY);

		// 性格路径
		String xgPath = BaseCache.getXG(xgRandomNum);
		System.out.println("xgPath==========" + xgPath);

		H5BackPictureDomain xgXY = new H5BackPictureDomain();
		xgXY.setX(169);
		xgXY.setY(614);
		xgXY.setWidth(227);
		xgXY.setHeight(173);

		PictureDealUtil.overlapImage(endOutFile, xgPath, endOutFile, xgXY);

		writeToPage(endOutFile, h5UserService.findName(id));

		cutmgPath.put("outAbsoluteFilePath",
				Global.getConfig("master.realUrl") + imgPath.get("fileName").replaceAll("base", "end"));
		// 返回新文件路径
		// imgPath.put("backgroundImg", backgroundImg);

		// 保存图片
		saveImgFile(cutmgPath, id);

		return "redirect:makeCard.html?outAbsoluteFilePath=" + cutmgPath.get("outAbsoluteFilePath");

	}

	/**
	 * <p>
	 * Title: reduceImg
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	private String reduceImg(H5BackPictureDomain h5BackPictureDomain, String cutPath) {
		System.out.println("h5BackPictureDomain:" + h5BackPictureDomain);
		System.out.println(
				"=========压缩" + h5BackPictureDomain.getWidth() + "=========" + h5BackPictureDomain.getHeight());
		System.out.println("=========压缩cutPath" + cutPath + "=========");

		File srcfile = new File(cutPath);
		System.out.println("压缩前图片大小：" + srcfile.length());
		PictureDealUtil.reduceImg(cutPath, cutPath, h5BackPictureDomain.getWidth(), h5BackPictureDomain.getHeight(),
				null);
		System.out.println("压缩后图片大小：" + srcfile.length());
		return cutPath;

	}

	/**
	 * <p>
	 * Title: saveImgFile
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	private void saveImgFile(Map<String, String> imgPath, int id) {

		// TODO Auto-generated method stub
		H5UserPictureDomain h5UserPictureDomain = new H5UserPictureDomain();

		h5UserPictureDomain.setId(id);

		h5UserPictureDomain.setSourcePath(imgPath.get("sourcePath"));

		h5UserPictureDomain.setCutPath(imgPath.get("outAbsoluteFilePath"));

		h5UserPictureService.insert(h5UserPictureDomain);
	}

	/**
	 * <p>
	 * Title: getBackgroundImg
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	private H5BackPictureDomain getBackgroundImg(int id) {

		// BackPictureCache.getH5BackPictureDomain(id);

		int sex = h5UserService.findSex(id);
		H5BackPictureDomain baseH5BackPictureDomain = new H5BackPictureDomain();
		baseH5BackPictureDomain.setSex(sex);
		List<H5BackPictureDomain> h5BackPictureDomains = h5BackPictureService.findByName(baseH5BackPictureDomain);
		Random random = new Random();
		int randomNum = random.nextInt(h5BackPictureDomains.size());
		System.out.println(
				"sex:" + sex + " randomNum:" + randomNum + " id:" + id + "  ===" + h5BackPictureDomains.get(randomNum));
		return h5BackPictureDomains.get(randomNum);
		// TODO Auto-generated method stub

	}

	/**
	 * <p>
	 * Title: publict
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @throws Exception
	 */
	private Map<String, String> saveImgFile(String fileName, String fileFolderPath, int count, String uploadImgFolder)
			throws Exception {
		// 项目在容器中实际发布运行的根路径
		Map<String, String> imgMap = new HashMap<String, String>();
		PictureDealUtil.spin(fileFolderPath, fileFolderPath, count * -90);
		imgMap.put("urlSourcePath", Global.getConfig("master.realUrl") + fileName);
		imgMap.put("absoluteSourcePath", fileFolderPath);
		imgMap.put("fileName", fileName);
		return imgMap;

	}

	/**
	 * <p>
	 * Title: saveImgFile
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @throws Exception
	 */
	private Map<String, String> saveImgFile(byte[] baseCode, int count, String uploadImgFolder) throws Exception {
		// 项目在容器中实际发布运行的根路径
		Map<String, String> imgMap = new HashMap<String, String>();
		String realPath = uploadImgFolder;
		String fileName = null;
		String fileFolderPath = null;

		if (baseCode != null) {// 判断上传的文件是否为空
			Long time = System.currentTimeMillis();

			fileName = time.toString() + "base.png";

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

	public void writeToPage(String filePath, String name) throws FileNotFoundException, IOException {

		ImageIcon imgIcon = new ImageIcon(filePath);
		Image theImg = imgIcon.getImage();
		int width = theImg.getWidth(null) == -1 ? 200 : theImg.getWidth(null);
		int height = theImg.getHeight(null) == -1 ? 200 : theImg.getHeight(null);
		BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bimage.createGraphics();
		Color mycolor = Color.decode("#23375d");
		g.setColor(mycolor);
		g.setBackground(Color.red);
		g.drawImage(theImg, 0, 0, null);
		g.setFont(new Font("华文中宋", Font.PLAIN, 23)); // 字体、字型、字号
		g.drawString(name, 69, 593); // 画文字
		g.dispose();
		ImageIO.write(bimage, "png", new FileOutputStream(filePath));
	}

	static class Img {
		private int id;
		private String formdata;
		private int count;

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getFormdata() {
			return formdata;
		}

		public void setFormdata(String formdata) {
			this.formdata = formdata;
		}

	}

}
