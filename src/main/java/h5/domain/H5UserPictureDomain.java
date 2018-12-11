/**
 * 2018年11月15日
 * 
 */
package h5.domain;

/**  
* <p>Title: H5UserPictureDomain.java</p>  
* <p>Description: </p>  
* @author zjd  
* @date 2018年11月15日  
* @remark Code is far away from bugs with the god animal protecting
*/
public class H5UserPictureDomain {
	private Integer id;
	private String sourcePath;
	private String cutPath;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
	public String getCutPath() {
		return cutPath;
	}
	public void setCutPath(String cutPath) {
		this.cutPath = cutPath;
	}
	
}
