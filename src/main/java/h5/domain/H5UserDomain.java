/**
 * 2018年11月15日
 * 
 */
package h5.domain;

import java.util.Date;

/**
 * <p>
 * Title: h5User.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zjd
 * @date 2018年11月15日
 * @remark Code is far away from bugs with the god animal protecting
 */
public class H5UserDomain {
	private Integer id;
	private String name;
	private String age;
	private Character sex;
	private Date date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Character getSex() {
		return sex;
	}

	public void setSex(Character sex) {
		this.sex = sex;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
