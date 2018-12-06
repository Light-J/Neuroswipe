package com.nsa.cubric.application.domain;

public class UserRating {
	private Long id;

	private Long userProfileId;

	private Integer scanId;

	private Boolean response;

	public UserRating() {
	}

	public UserRating(Long id, Long userProfileId, Integer scanId, Boolean response) {
		this.id = id;
		this.userProfileId = userProfileId;
		this.scanId = scanId;
		this.response = response;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	public Integer getScanId() {
		return scanId;
	}

	public void setScanId(Integer scanId) {
		this.scanId = scanId;
	}

	public Boolean getResponse() {
		return response;
	}

	public void setResponse(Boolean response) {
		this.response = response;
	}
}
