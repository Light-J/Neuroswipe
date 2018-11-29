package com.nsa.cubric.application.domain;

public class UserRating {
	private Long id;

	private Integer userProfileId;

	private Integer imageId;

	private Boolean response;

	public UserRating() {
	}

	public UserRating(Long id, Integer userProfileId, Integer imageId, Boolean response) {
		this.id = id;
		this.userProfileId = userProfileId;
		this.imageId = imageId;
		this.response = response;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(Integer userProfileId) {
		this.userProfileId = userProfileId;
	}

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public Boolean getResponse() {
		return response;
	}

	public void setResponse(Boolean response) {
		this.response = response;
	}
}
