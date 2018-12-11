package h5.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import h5.dao.H5UserPictureDAO;
import h5.domain.H5UserPictureDomain;

/**
 * @author Administrator
 *
 */
@Service
public class H5UserPictureService {

	@Resource
	H5UserPictureDAO h5UserPictureDAO;

	public Integer insert(H5UserPictureDomain h5UserPictureDomain) {
		return h5UserPictureDAO.insert(h5UserPictureDomain);
	}

}
