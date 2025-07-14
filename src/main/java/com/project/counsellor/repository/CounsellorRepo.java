package com.project.counsellor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.counsellor.entities.CounsellorEntity;

@Repository
public interface CounsellorRepo extends JpaRepository<CounsellorEntity, Integer> {

	public CounsellorEntity findByEmailAndPassword(String email, String password);
	public CounsellorEntity findByEmail(String email);
}
