package com.cencl.portal.pojo;

import com.cencl.pojo.TbItem;

/* *
* <p>Title: </p>
* <p>Description: </p>
* <p>Company: Nothing</p>
* @author:  phubing
* @date: 2017年12月19日上午10:26:20
* version 0.0.1
*/
public class ItemInfo extends TbItem {

	public String[] getImages() {
		String image = getImage();
		if (image != null) {
			String[] images = image.split(",");
			return images;
		}
		return null;
	}
}

