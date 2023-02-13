package com.reporter.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EfaxResponse {

	public class TaskItemResponse {
	  @JsonProperty(required = true)
	  private String fax_id;

	  @JsonProperty(required = true)
	  private String fax_status;

	  @JsonProperty(required = true)
	  private String status;

	  @JsonProperty(value = "is_important", required = true)
	  private boolean isImportant;

	  @JsonProperty(value = "date", required = true)
	  private LocalDateTime createdDate;

	  @JsonProperty(value = "user", required = true)
	  private String userId;


	  // Getters and setters here...
	}
}
