/**
 * 2018年11月22日
 * 
 */
package h5.cache;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Title: BaseCache.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zjd
 * @date 2018年11月22日
 * @remark Code is far away from bugs with the god animal protecting
 */
public class BaseCache {

	private static final Map<Integer, String> XG = new ConcurrentHashMap<>();
	private static final Map<Integer, String> JY = new ConcurrentHashMap<>();

	
	public static int getXGSize() {
		return XG.size();
	}
	
	public static int getJYSize() {
		return JY.size();
	}
	
	public static void init(ServletContextEvent contextEvent) {
		String projectFolder=contextEvent.getServletContext().getRealPath("/");
		String uploadImgFolder = new File(projectFolder).getParentFile().getAbsolutePath() + File.separator
				+ "uploadImgFolder" + File.separator;
		String xgPath=uploadImgFolder+"XG"+File.separator;
		String jyPath=uploadImgFolder+"JY"+File.separator;
		XG.put(0, xgPath+"1.png");
		XG.put(1, xgPath+"2.png");
		XG.put(2, xgPath+"3.png");
		XG.put(3, xgPath+"4.png");
		XG.put(4, xgPath+"5.png");
		XG.put(5, xgPath+"6.png");
		XG.put(6, xgPath+"7.png");
		XG.put(7, xgPath+"8.png");
		XG.put(8, xgPath+"9.png");
		

		JY.put(0, jyPath+"1.png");
		JY.put(1, jyPath+"2.png");
		JY.put(2, jyPath+"3.png");
		JY.put(3, jyPath+"4.png");
		JY.put(4, jyPath+"5.png");
		JY.put(5, jyPath+"6.png");
		JY.put(6, jyPath+"7.png");
		JY.put(7, jyPath+"8.png");
		JY.put(8, jyPath+"9.png");
		JY.put(9, jyPath+"10.png");
		JY.put(10, jyPath+"11.png");
		JY.put(11, jyPath+"12.png");
		JY.put(12, jyPath+"13.png");

	}

	public static String getXG(Integer id) {
		if (XG.get(id) == null) {
			id = 0;
		}
		return XG.get(id);
	}

	public static String getJY(Integer id) {
		if (JY.get(id) == null) {
			id = 0;
		}
		return JY.get(id);
	}

}
