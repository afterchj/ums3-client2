package com.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dao.CategoryDao;
import com.dao.FileStoreInfoDao;
import com.google.common.collect.Lists;
import com.mapper.BeanMapper;
import com.model.Category;
import com.model.FileStoreInfo;
import com.model.ThemeFile;
import com.model.dd.DataDict;
import com.service.AppAdService;
import com.service.CountService;
import com.service.FileStoreInfoService;
import com.utils.CollectionUtil;
import com.utils.Constants;
import com.utils.DateUtil;
import com.utils.convert.Convert;
import com.web.vo.CateItemVo;
import com.web.vo.DetailVo;
import com.web.vo.FileStoreInfoVo;
import com.web.vo.FrontCategoryInfoVo;
import com.web.vo.FrontThemeFileVo;
import com.web.vo.TemplateThemeFileVo;
import com.web.vo.ThemeFileVo;

@Service("fileStoreInfoService")
public class FileStoreInfoServiceImpl implements FileStoreInfoService {

	@Autowired
	private CategoryDao categoryDao;
	
	private FileStoreInfoDao fileStoreInfoDao;
	
	private AppAdService appAdService;
	
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	
	@Autowired
	private CountService countService;
	
	public static final long SIZE = 51;

	@Override
	@Cacheable(value = "fileStoreInfoService", key = "'getFileStoreInfoByTopic' + #topicId + '@' + #pageNo")
	public List<FrontThemeFileVo> getFileStoreInfoByTopic(Long topicId,
			Long pageNo) {
		List<FileStoreInfo> fileStoreInfos = fileStoreInfoDao
				.getByTopicWithPage(topicId, (pageNo - 1) * SIZE, SIZE);
		return convertSimplifyFront(fileStoreInfos);
	}
	
	@Override
	@Cacheable(value = "fileStoreInfoService", key = "'getFileStoreInfoByTemplate' + #templateId + '@' + #pageNo")
	public List<TemplateThemeFileVo> getFileStoreInfoByTemplate(Long templateId,
			Long pageNo) {
		List<FileStoreInfo> fileStoreInfos = fileStoreInfoDao
				.getByTemplateWithPage(templateId, (pageNo - 1) * SIZE, SIZE);
		return convertSimplifyTemplate(fileStoreInfos);
	}

	private List<TemplateThemeFileVo> convertSimplifyTemplate(
			List<FileStoreInfo> fileStoreInfos) {
		List<TemplateThemeFileVo> themeFileVos = Lists.newArrayList();
		if (CollectionUtil.isNotBlank(fileStoreInfos)) {
			for(FileStoreInfo vo : fileStoreInfos){
				TemplateThemeFileVo theme = BeanMapper.map(vo.getTheme(), TemplateThemeFileVo.class);
				theme.setIconPath(Constants.STATIC_SHOW + "/files/" + theme.getIconPath());
				theme.setUxPath(Constants.STATIC_SHOW + "/files/" + theme.getUxPath());
				themeFileVos.add(theme);
			}
		}
		return themeFileVos;
	}

	@Override
	@Cacheable(value = "fileStoreInfoService", key = "'getHomePage' + #gender")
	public List<FrontThemeFileVo> getHomePage(String gender) {
		// return findPage(page, Q_SHELF_THEME_STORE, st, sid, sid, lang);
		List<FileStoreInfo> fileStoreInfos = fileStoreInfoDao
				.getHomeByGender(DataDict.getGender(gender));
		return convertSimplifyFront(fileStoreInfos);
	}

//	private List<FrontFileStoreInfoVo> convertFront(
//			List<FileStoreInfo> fileStoreInfos) {
//		List<FrontFileStoreInfoVo> fileStoreInfoVos = Lists.newArrayList();
//		if (CollectionUtil.isNotBlank(fileStoreInfos)) {
//			fileStoreInfoVos = BeanMapper.mapList(fileStoreInfos,
//					new Convert<FrontFileStoreInfoVo>() {
//
//						@Override
//						public void convert(Object source,
//								FrontFileStoreInfoVo destinationObject) {
//							FileStoreInfo fileStoreInfo = (FileStoreInfo) source;
//							destinationObject.setTheme(BeanMapper.map(
//									fileStoreInfo.getTheme(), FrontThemeFileVo.class));
//						}
//					});
//		}
//		return fileStoreInfoVos;
//	}

