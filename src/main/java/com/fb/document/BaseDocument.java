package com.fb.document;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

public class BaseDocument {

	
	@CreatedDate
	private DateTime createdOn;

	@LastModifiedDate
	private DateTime lastModifiedOn;

	public DateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(DateTime createdOn) {
		this.createdOn = createdOn;
	}

	public DateTime getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(DateTime lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

}
