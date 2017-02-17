package com.cassan.circlefriend.bean;

import java.io.Serializable;

import cn.cassan.pm.model.UserInfo;

/**
 * 
* @ClassName: CommentItem 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yiw
* @date 2015-12-28 下午3:44:38 
*
 */
public class CommentItem implements Serializable{

	private String id;
	private UserInfo user;
	private UserInfo toReplyUser;
	private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
	public UserInfo getToReplyUser() {
		return toReplyUser;
	}
	public void setToReplyUser(UserInfo toReplyUser) {
		this.toReplyUser = toReplyUser;
	}
	
}
