package com.jcg.springmvc.mongo;

import java.io.Serializable;
public class Todo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id, label,description,status;

	public Todo() {
		super();
	}

	public Todo(String id, String label, String description,String status) {
		super();
		this.id = id;
		this.setLabel(label);
		this.setDescription(description);
		this.setStatus(status);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}