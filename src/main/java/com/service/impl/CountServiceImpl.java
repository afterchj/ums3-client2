package com.service.impl;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dao.LogCountMemberDao;
import com.dao.LogCountStoreDao;
import com.dao.LogCountUserDao;
import com.dao.LogCountWorkDao;
import com.service.CountService;

@Service("countService")
public class CountServiceImpl implements CountService {

	private LogCountStoreDao logCountStoreDao;
	private LogCountWorkDao logCountWorkDao;
	private LogCountMemberDao logCountMemberDao;
	private LogCountUserDao logCountUserDao;

	@Override
	@Cacheable(value = "countService", key = "'countTotalDownload' + #fname")
	public String countTotalDownload(String fname) {
		Long count = logCountStoreDao.getTotalDownload(fname);
		if (count == null) {
			count = 0L;
		}
		return convert(count, "ZH");
	}

	private String convert(long count, String language) {
		count = count * 10;

		if (count < 10000) {
			if (count < 1000) {
				if (StringUtils.equalsIgnoreCase(language, "zh"))
					return "1000以下";
				else
					return "0-1000";
			} else {
				String number = StringUtils.substring(String.valueOf(count), 0,
						1);
				for (int i = 1; i < 10; i++) {
					if (Integer.valueOf(number).equals(i)) {
						return i + "000+";
					}
				}
			}
		} else {
			String number = StringUtils.substring(String.valueOf(count), 0, 1);
			String tenThousandPosition = StringUtils.substring(
					String.valueOf(count), 0,
					String.valueOf(count).length() - 4);

			for (int i = 1; i < 10; i++) {
				if (Integer.valueOf(number).equals(i)) {
					if (StringUtils.equalsIgnoreCase(language, "zh"))
						return tenThousandPosition + "万+";
					else {
						long result = Long
								.valueOf(tenThousandPosition + "0000");

						return new DecimalFormat(",###").format(result) + "+";
					}
				}
			}

		}
		return "";
	}

	@Autowired
	public void setLogCountContentDao(LogCountStoreDao logCountContentDao) {
		this.logCountStoreDao = logCountContentDao;
	}

	@Override
	@Cacheable(value = "countService", key = "'countWorkVisitors' + #workId")
	public Long countWorkVisitors(String workId) {
		Long count = logCountWorkDao.getTotalWorkVisitors(workId);
		if (count == null) {
			count = 0L;
		}
		return count;
	}

	@Autowired
	public void setLogCountWorkDao(LogCountWorkDao logCountWorkDao) {
		this.logCountWorkDao = logCountWorkDao;
	}

	@Override
	@Cacheable(value = "countService", key = "'countMemberVisitors' + #memberId")
	public Long countMemberVisitors(String memberId) {
		Long count = logCountMemberDao.getTotalMemberVisitors(memberId);
		if (count == null) {
			count = 0L;
		}
		return count;
	}
	
	@Override
	public Long countUserVisitors(String uid) {
		Long count = logCountUserDao.getTotalUserVisitors(uid);
		if (count == null) {
			count = 0L;
		}
		return count;
	}
	
	@Override
	@Cacheable(value = "countService", key = "'countWorkPraisers' + #memberId")
	public Long countWorkPraisers(String memberId) {
		Long count = logCountWorkDao.getTotalWorkPraisers(memberId);
		if (count == null) {
			count = 0L;
		}
		return count;
	}

	@Autowired
	public void setLogCountMemberDao(LogCountMemberDao logCountMemberDao) {
		this.logCountMemberDao = logCountMemberDao;
	}

	@Autowired
	public void setLogCountUserDao(LogCountUserDao logCountUserDao) {
		this.logCountUserDao = logCountUserDao;
	}

}
