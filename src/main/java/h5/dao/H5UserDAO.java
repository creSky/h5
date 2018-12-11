/**
 * 2018年11月15日
 * 
 */
package h5.dao;

import h5.domain.H5UserDomain;

/**  
* <p>Title: h5Dao.java</p>  
* <p>Description: </p>  
* @author zjd  
* @date 2018年11月15日  
* @remark Code is far away from bugs with the god animal protecting
*/
public interface H5UserDAO {
	public int insert(H5UserDomain t);
	public int findSex(int t);
	public String  findName(int t);
}