	private List<FileStoreInfoVo> convert(List<FileStoreInfo> fileStoreInfos) {
		List<FileStoreInfoVo> fileStoreInfoVos = Lists.newArrayList();
		if (CollectionUtil.isNotBlank(fileStoreInfos)) {
			fileStoreInfoVos = BeanMapper.mapList(fileStoreInfos,
					new Convert<FileStoreInfoVo>() {

						@Override
						public void convert(Object source,
								FileStoreInfoVo destinationObject) {
							FileStoreInfo fileStoreInfo = (FileStoreInfo) source;
							destinationObject.setTheme(BeanMapper.map(
									fileStoreInfo.getTheme(), ThemeFileVo.class));
						}
					});
		}
		return fileStoreInfoVos;
	}

	@Autowired
	public void setFileStoreInfoDao(FileStoreInfoDao fileStoreInfoDao) {
		this.fileStoreInfoDao = fileStoreInfoDao;
	}

	@Override
	@Cacheable(value = "fileStoreInfoService", key = "'getStoreInfo' + #id")
	public FileStoreInfoVo getStoreInfo(String id) {
		FileStoreInfo fileStoreInfo = fileStoreInfoDao.get(id);
		FileStoreInfoVo fileStoreInfoVo = BeanMapper.map(fileStoreInfo,	FileStoreInfoVo.class);
		if (fileStoreInfoVo != null) {
			fileStoreInfoVo.setTheme(BeanMapper.map(fileStoreInfo.getTheme(), ThemeFileVo.class));
		}
		return fileStoreInfoVo;
	}
	
	@Override
	@Cacheable(value = "fileStoreInfoService", key = "'getDetailStoreInfo' + #id")
	public DetailVo getDetailStoreInfo(String id) {
		FileStoreInfo fileStoreInfo = fileStoreInfoDao.get(id);
		if(fileStoreInfo == null){
			return null;
		}
		DetailVo detailVo = convertToDetail(fileStoreInfo);
		return detailVo;
	}

	private DetailVo convertToDetail(FileStoreInfo fileStoreInfo) {
		DetailVo vo = new DetailVo();
		vo.setAuthor(fileStoreInfo.getAuthor());
		vo.setDescr(fileStoreInfo.getLongDescription());
		vo.setLabel(fileStoreInfo.getShortDescription());
		vo.setTitle(fileStoreInfo.getTitle());
		ThemeFile theme = fileStoreInfo.getTheme();
		if(theme != null){
			vo.setId(theme.getId() + "");
			vo.setName(theme.getName());
			vo.setDtype(theme.getDtype());
			vo.setIcon(Constants.STATIC_SHOW + "/files/" + theme.getIconPath());
			vo.setLockedPreView(Constants.STATIC_SHOW + "/files/" + theme.getPreWebPath());
			vo.setUnlockedPreView(Constants.STATIC_SHOW + "/files/" + theme.getPreClientPath());
			if(StringUtils.isNotBlank(fileStoreInfo.getTheme().getUxWvga())){
				vo.setDownloadUxUrl(Constants.STATIC_SHOW + "/files/" + fileStoreInfo.getTheme().getUxWvga());
				vo.setUxSize(fileStoreInfo.getTheme().getUxSize());
			}
			StringBuilder downloadUrl = new StringBuilder(Constants.STATIC_SHOW + "/files/");
			if (theme.getDtype().equals(Constants.DIY_LOCK)) {
				downloadUrl.append(fileStoreInfo.getTheme().getUxPath());
				vo.setSize(fileStoreInfo.getTheme().getUxSize());
			} else {
				downloadUrl.append(fileStoreInfo.getTheme().getApkPath());
				vo.setSize(fileStoreInfo.getTheme().getApkSize());
			}
			vo.setDownloadUrl(downloadUrl.toString());
		}
		return vo;
	}

	@Override
	@Cacheable(value = "fileStoreInfoService", key = "'getStoreInfosInShelf1' + #category")
	public List<FileStoreInfoVo> getStoreInfosInShelf(CateItemVo category) {
		List<FileStoreInfo> fileStoreInfos = null;
		if (category.getDescription().contains("other")) {
			if (category.getName().contains("游戏")) {
				fileStoreInfos = fileStoreInfoDao
						.getInShelf(Constants.Type.GAME.getValue());
			} else if (category.getName().contains("推荐")) {
				fileStoreInfos = fileStoreInfoDao.getInShelf(Constants.Type.APP
						.getValue());
			}
		} else {
			fileStoreInfos = fileStoreInfoDao.getByCategory(category.getId());
		}
		return convert(fileStoreInfos);
	}

