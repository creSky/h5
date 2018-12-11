package h5.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import h5.dao.H5UserAnswerDAO;
import h5.domain.H5UserAnswerDomain;

/**
 * @author Administrator
 *
 */
@Service
public class H5UserAnswerService {

	@Resource
	H5UserAnswerDAO h5UserAnswerDAO;

	public Integer insert(H5UserAnswerDomain h5UserAnswerDomain) {
		return h5UserAnswerDAO.insert(h5UserAnswerDomain);
	}

}
