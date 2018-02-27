package com.cencl.portal.pojo;

/* *
* <p>Title: 购物车pojo</p>
* <p>Description: </p>
* <p>Company: Nothing</p>
* @author:  phubing
* @date: 2017年12月22日上午11:03:53
* version 0.0.1
*/
public class CartItem {
	private long id;
	private String title;
	private Integer num;
	private long price;
	private String image;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
