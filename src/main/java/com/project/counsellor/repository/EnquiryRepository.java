package com.project.counsellor.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.counsellor.entities.EnquiryEntity;

public interface EnquiryRepository extends JpaRepository<EnquiryEntity, Integer> {

	public List<EnquiryEntity> findByCounsellorCounsellorId(Integer counsellorID);
}
