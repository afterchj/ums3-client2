package com.model.member;

import java.io.Serializable;
import java.util.Date;

//1	id	varchar	32	no	主键ID    
//2	member_id	varchar	32	no	作品人ID
//3	nickname	varchar	32	no	作品人昵称
//4	name	varchar	255	yes	作品名称
//6	descr	varchar	255	yes	作品描述(120字)
//7	create_date	timestamp		no	创建时间
//8	status	integer		no	状态   0: 正常  1: 停用
//9	favor	long			被赞次数
//9	visitor	long			被访问数
//10	info1	varchar	255	yes	信息1
//10	info2	varchar	255	yes	信息2
//11	comments	long		yes	总回帖数
//12	shared	varchar	255	yes	是否被共享到.  qq,xinlang, weibo

//@Entity
//@Table(name = "member_work")
public class Work implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String memberId;
	private String nickname;
	private String name;
	private String descr;
	private Date createDate;
	private Integer status;
	private Long favor;
	private Long visitor;
	private String info1;
	private String info2;
	private Long comments;
	private String shared;
	private String memberPic;
	private String uid;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getFavor() {
		return favor;
	}

	public void setFavor(Long favor) {
		this.favor = favor;
	}

	public Long getVisitor() {
		return visitor;
	}

	public void setVisitor(Long visitor) {
		this.visitor = visitor;
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

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

	public String getShared() {
		return shared;
	}

	public void setShared(String shared) {
		this.shared = shared;
	}

	public String getMemberPic() {
		return memberPic;
	}

	public void setMemberPic(String memberPic) {
		this.memberPic = memberPic;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