	@Override
	@Cacheable(value = "fileStoreInfoService", key = "'getStoreInfosInShelf2' + #type")
	public List<FileStoreInfoVo> getStoreInfosInShelf(String type) {
		List<FileStoreInfo> fileStoreInfos = fileStoreInfoDao.getInShelf(type);
		return convert(fileStoreInfos);
	}

	@Override
	public List<FileStoreInfoVo> shuffInfos(List<FileStoreInfoVo> originalInfos) {
		List<FileStoreInfoVo> correct = Lists.newArrayList();
		Collections.shuffle(originalInfos);
		FileStoreInfoVo firstWeight = getWeightInfo(originalInfos);
		correct.add(firstWeight);
		originalInfos.remove(firstWeight);
		FileStoreInfoVo secondWeight = getWeightInfo(originalInfos);
		correct.add(secondWeight);
		return correct;
	}

	private FileStoreInfoVo getWeightInfo(List<FileStoreInfoVo> infos) {
		int random = getRandom(getSum(infos));
		long weight = 0;
		for (FileStoreInfoVo info : infos) {
			weight += info.getTheme().getPercent();
			if (weight >= random) {
				return info;
			}
		}
		return null;
	}

	private int getSum(List<FileStoreInfoVo> infos) {
		int sum = 0;
		for (FileStoreInfoVo info : infos) {
			sum += info.getTheme().getPercent();
		}
		return sum;
	}

	private int getRandom(int seed) {
		return (int) Math.round(Math.random() * seed);
	}

	@Override
	@Cacheable(value = "fileStoreInfoService", key = "'getShelfByPage' + #gender + '@' + #page")
	public List<FrontThemeFileVo> getShelfByPage(String gender, Long page) {
		String type = DataDict.getGender(gender);
		List<FileStoreInfo> fileStoreInfos = fileStoreInfoDao
				.getByGenderWithPage(type, (page - 1) * 51, 51l);
		return convertSimplifyFront(fileStoreInfos);
	}
	
	@Override
	@Cacheable(value = "fileStoreInfoService", key = "'getHottestByCategory' + #categoryId + '@' + #page")
	public List<FrontThemeFileVo> getHottestByCategory(String categoryId,
			Long page) {
		String edate = com.utils.DateUtil.format(new Date(), "yyyy-MM-dd");
		String sdate = DateUtil.format(
				com.utils.DateUtil.getPerWeekDate(new Date()), "yyyy-MM-dd");
		List<FileStoreInfo> fileStoreInfos = fileStoreInfoDao
				.getByCategoryWithHottestPage(categoryId, sdate, edate, (page - 1) * SIZE, SIZE);
		return convertSimplifyFront(fileStoreInfos);
	}

	@Override
	@Cacheable(value = "fileStoreInfoService", key = "'getHottestPage' + #gender + '@' + #page")
	public List<FrontThemeFileVo> getHottestPage(String gender, Long page) {
		String edate = com.utils.DateUtil.format(new Date(), "yyyy-MM-dd");
		String sdate = DateUtil.format(
				com.utils.DateUtil.getPerWeekDate(new Date()), "yyyy-MM-dd");
		List<FileStoreInfo> fileStoreInfos = fileStoreInfoDao
				.getByHottestWithPage(sdate, edate, (page - 1) * SIZE, SIZE);
		return convertSimplifyFront(fileStoreInfos);
//		return mixThemeFilesWithAppAds(themeFiles, (page - 1) * SIZE, SIZE);
	}
	
//	private List<FrontThemeFileVo> mixThemeFilesWithAppAds(List<FrontThemeFileVo> themeFiles, Long startIndex, Long pageSize) {
//		return appAdService.mixThemeFilesWithAppAds(themeFiles, startIndex, SIZE, "hottest");
//	}

	private List<FrontThemeFileVo> convertSimplifyFront(
			List<FileStoreInfo> fileStoreInfos) {
		return FrontThemeFileVo.convertSimplifyFront(fileStoreInfos);
	}

	@Override
	@Cacheable(value = "fileStoreInfoService", key = "'getStoreInfoByCategory' + #categoryId + '@' + #page")
	public List<FrontThemeFileVo> getNewestByCategory(String categoryId,
			Long page) {
		Long pageSize = SIZE;
		Long startIndex = (page - 1) * pageSize;
		List<FileStoreInfo> fileStoreInfos = fileStoreInfoDao
				.getByCategoryWithNewestPage(categoryId, startIndex, pageSize);
		Validate.notNull(fileStoreInfos,
				"cannot find the elements in Category ID: %s at Page.%s",
				categoryId, page);
		return convertSimplifyFront(fileStoreInfos);
	}

//	@Override
//	public String getScrollJson(List<FrontFileStoreInfoVo> infos) {
//		StringBuilder buffer = new StringBuilder();
//		buffer.append("{");
//		if (CollectionUtil.isBlank(infos)) {
//			buffer.append("\"code\":900");
//		} else {
//			buffer.append("\"code\":200");
//		}
//		buffer.append(",\"data\":");
//		buffer.append(JSONArray.toJSONString(infos));
//		buffer.append("}");
//		return buffer.toString();
//	}
	
