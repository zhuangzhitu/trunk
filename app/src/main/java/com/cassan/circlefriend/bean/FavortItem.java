package com.cassan.circlefriend.bean;

import java.io.Serializable;

import cn.cassan.pm.model.UserInfo;

/**
 * 
* @ClassName: FavortItem 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yiw
* @date 2015-12-28 下午3:44:56 
*
 */
public class FavortItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private UserInfo user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
	
}
