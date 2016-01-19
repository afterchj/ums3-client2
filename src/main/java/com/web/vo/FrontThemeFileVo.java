package com.web.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.mapper.BeanMapper;
import com.model.FileStoreInfo;
import com.model.ThemeFile;
import com.utils.CollectionUtil;
import com.utils.Constants;
import com.utils.convert.Convert;

public class FrontThemeFileVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String title;
	private String iconPath;
	private Integer ishot;
	private Integer isnew;
	private Integer dtype;
	private String downloadTimes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static List<FrontThemeFileVo> convertSimplifyFront(
			List<FileStoreInfo> fileStoreInfos) {
		List<FrontThemeFileVo> fileStoreInfoVos = Lists.newArrayList();
		if (CollectionUtil.isNotBlank(fileStoreInfos)) {
			for (FileStoreInfo vo : fileStoreInfos) {
				FrontThemeFileVo theme = convert(vo.getTheme());
				fileStoreInfoVos.add(theme);
			}
		}
		return fileStoreInfoVos;
	}

	public static FrontThemeFileVo convert(ThemeFile themeFile) {
		FrontThemeFileVo themeVo = BeanMapper.map(themeFile,
				FrontThemeFileVo.class);
		themeVo.setIconPath(Constants.STATIC_SHOW + "/files/"
				+ themeVo.getIconPath());
		return themeVo;
	}
	
	public static List<FrontThemeFileVo> convert(Collection<ThemeFile> themeFiles) {
		return BeanMapper.mapList(themeFiles, new Convert<FrontThemeFileVo>() {

			@Override
			public void convert(Object source,
					FrontThemeFileVo destinationObject) {
				ThemeFile themeFile = (ThemeFile)source;
				destinationObject.setIconPath(Constants.STATIC_SHOW + "/files/"
						+ themeFile.getIconPath());
			}
		});
	}

	public Integer getIshot() {
		return ishot;
	}

	public void setIshot(Integer ishot) {
		this.ishot = ishot;
	}

	public Integer getIsnew() {
		return isnew;
	}

	public void setIsnew(Integer isnew) {
		this.isnew = isnew;
	}

	public Integer getDtype() {
		return dtype;
	}

	public void setDtype(Integer dtype) {
		this.dtype = dtype;
	}

	public String getDownloadTimes() {
		return downloadTimes;
	}

	public void setDownloadTimes(String downloadTimes) {
		this.downloadTimes = downloadTimes;
	}

}