	@Override
	public String getSimplifyScrollJson(List<FrontThemeFileVo> infos) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("{");
		if (CollectionUtil.isBlank(infos)) {
			buffer.append("\"code\":900");
		} else {
			buffer.append("\"code\":200");
		}
		buffer.append(",\"data\":");
		buffer.append(JSONArray.toJSONString(infos));
		buffer.append("}");
		return buffer.toString();
	}


	@Override
	@Cacheable(value = "fileStoreInfoService", key = "'getFileStoreInfoByTypeWithPage' + #type + '@' + #page")
	public List<FileStoreInfoVo> getFileStoreInfoByTypeWithPage(String type,
			Long page) {
		List<FileStoreInfo> fileStoreInfos = fileStoreInfoDao
				.getByGenderWithPage(type, (page - 1) * SIZE, SIZE);
		return convert(fileStoreInfos);
	}

	@Override
	@Cacheable(value = "fileStoreInfoService", key = "'getShuffleFileStoreByCategory' + #categoryID + '@' + #current.id + '@' + #num")
	public List<FileStoreInfoVo> getShuffleFileStoreByCategory(
			String categoryID, FileStoreInfoVo current, int num) {
		List<FileStoreInfoVo> total = getFileStoreByCategory(categoryID);
		List<FileStoreInfoVo> result = Lists.newArrayListWithCapacity(num);
		if(total != null && total.size() > 0){
			total.remove(current);
			if(total.size() <= num){
				result.addAll(total);
			}else {
				Collections.shuffle(total);
				for(int i=0; i<num; i++){
					result.add(total.get(i));
				}
			}
		}
		return result;
	}
	
	@Override
	@Cacheable(value = "fileStoreInfoService", key = "'getShuffleFileStoreByCategory2' + #categoryID + '@' + #current.id + '@' + #num")
	public List<DetailVo> getShuffleFileStoreByCategory(String categoryID,
			DetailVo current, int num) {
		List<DetailVo> total = getFileStoreByCategory2(categoryID);
		List<DetailVo> result = Lists.newArrayListWithCapacity(num);
		if(total != null && total.size() > 0){
			total.remove(current);
			if(total.size() <= num){
				result.addAll(total);
			}else {
				Collections.shuffle(total);
				for(int i=0; i<num; i++){
					result.add(total.get(i));
				}
			}
		}
		return result;
	}

	@Override
	@Cacheable(value = "fileStoreInfoService", key = "'getFileStoreByCategory' + #categoryID")
	public List<FileStoreInfoVo> getFileStoreByCategory(String categoryID) {
		List<FileStoreInfo> fileStoreInfos = fileStoreInfoDao.getByCategory(categoryID);
		return convert(fileStoreInfos);
	}
	
	private List<DetailVo> getFileStoreByCategory2(String categoryID) {
		List<FileStoreInfo> fileStoreInfos = fileStoreInfoDao.getByCategory(categoryID);
		return convertToDetail(fileStoreInfos);
	}

	private List<DetailVo> convertToDetail(List<FileStoreInfo> fileStoreInfos) {
		if(CollectionUtil.isBlank(fileStoreInfos)){
			return null;
		}
		List<DetailVo> detailVos = Lists.newArrayListWithCapacity(fileStoreInfos.size());
		for(FileStoreInfo fileStoreInfo : fileStoreInfos){
			detailVos.add(convertToDetail(fileStoreInfo));
		}
		return detailVos;
	}

	@Override
	public List<FrontThemeFileVo> getAuthorPage(String realName, Long pageNo) {
		if(StringUtils.isBlank(realName)){
			return null;
		}
		List<FileStoreInfo> fileStoreInfos = fileStoreInfoDao.getByAuthorWithPage(realName, (pageNo - 1) * SIZE, SIZE);
		return convertSimplifyFront(fileStoreInfos);
	}

	@Override
	public String getTemplateScrollJson(
			List<TemplateThemeFileVo> infos) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("{");
		if (infos.size() < SIZE) {
			buffer.append("\"code\":900");
		} else {
			buffer.append("\"code\":200");
		}
		buffer.append(",\"data\":");
		buffer.append(JSONArray.toJSONString(infos));
		buffer.append("}");
		return buffer.toString();
	}

	@Autowired
	public void setAppAdService(AppAdService appAdService) {
		this.appAdService = appAdService;
	}

	@Override
	public Map<String,List<FrontThemeFileVo>> getNewHomePageFile() {
		Map<String,List<FrontThemeFileVo>> resultMap = new HashMap<String,List<FrontThemeFileVo>>();
		String[] category = Constants.CATEGORY_LIST.split(",");
		for (String item : category) {
			List<FileStoreInfo> fsiList = fileStoreInfoDao.getByCategory(item);	
			List<FrontThemeFileVo> frontThemeFileVoList = null;
			if (fsiList.size() > 29) {
				frontThemeFileVoList = convertSimplifyFront(fsiList).subList(0, 30);
			} else {
				frontThemeFileVoList = convertSimplifyFront(fsiList);
			}
			for (FrontThemeFileVo vo : frontThemeFileVoList) {
				vo.setDownloadTimes(countService.countTotalDownload(vo.getTitle()));
			}
			Collections.shuffle(frontThemeFileVoList);
			redisTemplate.opsForHash().put("ums3_locker_category", item, JSONObject.toJSONString(frontThemeFileVoList));
			redisTemplate.expire("ums3_locker_category", this.getTodayLeftExpires(), TimeUnit.SECONDS);
			resultMap.put(item, frontThemeFileVoList);
		}
		return resultMap;
	}
	
	@Override
	public List<FrontCategoryInfoVo> getNewHomePage(Integer num) {
		String[] categoryArray = Constants.CATEGORY_LIST.split(",");
		List<FrontCategoryInfoVo> frontCategoryInfoVoList = new ArrayList<FrontCategoryInfoVo>();
		Map<Object, Object> resultMap =  redisTemplate.opsForHash().entries("ums3_locker_category");
		if (!resultMap.isEmpty()) {
			for (String item : categoryArray) {
				String ftfListJson = (String) redisTemplate.opsForHash().get("ums3_locker_category", item);
				List<FrontThemeFileVo> frontThemeFileVoList = JSONObject.parseArray(ftfListJson, FrontThemeFileVo.class);
				frontCategoryInfoVoList.add(this.setFrontCategoryInfoVo(frontThemeFileVoList, item, num));
				
			}
		} else {
			Map<String, List<FrontThemeFileVo>> ftfmap = this.getNewHomePageFile();
			for (String item : categoryArray) {
				List<FrontThemeFileVo> frontThemeFileVoList = ftfmap.get(item);
				frontCategoryInfoVoList.add(this.setFrontCategoryInfoVo(frontThemeFileVoList, item, num));
			}
		}
		return frontCategoryInfoVoList;
	}
	
	private Long getTodayLeftExpires() {
		Calendar cal = Calendar.getInstance(); 
		cal.set(Calendar.HOUR_OF_DAY, 24); 
		cal.set(Calendar.SECOND, 0); 
		cal.set(Calendar.MINUTE, 0); 
		cal.set(Calendar.MILLISECOND, 0); 
		return (cal.getTime().getTime() - new Date().getTime()) / 1000;
	}
	
	private FrontCategoryInfoVo setFrontCategoryInfoVo(List<FrontThemeFileVo> frontThemeFileVoList, String type, Integer num) {
		FrontCategoryInfoVo frontCategoryInfoVo = new FrontCategoryInfoVo();
		List<FrontThemeFileVo> subFrontThemeFileVoList = new ArrayList<FrontThemeFileVo>();
		if (frontThemeFileVoList.size() >= 3*num) {
			subFrontThemeFileVoList = frontThemeFileVoList.subList(3*num - 3, 3*num);
		} else {
			Random random = new Random();
			int r = random.nextInt(frontThemeFileVoList.size()-3);
			subFrontThemeFileVoList = frontThemeFileVoList.subList(r, r+3);
		}
		frontCategoryInfoVo.setFrontThemeFileVoList(subFrontThemeFileVoList);
		frontCategoryInfoVo.setType(type);
		Category category = categoryDao.get(type);
		frontCategoryInfoVo.setDescription(category.getDescription());
		frontCategoryInfoVo.setName(category.getName());
		frontCategoryInfoVo.setValue(category.getValue());
		return frontCategoryInfoVo;
		
	}


}
