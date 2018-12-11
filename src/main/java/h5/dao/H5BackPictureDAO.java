/**
 * 2018年11月15日
 * 
 */
package h5.dao;

import java.util.List;

import h5.domain.H5BackPictureDomain;

/**  
* <p>Title: h5Dao.java</p>  
* <p>Description: </p>  
* @author zjd  
* @date 2018年11月15日  
* @remark Code is far away from bugs with the god animal protecting
*/
public interface H5BackPictureDAO {
	public List<H5BackPictureDomain> findByName(H5BackPictureDomain t);
}
