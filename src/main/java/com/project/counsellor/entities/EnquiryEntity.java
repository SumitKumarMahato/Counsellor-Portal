package com.project.counsellor.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "enquiry_table")

public class EnquiryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enqId;
	private String studentName;
	private String studentPhno;
	private String classMode; 
	private String course;
	private String enqStatus;
	
	
	@ManyToOne
	@JoinColumn(name = "counsellor_id")
	private CounsellorEntity counsellor;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
