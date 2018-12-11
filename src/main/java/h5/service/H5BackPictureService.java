package h5.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import h5.dao.H5BackPictureDAO;
import h5.domain.H5BackPictureDomain;

/**
 * @author Administrator
 *
 */
@Service
public class H5BackPictureService {

	@Resource
	H5BackPictureDAO h5BackPictureDAO;

	public List<H5BackPictureDomain> findByName(H5BackPictureDomain name) {
		return h5BackPictureDAO.findByName(name);
	}

}
