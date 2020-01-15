package com.example.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author ${author}
 * @since 2019-08-26
 */
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
	@TableId(value="user_id", type= IdType.AUTO)
	private Long userId;
    /**
     * 用户昵称
     */
	@TableField("user_name")
	private String userName;
    /**
     * 用户密码
     */
	@TableField("user_pwd")
	private String userPwd;
    /**
     * 用户头像
     */
	@TableField("user_avatar")
	private String userAvatar;
    /**
     * 用户类型
     */
	@TableField("user_type")
	private String userType;
    /**
     * 个人简介
     */
	@TableField("user_description")
	private String userDescription;
    /**
     * 性别
     */
	@TableField("user_gender")
	private String userGender;
    /**
     * 注册时间
     */
	private Date registDate;
    /**
     * 趣拍
     */
	@TableField("video_count")
	private Long videoCount;
    /**
     * 关注
     */
	@TableField("follow_count")
	private Long followCount;
    /**
     * 粉丝
     */
	@TableField("fans_count")
	private Long fansCount;
    /**
     * 收藏
     */
	@TableField("collection_count")
	private Long collectionCount;
    /**
     * 喜欢/点赞
     */
	@TableField("like_count")
	private Long likeCount;
    /**
     * 分享
     */
	@TableField("share_count")
	private Long shareCount;
    /**
     * 角色ID
     */
	@TableField("role_id")
	private Long roleId;


	public Long getUserId() {
		return userId;
	}

	public User setUserId(Long userId) {
		this.userId = userId;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public User setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public User setUserPwd(String userPwd) {
		this.userPwd = userPwd;
		return this;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public User setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
		return this;
	}

	public String getUserType() {
		return userType;
	}

	public User setUserType(String userType) {
		this.userType = userType;
		return this;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public User setUserDescription(String userDescription) {
		this.userDescription = userDescription;
		return this;
	}

	public String getUserGender() {
		return userGender;
	}

	public User setUserGender(String userGender) {
		this.userGender = userGender;
		return this;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public User setRegistDate(Date registDate) {
		this.registDate = registDate;
		return this;
	}

	public Long getVideoCount() {
		return videoCount;
	}

	public User setVideoCount(Long videoCount) {
		this.videoCount = videoCount;
		return this;
	}

	public Long getFollowCount() {
		return followCount;
	}

	public User setFollowCount(Long followCount) {
		this.followCount = followCount;
		return this;
	}

	public Long getFansCount() {
		return fansCount;
	}

	public User setFansCount(Long fansCount) {
		this.fansCount = fansCount;
		return this;
	}

	public Long getCollectionCount() {
		return collectionCount;
	}

	public User setCollectionCount(Long collectionCount) {
		this.collectionCount = collectionCount;
		return this;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public User setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
		return this;
	}

	public Long getShareCount() {
		return shareCount;
	}

	public User setShareCount(Long shareCount) {
		this.shareCount = shareCount;
		return this;
	}

	public Long getRoleId() {
		return roleId;
	}

	public User setRoleId(Long roleId) {
		this.roleId = roleId;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.userId;
	}

}
