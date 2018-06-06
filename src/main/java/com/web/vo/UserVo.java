package com.web.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapping;

import com.alibaba.fastjson.JSONObject;
import com.mapper.BeanMapper;
import com.tpadsz.uic.user.api.vo.AppUser;
import com.tpadsz.uic.user.api.vo.TpadUser;

//1	id	string	32	no	主键ID    uuid
//2	loginName	string	50	no	登陆名称  天平后台自动分配
//3	descr	string	150	yes	描述(50字以内)
//4	nickname	string	32	no	昵称  默认与loginName相同。
//5	gender	int		yes	性别  0: female 1:male
//6	prov	int		yes	省份
//7	city	int		yes	城市
//8	birthyear	int		yes	出生年份
//9	mobile	string	32	yes	电话号码
//10	legalName	string	32	yes	真实姓名
//11	address	string	255	yes	住址
//12	token	string	32	no	服务端返回， 由客户端保存的登录有效信息。

public class UserVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String loginName;
	private String descr;
	private String nickname;
	private Integer gender;
	private Integer prov;
	private Integer city;
	private Integer birthyear;
	private Integer birthmonth;
	private Integer birthday;
	private String mobile;
	private String legalName;
	private String address;
	private String token;
	private Integer mode;
	private Integer mode2;
	private String label;
	private String communityUid;
	private boolean communityLoginSuc;
	private String icon;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Mapping("serialno")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getProv() {
		return prov;
	}

	public void setProv(Integer prov) {
		this.prov = prov;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getBirthyear() {
		return birthyear;
	}

	public void setBirthyear(Integer birthyear) {
		this.birthyear = birthyear;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static UserVo convert(AppUser user) {
		TpadUser tpadUser = user.getTpadUser();
		UserVo vo = BeanMapper.map(user, UserVo.class);
		vo.setMobile(tpadUser.getMobile());
		vo.setLegalName(tpadUser.getLegalName());
		vo.setAddress(tpadUser.getAddress());
		vo.setBirthyear(tpadUser.getBirthyear());
		vo.setBirthmonth(tpadUser.getBirthmonth());
		vo.setBirthday(tpadUser.getBirthday());
		vo.setCity(tpadUser.getCity());
		vo.setProv(tpadUser.getProv());
		vo.setGender(tpadUser.getGender());
		if(StringUtils.isNotBlank(tpadUser.getInfo1())){
			try {
				JSONObject jsonObj = JSONObject.parseObject(tpadUser.getInfo1());
				if(jsonObj != null){
					vo.setLabel(jsonObj.getString("label"));
				}
			} catch (Exception e) {
			}
		}
		return vo;
	}

	public Integer getBirthmonth() {
		return birthmonth;
	}

	public void setBirthmonth(Integer birthmonth) {
		this.birthmonth = birthmonth;
	}

	public Integer getBirthday() {
		return birthday;
	}

	public void setBirthday(Integer birthday) {
		this.birthday = birthday;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public Integer getMode2() {
		return mode2;
	}

	public void setMode2(Integer mode2) {
		this.mode2 = mode2;
	}

	public String getCommunityUid() {
		return communityUid;
	}

	public void setCommunityUid(String communityUid) {
		this.communityUid = communityUid;
	}

	public boolean isCommunityLoginSuc() {
		return communityLoginSuc;
	}

	public void setCommunityLoginSuc(boolean communityLoginSuc) {
		this.communityLoginSuc = communityLoginSuc;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
