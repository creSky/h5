package h5.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import h5.dao.H5UserDAO;
import h5.domain.H5UserDomain;

/**
 * @author Administrator
 *
 */
@Service
public class H5UserService {

	@Resource
	H5UserDAO h5UserDAO;

	public Integer insert(H5UserDomain h5UserDomain) {
		return h5UserDAO.insert(h5UserDomain);
	}

	
	public Integer findSex(int id) {
		return h5UserDAO.findSex(id);
	}
	
	public String findName(int id) {
		return h5UserDAO.findName(id);
	}

}
