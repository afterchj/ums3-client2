package com.web.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tpadsz.ctc.vo.Task;

//1	id	string	32	no	主键ID    uuid
//2	name	string	32		任务名称
//3	descr	string	500		任务描述
//4	prize	[{"id":"物品id", "num":"数量"},{}]			任务完成后获得物品
//5	cost	[{"id":"物品id", "num":"数量"},{}]			任务需要消耗的物品
//6	comsume				执行任务需要消耗的积分
//7	gain				任务完成后获得的积分
//8	startTime				任务开始时间
//9	endTime				任务结束时间
//10	repeated				任务可以多次提交的次数。
//11	data	jsonArray			cpa, cpc任务时使用， 其中存放需要在客户端自行选择的内容。
//（type为005时， 参考D.c CPA表）
//（type为006时， 参考D.d CPC表）
//12	times				单个任务必须完成times次后才可以提交。
//13	type				001: 签到  002：分享到QQ空间
//003:分享到微信朋友圈 004:CPA
//005:CPC 006:抢红包
//007: 送奖品
//14	displayTime				任务展示给用户的时间
//15	limitOfShow				任务展示次数限制

public class TaskVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String descr;
	private Integer consume;
	private Integer gain;
	private Date startTime;
	private Date endTime;
	private Integer repeated;
	private List<Map> data;
	private String type;
	private Integer times;
	private Date displayTime;
	private Integer limitOfShow;
	private Map<String, Object> config;

	public static TaskVo convert(Task task) {
		TaskVo vo = new TaskVo();
		vo.setConsume(task.getConsume());
		vo.setId(task.getId());
		vo.setName(task.getName());
		vo.setDescr(task.getDescr());
		vo.setGain(task.getGain());
		vo.setStartTime(task.getStartTime());
		vo.setEndTime(task.getEndTime());
		vo.setRepeated(task.getRepeated());
		vo.setData(task.getData());
		vo.setType(task.getType());
		vo.setTimes(task.getTimes());
		vo.setDisplayTime(task.getDisplayTime());
		vo.setLimitOfShow(task.getLimitOfShow());
		vo.setConfig(task.getConfig());
		return vo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getConsume() {
		return consume;
	}

	public void setConsume(Integer consume) {
		this.consume = consume;
	}

	public Integer getGain() {
		return gain;
	}

	public void setGain(Integer gain) {
		this.gain = gain;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getRepeated() {
		return repeated;
	}

	public void setRepeated(Integer repeated) {
		this.repeated = repeated;
	}

	public List<Map> getData() {
		return data;
	}

	public void setData(List<Map> data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Date getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(Date displayTime) {
		this.displayTime = displayTime;
	}

	public Integer getLimitOfShow() {
		return limitOfShow;
	}

	public void setLimitOfShow(Integer limitOfShow) {
		this.limitOfShow = limitOfShow;
	}

	public Map<String, Object> getConfig() {
		return config;
	}

	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}

}
