/**
 * 2018年11月22日
 * 
 */
package h5;

import java.util.Random;


import h5.domain.H5BackPictureDomain;
import h5.util.PictureDealUtil;

/**
 * <p>
 * Title: TestCV.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zjd
 * @date 2018年11月22日
 * @remark Code is far away from bugs with the god animal protecting
 */
public class TestCV {
	public static void main(String[] args) {
		
		
		
		H5BackPictureDomain backgroundImg = new H5BackPictureDomain();
		backgroundImg.setX(0);
		backgroundImg.setY(0);
		backgroundImg.setWidth(227);
		backgroundImg.setHeight(173);
		
		String bjPath = "D:\\Tomcat 8.5\\webapps\\uploadImgFolder\\01.png";
		String facePath = "D:\\Tomcat 8.5\\webapps\\uploadImgFolder\\1542873837206base.jpg";
		// 正式图路径
		PictureDealUtil.overlapImage(bjPath, facePath, "D:\\Tomcat 8.5\\webapps\\uploadImgFolder\\1542873837206end.jpg", backgroundImg);

		Random randomXG = new Random();
		int jyRandomNum = randomXG.nextInt(1);
		int xgRandomNum = randomXG.nextInt(1);
		

		// 寄语路径
		String jyPath = "D:\\Tomcat 8.5\\webapps\\uploadImgFolder\\JY\\1.png";
		System.out.println("jyPath==========" + jyPath);
		H5BackPictureDomain jyXY = new H5BackPictureDomain();
		jyXY.setX(122);
		jyXY.setY(306);
		jyXY.setWidth(227);
		jyXY.setHeight(173);
		PictureDealUtil.overlapImage("D:\\Tomcat 8.5\\webapps\\uploadImgFolder\\1542873837206end.jpg", jyPath, "D:\\Tomcat 8.5\\webapps\\uploadImgFolder\\1542873837206end.jpg", jyXY);

		// 性格路径
		String xgPath = "D:\\Tomcat 8.5\\webapps\\uploadImgFolder\\XG\\1.png";
		System.out.println("xgPath==========" + xgPath);

		H5BackPictureDomain xgXY = new H5BackPictureDomain();
		xgXY.setX(169);
		xgXY.setY(614);
		xgXY.setWidth(227);
		xgXY.setHeight(173);

		PictureDealUtil.overlapImage("D:\\Tomcat 8.5\\webapps\\uploadImgFolder\\1542873837206end.jpg", xgPath, "D:\\Tomcat 8.5\\webapps\\uploadImgFolder\\1542873837206end.jpg", xgXY);
	}
}
