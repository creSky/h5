/**
 * 2018年11月22日
 * 
 */
package h5.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import h5.cache.BaseCache;

/**
 * <p>
 * Title: BaseListener.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zjd
 * @date 2018年11月22日
 * @remark Code is far away from bugs with the god animal protecting
 */
public class BaseListener implements ServletContextListener {
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		BaseCache.init(sce);
	}
}
