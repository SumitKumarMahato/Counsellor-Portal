package com.project.counsellor.service;

import java.util.List;

import com.project.counsellor.dto.DashboardResponseDto;
import com.project.counsellor.dto.EnqFilterDto;
import com.project.counsellor.dto.EnquiryDto;

public interface EnquiryService {

	//show dashboard of logged in counsellor
	
	public DashboardResponseDto getDashboardInfo(Integer counsellorId);
	
	//add enquiries
	public boolean addEnquiry(EnquiryDto enqDto, Integer counsellorId);
	
	//display all the enquiries of the logged in counsellor
	public List<EnquiryDto> getEnquiries(Integer counsellorId);
	
	//display enquiries based on filters
	
	public List<EnquiryDto> getEnquiries(EnqFilterDto filterDto,Integer counsellorId);
	
	//to edit the enquiries
	public EnquiryDto getEnquiryById(Integer enqId);
	
	//to update enquiry
	public boolean updateEnquiry(Integer enqId,EnquiryDto enqDto,Integer counsellorId);
	
	
	//to delete enquiry
	public boolean deleteEnquiryById(Integer enqId);

	
}
