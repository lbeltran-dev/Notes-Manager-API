package com.blueoptima.work.sample.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "note")
@Getter
@Setter
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty
	@NotNull(message = "Title field must not be null")
	@NotBlank(message = "Title must not be empty")
	private String title;

	@JsonProperty
	@NotNull(message = "Content field must not be null")
	@NotBlank(message = "Content must not be empty")
	private String content;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;

	public Note() {

	}

	public Note(Long id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}

	// Printing method
	public String toString() {
		String returnString = "id: " + this.getId() + "\ntitle: " + this.getTitle() + "\ncontent: " + this.getContent()
				+ "\ncreatedAt: " + this.getCreatedAt().toString() + "\nupdatedAt: " + this.getUpdatedAt().toString()
				+ "\n----------------";
		return returnString;
	}

}
