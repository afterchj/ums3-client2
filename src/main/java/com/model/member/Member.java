package com.model.member;

import java.io.Serializable;
import java.util.Date;


//1	id	varchar	32	no	主键ID    uuid
//2	login_name	varchar	50	no	登陆名称
//3	descr	varchar	150	yes	描述(50字以内)
//4	create_date	date		no	创建时间
//5	login_date	date 		no	上次登陆时间
//6	nickname	varchar	50	no	昵称
//7	info1	varchar	255	yes	信息1
//8	info2	varchar	255	yes	信息2
//9	type	varchar	50	yes	登陆方式: QQ, 微博...
//10	status	integer		no	111  禁止登陆  禁止发帖 禁止发炎
//11	visitor	long		yes	作品广场被访问数
//12	gender	integer		yes	性别
//13	prov	varchar		yes	省份
//14	birthday	char	10	yes	生日
//15	qq	varchar	20	yes	QQ
//16	name	varchar	20	yes	真实姓名
//17	email	varchar	320	yes	邮箱
//18	mobile	varchar	20	yes	手机号码

//@Entity
//@Table(name="member_user")
public class Member implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String loginName;
	private String descr;
	private Date createDate;
	private Date loginDate;
	private String nickname;
	private String info1;
	private String info2;
	private String type;
	//11   禁止发言  禁止发帖 
	private Integer allowComment;
	private Integer allowPost;
	private Long visitor;
	private Integer gender;
	private String birthday;
	private String qq;
	private String realName;
	private String email;
	private String mobile;
	private String pic;
	private String uid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getInfo1() {
		return info1;
	}

	public void setInfo1(String info1) {
		this.info1 = info1;
	}

	public String getInfo2() {
		return info2;
	}

	public void setInfo2(String info2) {
		this.info2 = info2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getVisitor() {
		return visitor;
	}

	public void setVisitor(Long visitor) {
		this.visitor = visitor;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getAllowComment() {
		return allowComment;
	}

	public void setAllowComment(Integer allowComment) {
		this.allowComment = allowComment;
	}

	public Integer getAllowPost() {
		return allowPost;
	}

	public void setAllowPost(Integer allowPost) {
		this.allowPost = allowPost;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
