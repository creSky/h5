/**
 * @Title:FileUtil.java
 * @author:Riozenc
 * @datetime:2015年8月18日 下午3:58:29
 */
package h5.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.util.Base64Utils;


public class FileUtil {

	/**
	 * 上传图片 基于base64
	 * 
	 * @param base64Data
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static File uploadPictureByBase64(String base64Data, String path, String fileName) throws Exception {

		String dataPrix = "";
		String data = "";
		if (base64Data == null || "".equals(base64Data)) {
			throw new Exception("上传失败，上传图片数据为空");
		} else {
			String[] d = base64Data.split("base64,");
			if (d != null && d.length == 2) {
				dataPrix = d[0];
				data = d[1];
			} else {
				throw new Exception("上传失败，数据不合法");
			}
		}
		String suffix = null;
		if ("data:image/jpg;".equalsIgnoreCase(dataPrix)) {// data:image/jpeg;base64,base64编码的jpeg图片数据
			suffix = ".jpg";
		} else if ("data:image/jpeg;".equalsIgnoreCase(dataPrix)) {// data:image/jpeg;base64,base64编码的jpeg图片数据
			suffix = ".jpg";
		} else if ("data:image/x-icon;".equalsIgnoreCase(dataPrix)) {// data:image/x-icon;base64,base64编码的icon图片数据
			suffix = ".ico";
		} else if ("data:image/gif;".equalsIgnoreCase(dataPrix)) {// data:image/gif;base64,base64编码的gif图片数据
			suffix = ".gif";
		} else if ("data:image/png;".equalsIgnoreCase(dataPrix)) {// data:image/png;base64,base64编码的png图片数据
			suffix = ".png";
		} else {
			throw new Exception("上传图片格式不合法");
		}
		File file = FileUtil.createFile(path + File.separator + fileName + suffix);
		byte[] bs = Base64Utils.decodeFromString(data);
		try {
			// 使用apache提供的工具类操作流
			FileUtils.writeByteArrayToFile(file, bs);
		} catch (Exception ee) {
			throw new Exception("上传失败，写入文件失败，" + ee.getMessage());
		}
		return file;
	}

	public static void upload(File file, String fileName) {
		String docPath = Global.getConfig("file.doc.path");

		if (isDirectory(new File(docPath))) {
			// 是文件夹

		}
	}

	public static File createFile(String fileName) {
		File file = new File(fileName);
		try {
			if (file.exists()) {
				if (file.isDirectory()) {
					throw new IOException("File '" + file + "' exists but is a directory");
				}
				if (file.canWrite() == false) {
					throw new IOException("File '" + file + "' cannot be written to");
				}
			} else {
				final File parent = file.getParentFile();
				if (parent != null) {
					if (!parent.mkdirs() && !parent.isDirectory()) {
						throw new IOException("Directory '" + parent + "' could not be created");
					}
				}
			}
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;

	}

	public static File createFile(String docPath, String fileName) {
		return createFile(docPath + File.separator + fileName);
	}

	/**
	 * 校验文件合理性
	 * 
	 * @param file
	 * @return
	 */
	public static boolean fileIsValid(File file) {
		// 判断文件是否存在
		if (file.exists()) {
			// 判断文件是否是目录
			if (!file.isDirectory()) {
				// 文件
				if (file.isFile()) {
					return true;
				}
			}

		} else {

			throw new RuntimeException(file.getPath() + "文件不存在。。。");
		}
		return false;
	}

	/**
	 * 是否为目录
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isDirectory(File file) {

		// 判断文件是否存在
		if (file.exists()) {
			// 判断文件是否是目录
			if (file.isDirectory()) {
				// 文档目录
				return true;
			} else {
				return false;
			}

		} else {
			throw new RuntimeException(file.getPath() + "文件不存在。。。");
		}
	}

}
