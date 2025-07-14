package com.project.counsellor.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.counsellor.dto.CounsellorDto;
import com.project.counsellor.entities.CounsellorEntity;
import com.project.counsellor.repository.CounsellorRepo;

@Service
public class CounsellorServiceImpl implements CounsellorSevice {
	
	@Autowired
	private CounsellorRepo counsellorRepo; 
	

	@Override
	public CounsellorDto login(CounsellorDto counsellorDto) {
		
	 CounsellorEntity counsellorEntity = counsellorRepo.findByEmailAndPassword(counsellorDto.getEmail(), counsellorDto.getPassword());
	  if(counsellorEntity != null) {
		  
//		  copy entity object data into dto object and dto object
		  CounsellorDto dto = new CounsellorDto();
		  BeanUtils.copyProperties(counsellorEntity, dto);
		  
		  return dto; 
	  }
		return null;
	}

	@Override
	public boolean uniqueEmailCheck(String email) {
		
		CounsellorEntity counsellorEntity = counsellorRepo.findByEmail(email);

		return counsellorEntity == null;
	}

	@Override
	public boolean register(CounsellorDto counsellorDto) {
		
//		copy the data from dto to entity
		CounsellorEntity counsellorEntity = new CounsellorEntity();
		BeanUtils.copyProperties(counsellorDto, counsellorEntity);
		
		CounsellorEntity saveEntity = counsellorRepo.save(counsellorEntity);
		
		return saveEntity.getCounsellorId() != null;
	} 

}
