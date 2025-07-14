package com.project.counsellor.service;

import com.project.counsellor.dto.CounsellorDto;

public interface CounsellorSevice {
	//login
	public CounsellorDto login(CounsellorDto counsellorDto);
	
	//check duplicate email
	public boolean uniqueEmailCheck(String email);
	
	//register counsellor
	public boolean register(CounsellorDto counsellorDto);
	
}
