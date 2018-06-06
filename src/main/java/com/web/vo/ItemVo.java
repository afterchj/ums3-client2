package com.web.vo;

import java.io.Serializable;
import java.util.Date;

//1	id	string	32	no	主键ID    uuid
//2	name	string	32		物品名称
//3	remarks	string	500		物品备注
//4	stock	int			库存
//5	sales	int			销售额
//6	expiryDate				商品有效期
//7	descr				商品描述
//8	instruction				商品使用说明
//9	shelf				货架ID
//10	price				展示价格


public class ItemVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String remarks;
	private Integer stock;
	private Integer sales;
	private Date expiryDate;
	private String descr;
	private String instructions;
	private String shelf;
	private Integer price;

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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getShelf() {
		return shelf;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
